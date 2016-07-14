package tk.davictor.endless_scroll_sample;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.Random;

import tk.davictor.endless_scroll_sample.adapter.GridViewAdapter;
import tk.davictor.endless_scroll_sample.model.User;

/**
 *
 * Sample with using {@link GridView} with {@link EndlessScrollListener}
 *
 * 14.07.2016
 * Created by @davinctor.
 */
public class GridViewFragment extends AdapterViewFragment {

    private static final Random rand = new Random();
    private static final int GRID_VIEW_MIN_COLUMNS = 2;
    private static final int GRID_VIEW_MAX_COLUMNS = 3;

    @Override
    protected int getLayoutResId() {
        return R.layout.content_main_grid_view;
    }

    @Override
    protected AbsListView initAndGetAdapterView(View rootView) {
        GridView gridView = (GridView) rootView.findViewById(R.id.grid_view_list);

        // '+1' because rand.nextInt(int) generate value from [0, GRID_VIEW_MAX_COLUMNS)
        int columnCount = rand.nextInt(GRID_VIEW_MAX_COLUMNS + 1);
        columnCount = columnCount <= 1 ? GRID_VIEW_MIN_COLUMNS : columnCount;
        gridView.setNumColumns(columnCount);

        return gridView;
    }

    @Override
    protected ArrayAdapter<User> initAndGetAdapter() {
        return new GridViewAdapter(getContext());
    }
}
