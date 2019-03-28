package com.example.pby.gam_study.page.newKind.presenter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.factory.GlideFactory;
import com.example.pby.gam_study.fragment.util.Observable;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.newKind.NewKindAdapter;
import com.example.pby.gam_study.page.newKind.NewKindFragment;
import com.example.pby.gam_study.page.newKind.NewKindItem;
import com.example.pby.gam_study.util.ArrayUtil;
import com.example.pby.gam_study.util.ResourcesUtil;

import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Optional;

public class NewKindItemPresenter extends Presenter {

    @Inject(AccessIds.ITEM_DATA)
    NewKindItem mNewKindItem;
    @Inject(AccessIds.ITEM_POSITION)
    int position;
    @Inject(AccessIds.OBSERVABLE)
    Observable mObservable;
    @Inject(AccessIds.RECYCLER_ADAPTER)
    NewKindAdapter mAdapter;
    @Inject(AccessIds.PAYLOAD)
    List<Object> mPayloads;

    @Nullable
    @BindView(R.id.cover)
    ImageView mImageCover;
    @Nullable
    @BindView(R.id.kind_name_edit)
    EditText mKindNameEdit;
    @Nullable
    @BindView(R.id.kind_name)
    TextView mKindNameView;
    @Nullable
    @BindView(R.id.select)
    ImageView mSelectView;

    private final TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mObservable.notifyChanged(NewKindFragment.KEY_OBSERVABLE_KIND_NAME, s.toString());
            mNewKindItem.setText(s.toString());
        }
    };

    @Override
    protected void onBind() {
        if (position == 0) {
            if (mKindNameView != null) {
                mKindNameView.setText(ResourcesUtil.getString(getCurrentActivity(), R.string.kind_name));
            }
            if (mKindNameEdit != null) {
                mKindNameEdit.setText(mNewKindItem.getText());
                mKindNameEdit.removeTextChangedListener(mTextWatcher);
                mKindNameEdit.addTextChangedListener(mTextWatcher);
            }
        } else if (position == 1) {
            if (mKindNameView != null) {
                mKindNameView.setText(ResourcesUtil.getString(getCurrentActivity(), R.string.kind_name_select));
            }
        } else {
            if (mImageCover != null) {
                if (ArrayUtil.isEmpty(mPayloads)) {
                    Glide.with(getCurrentFragment())
                            .asBitmap()
                            .apply(GlideFactory.createRequestOption(getCurrentActivity()))
                            .placeholder(R.mipmap.placeholder)
                            .load(mNewKindItem.getText())
                            .into(mImageCover);
                }
                if (mSelectView != null) {
                    mSelectView.setVisibility(mNewKindItem.isSelect() ? View.VISIBLE : View.GONE);
                }
            }
        }
    }

    @Optional
    @OnClick(R.id.cover)
    public void onCoverClick(View view) {
        List<NewKindItem> dataList = mAdapter.getDataList();
        for (NewKindItem newKindItem : dataList) {
            newKindItem.setSelect(false);
        }
        final NewKindItem kindItem = mAdapter.getDataList().get(position);
        kindItem.setSelect(!kindItem.isSelect());
        mAdapter.notifyItemRangeChanged(2, mAdapter.getItemCount() - 2, "");
        mObservable.notifyChanged(NewKindFragment.KEY_OBSERVABLE_COVER, kindItem.isSelect() ? kindItem.getText() : null);
    }

}
