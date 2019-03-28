package com.example.pby.gam_study.page.card.presenter;

import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.page.card.request.UpdateTimeRequest;

public class CardUpdateTimePresenter extends Presenter {

    @Inject(AccessIds.KIND_ID)
    String mKindId;

    private UpdateTimeRequest mRequest;

    @Override
    protected void onBind() {
        if (mRequest != null) {
            mRequest.cancel();
        }
        mRequest = new UpdateTimeRequest(mKindId);
        mRequest.enqueue();
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
}