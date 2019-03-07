package com.example.pby.gam_study.page.editImage;

import android.os.Bundle;

import com.example.annation.Provides;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.fragment.BaseFragment;
import com.example.pby.gam_study.mvp.Presenter;

public class EditImageFragment extends BaseFragment {

    private static final String IMAGE_URL = "image_url";
    private String mUrl;

    public static EditImageFragment newInstance(String url) {
        EditImageFragment editImageFragment = new EditImageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(IMAGE_URL, url);
        editImageFragment.setArguments(bundle);
        return editImageFragment;
    }

    @Override
    public void onPrepareBaseContext() {
        super.onPrepareBaseContext();
        mUrl = getArguments() != null ? getArguments().getString(IMAGE_URL) : null;
    }

    @Override
    public Presenter onCreatePresenter() {
        Presenter presenter = super.onCreatePresenter();
        presenter.add(new EditImagePresenter());
        return presenter;
    }


    @Override
    public Object onCreateBaseContext() {
        Context context = new Context();
        context.mContext = (BaseFragment.Context) super.onCreateBaseContext();
        context.mUrl = mUrl;
        return context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_image;
    }

    public static class Context {
        @Provides(deepProvides = true)
        public BaseFragment.Context mContext;
        @Provides(AccessIds.URL)
        public String mUrl;
    }

}
