package com.example.pby.gam_study.page.sendPost.presenter;

import android.view.View;

import com.example.annation.Module;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.factory.experssion.ExpressionFactory;
import com.example.pby.gam_study.factory.experssion.ExpressionFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.sendPost.SendPostFragment;
import com.example.pby.gam_study.widget.EmojiEditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.OnClick;

@Module(SendPostFragment.Context.class)
public class ExpressionPresenter extends Presenter {


    @BindView(R.id.post_content)
    EmojiEditText mEditText;

    private final ExpressionFragment.OnExpressionClickListener mOnExpressionClickListener = fileName -> {
        mEditText.appendExpression(fileName);
    };


    @OnClick(R.id.expression)
    public void onExpressionClick(View view) {
        final FragmentManager fragmentManager = getCurrentFragment().getChildFragmentManager();
        final Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragment != null) {
            transaction.remove(fragment);
        } else {
            transaction.add(R.id.fragment_container, ExpressionFactory.createExpressionFragment(mOnExpressionClickListener));
        }
        transaction.commitAllowingStateLoss();
    }
}
