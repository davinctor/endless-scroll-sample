package tk.davictor.endless_scroll_sample.recycler_view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import tk.davictor.endless_scroll_sample.recycler_view.adapter.LinearLayoutRecyclerViewAdapter;
import tk.davictor.endless_scroll_sample.recycler_view.adapter.RecyclerViewAdapter;

/**
 * 17.07.2016
 * Created by @davinctor.
 */
public class LinearLayoutManagerFragment extends RecyclerViewFragment {

    @Override
    protected void initRecyclerView(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        // Set LayoutManager
        recyclerView.setLayoutManager(layoutManager);

        /*******************************************************************************************
         *                                  MAIN PART OF THIS ALL SAMPLE
         *******************************************************************************************/
        // Set EndlessScrollListener
        EndlessRecyclerViewScrollListener.create(recyclerView, layoutManager, VISIBLE_THRESHOLD)
                .setOnLoadMoreListener(new EndlessRecyclerViewScrollListener.OnLoadMoreListener() {
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
        return new LinearLayoutRecyclerViewAdapter(getContext());
    }
}
