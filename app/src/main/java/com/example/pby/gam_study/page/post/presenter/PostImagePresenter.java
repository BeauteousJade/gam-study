package com.example.pby.gam_study.page.post.presenter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blade.annotation.Inject;
import com.bumptech.glide.Glide;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.Post;
import com.example.pby.gam_study.page.browseImage.BrowseImageActivity;
import com.example.pby.gam_study.util.ArrayUtil;
import com.example.pby.gam_study.util.DisplayUtil;
import com.example.pby.gam_study.widget.ForegroundImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PostImagePresenter extends Presenter {

    @Inject(AccessIds.ITEM_DATA)
    Post mPost;

    @BindView(R.id.image_container)
    LinearLayout mImageContainer;

    @Override
    protected void onBind() {
        mImageContainer.removeAllViews();
        final List<String> imageUrlList = mPost.getImageUrlList();
        if (ArrayUtil.isEmpty(imageUrlList)) {
            mImageContainer.setVisibility(View.GONE);
        } else {
            mImageContainer.setVisibility(View.VISIBLE);
            int count = Math.min(3, imageUrlList.size());
            for (int i = 0; i < count; i++) {
                ForegroundImageView imageView = new ForegroundImageView(getCurrentActivity());
                if (i == count - 1 && imageUrlList.size() > 3) {
                    imageView.setForegroundDrawable(getDrawable(R.drawable.fg_add));
                }
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DisplayUtil.dpToPx(getCurrentActivity(), 80), LinearLayout.LayoutParams.MATCH_PARENT);
                if (i > 0 && imageUrlList.size() > 1) {
                    lp.leftMargin = DisplayUtil.dpToPx(getCurrentActivity(), 10);
                }
                imageView.setLayoutParams(lp);
                mImageContainer.addView(imageView);
                Glide.with(getCurrentFragment())
                        .asBitmap()
                        .placeholder(R.mipmap.placeholder)
                        .error(R.mipmap.placeholder)
                        .load(imageUrlList.get(i))
                        .into(imageView);

                imageView.setOnClickListener(v -> BrowseImageActivity.startActivity(getCurrentActivity(), (ArrayList<String>) mPost.getImageUrlList()));
            }
        }
    }
}
