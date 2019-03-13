package com.example.pby.gam_study.page.editCard.presenter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.annation.Inject;
import com.example.annation.Module;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.RequestCode;
import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.fragment.util.Observable;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.other.GlideImageEngine;
import com.example.pby.gam_study.page.editCard.EditCardFragment;
import com.example.pby.gam_study.util.ArrayUtil;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.util.List;

import butterknife.OnClick;

@Module(BaseRecyclerAdapter.Context.class)
public class EmptyImagePresenter extends Presenter {

    @Inject(AccessIds.ITEM_POSITION)
    int mPosition;
    @Inject(AccessIds.RECYCLER_ADAPTER)
    BaseRecyclerAdapter mAdapter;
    @Inject(AccessIds.OBSERVABLE)
    Observable mObservable;
    private final BaseActivity.OnActivityResultListener mOnActivityResultListener = new BaseActivity.OnActivityResultListener() {

        @SuppressWarnings("unchecked")
        @Override
        public boolean onResult(int requestCode, int resultCode, @Nullable Intent data) {
            if (requestCode == RequestCode.REQUEST_SELECT_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
                List<String> list = Matisse.obtainPathResult(data);
                if (!ArrayUtil.isEmpty(list)) {
                    mAdapter.setData(mPosition, list.get(0), false);
                    mObservable.notifyChanged(EditCardFragment.KEY_OBSERVABLE_OLD_IMAGE, list.get(0));
                    return true;
                }
            }
            return false;
        }
    };

    @Override
    protected void onBind() {
        getCurrentActivity().addOnActivityResultListener(mOnActivityResultListener);
    }

    @Override
    protected void onUnBind() {
        getCurrentActivity().removeOnActivityResultListener(mOnActivityResultListener);
    }

    @Override
    protected void onDestroy() {
        getCurrentActivity().removeOnActivityResultListener(mOnActivityResultListener);
    }

    @OnClick(R.id.empty_image)
    public void onEmptyImageClick(View view) {
        Matisse
                .from(getCurrentActivity())
                //选择视频和图片
                .choose(MimeType.ofImage())
                .theme(R.style.gam_study)
                .imageEngine(new GlideImageEngine())
                .forResult(RequestCode.REQUEST_SELECT_IMAGE);
    }
}
