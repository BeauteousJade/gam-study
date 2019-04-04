package com.example.pby.gam_study.page.home.page.mine.presenter.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.RequestCode;
import com.example.pby.gam_study.activity.BaseActivity;
import com.example.pby.gam_study.fragment.dialog.GamDialogFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.User;
import com.example.pby.gam_study.network.request.RequestCallback;
import com.example.pby.gam_study.network.response.Response;
import com.example.pby.gam_study.other.GlideImageEngine;
import com.example.pby.gam_study.page.home.page.mine.MineAdapter;
import com.example.pby.gam_study.page.home.page.mine.request.UpdateAvatarRequest;
import com.example.pby.gam_study.util.ArrayUtil;
import com.example.pby.gam_study.util.DisplayUtil;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.OnClick;

public class UserHeadClickPresenter extends Presenter implements View.OnClickListener {

    @Inject(AccessIds.ITEM_DATA)
    User mUser;
    @Inject(AccessIds.RECYCLER_ADAPTER)
    MineAdapter mAdapter;
    @Inject(AccessIds.VIEW_HOLDER)
    RecyclerView.ViewHolder mViewHolder;

    private GamDialogFragment mDialogFragment;
    private UpdateAvatarRequest mRequest;

    private final BaseActivity.OnActivityResultListener mOnActivityResultListener = new BaseActivity.OnActivityResultListener() {
        @Override
        public boolean onResult(int requestCode, int resultCode, @Nullable Intent data) {
            if (requestCode == RequestCode.REQUEST_SELECT_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
                List<String> list = Matisse.obtainPathResult(data);
                if (!ArrayUtil.isEmpty(list)) {
                    String path = list.get(0);
                    if (mRequest != null) {
                        mRequest.cancel();
                    }
                    mRequest = new UpdateAvatarRequest(path);
                    mRequest.enqueue(mRequestCallback);
                    return true;
                }
            }
            return false;
        }
    };

    private final RequestCallback<String> mRequestCallback = new RequestCallback<String>() {
        @Override
        public void onResult(Response<String> response) {
            if (response.getError() == null && response.getData() != null) {
                mUser.setHead(response.getData());
                mAdapter.notifyItemChanged(mViewHolder.getAdapterPosition(), User.HEAD);
            }
        }
    };

    @Override
    protected void onCreate() {
        mDialogFragment = new GamDialogFragment.Builder(GamDialogFragment.LocationStyle.STYLE_CENTER_BOTTOM, R.layout.menu_avatar)
                .setAnchorView(getCurrentActivity().findViewById(android.R.id.content))
                .setWindowWidth(DisplayUtil.getScreenWidth(getCurrentActivity()))
                .setAnimationStyle(R.style.menu_show_animation)
                .setOnViewClickListener(this, R.id.update_avatar, R.id.cancel)
                .build();

        getCurrentActivity().addOnActivityResultListener(mOnActivityResultListener);
    }

    @Override
    protected void onDestroy() {
        getCurrentActivity().removeOnActivityResultListener(mOnActivityResultListener);
    }

    @OnClick(R.id.avatar)
    public void onAvatarClick(View view) {
        mDialogFragment.show(getCurrentFragment().getChildFragmentManager(), "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_avatar:
                Matisse
                        .from(getCurrentActivity())
                        //选择视频和图片
                        .choose(MimeType.ofImage())
                        .countable(true)
                        .maxSelectable(1)
                        .theme(R.style.gam_study)
                        .imageEngine(new GlideImageEngine())
                        .forResult(RequestCode.REQUEST_SELECT_IMAGE);
                break;
        }
        mDialogFragment.dismiss();
    }
}
