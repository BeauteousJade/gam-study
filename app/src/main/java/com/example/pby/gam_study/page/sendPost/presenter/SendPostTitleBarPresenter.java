package com.example.pby.gam_study.page.sendPost.presenter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blade.annotation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.factory.DialogFactory;
import com.example.pby.gam_study.fragment.dialog.GamDialogFragment;
import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Post;
import com.example.pby.gam_study.network.request.Request;
import com.example.pby.gam_study.network.request.RequestCallback;
import com.example.pby.gam_study.page.sendPost.request.PostRequest;
import com.example.pby.gam_study.util.SoftKeyboardUtils;
import com.example.pby.gam_study.util.StringUtil;
import com.example.pby.gam_study.util.ToastUtil;
import com.example.pby.gam_study.widget.EmojiEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class SendPostTitleBarPresenter extends Presenter {

    @BindView(R.id.title)
    TextView mTitleView;
    @BindView(R.id.left_icon)
    ImageView mLeftView;
    @BindView(R.id.right_icon)
    ImageView mRightView;
    @BindView(R.id.post_content)
    EmojiEditText mEditText;

    @Inject(AccessIds.RECYCLER_ADAPTER)
    BaseRecyclerAdapter<Object> mAdapter;


    private Request<Post> mRequest;
    private GamDialogFragment mLoadDialog;

    private final RequestCallback<Post> mRequestCallback = response -> {
        mLoadDialog.dismiss();
        if (response.getData() != null && response.getError() == null) {
            ToastUtil.info(getCurrentActivity(), getString(R.string.share_success));
            getCurrentActivity().finish();
        } else {
            ToastUtil.info(getCurrentActivity(), getString(R.string.share_failure));
        }
    };

    @Override
    protected void onCreate() {
        mLoadDialog = DialogFactory.createLoadDialog(getCurrentActivity());
    }

    @Override
    protected void onBind() {
        mLeftView.setImageDrawable(getDrawable(R.drawable.bg_back));
        mRightView.setImageDrawable(getDrawable(R.drawable.bg_ok));
        mTitleView.setText(getString(R.string.title_send_post));
    }

    @OnClick(R.id.left_icon)
    public void onLeftClick(View view) {
        SoftKeyboardUtils.hideSoftKeyboard(getCurrentActivity());
        getCurrentActivity().finish();
    }

    @SuppressWarnings("unchecked")
    @OnClick(R.id.right_icon)
    public void onRightClick(View view) {
        if (mRequest != null) {
            mRequest.cancel();
        }
        if (StringUtil.isEmpty(mEditText.getText()) && mAdapter.getItemCount() == 1) {
            ToastUtil.error(getCurrentActivity(), R.string.share_content_empty);
            return;
        }
        final Post post = new Post();
        post.setContent(Objects.requireNonNull(mEditText.getText()).toString());
        post.setUser(LoginManager.getCurrentUser());
        List<String> imageUrlList = new ArrayList<>((List) mAdapter.getDataList());
        if (imageUrlList.size() < 6) {
            imageUrlList.remove(imageUrlList.size() - 1);
        }
        post.setImageUrlList(imageUrlList);
        mRequest = new PostRequest(post);
        mLoadDialog.show(getCurrentFragment().getChildFragmentManager(), "");
        mRequest.enqueue(mRequestCallback);
    }

    @Override
    protected void onUnBind() {
        if (mRequest != null) {
            mRequest.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        if (mRequest != null) {
            mRequest.cancel();
        }
    }
}
