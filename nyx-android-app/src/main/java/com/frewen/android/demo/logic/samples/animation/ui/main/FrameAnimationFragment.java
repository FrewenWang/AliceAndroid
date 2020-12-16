package com.frewen.android.demo.logic.samples.animation.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.frewen.android.demo.R;
import com.frewen.aura.framework.fragment.BaseViewFragment;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

/**
 * 帧动画的实现页面
 */
public class FrameAnimationFragment extends BaseViewFragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

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
        final TextView textView = view.findViewById(R.id.section_label);
        pageViewModel.getText().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

    }
}