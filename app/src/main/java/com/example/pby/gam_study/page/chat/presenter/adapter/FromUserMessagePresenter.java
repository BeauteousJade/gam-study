package com.example.pby.gam_study.page.chat.presenter.adapter;

import android.widget.ImageView;

import com.blade.annotation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.GlideApp;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.factory.GlideFactory;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Message;
import com.example.pby.gam_study.widget.EmojiTextView;

import butterknife.BindView;

public class FromUserMessagePresenter extends Presenter {

    @Inject(AccessIds.ITEM_DATA)
    Message mMessage;

    @BindView(R.id.avatar)
    ImageView mAvatarView;
    @BindView(R.id.content)
    EmojiTextView mContentView;

    @Override
    protected void onBind() {
        GlideApp.with(getCurrentFragment())
                .asBitmap()
                .apply(GlideFactory.createCircleOption())
                .load(mMessage.getFromUser().getHead())
                .into(mAvatarView);

        mContentView.setContent(mMessage.getContent());
    }
}
