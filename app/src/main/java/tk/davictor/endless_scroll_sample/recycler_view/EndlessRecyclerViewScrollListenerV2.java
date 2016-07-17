package tk.davictor.endless_scroll_sample.recycler_view;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Endless scroll listener implementation for RecyclerView with more convenient interface (as for me)
 * @see  <a href="http://davinctor.tk/endless_scrolling_listview_gridview_recyclerview">Link to article</a>
 *
 * 17.07.2016
 * Created by @davinctor.
 */
@SuppressWarnings("unused")
public class EndlessRecyclerViewScrollListenerV2 extends RecyclerView.OnScrollListener {
    private static final int DEFAULT_VISIBLE_THRESHOLD = 5;
    private static final int DEFAULT_STARTING_PAGE_INDEX = 0;

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold;
    // The current offset index of data you have loaded
    private int currentPage;
    // The total number of items in the data set after the last load
    private int previousTotalItemCount = 0;
    // True if we are still waiting for the last set of data to load.
    private boolean loading = true;
    // Sets the starting page index
    private int startingPageIndex;

    // The field also use when EndlessRecyclerViewScrollListenerV2 use for GridLayoutManager
    private LinearLayoutManager linearLayoutManager;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    private OnLoadMoreListener onLoadMoreListener;

    /**
     * Create instance of EndlessRecyclerViewScrollListenerV2 with defaults
     * @param recyclerView recycler view
     * @param layoutManager layout manager
     * @return instance of {@link EndlessRecyclerViewScrollListenerV2}
     */
    public static EndlessRecyclerViewScrollListenerV2 create(@NonNull RecyclerView recyclerView,
                                                             @NonNull LinearLayoutManager layoutManager) {
        return create(recyclerView, layoutManager, DEFAULT_VISIBLE_THRESHOLD, DEFAULT_STARTING_PAGE_INDEX);
    }

    /**
     * Create instance of EndlessRecyclerViewScrollListenerV2 with defaults
     * @param recyclerView recycler view
     * @param layoutManager layout manager
     * @return instance of {@link EndlessRecyclerViewScrollListenerV2}
     */
    public static EndlessRecyclerViewScrollListenerV2 create(@NonNull RecyclerView recyclerView,
                                                             @NonNull GridLayoutManager layoutManager) {
        return create(recyclerView, layoutManager, DEFAULT_VISIBLE_THRESHOLD, DEFAULT_STARTING_PAGE_INDEX);
    }

    /**
     * Create instance of EndlessRecyclerViewScrollListenerV2 with defaults
     * @param recyclerView recycler view
     * @param layoutManager layout manager
     * @return instance of {@link EndlessRecyclerViewScrollListenerV2}
     */
    public static EndlessRecyclerViewScrollListenerV2 create(@NonNull RecyclerView recyclerView,
                                                             @NonNull StaggeredGridLayoutManager layoutManager) {
        return create(recyclerView, layoutManager, DEFAULT_VISIBLE_THRESHOLD, DEFAULT_STARTING_PAGE_INDEX);
    }

    /**
     * Create instance of EndlessRecyclerViewScrollListenerV2 with visibleThreshold
     * @param recyclerView recycler view
     * @param layoutManager layout manager
     * @param visibleThreshold  The minimum amount of items to have below your current scroll position
     *                          before loading more.
     * @return instance of {@link EndlessRecyclerViewScrollListenerV2}
     */
    public static EndlessRecyclerViewScrollListenerV2 create(@NonNull RecyclerView recyclerView,
                                                             @NonNull LinearLayoutManager layoutManager,
                                                             int visibleThreshold) {
        return create(recyclerView, layoutManager, visibleThreshold, DEFAULT_STARTING_PAGE_INDEX);
    }

    /**
     * Create instance of EndlessRecyclerViewScrollListenerV2 with visibleThreshold
     * @param recyclerView recycler view
     * @param layoutManager layout manager
     * @param visibleThreshold  The minimum amount of items to have below your current scroll position
     *                          before loading more.
     * @return instance of {@link EndlessRecyclerViewScrollListenerV2}
     */
    public static EndlessRecyclerViewScrollListenerV2 create(@NonNull RecyclerView recyclerView,
                                                             @NonNull GridLayoutManager layoutManager,
                                                             int visibleThreshold) {
        return create(recyclerView, layoutManager, visibleThreshold, DEFAULT_STARTING_PAGE_INDEX);
    }

    /**
     * Create instance of EndlessRecyclerViewScrollListenerV2 with visibleThreshold
     * @param recyclerView recycler view
     * @param layoutManager layout manager
     * @param visibleThreshold  The minimum amount of items to have below your current scroll position
     *                          before loading more.
     * @return instance of {@link EndlessRecyclerViewScrollListenerV2}
     */
    public static EndlessRecyclerViewScrollListenerV2 create(@NonNull RecyclerView recyclerView,
                                                             @NonNull StaggeredGridLayoutManager layoutManager,
                                                             int visibleThreshold) {
        return create(recyclerView, layoutManager, visibleThreshold, DEFAULT_STARTING_PAGE_INDEX);
    }

    /**
     * Create instance of EndlessRecyclerViewScrollListenerV2 with visibleThreshold and startingPageIndex
     * @param recyclerView recycler view
     * @param layoutManager layout manager
     * @param visibleThreshold  The minimum amount of items to have below your current scroll position
     *                          before loading more.
     * @param startingPageIndex Starting page index. When data at first load or was reset - current
     *                          page will be equal to param startingPageIndex.
     * @return instance of {@link EndlessRecyclerViewScrollListenerV2}
     */
    public static EndlessRecyclerViewScrollListenerV2 create(@NonNull RecyclerView recyclerView,
                                                             @NonNull LinearLayoutManager layoutManager,
                                                             int visibleThreshold,
                                                             int startingPageIndex) {
        EndlessRecyclerViewScrollListenerV2 listener =
                new EndlessRecyclerViewScrollListenerV2(layoutManager, visibleThreshold, startingPageIndex);
        recyclerView.addOnScrollListener(listener);
        return listener;
    }

    /**
     * Create instance of EndlessRecyclerViewScrollListenerV2 with visibleThreshold and startingPageIndex
     * @param recyclerView recycler view
     * @param layoutManager layout manager
     * @param visibleThreshold  The minimum amount of items to have below your current scroll position
     *                          before loading more.
     * @param startingPageIndex Starting page index. When data at first load or was reset - current
     *                          page will be equal to param startingPageIndex.
     * @return instance of {@link EndlessRecyclerViewScrollListenerV2}
     */
    public static EndlessRecyclerViewScrollListenerV2 create(@NonNull RecyclerView recyclerView,
                                                             @NonNull GridLayoutManager layoutManager,
                                                             int visibleThreshold,
                                                             int startingPageIndex) {
        EndlessRecyclerViewScrollListenerV2 listener =
                new EndlessRecyclerViewScrollListenerV2(layoutManager, visibleThreshold, startingPageIndex);
        recyclerView.addOnScrollListener(listener);
        return listener;
    }

    /**
     * Create instance of EndlessRecyclerViewScrollListenerV2  with visibleThreshold and startingPageIndex
     * @param recyclerView recycler view
     * @param layoutManager layout manager
     * @param visibleThreshold  The minimum amount of items to have below your current scroll position
     *                          before loading more.
     * @param startingPageIndex Starting page index. When data at first load or was reset - current
     *                          page will be equal to param startingPageIndex.
     * @return instance of {@link EndlessRecyclerViewScrollListenerV2}
     */
    public static EndlessRecyclerViewScrollListenerV2 create(@NonNull RecyclerView recyclerView,
                                                             @NonNull StaggeredGridLayoutManager layoutManager,
                                                             int visibleThreshold,
                                                             int startingPageIndex) {
        EndlessRecyclerViewScrollListenerV2 listener =
                new EndlessRecyclerViewScrollListenerV2(layoutManager, visibleThreshold, startingPageIndex);
        recyclerView.addOnScrollListener(listener);
        return listener;
    }

    /**
     * Create instance of EndlessRecyclerViewScrollListenerV2 with defaults
     * @param layoutManager layout manager
     */
    public EndlessRecyclerViewScrollListenerV2(LinearLayoutManager layoutManager) {
        this(layoutManager, DEFAULT_VISIBLE_THRESHOLD, DEFAULT_STARTING_PAGE_INDEX);
    }

    /**
     * Create instance of EndlessRecyclerViewScrollListenerV2 with defaults
     * @param layoutManager layout manager
     */
    public EndlessRecyclerViewScrollListenerV2(GridLayoutManager layoutManager) {
        this(layoutManager, DEFAULT_VISIBLE_THRESHOLD, DEFAULT_STARTING_PAGE_INDEX);
    }

    /**
     * Create instance of EndlessRecyclerViewScrollListenerV2 with defaults
     * @param layoutManager layout manager
     */
    public EndlessRecyclerViewScrollListenerV2(StaggeredGridLayoutManager layoutManager) {
        this(layoutManager, DEFAULT_VISIBLE_THRESHOLD, DEFAULT_STARTING_PAGE_INDEX);
    }

    /**
     * Create instance of EndlessRecyclerViewScrollListenerV2  with visibleThreshold
     * @param layoutManager layout manager
     * @param visibleThreshold  The minimum amount of items to have below your current scroll position
     *                          before loading more.
     */
    public EndlessRecyclerViewScrollListenerV2(LinearLayoutManager layoutManager, int visibleThreshold) {
        this(layoutManager, visibleThreshold, DEFAULT_STARTING_PAGE_INDEX);
    }

    /**
     * Create instance of EndlessRecyclerViewScrollListenerV2  with visibleThreshold
     * @param layoutManager layout manager
     * @param visibleThreshold  The minimum amount of items to have below your current scroll position
     *                          before loading more.
     */
    public EndlessRecyclerViewScrollListenerV2(GridLayoutManager layoutManager, int visibleThreshold) {
        this(layoutManager, visibleThreshold, DEFAULT_STARTING_PAGE_INDEX);
    }

    /**
     * Create instance of EndlessRecyclerViewScrollListenerV2  with visibleThreshold
     * @param layoutManager layout manager
     * @param visibleThreshold  The minimum amount of items to have below your current scroll position
     *                          before loading more.
     */
    public EndlessRecyclerViewScrollListenerV2(StaggeredGridLayoutManager layoutManager,
                                               int visibleThreshold) {
        this(layoutManager, visibleThreshold, DEFAULT_STARTING_PAGE_INDEX);
    }

    /**
     * Create instance of EndlessRecyclerViewScrollListenerV2  with visibleThreshold and startingPageIndex
     * @param layoutManager layout manager
     * @param visibleThreshold  The minimum amount of items to have below your current scroll position
     *                          before loading more.
     * @param startingPageIndex Starting page index. When data at first load or was reset - current
     *                          page will be equal to param startingPageIndex.
     */
    public EndlessRecyclerViewScrollListenerV2(LinearLayoutManager layoutManager,
                                               int visibleThreshold,
                                               int startingPageIndex) {
        this.linearLayoutManager = layoutManager;
        if (visibleThreshold < 0) {
            throw new IllegalArgumentException("Invalid EndlessRecyclerViewScrollListenerV2#visibleThreshold can't be less 0");
        }
        this.visibleThreshold = visibleThreshold;
        if (startingPageIndex < 0) {
            throw new IllegalArgumentException("Invalid EndlessRecyclerViewScrollListenerV2#startingPageIndex can't be less 0");
        }
        this.startingPageIndex = startingPageIndex;
        this.currentPage = startingPageIndex;
    }

    /**
     * Create instance of EndlessRecyclerViewScrollListenerV2  with visibleThreshold and startingPageIndex
     * @param layoutManager layout manager
     * @param visibleThreshold  The minimum amount of items to have below your current scroll position
     *                          before loading more.
     * @param startingPageIndex Starting page index. When data at first load or was reset - current
     *                          page will be equal to param startingPageIndex.
     */
    public EndlessRecyclerViewScrollListenerV2(GridLayoutManager layoutManager,
                                               int visibleThreshold,
                                               int startingPageIndex) {
        // GridLayoutManager extends from LinearLayoutManager and doesn't override
        // #findLastVisibleItemPosition() and #findFirstVisibleItemPosition methods
        this.linearLayoutManager = layoutManager;
        if (visibleThreshold < 0) {
            throw new IllegalArgumentException("Invalid EndlessRecyclerViewScrollListenerV2#visibleThreshold can't be less 0");
        }
        this.visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
        if (startingPageIndex < 0) {
            throw new IllegalArgumentException("Invalid EndlessRecyclerViewScrollListenerV2#startingPageIndex can't be less 0");
        }
        this.startingPageIndex = startingPageIndex;
        this.currentPage = startingPageIndex;
    }

    /**
     * Create instance of EndlessRecyclerViewScrollListenerV2  with visibleThreshold and startingPageIndex
     * @param layoutManager layout manager
     * @param visibleThreshold  The minimum amount of items to have below your current scroll position
     *                          before loading more.
     * @param startingPageIndex Starting page index. When data at first load or was reset - current
     *                          page will be equal to param startingPageIndex.
     */
    public EndlessRecyclerViewScrollListenerV2(StaggeredGridLayoutManager layoutManager,
                                               int visibleThreshold,
                                               int startingPageIndex) {
        this.staggeredGridLayoutManager = layoutManager;
        if (visibleThreshold < 0) {
            throw new IllegalArgumentException("Invalid EndlessRecyclerViewScrollListenerV2#visibleThreshold can't be less 0");
        }
        this.visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
        if (startingPageIndex < 0) {
            throw new IllegalArgumentException("Invalid EndlessRecyclerViewScrollListenerV2#startingPageIndex can't be less 0");
        }
        this.startingPageIndex = startingPageIndex;
        this.currentPage = startingPageIndex;
    }

    /**
     * Used for {@link StaggeredGridLayoutManager} to get last visible item
     * @param lastVisibleItemPositions array of positions last visible items at {@link StaggeredGridLayoutManager}
     * @return position of most last visible item at {@link StaggeredGridLayoutManager}
     */
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

    /**
     * Set {@link OnLoadMoreListener} listener which listens onLoadMore(int,int) actions
     * @param onLoadMoreListener instance of {@link OnLoadMoreListener}
     */
    public EndlessRecyclerViewScrollListenerV2 setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
        return this;
    }

    /**
     * Get {@link OnLoadMoreListener} listener which listens onLoadMore(int,int) actions
     */
    public OnLoadMoreListener getOnLoadMoreListener() {
        return onLoadMoreListener;
    }

    /**
     * Set new starting page index. When data at first load or was reset - current page will be equal
     *  to param startingPageIndex.
     * @param startingPageIndex start page index
     */
    public EndlessRecyclerViewScrollListenerV2 setStartingPage(int startingPageIndex) {
        if (startingPageIndex < 0) {
            throw new IllegalArgumentException("Invalid EndlessRecyclerViewScrollListenerV2#startingPageIndex can't be less 0");
        }
        this.startingPageIndex = startingPageIndex;
        return this;
    }

    /**
     * Get starting page index. When data at first load or was reset - current page will be equal
     *  to param startingPageIndex.
     */
    public int getStartingPageIndex() {
        return startingPageIndex;
    }

    /**
     * Set the new minimum amount of items to have below your current scroll position before loading more.
     * @param visibleThreshold visible threshold
     */
    public EndlessRecyclerViewScrollListenerV2 setVisibleThreshold(int visibleThreshold) {
        if (visibleThreshold < 0) {
            throw new IllegalArgumentException("Invalid EndlessRecyclerViewScrollListenerV2#visibleThreshold can't be less 0");
        }

        if (staggeredGridLayoutManager != null ) {
            this.visibleThreshold = visibleThreshold * staggeredGridLayoutManager.getSpanCount();
        } else if (linearLayoutManager instanceof GridLayoutManager) {
            this.visibleThreshold = visibleThreshold * ((GridLayoutManager) linearLayoutManager).getSpanCount();
        } else {
            this.visibleThreshold = visibleThreshold;
        }

        return this;
    }

    /**
     * Get the new minimum amount of items to have below your current scroll position before loading more.
     */
    public int getVisibleThreshold() {
        return visibleThreshold;
    }

    /**
     * Is now data in loading process
     */
    public boolean isLoading() {
        return loading;
    }

    /**
     * Update current page (analog of offset)
     * @param currentPage new current page
     */
    public EndlessRecyclerViewScrollListenerV2 setCurrentPage(int currentPage) {
        if (currentPage < 0) {
            throw new IllegalArgumentException("Invalid EndlessRecyclerViewScrollListenerV2#currentPage can't be less 0");
        }
        this.currentPage = currentPage;
        return this;
    }

    /**
     * Get current page (analog of offset)
     */
    public int getCurrentPage() {
        return currentPage;
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
            }
            loading = true;
        }
    }

    public interface OnLoadMoreListener {
        /**
         *  Defines the process for actually loading more data based on page.
         *  @param page current page at list
         *  @param totalItemsCount total items count at adapter
         */
        void onLoadMore(int page, int totalItemsCount);
    }

}