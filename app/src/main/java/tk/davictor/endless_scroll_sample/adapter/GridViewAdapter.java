package tk.davictor.endless_scroll_sample.adapter;

import android.content.Context;

import tk.davictor.endless_scroll_sample.R;

/**
 * Extends from {@link ListViewAdapter} because {@link android.widget.GridView} also can use this type of adapter
 * Created by @davinctor.
 */
public class GridViewAdapter extends ListViewAdapter {

    public GridViewAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.grid_item_user;
    }
}
