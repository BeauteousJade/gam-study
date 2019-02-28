package com.example.pby.gam_study.page.home.page;

import com.example.pby.gam_study.fragment.tabhost.TabHost;

public interface HomePage extends TabHost {
    String getTitle();

    default int getRightIcon() {
        return 0;
    }

    default int getLeftIcon() {
        return 0;
    }
}
