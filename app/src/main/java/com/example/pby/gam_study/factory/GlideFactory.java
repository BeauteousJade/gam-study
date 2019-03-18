package com.example.pby.gam_study.factory;

import android.content.Context;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.util.ResourcesUtil;

public class GlideFactory {

    public static RequestOptions createRequestOption(int corner) {
        final RoundedCorners roundedCorners = new RoundedCorners(corner);
        return RequestOptions.bitmapTransform(roundedCorners);
    }

    public static RequestOptions createRequestOption(Context context) {
        final int radius = ResourcesUtil.getDimens(context, R.dimen.radius);
        return createRequestOption(radius);
    }

    public static RequestOptions createCircleOption() {
        final CircleCrop circleCrop = new CircleCrop();
        return RequestOptions.bitmapTransform(circleCrop);
    }
}
