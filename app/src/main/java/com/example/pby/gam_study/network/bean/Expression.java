package com.example.pby.gam_study.network.bean;

import android.graphics.drawable.Drawable;

public class Expression {

    private String mFileName;
    private Drawable mDrawable;

    public Expression(String fileName, Drawable drawable) {
        this.mFileName = fileName;
        this.mDrawable = drawable;
    }

    public String getFileName() {
        return mFileName;
    }

    public void setFileName(String fileName) {
        this.mFileName = fileName;
    }

    public Drawable getDrawable() {
        return mDrawable;
    }

    public void setDrawable(Drawable drawable) {
        this.mDrawable = drawable;
    }
}
