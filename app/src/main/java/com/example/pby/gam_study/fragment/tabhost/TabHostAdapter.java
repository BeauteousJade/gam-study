package com.example.pby.gam_study.fragment.tabhost;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TabHostAdapter extends RecyclerView.Adapter<TabHostAdapter.TabHostViewHolder> {


    @NonNull
    @Override
    public TabHostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TabHostViewHolder tabHostViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class TabHostViewHolder extends RecyclerView.ViewHolder {

        View mContainerView;

        public TabHostViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
