package com.example.pby.gam_study.page.editCard;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.annation.Provides;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.factory.LayoutManagerFactory;
import com.example.pby.gam_study.fragment.RecyclerViewFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Card;
import com.example.pby.gam_study.page.editCard.presenter.EditCardTitleBarPresenter;
import com.example.pby.gam_study.page.editCard.presenter.NewCardTitleBarPresenter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class EditCardFragment extends RecyclerViewFragment {

    public static final String KEY_OBSERVABLE_ANSWER = "key_observable_answer";
    public static final String KEY_OBSERVABLE_OLD_IMAGE = "KEY_OBSERVABLE_OLD_IMAGE";
    public static final String KEY_OBSERVABLE_EDIT_IMAGE = "key_observable_edit_image";

    private String mKindId;
    private int mType = EditCardActivity.TYPE_NEW_CARD;
    private Card mCard;

    public static EditCardFragment newInstance(String kindId) {
        EditCardFragment newCardFragment = new EditCardFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EditCardActivity.KIND_ID, kindId);
        bundle.putInt(EditCardActivity.KEY_TYPE, EditCardActivity.TYPE_NEW_CARD);
        newCardFragment.setArguments(bundle);
        return newCardFragment;
    }

    public static EditCardFragment newInstance(Card card, int type) {
        EditCardFragment newCardFragment = new EditCardFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EditCardActivity.CARD, card);
        bundle.putInt(EditCardActivity.KEY_TYPE, type);
        newCardFragment.setArguments(bundle);
        return newCardFragment;
    }

    @Override
    protected void onPrepare() {
        mKindId = Objects.requireNonNull(getArguments()).getString(EditCardActivity.KIND_ID);
        mType = Objects.requireNonNull(getArguments()).getInt(EditCardActivity.KEY_TYPE);
        mCard = Objects.requireNonNull(getArguments()).getParcelable(EditCardActivity.CARD);
    }

    @Override
    protected BaseRecyclerAdapter onCreateAdapter() {
        final String imageUrl;
        final String answer;
        switch (mType) {
            case EditCardActivity.TYPE_EDI_OLD_CARD:
                imageUrl = mCard.getOldImageUrl();
                answer = mCard.getAnswer();
                break;
            case EditCardActivity.TYPE_EDIT_NEW_CARD:
                imageUrl = mCard.getEditImageUrl();
                answer = mCard.getAnswer();
                break;
            default:
                imageUrl = null;
                answer = "";
                break;
        }
        // 第一个位置为null，表示当前选择的图片还未上传，显示默认图片。
        // 第二个位置用作占位符，表示显示title。
        // 第三个位置存储答案，默认还未设置答案。
        final List<String> dataList = Arrays.asList(imageUrl, "", answer);
        return new EditCardAdapter(dataList);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T onCreateBaseContext() {
        Context context = new Context();
        context.mContext = super.onCreateBaseContext();
        context.mKindId = mKindId;
        context.mType = mType;
        context.mCard = mCard;
        return (T) context;
    }

    @Override
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return LayoutManagerFactory.createVerticalLayoutManager(requireContext());
    }

    @Override
    public Presenter onCreatePresenter() {
        final Presenter presenter = super.onCreatePresenter();
        switch (mType) {
            case EditCardActivity.TYPE_EDIT_NEW_CARD:
            case EditCardActivity.TYPE_EDI_OLD_CARD:
                presenter.add(new EditCardTitleBarPresenter());
                break;
            default:
                presenter.add(new NewCardTitleBarPresenter());
                break;
        }
        return presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recyclerview_event_title;
    }

    public static class Context {
        @Provides(deepProvides = true)
        public RecyclerViewFragment.Context mContext;
        @Provides(AccessIds.KIND_ID)
        public String mKindId;
        @Provides(AccessIds.TYPE)
        public int mType;
        @Provides(AccessIds.CARD)
        public Card mCard;
    }
}
