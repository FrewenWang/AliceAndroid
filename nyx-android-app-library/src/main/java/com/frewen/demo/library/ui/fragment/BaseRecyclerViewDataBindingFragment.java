package com.frewen.demo.library.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frewen.aura.framework.mvvm.vm.BasePagedListViewModel;
import com.frewen.demo.library.R;
import com.frewen.demo.library.databinding.FragmentRefreshRecylerViewBinding;
import com.frewen.demo.library.widgets.RefreshEmptyView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @filename: BaseRcyclerViewDataBindingFragment
 * @author: Frewen.Wong
 * @time: 12/6/20 8:12 PM
 * @version: 1.0.0
 * @introduction: 这个的布局是使用RecyclerView+下拉刷新和DataBinding功能的Fragment
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public abstract class BaseRecyclerViewDataBindingFragment<Data, VM extends BasePagedListViewModel<Data>>
        extends BaseDataBindingFragment<FragmentRefreshRecylerViewBinding, VM> implements OnRefreshListener, OnLoadMoreListener {

    protected RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;
    private RefreshEmptyView mEmptyView;
    private PagedListAdapter adapter;
    private DividerItemDecoration decoration;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //getRoot() 返回与绑定关联的布局文件中最外面的View。
        getBinding().getRoot().setFitsSystemWindows(true);

        mRecyclerView = getBinding().recyclerView;
        mRefreshLayout = getBinding().refreshLayout;
        mEmptyView = getBinding().emptyView;

        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(true);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);

        // 设置RecyclerView的Adapter
        adapter = getAdapter();
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(null);

        //默认给列表中的Item 一个 10dp的ItemDecoration
        decoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        decoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.shape_divider_list_10dp));
        mRecyclerView.addItemDecoration(decoration);


        initData();
    }

    private void initData() {
        //触发页面初始化数据加载的逻辑
        getViewModel().getPageData().observe(getViewLifecycleOwner(), new Observer<PagedList<Data>>() {
            @Override
            public void onChanged(PagedList<Data> pagedList) {
                BaseRecyclerViewDataBindingFragment.this.submitList(pagedList);
            }
        });

        //监听分页时有无更多数据,以决定是否关闭上拉加载的动画
        // mViewModel.getBoundaryPageData().observe(this, hasData -> finishRefresh(hasData));
    }

    /**
     * 我们监听LiveData的数据，当我们监听到LiveData的数据之后
     * 我们来更新Adapter的submitList来进行提交数据
     *
     * @param result
     */
    public void submitList(PagedList<Data> result) {
        //只有当新数据集合大于0 的时候，才调用adapter.submitList
        //否则可能会出现 页面----有数据----->被清空-----空布局
        if (result.size() > 0) {
            adapter.submitList(result);
        }
        finishRefresh(result.size() > 0);
    }

    /**
     * 我们来进行停止刷新的操作
     *
     * @param hasData 判断是否有数据
     */
    public void finishRefresh(boolean hasData) {
        PagedList<Data> currentList = adapter.getCurrentList();
        hasData = hasData || currentList != null && currentList.size() > 0;
        RefreshState state = mRefreshLayout.getState();
        if (state.isFooter && state.isOpening) {
            mRefreshLayout.finishLoadMore();
        } else if (state.isHeader && state.isOpening) {
            mRefreshLayout.finishRefresh();
        }

        if (hasData) {
            mEmptyView.setVisibility(View.GONE);
        } else {
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 所有继承自BaseRecyclerView的Fragment都是使用的这个布局
     */
    @Override
    public int getLayoutId() {
        return R.layout.fragment_refresh_recyler_view;
    }

    /**
     * 因而 我们在 onCreateView的时候 创建了 PagedListAdapter
     * 所以，如果arguments 有参数需要传递到Adapter 中，那么需要在getAdapter()方法中取出参数。
     */
    public abstract PagedListAdapter getAdapter();
}
