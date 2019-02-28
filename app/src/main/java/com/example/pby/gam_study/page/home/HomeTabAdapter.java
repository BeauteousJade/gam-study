package com.example.pby.gam_study.page.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pby.gam_study.R;
import com.example.pby.gam_study.util.ResourcesUtil;
import com.example.pby.gam_study.widget.item.ItemLayout;
import com.example.pby.gam_study.widget.item.ItemViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeTabAdapter extends ItemLayout.Adapter<HomeTabAdapter.HomeTabViewHolder> {

    private List<HomeTab> mHomeTabs;

    public HomeTabAdapter(List<HomeTab> homeTabs) {
        mHomeTabs = homeTabs;
    }

    @Override
    public int getItemCount() {
        return mHomeTabs.size();
    }

    @Override
    public HomeTabViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_home, parent, false);
        return new HomeTabViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HomeTabViewHolder homeTabViewHolder, int position) {
        final HomeTab homeTab = mHomeTabs.get(position);
        final Context context = homeTabViewHolder.getItemView().getContext();
        final int tabIcon = homeTab.isSelected() ? homeTab.getSelectedTabIcon() : homeTab.getUnSelectedTabIcon();
        homeTabViewHolder.mTabIcon.setImageDrawable(ResourcesUtil.getDrawable(homeTabViewHolder.getItemView().getContext(), tabIcon));
        homeTabViewHolder.mTabText.setText(mHomeTabs.get(position).getTabText());
        if (homeTab.isSelected()) {
            homeTabViewHolder.mTabText.setTextColor(ResourcesUtil.getColor(context, R.color.color_item_selected_blue));
        } else {
            homeTabViewHolder.mTabText.setTextColor(ResourcesUtil.getColor(context, R.color.color_item_dark));
        }
    }

    public class HomeTabViewHolder extends ItemViewHolder {

        @BindView(R.id.tab_icon)
        ImageView mTabIcon;
        @BindView(R.id.tab_text)
        TextView mTabText;

        public HomeTabViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
