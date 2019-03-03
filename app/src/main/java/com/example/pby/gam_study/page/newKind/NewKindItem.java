package com.example.pby.gam_study.page.newKind;

public class NewKindItem {

    private boolean isSelect;
    private String text;

    public NewKindItem(boolean isSelect, String text) {
        this.isSelect = isSelect;
        this.text = text;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
