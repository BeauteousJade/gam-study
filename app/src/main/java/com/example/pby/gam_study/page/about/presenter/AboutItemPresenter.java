package com.example.pby.gam_study.page.about.presenter;

import android.view.View;
import android.widget.TextView;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.fragment.dialog.GamDialogFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.request.RequestCallback;
import com.example.pby.gam_study.network.response.Response;
import com.example.pby.gam_study.page.about.CheckUpdateRequest;
import com.example.pby.gam_study.page.about.FileDownloadRequest;
import com.example.pby.gam_study.util.GamNotificationManager;
import com.example.pby.gam_study.util.ResourcesUtil;
import com.example.pby.gam_study.util.ToastUtil;

import java.io.File;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

public class AboutItemPresenter extends Presenter implements View.OnClickListener {

    @BindView(R.id.version)
    TextView mVersionView;


    private CheckUpdateRequest mCheckUpdateRequest;
    private FileDownloadRequest mFileDownloadRequest;
    private GamDialogFragment mDialogFragment;
    private String mDownUrl;

    private final RequestCallback<String> mCheckUpdateRequestCallback = new RequestCallback<String>() {
        @Override
        public void onResult(Response<String> response) {
            if (response.getError() == null && response.getData() != null) {
                mDownUrl = response.getData();
                mDialogFragment.show(getCurrentFragment().getChildFragmentManager(), "");
            } else {
                ToastUtil.info(getCurrentActivity(), R.string.version_is_newest);
            }
        }
    };

    private FileDownloadRequest.DownloadListener mDownloadListener = new FileDownloadRequest.DownloadListener() {
        @Override
        public void onStart() {

        }

        @Override
        public void onDownload(int progress) {
            GamNotificationManager.getInstance(getCurrentActivity()).sendDownloadNotification(progress);
        }

        @Override
        public void onStop(@Nullable Throwable throwable, @Nullable File file) {
            if (throwable == null || file != null) {
                GamNotificationManager.getInstance(getCurrentActivity()).sendDownloadSuccessNotification(file);
            } else {
                GamNotificationManager.getInstance(getCurrentActivity()).sendDownloadFailNotification();
            }
        }
    };

    @Override
    protected void onBind() {
        if (mDialogFragment == null) {
            mDialogFragment = new GamDialogFragment.Builder(GamDialogFragment.LocationStyle.STYLE_CENTER, R.layout.dialog_download_apk)
                    .setAnchorView(getRootView())
                    .setOnViewClickListener(this, R.id.sure, R.id.cancel)
                    .build();
        }
        mVersionView.setText(ResourcesUtil.getVersionName(getCurrentActivity()));
    }

    @OnClick(R.id.check_update_container)
    public void onCheckUpdateClick(View view) {
        mCheckUpdateRequest = new CheckUpdateRequest(mVersionView.getText().toString());
        mCheckUpdateRequest.enqueue(mCheckUpdateRequestCallback);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sure:
                mFileDownloadRequest = new FileDownloadRequest(mDownUrl, getCurrentActivity().getCacheDir().getAbsolutePath() + File.separator + "new_install.apk");
                mFileDownloadRequest.enqueue(mDownloadListener);
                break;
        }
        mDialogFragment.dismiss();
    }
}
