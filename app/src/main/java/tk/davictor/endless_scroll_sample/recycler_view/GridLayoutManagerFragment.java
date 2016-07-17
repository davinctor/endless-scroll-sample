package tk.davictor.endless_scroll_sample.recycler_view;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import tk.davictor.endless_scroll_sample.recycler_view.adapter.GridLayoutManagerRecyclerViewAdapter;
import tk.davictor.endless_scroll_sample.recycler_view.adapter.RecyclerViewAdapter;

/**
 * 17.07.2016
 * Created by @davinctor.
 */
public class GridLayoutManagerFragment extends RecyclerViewFragment {

    @Override
    protected void initRecyclerView(RecyclerView recyclerView) {
        // '+1' because rand.nextInt(int) generate value from [0, GRID_VIEW_MAX_COLUMNS)
        int columnCount = rand.nextInt(GRID_VIEW_MAX_COLUMNS + 1);
        columnCount = columnCount <= 1 ? GRID_VIEW_MIN_COLUMNS : columnCount;
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), columnCount);

        // Set LayoutManager
        recyclerView.setLayoutManager(layoutManager);

        /*******************************************************************************************
         *                                  MAIN PART OF THIS ALL SAMPLE
         *******************************************************************************************/
        // Set EndlessScrollListener
        EndlessRecyclerViewScrollListenerV2.create(recyclerView, layoutManager)
                .setVisibleThreshold(VISIBLE_THRESHOLD)
                .setOnLoadMoreListener(new EndlessRecyclerViewScrollListenerV2.OnLoadMoreListener() {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount) {
                        loadNextPortion(page, totalItemsCount);
                    }
                });
        /*******************************************************************************************
         *                                         END
         ******************************************************************************************/
    }

    @Override
    protected RecyclerViewAdapter initAndGetAdapter() {
        return new GridLayoutManagerRecyclerViewAdapter(getContext());
    }
}