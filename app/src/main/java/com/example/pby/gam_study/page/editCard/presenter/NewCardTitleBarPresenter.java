package com.example.pby.gam_study.page.editCard.presenter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blade.annotation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.factory.DialogFactory;
import com.example.pby.gam_study.fragment.dialog.GamDialogFragment;
import com.example.pby.gam_study.fragment.util.Observable;
import com.example.pby.gam_study.fragment.util.Observer;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.editCard.EditCardFragment;
import com.example.pby.gam_study.page.editCard.request.NewCardRequest;
import com.example.pby.gam_study.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class NewCardTitleBarPresenter extends Presenter {

    @BindView(R.id.left_icon)
    ImageView mLeftView;
    @BindView(R.id.right_icon)
    ImageView mRightView;
    @BindView(R.id.title)
    TextView mTitleView;
    @Inject(AccessIds.OBSERVABLE)
    Observable mObservable;
    @Inject(AccessIds.KIND_ID)
    String mKindId;


    private String mAnswer;
    private String mOldImageUrl;
    private String mEditImageUrl;
    private GamDialogFragment mLoadDialog;

    private NewCardRequest mRequest;

    private final Observer mObserver = new Observer() {
        @Override
        public void onChanged(String key, Object obj) {
            switch (key) {
                case EditCardFragment.KEY_OBSERVABLE_ANSWER:
                    mAnswer = obj.toString();
                    break;
                case EditCardFragment.KEY_OBSERVABLE_OLD_IMAGE:
                    mOldImageUrl = obj.toString();
                    break;
                case EditCardFragment.KEY_OBSERVABLE_EDIT_IMAGE:
                    mEditImageUrl = obj.toString();
                    break;
            }
        }
    };

    @Override
    protected void onCreate() {
        mLoadDialog = DialogFactory.createLoadDialog(getCurrentActivity());
    }

    @Override
    protected void onBind() {
        mTitleView.setText(getString(R.string.title_make_card));
        mLeftView.setImageDrawable(getDrawable(R.drawable.bg_back));
        mRightView.setImageDrawable(getDrawable(R.mipmap.icon_next));
        mObservable.addObserver(mObserver);
    }

    @Override
    protected void onUnBind() {
        mObservable.removeObserver(mObserver);
    }


    @Override
    protected void onDestroy() {
        mObservable.removeObserver(mObserver);
        releaseRequest();
    }

    @OnClick(R.id.left_icon)
    public void onLeftClick(View view) {
        getCurrentActivity().finish();
    }

    @OnClick(R.id.right_icon)
    public void onRightClick(View view) {
        if (mOldImageUrl == null) {
            ToastUtil.error(getCurrentActivity(), R.string.image_not_select);
            return;
        }
        if (mEditImageUrl == null) {
            ToastUtil.error(getCurrentActivity(), R.string.image_not_edit);
            return;
        }
        if (mAnswer == null) {
            ToastUtil.error(getCurrentActivity(), R.string.answer_empty);
            return;
        }
        releaseRequest();
        mRequest = new NewCardRequest(mKindId, mOldImageUrl, mEditImageUrl, mAnswer);
        mLoadDialog.show(getCurrentFragment().getChildFragmentManager(), "");
        mRequest.enqueue(response -> {
            if (response.getError() == null && response.getData() != null) {
                ToastUtil.error(getCurrentActivity(), R.string.new_success);
                getCurrentActivity().finish();
            } else {
                ToastUtil.error(getCurrentActivity(), R.string.new_failure);
            }
            mLoadDialog.dismiss();
        });
    }

    private void releaseRequest() {
        if (mRequest != null) {
            mRequest.cancel();
        }
    }
}
