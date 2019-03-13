package com.example.pby.gam_study.page.editCard.presenter;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.annation.Inject;
import com.example.annation.Module;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.fragment.util.Observable;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.editCard.EditCardFragment;

import butterknife.BindView;

@Module(BaseRecyclerAdapter.Context.class)
public class AnswerPresenter extends Presenter {

    @Inject(AccessIds.ITEM_POSITION)
    int mPosition;
    @Inject(AccessIds.ITEM_DATA)
    String mAnswer;
    @Inject(AccessIds.RECYCLER_ADAPTER)
    BaseRecyclerAdapter mAdapter;
    @Inject(AccessIds.OBSERVABLE)
    Observable mObservable;
    @BindView(R.id.answer)
    EditText mEditText;


    private final TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @SuppressWarnings("unchecked")
        @Override
        public void afterTextChanged(Editable s) {
            mAdapter.getDataList().set(mPosition, s.toString());
            mObservable.notifyChanged(EditCardFragment.KEY_OBSERVABLE_ANSWER, s.toString());
        }
    };

    @Override
    protected void onBind() {
        mEditText.addTextChangedListener(mTextWatcher);
        mEditText.setText(mAnswer);
    }

    @Override
    protected void onUnBind() {
        mEditText.addTextChangedListener(mTextWatcher);
    }

    @Override
    protected void onDestroy() {
        mEditText.removeTextChangedListener(mTextWatcher);
    }
}
