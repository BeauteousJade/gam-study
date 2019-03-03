package com.example.pby.gam_study.factory;

import android.app.Activity;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.fragment.dialog.GamDialogFragment;

public class LoadDialogFactory {

    public static GamDialogFragment getLoadDialog(Activity activity) {
        return new GamDialogFragment.Builder(GamDialogFragment.LocationStyle.STYLE_CENTER, R.layout.load_layout)
                .setAnchorView(activity.findViewById(android.R.id.content))
                .setCancel(false)
                .setCanceledOnTouchOutside(false)
                .build();
    }
}
