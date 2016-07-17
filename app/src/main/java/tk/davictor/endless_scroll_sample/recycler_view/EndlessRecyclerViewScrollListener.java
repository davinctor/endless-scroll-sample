package tk.davictor.endless_scroll_sample.recycler_view;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * 17.07.2016
 * Created by @davinctor.
 */
public class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 5; // default value
    // The current offset index of data you have loaded
    private int currentPage = 0;
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    // True if we are still waiting for the last set of data to load.
    private boolean loading = true;
    // Sets the starting page index
    private int startingPageIndex = 0;

    // The field also use when EndlessRecyclerViewScrollListener use for GridLayoutManager
    private LinearLayoutManager linearLayoutManager;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    private OnLoadMoreListener onLoadMoreListener;

    public static EndlessRecyclerViewScrollListener create(@NonNull RecyclerView recyclerView,
                                                           @NonNull LinearLayoutManager layoutManager) {
        EndlessRecyclerViewScrollListener listener = new EndlessRecyclerViewScrollListener(layoutManager);
        recyclerView.addOnScrollListener(listener);
        return listener;
    }

    public static EndlessRecyclerViewScrollListener create(@NonNull RecyclerView recyclerView,
                                                           @NonNull GridLayoutManager layoutManager) {
        EndlessRecyclerViewScrollListener listener = new EndlessRecyclerViewScrollListener(layoutManager);
        recyclerView.addOnScrollListener(listener);
        return listener;
    }

    public static EndlessRecyclerViewScrollListener create(@NonNull RecyclerView recyclerView,
                                                           @NonNull StaggeredGridLayoutManager layoutManager) {
        EndlessRecyclerViewScrollListener listener = new EndlessRecyclerViewScrollListener(layoutManager);
        recyclerView.addOnScrollListener(listener);
        return listener;
    }

    public static EndlessRecyclerViewScrollListener create(@NonNull RecyclerView recyclerView,
                                                           @NonNull LinearLayoutManager layoutManager,
                                                           int visibleThreshold) {
        EndlessRecyclerViewScrollListener listener =
                new EndlessRecyclerViewScrollListener(layoutManager, visibleThreshold);
        recyclerView.addOnScrollListener(listener);
        return listener;
    }

    public static EndlessRecyclerViewScrollListener create(@NonNull RecyclerView recyclerView,
                                                           @NonNull GridLayoutManager layoutManager,
                                                           int visibleThreshold) {
        EndlessRecyclerViewScrollListener listener =
                new EndlessRecyclerViewScrollListener(layoutManager, visibleThreshold);
        recyclerView.addOnScrollListener(listener);
        return listener;
    }

    public static EndlessRecyclerViewScrollListener create(@NonNull RecyclerView recyclerView,
                                                           @NonNull StaggeredGridLayoutManager layoutManager,
                                                           int visibleThreshold) {
        EndlessRecyclerViewScrollListener listener =
                new EndlessRecyclerViewScrollListener(layoutManager, visibleThreshold);
        recyclerView.addOnScrollListener(listener);
        return listener;
    }

    public EndlessRecyclerViewScrollListener(LinearLayoutManager layoutManager, int visibleThreshold) {
        this.linearLayoutManager = layoutManager;
        this.visibleThreshold = visibleThreshold;
    }

    public EndlessRecyclerViewScrollListener(GridLayoutManager layoutManager, int visibleThreshold) {
        // GridLayoutManager extends from LinearLayoutManager and doesn't override
        // #findLastVisibleItemPosition() and #findFirstVisibleItemPosition methods
        this.linearLayoutManager = layoutManager;
        this.visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
    }

    public EndlessRecyclerViewScrollListener(StaggeredGridLayoutManager layoutManager, int visibleThreshold) {
        this.staggeredGridLayoutManager = layoutManager;
        this.visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
    }

    public EndlessRecyclerViewScrollListener(LinearLayoutManager layoutManager) {
        this.linearLayoutManager = layoutManager;
    }

    public EndlessRecyclerViewScrollListener(GridLayoutManager layoutManager) {
        // GridLayoutManager extends from LinearLayoutManager and doesn't override
        // #findLastVisibleItemPosition() and #findFirstVisibleItemPosition methods
        this.linearLayoutManager = layoutManager;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
    }

    public EndlessRecyclerViewScrollListener(StaggeredGridLayoutManager layoutManager) {
        this.staggeredGridLayoutManager = layoutManager;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
    }

    protected int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.
    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        int lastVisibleItemPosition = 0;
        int totalItemCount = 0;

        // Get actual amount of elements in RecyclerView and position of last visible one.
        if (linearLayoutManager != null) {
            totalItemCount = linearLayoutManager.getItemCount();
            lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
        } else if (staggeredGridLayoutManager != null) {
            totalItemCount = staggeredGridLayoutManager.getItemCount();
            int[] lastVisibleItemPositions = staggeredGridLayoutManager.findLastVisibleItemPositions(null);
            // get maximum element within the list
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
        }

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            currentPage = this.startingPageIndex;
            previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                loading = true;
            }
        }

        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many total columns there are too
        if (!loading && (lastVisibleItemPosition + visibleThreshold > totalItemCount)) {
            currentPage++;
            if (onLoadMoreListener != null) {
                onLoadMoreListener.onLoadMore(currentPage, totalItemCount);
            } else {
                onLoadMore(currentPage, totalItemCount);
            }
            loading = true;
        }
    }

    // Defines the process for actually loading more data based on page
    public void onLoadMore(int page, int totalItemsCount){
        /* NOP */
    }

    public interface OnLoadMoreListener {
        void onLoadMore(int page, int totalItemsCount);
    }

}