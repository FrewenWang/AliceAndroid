package com.frewen.android.demo.logic.samples.animation.ui.main;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.frewen.android.demo.R;
import com.frewen.android.demo.logic.samples.animation.surface.FrameSurfaceView;
import com.frewen.android.demo.logic.samples.animation.surface.MethodUtil;
import com.frewen.android.demo.logic.samples.animation.surface.NumberUtil;
import com.frewen.android.demo.logic.samples.animation.v1.IFrameAnimView;
import com.frewen.aura.framework.fragment.BaseViewFragment;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import static com.frewen.aura.toolkits.core.AuraToolKits.getAssets;

/**
 * 帧动画的实现页面
 */
public class FrameAnimationFragment extends BaseViewFragment {
    private static final String TAG = "FrameAnimationFragment";
    private static final String ARG_SECTION_NUMBER = "section_number";
    private PageViewModel pageViewModel;
    private FrameSurfaceView frameSurfaceView;
    public static final int FRAM_ANIMATION_DURATION = 600;

    private List<Integer> normalBitmaps = Arrays.asList(
            R.drawable.watch_reward_1,
            R.drawable.watch_reward_2,
            R.drawable.watch_reward_3,
            R.drawable.watch_reward_4,
            R.drawable.watch_reward_5,
            R.drawable.watch_reward_6,
            R.drawable.watch_reward_7,
            R.drawable.watch_reward_8,
            R.drawable.watch_reward_9,
            R.drawable.watch_reward_10,
            R.drawable.watch_reward_11,
            R.drawable.watch_reward_12,
            R.drawable.watch_reward_13,
            R.drawable.watch_reward_14,
            R.drawable.watch_reward_15,
            R.drawable.watch_reward_16,
            R.drawable.watch_reward_17,
            R.drawable.watch_reward_18,
            R.drawable.watch_reward_19,
            R.drawable.watch_reward_20,
            R.drawable.watch_reward_21,
            R.drawable.watch_reward_22
    );
    private List<Integer> hugeBitmaps = Arrays.asList(
            R.raw.frame0,
            R.raw.frame1,
            R.raw.frame2,
            R.raw.frame3,
            R.raw.frame4,
            R.raw.frame5,
            R.raw.frame6,
            R.raw.frame7,
            R.raw.frame8,
            R.raw.frame9,
            R.raw.frame10,
            R.raw.frame11,
            R.raw.frame12,
            R.raw.frame13,
            R.raw.frame14,
            R.raw.frame15,
            R.raw.frame16,
            R.raw.frame17,
            R.raw.frame18,
            R.raw.frame19
    );

    public static FrameAnimationFragment newInstance(int index) {
        FrameAnimationFragment fragment = new FrameAnimationFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_animation_demo;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        frameSurfaceView = view.findViewById(R.id.sv_frame);
        frameSurfaceView.setBitmapIds(normalBitmaps);
        frameSurfaceView.setDuration(FRAM_ANIMATION_DURATION);

        initView(view);

    }

    private void initView(View view) {
        view.findViewById(R.id.btn_decode_resource).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long span = MethodUtil.time(new Runnable() {
                    @Override
                    public void run() {
                        BitmapFactory.decodeResource(getResources(), R.drawable.frame4);
                    }
                });
                NumberUtil.average("decode resource", span);
            }
        });

        view.findViewById(R.id.btn_decode_stream).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long span = MethodUtil.time(new Runnable() {
                    @Override
                    public void run() {
                        InputStream inputStream = getResources().openRawResource(R.raw.frame4);
                        BitmapFactory.decodeStream(inputStream);
                    }
                });
                NumberUtil.average("decode stream", span);
            }
        });

        view.findViewById(R.id.btn_rapid_decode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long span = MethodUtil.time(new Runnable() {
                    @Override
                    public void run() {
                        InputStream inputStream = getResources().openRawResource(R.raw.frame4);
                        //BitmapDecoder.from(inputStream).decode();
                    }
                });
                NumberUtil.average("rapid decode", span);
            }
        });

        view.findViewById(R.id.btn_decode_asset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long span = MethodUtil.time(new Runnable() {
                    @Override
                    public void run() {
                        InputStream inputStream = null;
                        try {
                            inputStream = getAssets().open("frame4.png");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        BitmapFactory.decodeStream(inputStream);
                    }
                });
                NumberUtil.average("assets decode", span);
            }
        });
        view.findViewById(R.id.btn_start_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimationByAnimationDrawable(view);
            }
        });

        view.findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //play frame animation by FrameSurfaceView which is much more memory-efficient than AnimationDrawable
                frameSurfaceView.setRepeatTimes(FrameSurfaceView.INFINITE);
                frameSurfaceView.start();
            }
        });

        frameSurfaceView = view.findViewById(R.id.sv_frame);
        frameSurfaceView.setBitmapIds(hugeBitmaps);
        frameSurfaceView.setDuration(FRAM_ANIMATION_DURATION);

    }

    /**
     * play frame animation by AnimationDrawable which will cause a disaster to memory if bitmap is huge(around 1MB)
     */
    private void startAnimationByAnimationDrawable(View view) {

        IFrameAnimView frameAnimDrawable = view.findViewById(R.id.ivFrameAnimation);
        frameAnimDrawable.startAnim("pay_scan");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                frameAnimDrawable.setCounts(2);
                frameAnimDrawable.startAnim("pay_success");
                frameAnimDrawable.setAnimationCallback(new IFrameAnimView.AnimationCallback() {
                    @Override
                    public void onFinished() {
                        Log.d(TAG, "FMsg:onFinished() called");
                    }
                });
            }
        }, 5 * 1000);
    }

}