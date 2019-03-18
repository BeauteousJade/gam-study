package com.example.pby.gam_study.page.post.presenter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.annation.Inject;
import com.example.annation.Module;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.factory.experssion.ExpressionFactory;
import com.example.pby.gam_study.factory.experssion.ExpressionFragment;
import com.example.pby.gam_study.fragment.RefreshRecyclerViewFragment;
import com.example.pby.gam_study.fragment.util.Observable;
import com.example.pby.gam_study.fragment.util.Observer;
import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Comment;
import com.example.pby.gam_study.network.bean.Post;
import com.example.pby.gam_study.network.request.Request;
import com.example.pby.gam_study.object.CommentObject;
import com.example.pby.gam_study.other.KeyboardListener;
import com.example.pby.gam_study.page.post.NewsPageFragment;
import com.example.pby.gam_study.page.post.request.CommentRequest;
import com.example.pby.gam_study.util.DisplayUtil;
import com.example.pby.gam_study.util.SoftKeyboardUtils;
import com.example.pby.gam_study.util.StringUtil;
import com.example.pby.gam_study.widget.EmojiEditText;

import java.util.Objects;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.OnClick;

@Module(RefreshRecyclerViewFragment.Context.class)
public class PostInputPresenter extends Presenter {


    @BindView(R.id.comment_container)
    View mCommentContainer;
    @BindView(R.id.fragment_container)
    View mFragmentContainer;
    @BindView(R.id.post_content)
    EmojiEditText mEmojiEditText;
    @BindView(R.id.sure)
    Button mSureButton;


    @Inject(AccessIds.OBSERVABLE)
    Observable mObservable;
    @Inject(AccessIds.RECYCLER_ADAPTER)
    BaseRecyclerAdapter<Post> mAdapter;

    private CommentObject mCommentObject;
    private int mOldHeight;
    private Request<Comment> mRequest;


    private final Observer mObserver = (key, obj) -> {
        switch (key) {
            case NewsPageFragment
                    .KEY_EXPRESSION_CLICK:
                SoftKeyboardUtils.showORhideSoftKeyboard(getCurrentActivity());
                mCommentObject = (CommentObject) obj;
                break;
        }
    };

    private final ExpressionFragment.OnExpressionClickListener mOnExpressionClickListener = fileName -> mEmojiEditText.appendExpression(fileName);

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

    private KeyboardListener mKeyboardListener;
    private boolean isClickExpression;
    private boolean isOnce;
    private float mTranslationY;

    @Override
    protected void onCreate() {
        mKeyboardListener = new KeyboardListener(getCurrentActivity()) {
            @Override
            public void onHide(int height) {
                hideInput();
            }

            @Override
            public void onShow(int height, int keyboardTop) {
                if (!isOnce) {
                    mTranslationY = keyboardTop - mCommentContainer.getY() + mOldHeight - DisplayUtil.dpToPx(getCurrentActivity(), 3);
                    ViewGroup.LayoutParams lp = mFragmentContainer.getLayoutParams();
                    lp.height = height;
                    mFragmentContainer.setLayoutParams(lp);
                    final FragmentManager fragmentManager = getCurrentFragment().getFragmentManager();
                    Fragment fragment = Objects.requireNonNull(fragmentManager).findFragmentById(mFragmentContainer.getId());
                    final FragmentTransaction transaction = fragmentManager.beginTransaction();
                    if (fragment == null) {
                        fragment = ExpressionFactory.createExpressionFragment(mOnExpressionClickListener);
                        transaction.add(mFragmentContainer.getId(), fragment);
                    }
                    transaction.commitAllowingStateLoss();
                    isOnce = true;
                }
                mCommentContainer.setTranslationY(mTranslationY);
                mEmojiEditText.requestFocus();
            }
        };
    }

    private void hideInput() {
        if (!isClickExpression) {
            mCommentContainer.setTranslationY(mCommentContainer.getHeight());
        } else {
            mCommentContainer.setTranslationY(0);
        }
        isClickExpression = false;
    }

    @Override
    protected void onBind() {
        getRootView().post(() -> {
            mOldHeight = mCommentContainer.getHeight();
            mCommentContainer.setTranslationY(mOldHeight);
        });
        mObservable.removeObserver(mObserver);
        mObservable.addObserver(mObserver);
        getCurrentActivity().getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(mKeyboardListener);
        getCurrentActivity().getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(mKeyboardListener);
        mEmojiEditText.removeTextChangedListener(mTextWatcher);
        mEmojiEditText.addTextChangedListener(mTextWatcher);
        reminderEditTextStatus();
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

    @OnClick(R.id.expression)
    public void onExpressionCLick(View view) {
        isClickExpression = true;
        SoftKeyboardUtils.showORhideSoftKeyboard(getCurrentActivity());
    }

    @OnClick(R.id.sure)
    public void onSureClick(View view) {
        if (mCommentObject == null) {
            return;
        }
        final String commentContent = Objects.requireNonNull(mEmojiEditText.getText()).toString();
        Comment comment = new Comment();
        comment.setContent(commentContent);
        comment.setFromUser(LoginManager.getCurrentUser());
        if (mCommentObject != null && mCommentObject.getData() instanceof Comment) {
            comment.setToUser(((Comment) mCommentObject.getData()).getFromUser());
        }
        mEmojiEditText.setText("");
        if (mRequest != null) {
            mRequest.cancel();
        }
        mRequest = new CommentRequest(comment);
        mRequest.enqueue();
        mAdapter.getItem(mCommentObject.getPostPosition()).getCommentList().add(comment);
        mAdapter.notifyItemChanged(mCommentObject.getPostPosition());
        mCommentObject = null;
        hideInput();
    }

    @Override
    protected void onUnBind() {
        getCurrentActivity().getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(mKeyboardListener);
        mEmojiEditText.removeTextChangedListener(mTextWatcher);
        mObservable.removeObserver(mObserver);
    }

    @Override
    protected void onDestroy() {
        getCurrentActivity().getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(mKeyboardListener);
        mEmojiEditText.removeTextChangedListener(mTextWatcher);
        mObservable.removeObserver(mObserver);
    }
}
