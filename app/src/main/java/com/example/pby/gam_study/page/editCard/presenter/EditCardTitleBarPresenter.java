package com.example.pby.gam_study.page.editCard.presenter;

import android.app.Activity;
import android.content.Intent;
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
import com.example.pby.gam_study.network.bean.Card;
import com.example.pby.gam_study.page.editCard.EditCardActivity;
import com.example.pby.gam_study.page.editCard.EditCardFragment;
import com.example.pby.gam_study.page.editCard.request.EditCardRequest;
import com.example.pby.gam_study.util.ToastUtil;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class EditCardTitleBarPresenter extends Presenter {
    @BindView(R.id.left_icon)
    ImageView mLeftView;
    @BindView(R.id.right_icon)
    ImageView mRightView;
    @BindView(R.id.title)
    TextView mTitleView;

    @Inject(AccessIds.OBSERVABLE)
    Observable mObservable;
    @Inject(AccessIds.CARD)
    Card mCard;
    @Inject(AccessIds.TYPE)
    int mType;

    private boolean mIsEdit;
    private EditCardRequest mRequest;


    private final Observer mObserver = new Observer() {
        @Override
        public void onChanged(String key, Object obj) {
            switch (key) {
                case EditCardFragment.KEY_OBSERVABLE_ANSWER:
                    if (!Objects.equals(mCard.getAnswer(), obj.toString())) {
                        mIsEdit = true;
                    }
                    mCard.setAnswer(obj.toString());
                    break;
                case EditCardFragment.KEY_OBSERVABLE_EDIT_IMAGE:
                    if (!Objects.equals(mCard.getEditImageUrl(), obj.toString())) {
                        mIsEdit = true;
                    }
                    mCard.setEditImageUrl(obj.toString());
                    break;
            }
        }
    };

    private GamDialogFragment mLoadDialog;

    @Override
    protected void onCreate() {
        mLoadDialog = DialogFactory.createLoadDialog(getCurrentActivity());
    }

    @Override
    protected void onBind() {
        switch (mType) {
            case EditCardActivity.TYPE_EDI_OLD_CARD:
                mTitleView.setText(getString(R.string.item_edit_old_image));
                break;
            case EditCardActivity.TYPE_EDIT_NEW_CARD:
                mTitleView.setText(getString(R.string.item_edit_new_image));
                break;
        }
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
    }

    @OnClick(R.id.left_icon)
    public void onLeftClick(View view) {
        getCurrentActivity().finish();
    }

    @OnClick(R.id.right_icon)
    public void onRightClick(View view) {
        if (!mIsEdit) {
            ToastUtil.info(getCurrentActivity(), R.string.no_edit);
            return;
        }
        mLoadDialog.show(getCurrentFragment().getChildFragmentManager(), "");
        if (mRequest != null) {
            mRequest.cancel();
        }
        mRequest = new EditCardRequest(mCard);
        mRequest.enqueue(response -> {
            if (response.getError() == null && response.getData() != null) {
                ToastUtil.info(getCurrentActivity(), R.string.edit_success);
                Intent data = new Intent();
                data.putExtra(EditCardActivity.KEY_EDIT_CARD, response.getData());
                getCurrentActivity().setResult(Activity.RESULT_OK, data);
                getCurrentActivity().finish();
            } else {
                ToastUtil.info(getCurrentActivity(), R.string.edit_failure);
            }
            mLoadDialog.dismiss();
        });
    }
}
