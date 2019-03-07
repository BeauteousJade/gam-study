package com.example.pby.gam_study.page.editImage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.pby.gam_study.RequestCode;
import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.fragment.BaseFragment;

public class EditImageActivity extends BaseActivity {

    public static final String IMAGE_URL = "image_url";
    private String mUrl;

    public static void startActivityForResult(BaseActivity activity, String url) {
        Intent intent = new Intent(activity, EditImageActivity.class);
        intent.putExtra(IMAGE_URL, url);
        activity.startActivityForResult(intent, RequestCode.REQUEST_EDIT_IMAGE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mUrl = getIntent().getStringExtra(IMAGE_URL);
        super.onCreate(savedInstanceState);
    }

    @Override
    public BaseFragment buildCurrentFragment() {
        return EditImageFragment.newInstance(mUrl);
    }
}
