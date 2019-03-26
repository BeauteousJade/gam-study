package com.example.pby.gam_study.page.browseImage;

import android.content.Intent;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.fragment.BaseFragment;
import com.example.pby.gam_study.util.ResourcesUtil;

import java.util.ArrayList;

public class BrowseImageActivity extends BaseActivity {

    public static final String IMAGE_URL_LIST = "image_url_list";

    private ArrayList<String> mImageUrlList;

    public static void startActivity(BaseActivity activity, ArrayList<String> imageUrlList) {
        Intent intent = new Intent(activity, BrowseImageActivity.class);
        intent.putStringArrayListExtra(IMAGE_URL_LIST, imageUrlList);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.scale_in, 0);
    }

    @Override
    protected void onPrepare() {
        super.onPrepare();
        mImageUrlList = getIntent().getStringArrayListExtra(IMAGE_URL_LIST);
        findViewById(android.R.id.content).setBackgroundColor(ResourcesUtil.getColor(this, R.color.black));
    }

    @Override
    public BaseFragment buildCurrentFragment() {
        return BrowseImageFragment.newInstance(mImageUrlList);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.scale_out);
    }
}
