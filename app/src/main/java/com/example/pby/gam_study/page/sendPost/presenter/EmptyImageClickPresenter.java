package com.example.pby.gam_study.page.sendPost.presenter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.RequestCode;
import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.other.GlideImageEngine;
import com.example.pby.gam_study.util.ArrayUtil;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.util.List;

import androidx.annotation.Nullable;
import butterknife.OnClick;

public class EmptyImageClickPresenter extends Presenter {

    private static final int MAX_COUNT = 6;

    @Inject(AccessIds.RECYCLER_ADAPTER)
    BaseRecyclerAdapter<String> mAdapter;
    @Inject(AccessIds.ITEM_DATA)
    Object mUrl;


    private final BaseActivity.OnActivityResultListener mOnActivityResultListener = new BaseActivity.OnActivityResultListener() {
        @Override
        public boolean onResult(int requestCode, int resultCode, @Nullable Intent data) {
            if (requestCode == RequestCode.REQUEST_SELECT_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
                List<String> list = Matisse.obtainPathResult(data);
                if (!ArrayUtil.isEmpty(list)) {
                    if (mAdapter.getItemCount() + list.size() == 6) {
                        mAdapter.remove(mAdapter.getItemCount() - 1);
                    }
                    mAdapter.addItemList(0, list);
                    return true;
                }
            }
            return false;
        }
    };

    @Override
    protected void onBind() {
        getCurrentActivity().removeOnActivityResultListener(mOnActivityResultListener);
        getCurrentActivity().addOnActivityResultListener(mOnActivityResultListener);
    }

    @Override
    protected void onDestroy() {
        getCurrentActivity().removeOnActivityResultListener(mOnActivityResultListener);
    }

    @OnClick(R.id.image)
    public void onImageClick(View view) {
        Matisse
                .from(getCurrentActivity())
                //选择视频和图片
                .choose(MimeType.ofImage())
                .countable(true)
                .maxSelectable(MAX_COUNT - mAdapter.getItemCount())
                .theme(R.style.gam_study)
                .imageEngine(new GlideImageEngine())
                .forResult(RequestCode.REQUEST_SELECT_IMAGE);
    }
}
