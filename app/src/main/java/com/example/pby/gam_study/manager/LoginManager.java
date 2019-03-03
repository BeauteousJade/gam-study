package com.example.pby.gam_study.manager;

import android.content.Context;
import android.support.annotation.IntDef;

import com.example.pby.gam_study.Constants;
import com.example.pby.gam_study.network.bean.User;
import com.tencent.tauth.Tencent;

public class LoginManager {

    public static final String USER_ID = "user_id";
    private static Tencent sTencent;
    private static User mCurrentUser;

    public static Tencent getTencent(Context context) {
        if (sTencent == null) {
            sTencent = Tencent.createInstance(Constants.APP_ID, context);
        }
        return sTencent;
    }


    public static User getCurrentUser() {
        return mCurrentUser;
    }

    public static void setCurrentUser(User user) {
        mCurrentUser = user;
    }

}
