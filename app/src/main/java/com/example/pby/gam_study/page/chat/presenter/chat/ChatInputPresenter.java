package com.example.pby.gam_study.page.chat.presenter.chat;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.factory.experssion.ExpressionFactory;
import com.example.pby.gam_study.factory.experssion.ExpressionFragment;
import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Message;
import com.example.pby.gam_study.network.bean.User;
import com.example.pby.gam_study.page.chat.ChatAdapter;
import com.example.pby.gam_study.page.chat.request.SendMessageRequest;
import com.example.pby.gam_study.util.SoftKeyboardUtils;
import com.example.pby.gam_study.util.StringUtil;
import com.example.pby.gam_study.util.key.KeyboardHeightObserverImpl;
import com.example.pby.gam_study.widget.EmojiEditText;

import java.util.Objects;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class ChatInputPresenter extends Presenter {

    @BindView(R.id.comment_container)
    View mCommentContainer;
    @BindView(R.id.fragment_container)
    View mFragmentContainer;
    @BindView(R.id.post_content)
    EmojiEditText mEmojiEditText;
    @BindView(R.id.sure)
    Button mSureButton;

    @Inject(AccessIds.RECYCLER_ADAPTER)
    ChatAdapter mAdapter;
    @Inject(AccessIds.USER)
    User mUser;
    @Inject(AccessIds.RECYCLER_VIEW)
    RecyclerView mRecyclerView;


    private boolean mIsHideExpression = true;
    private boolean isOnce;
    private int mDefaultTranslationY;
    private SendMessageRequest mRequest;

    private final ExpressionFragment.OnExpressionClickListener mOnExpressionClickListener = fileName -> mEmojiEditText.appendExpression(fileName);
    private final KeyboardHeightObserverImpl mKeyboardListener = new KeyboardHeightObserverImpl() {
        @Override
        public void onHide() {
            hideInput();
        }

        @Override
        public void onShow(int height) {
            if (!isOnce) {
                ViewGroup.LayoutParams lp = mFragmentContainer.getLayoutParams();
                lp.height = height;
                mFragmentContainer.setLayoutParams(lp);
                mFragmentContainer.requestLayout();
                isOnce = true;
            }
            mIsHideExpression = true;
            mCommentContainer.setTranslationY(0);
            mEmojiEditText.requestFocus();
            mDefaultTranslationY += height - mDefaultTranslationY;
        }
    };
    private final TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            reminderEditTextStatus();
        }
    };

    @Override
    protected void onBind() {
        mDefaultTranslationY = (int) mCommentContainer.getTranslationY();
        reminderEditTextStatus();
        getCurrentActivity().removeKeyboardHeightObserver(mKeyboardListener);
        getCurrentActivity().addKeyboardHeightObserver(mKeyboardListener);
        addExpressionFragmentIfNeed();
        mEmojiEditText.removeTextChangedListener(mTextWatcher);
        mEmojiEditText.addTextChangedListener(mTextWatcher);
    }

    @Override
    protected void onDestroy() {
        getCurrentActivity().removeKeyboardHeightObserver(mKeyboardListener);
        mEmojiEditText.removeTextChangedListener(mTextWatcher);
    }

    private void addExpressionFragmentIfNeed() {
        final FragmentManager fragmentManager = getCurrentFragment().getFragmentManager();
        Fragment fragment = Objects.requireNonNull(fragmentManager).findFragmentById(mFragmentContainer.getId());
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragment == null) {
            fragment = ExpressionFactory.createExpressionFragment(mOnExpressionClickListener);
            transaction.add(mFragmentContainer.getId(), fragment);
        }
        transaction.commitAllowingStateLoss();
    }

    private void hideInput() {
        if (mIsHideExpression) {
            mCommentContainer.setTranslationY(mDefaultTranslationY);
        } else {
            mCommentContainer.setTranslationY(0);
        }
        mIsHideExpression = true;
    }

    @OnClick(R.id.expression)
    public void onExpressionCLick(View view) {
        if (SoftKeyboardUtils.isSoftShowing(getCurrentActivity())) {
            mIsHideExpression = false;
            SoftKeyboardUtils.showORhideSoftKeyboard(getCurrentActivity());
        } else {
            if (mCommentContainer.getTranslationY() == 0) {
                mCommentContainer.animate().translationY(mDefaultTranslationY).setDuration(200).start();
            } else {
                mCommentContainer.animate().translationY(0).setDuration(200).start();
            }
        }
    }


    @OnClick(R.id.sure)
    public void onSureClick(View view) {
        String content = Objects.requireNonNull(mEmojiEditText.getText()).toString();
        if (StringUtil.isEmpty(content)) {
            return;
        }
        Message message = new Message();
        message.setToUser(mUser);
        message.setFromUser(LoginManager.getCurrentUser());
        message.setContent(content);
        message.setSendUserId(LoginManager.getCurrentUser().getId());
        message.setTime(System.currentTimeMillis());
        mAdapter.addItem(message);
        mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
        mEmojiEditText.setText("");
        mIsHideExpression = true;
        hideInput();
        mRequest = new SendMessageRequest(message);
        mRequest.enqueue();
    }

    private void reminderEditTextStatus() {
        if (StringUtil.isEmpty(mEmojiEditText.getText())) {
            mSureButton.setSelected(true);
            mSureButton.setEnabled(false);
        } else {
            mSureButton.setSelected(false);
            mSureButton.setEnabled(true);
        }
    }
}
