package com.endava.myendava.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.endava.myendava.R;
import com.endava.myendava.utils.AnimationHelper;

import java.util.Random;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class GuestInfoFragment extends BaseFragment {

    @BindView(R.id.wifi_password_textview)
    TextView mPasswordTextView;
    @BindView(R.id.visitor_card_textview)
    TextView mVisitorCardTextView;
    @BindView(R.id.copy_success_overlay)
    ImageView mCopySuccessOverlay;

    private Unbinder mUnbinder;

    public GuestInfoFragment() {
    }

    public static GuestInfoFragment newInstance() {
        return new GuestInfoFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mUnbinder = ButterKnife.bind(this, view);
        mPasswordTextView.setText(UUID.randomUUID().toString().substring(0, 16));
        Random random = new Random();
        mVisitorCardTextView.setText(String.valueOf(random.nextInt(100)));
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_guest_info;
    }

    @Override
    public void setupModule() {
        locator.getGuestInfoComponent(this).inject(this);
    }

    @OnClick(R.id.copy_framelayout)
    void onClick() {
        ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(null, mPasswordTextView.getText());
        clipboardManager.setPrimaryClip(clip);
        animateCopySuccess();
    }

    private void animateCopySuccess() {
        mCopySuccessOverlay.setVisibility(View.VISIBLE);
        AnimationHelper.animateFadeIn(mCopySuccessOverlay, 400);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
