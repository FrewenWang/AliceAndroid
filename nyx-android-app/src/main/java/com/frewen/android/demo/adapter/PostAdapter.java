package com.frewen.android.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.frewen.android.demo.R;
import com.frewen.android.demo.databinding.ItemLayoutViewPostTypeImageBinding;
import com.frewen.android.demo.databinding.ItemLayoutViewPostTypeVideoBinding;
import com.frewen.android.demo.logic.model.Post;
import com.frewen.android.demo.views.PostListPlayerView;
import com.frewen.demo.library.adapter.holder.AbsPagedListAdapter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import static com.frewen.android.demo.BR.feed;
import static com.frewen.android.demo.BR.lifeCycleOwner;

/**
 * @filename: PostAdapter
 * @author: Frewen.Wong
 * @time: 12/10/20 10:42 PM
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class PostAdapter extends AbsPagedListAdapter<Post, PostAdapter.ViewHolder> {


    private final LayoutInflater inflater;
    private final Context mContext;
    private final String mCategory;

    public PostAdapter(Context context, String category) {
        super(new DiffUtil.ItemCallback<Post>() {
            @Override
            public boolean areItemsTheSame(@NonNull Post oldItem, @NonNull Post newItem) {
                // 判断新旧两个Item是否是一致的
                return oldItem.id == newItem.id;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Post oldItem, @NonNull Post newItem) {
                return oldItem.equals(newItem);
            }
        });

        inflater = LayoutInflater.from(context);
        mContext = context;
        mCategory = category;
    }

    @Override
    protected int getOriginalItemViewType(int position) {
        Post post = getItem(position);
        if (post.itemType == Post.TYPE_IMAGE_TEXT) {
            return R.layout.item_layout_view_post_type_image;
        } else if (post.itemType == Post.TYPE_VIDEO) {
            return R.layout.item_layout_view_post_type_video;
        }
        return super.getOriginalItemViewType(position);
    }

    @Override
    protected void onBindOriginalDataViewHolder(ViewHolder holder, int position) {
        final Post post = getItem(position);
        holder.bindData(post);
        holder.itemView.setOnClickListener(v -> {
            // TODO 进入详情页
        });
    }

    @Override
    protected ViewHolder onCreateOriginalDataViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, viewType, parent, false);
        return new ViewHolder(binding.getRoot(), binding);
    }

    /**
     * 这个PostAdapter中的ViewHolder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewDataBinding mBinding;
        public PostListPlayerView mPostListPlayerView;
        public ImageView postImage;

        public ViewHolder(@NonNull View itemView, ViewDataBinding binding) {
            super(itemView);
            mBinding = binding;
        }

        /**
         * 这里面我们也使用DataBinding
         *
         * @param item
         */
        public void bindData(Post item) {
            //这里之所以手动绑定数据的原因是 图片 和视频区域都是需要计算的
            //而dataBinding的执行默认是延迟一帧的。
            //当列表上下滑动的时候 ，会明显的看到宽高尺寸不对称的问题
            mBinding.setVariable(feed, item);
            mBinding.setVariable(lifeCycleOwner, mContext);
            if (mBinding instanceof ItemLayoutViewPostTypeImageBinding) {
                ItemLayoutViewPostTypeImageBinding imageBinding = (ItemLayoutViewPostTypeImageBinding) mBinding;
                postImage = imageBinding.postImage;
                imageBinding.postImage.bindData(item.width, item.height, 16, item.cover);
                //imageBinding.setFeed(item);
                //imageBinding.interactionBinding.setLifeCycleOwner((LifecycleOwner) mContext);
            } else if (mBinding instanceof ItemLayoutViewPostTypeVideoBinding) {
                ItemLayoutViewPostTypeVideoBinding videoBinding = (ItemLayoutViewPostTypeVideoBinding) mBinding;
                videoBinding.listPlayerView.bindData(mCategory, item.width, item.height, item.cover, item.url);
                mPostListPlayerView = videoBinding.listPlayerView;
                //videoBinding.setFeed(item);
                //videoBinding.interactionBinding.setLifeCycleOwner((LifecycleOwner) mContext);
            }
        }
    }
}
