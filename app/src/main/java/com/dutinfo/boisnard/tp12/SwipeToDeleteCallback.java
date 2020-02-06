package com.dutinfo.boisnard.tp12;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Swipe to delete tasks
 */
class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private final MainActivity m;

    public SwipeToDeleteCallback(MainActivity m) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.m = m;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();

        // Remove the task
        m.removeElement(position);
    }
}