package com.frewen.android.demo.business.samples.view.custom.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

/**
 * @version 1.0
 *         我们来自己实现一个RecyclerView的实现，来真正体验一下自定义容器View的魅力
 *         同时我们来学习一下RecyclerView的复用机制、适配器模式、LayoutManager、测量问题、渲染问题、重复Item的加载问题
 *         缓存等架构思想。
 * @filename: AuraRecyclerView
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/9/28 13:58
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class AuraRecyclerView extends ViewGroup {
    private static final String TAG = "AuraRecyclerView";
    /**
     * RecyclerView持有mAdapter对象
     */
    private AuraAdapter mAdapter;
    /**
     * RecyclerView的回收池的管理类
     */
    final AuraRecycler mRecycler = new AuraRecycler();

    private boolean needRelayout = true;
    private int width;
    private int height;
    private int rowCount;
    private int[] heights;

    /**
     * RecyclerView刚加载的时候其实是最耗性能的逻辑。
     * 之后RecyclerView利用类似传送带的原理，永远只有用户看到的数据才会被加载到内存
     * 随着滑动传送带源源不断的传送数以万计的货物。
     * 而RecyclerView也可以加载数以万计的Item
     */
    public AuraRecyclerView(Context context) {
        this(context, null);
    }

    /**
     * 第二步：我需要来研究AuraRecyclerView是怎么去控制第一屏的Item的加载（比如8个Item）
     * 这个也是最耗性能的逻辑
     *
     * @param context
     * @param attrs
     */
    public AuraRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AuraRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public AuraRecyclerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        defaultOnMeasure(widthMeasureSpec, heightMeasureSpec);
        return;
    }

    /**
     * 默认的RecyclerView的onMeasure的回调处理
     */
    private void defaultOnMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = MeasureSpecUtils.chooseSize(widthMeasureSpec,
                getPaddingLeft() + getPaddingRight(),
                ViewCompat.getMinimumWidth(this));
        final int height = MeasureSpecUtils.chooseSize(heightMeasureSpec,
                getPaddingTop() + getPaddingBottom(),
                ViewCompat.getMinimumHeight(this));
        setMeasuredDimension(width, height);
    }

    /**
     * 第一步：我们要知道自定义的ViewGroup要重启onLayout。
     * 为什么要重写onLayout呢。因为他要摆放自己的子View啊
     *
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (needRelayout || changed) {
            needRelayout = false;
            /// 调用具体实现
            dispatchLayout(changed, left, top, right, bottom);
        }

    }

    /**
     * dispatchLayout是onLayout回调的具体实现
     */
    private void dispatchLayout(boolean changed, int left, int top, int right, int bottom) {
        if (mAdapter == null) {
            Log.e(TAG, "No adapter attached; skipping layout");
            return;
        }

        addChildViewInternal(changed, left, top, right, bottom);
    }

    /**
     * TODO 后续这个方法我们需要替换掉
     * 这个是我们自己实现RecyclerView的内存实现机制
     */
    private void addChildViewInternal(boolean changed, int left, int top, int right, int bottom) {
        /// 我们根据View布局的四个坐标我们来计算当前View的真实的宽度和高度
        width = right - left;
        height = bottom - top;
        /// 这是所有的RecyclerView的Item的数量
        rowCount = mAdapter.getItemCount();
        // 用来存储每个Item的高度的数组
        heights = new int[rowCount];

        for (int i = 0; i < rowCount; i++) {
            heights[i] = mAdapter.getItemHeight(i);
        }

        // RecyclerView的View怎么来控制第一屏幕的绘制。
        // 主要是根据屏幕的高度，来判断多少个Item可以占满屏幕高度
        int itemLift, itemTop = 0, itemRight, itemBottom;
        for (int i = 0; i < rowCount && itemTop < height; i++) {
            itemBottom = itemTop + heights[i];
            // addView();
            itemTop = itemBottom;
        }

    }


    /**
     * 这个方法需要进程子View的布局
     *
     * @param row
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    private AuraViewHolder makeAndStep(int row, int left, int top, int right, int bottom) {
        AuraViewHolder holder = obtainView(row, right - left, bottom - top);
        holder.itemView.layout(left, top, right, bottom);
        return holder;
    }

    /**
     * @param row
     * @param width
     * @param height
     */
    private AuraViewHolder obtainView(int row, int width, int height) {
        // 首先.我们先拿到布局类型
        int itemType = mAdapter.getItemViewType(row);
        AuraViewHolder viewHolder = mRecycler.getRecycledViewPool().getRecycledView(itemType);
        if (viewHolder == null) {
            // 当回收池中没有当前ItemView类型的ViewHolder
            viewHolder = mAdapter.onCreateViewHolder(this, itemType);
        }
        if (null == viewHolder) {
            throw new RuntimeException("ViewHolder should not be null !!!");
        }
        mAdapter.onBindViewHolder(viewHolder, row);
        return viewHolder;
    }

    /**
     * ==============================下面都是留给View外部去调用的方法================================
     */
    /**
     * AuraRecyclerView对外提供的适配器的方法
     *
     * @param adapter
     */
    public void setAdapter(@Nullable AuraAdapter adapter) {
        /// 设置完适配器之后，我们就要重新执行requestLayout
        setAdapterInternal(adapter, false, true);
        // 设置完Adapter之后，我们就要重新进行测量、布局、绘制的相关流程
        // requestLayout触发onMeasure、onLayout、onDraw
        requestLayout();
    }

    /**
     * 这个方法是给RecyclerView设置Adapter的时候的，内部调用方法
     *
     * @param adapter                设置的适配器
     * @param compatibleWithPrevious 是否需要兼容之前的（TODO 不太懂）
     * @param removeAndRecycleViews  是否需要移除并且回收View
     */
    private void setAdapterInternal(AuraAdapter adapter, boolean compatibleWithPrevious, boolean removeAndRecycleViews) {

        /// 如果不需要只需要兼容之前的View或者需要移除并且回收View
        if (!compatibleWithPrevious || removeAndRecycleViews) {
            removeAndRecycleViews();
        }
        // 设置适配器
        mAdapter = adapter;
    }

    /**
     * 回收的视图池允许多个RecyclerView共享一个公共的废料视图池。
     * 如果您有多个带有使用相同视图类型的适配器的RecyclerViews，
     * 例如，如果您有多个具有相同项目视图类型的数据集，则由{@link androidx.viewpager.widget.ViewPager}显示。
     *
     * @param pool
     */
    public void setRecycledViewPool(@Nullable AuraRecycledViewPool pool) {
        // TODO 怎么去设置RecyclerViewPool
    }

    /**
     * 移除并且回收Views
     */
    void removeAndRecycleViews() {
        // we should clear it here before adapters are swapped to ensure correct callbacks.
        mRecycler.clear();
    }

}
