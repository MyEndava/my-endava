package com.endava.myendava.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.endava.myendava.R;
import com.endava.myendava.app.ApplicationServiceLocator;
import com.endava.myendava.presenters.fragments.BaseFragment;
import com.endava.myendava.presenters.fragments.GuestInfoPresenter;
import com.endava.myendava.utils.AnimationHelper;
import com.endava.myendava.views.fragments.GuestInfoView;

import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class GuestInfoFragment extends BaseFragment implements GuestInfoView {

    @BindView(R.id.wifi_password_textview)
    TextView mPasswordTextView;

    @BindView(R.id.copy_success_overlay)
    ImageView mCopySuccessOverlay;

    @Inject
    GuestInfoPresenter mPresenter;

    private Unbinder mUnbinder;

    public GuestInfoFragment() {
    }

    public static GuestInfoFragment newInstance() {
        return new GuestInfoFragment();
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guest_info, parent, false);
        setupModule();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mUnbinder = ButterKnife.bind(this, view);
        mPasswordTextView.setText(UUID.randomUUID().toString());
        informPresenterViewReady();
    }

    private void informPresenterViewReady() {
        mPresenter.viewReady();
    }

    private void setupModule() {
        ApplicationServiceLocator locator = (ApplicationServiceLocator) getActivity().getApplicationContext();
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
