package com.example.pby.gam_study.page.card.presenter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.annation.Inject;
import com.example.annation.Module;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.fragment.dialog.GamDialogFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.browseImage.BrowseImageActivity;
import com.example.pby.gam_study.page.card.CardFragment;
import com.example.pby.gam_study.page.editCard.EditCardActivity;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

@Module(CardFragment.Context.class)
public class CardTitleBarPresenter extends Presenter implements View.OnClickListener {

    @BindView(R.id.left_icon)
    ImageView mLeftView;
    @BindView(R.id.right_icon)
    ImageView mRightView;
    @BindView(R.id.title)
    TextView mTitleView;
    @Inject(AccessIds.TITLE)
    String mKindName;
    @Inject(AccessIds.KIND_ID)
    String mKindId;


    private GamDialogFragment mDialogFragment;

    @Override
    protected void onBind() {
        mTitleView.setText(mKindName);
        mLeftView.setImageResource(R.drawable.bg_back);
        mRightView.setImageResource(R.drawable.add);
    }

    @OnClick(R.id.left_icon)
    public void onLeftClick(View view) {
        getCurrentActivity().finish();
    }

    @OnClick(R.id.right_icon)
    public void onRightClick(View view) {
        if (mDialogFragment == null) {
            mDialogFragment = new GamDialogFragment.Builder(GamDialogFragment.LocationStyle.STYLE_RIGHT_BOTTOM, R.layout.menu_card)
                    .setAnchorView(view)
                    .setOnViewClickListener(this, R.id.menu_new_card)
                    .build();
        }
        mDialogFragment.show(getCurrentFragment().getChildFragmentManager(), "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_new_card:
                EditCardActivity.startActivity(getCurrentActivity(), mKindId);
                break;
        }
        mDialogFragment.dismiss();
    }
}
