package com.frewen.android.demo.business.samples.glide;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.frewen.android.demo.R;
import com.frewen.aura.framework.ui.BaseButterKnifeActivity;

import androidx.annotation.Nullable;
import butterknife.BindView;

/**
 * Glide4Activity
 */
public class Glide4Activity extends BaseButterKnifeActivity {

    @BindView(R.id.img)
    ImageView mImageView;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_glide4;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {

        /**
         * Glide的生命周期的概念？？？
         *
         * Glide的生命周期主要是为了按需加载、中断请求。
         * 当用户退出Activity.就证明页面退出，那么我们里面那些其他的图片请求其实就不需要在加载了。
         * 这个时候我们就需要在可以中断那些已经准备好的请求。否则这些异步的网络请求任务，会在后台依然执行
         * 这个时候可能会造成：
         * 1、性能损耗
         * 2、内存占用
         * 3、浪费流量
         *
         * 四级缓存？？？
         *
         *
         *
         */
        String url = "http://pic.netbian.com/uploads/allimg/161021/222958-14770601988007.jpg";

        String setCookie = "__cfduid=d7f75d36fc63bd1b3eea9a2ba92e968ce1601000445;" +
                " zkhanecookieclassrecord=%2C54%2C; " +
                "Hm_lvt_526caf4e20c21f06a4e9209712d6a20e=1601000447; " +
                "PHPSESSID=00vrkvq8j724fdtk4caf1esoh6; " +
                "zkhanmlusername=%C2%F3%D3%EA%D5%C7%CF%AA%CC%EF; " +
                "zkhanmluserid=1159822; " +
                "zkhanmlgroupid=1; " +
                "zkhanmlrnd=leZgUmUJgSZPo3raCnAD;" +
                " zkhanmlauth=5c9de3d4d112d441b0f6aeb8ee9dd0df; " +
                "Hm_lpvt_526caf4e20c21f06a4e9209712d6a20e=1601000528";


        /// Glide.with(this).load(url).into(mImageView);
        // 我们为了更好的分析，我们将这种链式调用进行拆分
        RequestManager requestManager = Glide.with(this);
        RequestBuilder<Drawable> loaderBuilder = requestManager.load(url).addListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                Log.d("Glide", "FMsg:onLoadFailed() called with: e = [" + e + "], model = [" + model + "], target = [" + target + "], isFirstResource = [" + isFirstResource + "]");
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                Log.d("Glide", "FMsg:onResourceReady() called with: resource = [" + resource + "], model = [" + model + "], target = [" + target + "], dataSource = [" + dataSource + "], isFirstResource = [" + isFirstResource + "]");
                return false;
            }
        });
        /// 到我们调用这个into方法后，如果网络请求没有被pause. 那么直接加入到运行中的队列。然后直接执行request.begin的方法
        ///


        //// Glide的请求是怎么被维护的？？
        /// Glide主要是通过自己在内部创建的一个无UI的Fragment，来相当于监听外部的Activity或者Fragment的生命周期
        /// 然后根据生命周期去调用回到RequestManager的onStart、onStop、onDestroy等生命周期方法回到
        /// 然后通知RequestTracker来进行请求的管理。到底是执行Request的begin还是ClearRequest的线管操作

        // 下面就是步骤介绍：
        /// 当Fragment回调onStop的时候, 回调RequestManager的onStart.然后通知RequestTracker来进行请求的管理。执行Request的begin
        /// 当Fragment回调onStop的时候，则将所有的运行的中的请求，暂时放入到等待队列中
        // 当Fragment回调onDestroy中的时候。则清除所有的请求，并且释放相关的监听回调。并且注销RequestManager
        loaderBuilder.into(mImageView);


        //// 图片请求的缓存
        // 直接获取网络的上的图片。
        // 如果是一些小图片，数量比较多的情况。发现显示速度比较慢。 ->直接存到内存中
        // 不是所有的图片都能存到内存中，毕竟内存有限
        // 超过内存缓存的空间阈值。则存储的本地存储中
        // 通过LRUCache 的内存缓存策略
    }

}
