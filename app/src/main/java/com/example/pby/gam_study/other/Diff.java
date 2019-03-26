package com.example.pby.gam_study.other;

public interface Diff {
    boolean areItemsTheSame(Diff diff);

    boolean onContentTheme(Diff diff);

    Object getChangePayload(Diff diff);
}
