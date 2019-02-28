package com.example.pby.gam_study.page.login.presenter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.request.RequestCallback;
import com.example.pby.gam_study.network.response.Response;
import com.example.pby.gam_study.page.login.CodeRequest;
import com.example.pby.gam_study.page.login.LoginEvent;
import com.example.pby.gam_study.page.login.bean.CodeBean;
import com.example.pby.gam_study.page.login.widget.CountDownTextView;
import com.example.pby.gam_study.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginPresenter extends Presenter {
    private static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    @BindView(R.id.count_down)
    CountDownTextView mCountDownTextView;
    @BindView(R.id.phone_number)
    EditText mPhoneNumber;

    private final TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (isLegalPhoneNumber(s)) {
                mCountDownTextView.enableCountDown(true);
            } else {
                resetCountDownStatus();
            }
        }
    };

    private final CodeRequest mCodeRequest = new CodeRequest();

    @Override
    protected void onBind() {
        resetCountDownStatus();
        mPhoneNumber.removeTextChangedListener(mTextWatcher);
        mPhoneNumber.addTextChangedListener(mTextWatcher);
    }

    @OnClick(R.id.count_down)
    public void onClickCountDown(View view) {
        mCodeRequest.cancel();
//        mCodeRequest.enqueue(response -> {
//            if (response.getError() != null) {
//
//            } else {
//
//            }
//        });
        ToastUtil.error(getCurrentActivity(), "test");
        resetCountDownStatus();
        mCountDownTextView.start();
    }

    @OnClick(R.id.login)
    public void onClickLogin(View view) {
        EventBus.getDefault().post(new LoginEvent(true));
    }

    @Override
    protected void onDestroy() {
        mCodeRequest.cancel();
        mCountDownTextView.stop();
    }

    private void resetCountDownStatus() {
        mCountDownTextView.setOriginText(getString(R.string.get_validate_code));
        mCountDownTextView.setCountDownRegexText(getString(R.string.count_down_regex_text));
        mCountDownTextView.enableCountDown(false);
    }

    private boolean isLegalPhoneNumber(CharSequence text) {
        return Pattern.matches(REGEX_MOBILE, text);
    }
}
