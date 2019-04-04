package com.example.pby.gam_study.page.userlist.presenter.adapter;

import android.view.View;

import com.example.annation.Inject;
import com.example.pby.gam_study.AccessIds;
import com.example.pby.gam_study.R;
import com.example.pby.gam_study.adapter.base.BaseRecyclerAdapter;
import com.example.pby.gam_study.manager.LoginManager;
import com.example.pby.gam_study.mvp.Presenter;
import com.example.pby.gam_study.network.bean.User;
import com.example.pby.gam_study.page.profile.UserProfileActivity;
import com.example.pby.gam_study.page.profile.request.UnFollowRequest;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.OnClick;

public class FollowUserItemClickPresenter extends Presenter {

    @Inject(AccessIds.ITEM_DATA)
    User mUser;
    @Inject(AccessIds.VIEW_HOLDER)
    RecyclerView.ViewHolder mViewHolder;
    @Inject(AccessIds.RECYCLER_ADAPTER)
    BaseRecyclerAdapter mAdapter;

    private UnFollowRequest mRequest;


    @OnClick(R.id.follow)
    public void onFollowClick(View view) {
        mRequest = new UnFollowRequest(LoginManager.getCurrentUser().getId(), mUser.getId());
        mRequest.enqueue();
        mAdapter.remove(mViewHolder.getAdapterPosition());
    }

    @OnClick(R.id.item_container)
    public void onItemContainerClick(View view) {
        UserProfileActivity.startActivity(getCurrentActivity(), mUser);
    }
}
