package tk.davictor.endless_scroll_sample.recycler_view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;

import tk.davictor.endless_scroll_sample.R;

/**
 *
 * RecyclerViewAdapter for using with LinearLayoutManager in RecyclerView
 *
 * 17.07.2016
 * Created by @davinctor.
 */
public class LinearLayoutRecyclerViewAdapter extends RecyclerViewAdapter {

    public LinearLayoutRecyclerViewAdapter(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int layoutResId() {
        return R.layout.list_item_user;
    }
}
