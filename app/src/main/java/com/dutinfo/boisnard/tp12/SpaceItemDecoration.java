package com.dutinfo.boisnard.tp12;


import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

/**
 * Space between items
 */
class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int space;
    private final boolean addSpaceAboveFirstItem;
    private final boolean addSpaceBelowLastItem;

    public SpaceItemDecoration(int space, boolean addSpaceAboveFirstItem,
                               boolean addSpaceBelowLastItem) {
        this.space = space;
        this.addSpaceAboveFirstItem = addSpaceAboveFirstItem;
        this.addSpaceBelowLastItem = addSpaceBelowLastItem;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (space <= 0) {
            return;
        }

        if (addSpaceAboveFirstItem && parent.getChildLayoutPosition(view) < 1
                || parent.getChildLayoutPosition(view) >= 1) {
            if (getOrientation(parent) == LinearLayoutManager.VERTICAL) {
                outRect.top = space;
            } else {
                outRect.left = space;
            }
        }

        if (addSpaceBelowLastItem
                && parent.getChildAdapterPosition(view) == getTotalItemCount(parent) - 1) {
            if (getOrientation(parent) == LinearLayoutManager.VERTICAL) {
                outRect.bottom = space;
            } else {
                outRect.right = space;
            }
        }
    }

    private int getTotalItemCount(RecyclerView parent) {
        return Objects.requireNonNull(parent.getAdapter()).getItemCount();
    }

    private int getOrientation(RecyclerView parent) {
        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
        } else {
            throw new IllegalStateException(
                    "SpaceItemDecoration can only be used with a LinearLayoutManager.");
        }
    }
}