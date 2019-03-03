package com.example.pby.gam_study.page.newKind.presenter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.annation.Inject;
import com.example.annation.Module;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.fragment.dialog.GamDialogFragment;
import com.example.pby.gam_study.factory.LoadDialogFactory;
import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.request.RequestCallback;
import com.example.pby.gam_study.network.response.Response;
import com.example.pby.gam_study.page.newKind.NewKindFragment;
import com.example.pby.gam_study.page.newKind.NewKindObservable;
import com.example.pby.gam_study.page.newKind.NewKindObserver;
import com.example.pby.gam_study.page.newKind.request.NewKindRequest;
import com.example.pby.gam_study.util.ResourcesUtil;
import com.example.pby.gam_study.util.StringUtil;
import com.example.pby.gam_study.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

@Module(NewKindFragment.Context.class)
public class NewKindTitleBarPresenter extends Presenter {

    @BindView(R.id.left_icon)
    ImageView mLeftView;
    @BindView(R.id.right_icon)
    ImageView mRightView;
    @BindView(R.id.title)
    TextView mTitleView;

    @Inject(AccessIds.OBSERVABLE)
    NewKindObservable mObservable;

    private String mKindName;
    private String mCover;

    private final NewKindRequest mRequest = new NewKindRequest();
    private GamDialogFragment mLoadDialog;

    private final NewKindObserver mObserver = new NewKindObserver() {
        @Override
        public void onKindNameChanged(String kindName) {
            mKindName = kindName;
        }

        @Override
        public void onKindCoverChanged(String cover) {
            mCover = cover;
        }
    };

    private final RequestCallback<Integer> mCallback = new RequestCallback<Integer>() {
        @Override
        public void onResult(Response<Integer> response) {
            if (response.getError() == null && response.getData() != 0) {
                getCurrentActivity().finish();
                ToastUtil.info(getCurrentActivity(), ResourcesUtil.getString(getCurrentActivity(), R.string.new_success));
            } else {
                if (response.getError() != null) {
                    ToastUtil.info(getCurrentActivity(), ResourcesUtil.getString(getCurrentActivity(), R.string.new_failure));
                } else {
                    ToastUtil.info(getCurrentActivity(), ResourcesUtil.getString(getCurrentActivity(), R.string.kind_nam_exist));
                }
            }
            mLoadDialog.dismiss();
        }
    };

    @Override
    protected void onCreate() {
        mLoadDialog = LoadDialogFactory.getLoadDialog(getCurrentActivity());
    }

    @Override
    protected void onBind() {
        mTitleView.setText(ResourcesUtil.getString(getCurrentActivity(), R.string.menu_item_new_kind));
        mLeftView.setImageResource(R.mipmap.icon_back);
        mRightView.setImageResource(R.mipmap.icon_ok);
        mObservable.removeObserver(mObserver);
        mObservable.addObserver(mObserver);
    }

    @Override
    protected void onDestroy() {
        mRequest.cancel();
    }

    @OnClick(R.id.left_icon)
    public void onLeftClick(View view) {
        getCurrentActivity().finish();
    }

    @OnClick(R.id.right_icon)
    public void onRightClick(View view) {
        if (StringUtil.isEmpty(mCover)) {
            ToastUtil.error(getCurrentActivity(), ResourcesUtil.getString(getCurrentActivity(), R.string.cover_is_empty));
            return;
        }
        if (StringUtil.isEmpty(mKindName)) {
            ToastUtil.error(getCurrentActivity(), ResourcesUtil.getString(getCurrentActivity(), R.string.kind_name_is_empty));
            return;
        }
        mRequest.cancel();
        mRequest.setCover(mCover);
        mRequest.setKindName(mKindName);
        mRequest.setUserId(LoginManager.getCurrentUser().getId());
        mRequest.enqueue(mCallback);
        mLoadDialog.show(getCurrentFragment().getChildFragmentManager(), "");
    }
}
