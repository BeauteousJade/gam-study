package com.example.pby.gam_study.page.newCard;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.mvp.Presenter;

import java.util.List;

public class NewCardAdapter extends BaseRecyclerAdapter<String> {

    private static final int TYPE_EMPTY_IMAGE = 0;
    private static final int TYPE_IMAGE = 1;
    private static final int TYPE_TITLE = 2;
    private static final int TYPE_ANSWER = 3;


    public NewCardAdapter(List<String> dataList) {
        super(dataList);
    }

    @Override
    public int getItemViewLayoutNoEmpty(int viewType) {
        switch (viewType) {
            case TYPE_EMPTY_IMAGE:
                return R.layout.item_empty_image;
            case TYPE_IMAGE:
                return R.layout.item_show_image;
            case TYPE_TITLE:

                break;
            case TYPE_ANSWER:
                break;
            default:
                break;
        }
        return 0;
    }

    @Override
    public int getItemViewTypeNoEmpty(int position) {
        if (position == 0 && mDataList.get(position) == null) {
            return TYPE_EMPTY_IMAGE;
        } else if (position == 0) {
            return TYPE_IMAGE;
        } else if (position == 1) {
            return TYPE_TITLE;
        } else if (position == 2) {
            return TYPE_ANSWER;
        }
        return super.getItemViewTypeNoEmpty(position);
    }

    @Override
    protected Presenter onCreatePresenter() {
        return null;
    }
}
