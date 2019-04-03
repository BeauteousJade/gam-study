package com.example.pby.gam_study.network.bean;

import com.example.pby.gam_study.other.Diff;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class MapBean implements Diff {

    public static final String KEY = "key";
    public static final String VALUE = "value";

    @SerializedName("key")
    private String mKey;
    @SerializedName("value")
    private String mValue;

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        this.mKey = key;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        this.mValue = value;
    }


    @Override
    public boolean areItemsTheSame(Diff diff) {
        if(diff instanceof MapBean){
            return Objects.equals(mKey, ((MapBean) diff).mKey);
        }
        return false;
    }

    @Override
    public boolean onContentTheme(Diff diff) {
        if(diff instanceof MapBean) {
            return Objects.equals(mValue, ((MapBean) diff).mValue);
        }
        return false;
    }

    @Override
    public Object getChangePayload(Diff diff) {
        if(diff instanceof MapBean){
            StringBuilder stringBuilder = new StringBuilder();
            if(!Objects.equals(mKey, ((MapBean) diff).mKey)){
                stringBuilder.append(KEY);
            }
            if(!Objects.equals(mValue, ((MapBean) diff).mValue)){
                stringBuilder.append(VALUE);
            }
            return stringBuilder.toString();
        }
        return null;
    }
}
