package com.example.pby.gam_study.factory.experssion.presenter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.factory.experssion.ExpressionConvertRequest;
import com.example.pby.gam_study.factory.experssion.ExpressionFragment;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.request.Request;
import com.example.pby.gam_study.network.request.RequestCallback;
import com.example.pby.gam_study.network.response.Response;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class LoadExpressionPresenter extends Presenter {

    @Inject(AccessIds.ITEM_DATA)
    String mFileName;

    @Inject(AccessIds.LISTENER)
    ExpressionFragment.OnExpressionClickListener mOnExpressionClickListener;
    @Inject(AccessIds.RECYCLER_VIEW)
    RecyclerView mRecyclerView;

    @BindView(R.id.expression)
    ImageView mImageView;

    private final RequestCallback<Drawable> mRequestCallback = new RequestCallback<Drawable>() {
        @Override
        public void onResult(Response<Drawable> response) {
            if (response.getError() == null && response.getData() != null) {
                mImageView.setImageDrawable(response.getData());
            }

        }
    };

    private Request<Drawable> mRequest;


    @Override
    protected void onBind() {
        if (mRequest != null) {
            mRequest.cancel();
        }
        mRequest = new ExpressionConvertRequest(getCurrentActivity(), mFileName);
        mRequest.enqueue(mRequestCallback);
    }

    @Override
    protected void onUnBind() {
        if (mRequest != null) {
            mRequest.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        if (mRequest != null) {
            mRequest.cancel();
        }
    }

    @OnClick(R.id.expression)
    public void onExpressionClick(View view) {
        mOnExpressionClickListener.onExpressionClick(mFileName);
    }
}
