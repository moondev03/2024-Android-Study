package com.example.diffutil.utils

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.diffutil.ui.MonsterRvAdapter

class ItemTouchHelperCallback(private val recyclerView: RecyclerView) :
    ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT
    ) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        (recyclerView.adapter as? MonsterRvAdapter)?.moveItem(
            viewHolder.adapterPosition,
            target.adapterPosition
        )
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        (recyclerView.adapter as? MonsterRvAdapter)?.removeItem(viewHolder.adapterPosition)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG || actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            (viewHolder as? MonsterRvAdapter.MonsterViewHolder)?.setAlpha(0.5f)
        }
    }

    override fun clearView(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ) {
        super.clearView(recyclerView, viewHolder)
        (viewHolder as? MonsterRvAdapter.MonsterViewHolder)?.setAlpha(1.0f)
    }
}