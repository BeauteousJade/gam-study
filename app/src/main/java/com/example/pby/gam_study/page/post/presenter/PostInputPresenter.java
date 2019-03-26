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
import com.example.pby.gam_study.network.bean.User;
import com.example.pby.gam_study.network.request.Request;
import com.example.pby.gam_study.object.CommentObject;
import com.example.pby.gam_study.page.post.PostFragment;
import com.example.pby.gam_study.page.post.PostLinearLayoutManager;
import com.example.pby.gam_study.page.post.request.CommentRequest;
import com.example.pby.gam_study.util.SoftKeyboardUtils;
import com.example.pby.gam_study.util.StringUtil;
import com.example.pby.gam_study.util.key.KeyboardHeightObserverImpl;
import com.example.pby.gam_study.widget.EmojiEditText;
import com.example.pby.gam_study.widget.viewpager2.ViewPager2;

import java.util.Objects;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
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
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;


    @Inject(AccessIds.OBSERVABLE)
    Observable mObservable;
    @Inject(AccessIds.RECYCLER_ADAPTER)
    BaseRecyclerAdapter<Post> mAdapter;
    @Inject(AccessIds.LAYOUT_MANAGER)
    PostLinearLayoutManager mLayoutManager;

    private CommentObject mCurrentCommentObject;
    private CommentObject mPreCommentObject;
    private int mOldHeight;
    private Request<Comment> mRequest;


    private final Observer mObserver = (key, obj) -> {
        switch (key) {
            case PostFragment
                    .KEY_EXPRESSION_CLICK:
            case PostFragment.KEY_ADD_COMMENT:
                mPreCommentObject = mCurrentCommentObject;
                mCurrentCommentObject = (CommentObject) obj;
                SoftKeyboardUtils.showORhideSoftKeyboard(getCurrentActivity());

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

    private KeyboardHeightObserverImpl mKeyboardListener;
    private boolean mIsHideInput;
    private boolean isOnce;
    private ViewPager2 mViewPager2;

    @Override
    protected void onCreate() {
        mKeyboardListener = new KeyboardHeightObserverImpl() {
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
                mCommentContainer.setTranslationY(0);
                mEmojiEditText.requestFocus();
                resetEditTextContentIfNeed();
                enableScroll(false);
            }
        };
    }

    private void hideInput() {
        if (!mIsHideInput) {
            mCommentContainer.setTranslationY(mCommentContainer.getHeight());
            enableScroll(true);
        } else {
            mCommentContainer.setTranslationY(0);
        }
        mIsHideInput = false;
    }

    private void enableScroll(boolean isScroll) {
        mViewPager2.setCanScrollHorizontally(isScroll);
        mLayoutManager.setCanScrollVertically(isScroll);
        mRefreshLayout.setEnabled(isScroll);
    }


    private void resetEditTextContentIfNeed() {
        if (mPreCommentObject == null || mPreCommentObject.getData() != mCurrentCommentObject.getData()) {
            mEmojiEditText.setText("");
            User toUser = null;
            if (mCurrentCommentObject.getData() instanceof Post) {
                toUser = ((Post) mCurrentCommentObject.getData()).getUser();
            } else if (mCurrentCommentObject.getData() instanceof Comment) {
                toUser = ((Comment) mCurrentCommentObject.getData()).getToUser();
            }
            if (toUser != null) {
                mEmojiEditText.setHint(getString(R.string.reply) + " " + toUser.getNickName());
            }
        }
    }

    @Override
    protected void onBind() {
        mViewPager2 = getCurrentActivity().findViewById(R.id.viewPager2);
        getRootView().post(() -> {
            mOldHeight = mCommentContainer.getHeight();
            mCommentContainer.setTranslationY(mOldHeight);
        });
        mObservable.removeObserver(mObserver);
        mObservable.addObserver(mObserver);
        getCurrentActivity().removeKeyboardHeightObserver(mKeyboardListener);
        getCurrentActivity().addKeyboardHeightObserver(mKeyboardListener);
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
        mIsHideInput = true;
        SoftKeyboardUtils.showORhideSoftKeyboard(getCurrentActivity());
    }

    @OnClick(R.id.sure)
    public void onSureClick(View view) {
        if (mCurrentCommentObject == null) {
            return;
        }
        final String commentContent = Objects.requireNonNull(mEmojiEditText.getText()).toString();
        Comment comment = new Comment();
        comment.setId(StringUtil.generateId());
        comment.setContent(commentContent);
        comment.setFromUser(LoginManager.getCurrentUser());
        if (mCurrentCommentObject.getData() instanceof Comment) {
            comment.setToUser(((Comment) mCurrentCommentObject.getData()).getFromUser());
        }
        comment.setPostId(mCurrentCommentObject.getPostId());
        mEmojiEditText.setText("");
        if (mRequest != null) {
            mRequest.cancel();
        }
        mRequest = new CommentRequest(comment);
        mRequest.enqueue();
        mAdapter.getItem(mCurrentCommentObject.getPostPosition()).getCommentList().add(comment);
        mAdapter.notifyItemChanged(mCurrentCommentObject.getPostPosition(), Post.COMMENT_PAY_LOAD);
        mCurrentCommentObject = null;
        hideInput();
        SoftKeyboardUtils.hideSoftKeyboard(getCurrentActivity());
    }

    @Override
    protected void onUnBind() {
        getCurrentActivity().removeKeyboardHeightObserver(mKeyboardListener);
        mEmojiEditText.removeTextChangedListener(mTextWatcher);
        mObservable.removeObserver(mObserver);
    }

    @Override
    protected void onDestroy() {
        getCurrentActivity().removeKeyboardHeightObserver(mKeyboardListener);
        mEmojiEditText.removeTextChangedListener(mTextWatcher);
        mObservable.removeObserver(mObserver);
    }
}
