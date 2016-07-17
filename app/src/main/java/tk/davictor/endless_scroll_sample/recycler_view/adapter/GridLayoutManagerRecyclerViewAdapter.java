package tk.davictor.endless_scroll_sample.recycler_view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;

import tk.davictor.endless_scroll_sample.R;

/**
 *
 * RecyclerViewAdapter for using with GridLayoutManager in RecyclerView
 *
 * 17.07.2016
 * Created by @davinctor.
 */
public class GridLayoutManagerRecyclerViewAdapter extends RecyclerViewAdapter {

    public GridLayoutManagerRecyclerViewAdapter(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int layoutResId() {
        return R.layout.grid_item_user;
    }
}
