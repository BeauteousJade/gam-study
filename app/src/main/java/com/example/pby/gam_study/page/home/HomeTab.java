package com.example.pby.gam_study.page.home;

public class HomeTab {

    private String mTabText;
    private int mUnSelectedTabIcon;
    private int mSelectedTabIcon;
    private boolean mSelected;

    public HomeTab(String tabText, int unSelectedTabIcon, int selectedTabIcon, boolean selected) {
        this.mTabText = tabText;
        this.mUnSelectedTabIcon = unSelectedTabIcon;
        mSelectedTabIcon = selectedTabIcon;
        mSelected = selected;
    }

    public HomeTab(String tabText, int unSelectedTabIcon, int selectedTabIcon) {
        this(tabText, unSelectedTabIcon, selectedTabIcon, false);
    }

    public String getTabText() {
        return mTabText;
    }

    public int getUnSelectedTabIcon() {
        return mUnSelectedTabIcon;
    }

    public int getSelectedTabIcon() {
        return mSelectedTabIcon;
    }

    public void setTabText(String tabText) {
        mTabText = tabText;
    }

    public void setTabIcon(int tabIcon) {
        mUnSelectedTabIcon = tabIcon;
    }

    public void setSelected(boolean selected) {
        mSelected = selected;
    }

    public boolean isSelected() {
        return mSelected;
    }
}
