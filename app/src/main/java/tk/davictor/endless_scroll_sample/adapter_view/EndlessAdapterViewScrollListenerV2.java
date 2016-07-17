package tk.davictor.endless_scroll_sample.adapter_view;

import android.support.annotation.NonNull;
import android.widget.AbsListView;

/**
 * Endless scroll listener implementation for AdapterView successors with more convenient interface (as for me)
 * @see  <a href="http://davinctor.tk/endless_scrolling_listview_gridview_recyclerview">Link to article</a>
 *
 * 17.07.2016
 * Created by @davinctor.
 */
@SuppressWarnings("unused")
public class EndlessAdapterViewScrollListenerV2 implements AbsListView.OnScrollListener {
    private static final int DEFAULT_VISIBLE_THRESHOLD = 5;
    private static final int DEFAULT_STARTING_PAGE_INDEX = 0;

    // The minimum number of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold;
    // The current offset index of data you have loaded
    private int currentPage;
    // The total number of items in the data set after the last load
    private int previousTotalItemCount;
    // True if we are still waiting for the last set of data to load.
    private boolean loading = true;
    // Sets the starting page index
    private int startingPageIndex;

    private OnLoadMoreListener onLoadMoreListener;

    /**
     * Create instance of EndlessAdapterViewScrollListenerV2 with defaults
     * @param absListView any successor of {@link AbsListView}
     */
    public static EndlessAdapterViewScrollListenerV2 create(@NonNull AbsListView absListView) {
        return create(absListView, DEFAULT_VISIBLE_THRESHOLD, DEFAULT_STARTING_PAGE_INDEX);
    }

    /**
     * Create instance of EndlessAdapterViewScrollListenerV2 with visibleThreshold
     * @param absListView any successor of {@link AbsListView}
     * @param visibleThreshold  The minimum amount of items to have below your current scroll position
     *                          before loading more.
     */
    public static EndlessAdapterViewScrollListenerV2 create(@NonNull AbsListView absListView, int visibleThreshold) {
        return create(absListView, visibleThreshold, DEFAULT_STARTING_PAGE_INDEX);
    }

    /**
     * Create instance of EndlessAdapterViewScrollListenerV2 with visibleThreshold and startingPageIndex
     * @param absListView any successor of {@link AbsListView}
     * @param visibleThreshold  The minimum amount of items to have below your current scroll position
     *                          before loading more.
     * @param startingPageIndex Starting page index. When data at first load or was reset - current
     *                          page will be equal to param startingPageIndex.
     */
    public static EndlessAdapterViewScrollListenerV2 create(@NonNull AbsListView absListView,
                                                            int visibleThreshold,
                                                            int startingPageIndex) {
        EndlessAdapterViewScrollListenerV2 listener =
                new EndlessAdapterViewScrollListenerV2(visibleThreshold, startingPageIndex);
        absListView.setOnScrollListener(listener);
        return listener;
    }

    /**
     * Create instance of EndlessAdapterViewScrollListenerV2 with defaults
     */
    public EndlessAdapterViewScrollListenerV2() {
        this(DEFAULT_VISIBLE_THRESHOLD, DEFAULT_STARTING_PAGE_INDEX);
    }

    /**
     * Create instance of EndlessAdapterViewScrollListenerV2 with visibleThreshold
     * @param visibleThreshold  The minimum amount of items to have below your current scroll position
     *                          before loading more.
     */
    public EndlessAdapterViewScrollListenerV2(int visibleThreshold) {
        this(visibleThreshold, DEFAULT_STARTING_PAGE_INDEX);
    }

    /**
     * Create instance of EndlessAdapterViewScrollListenerV2 with visibleThreshold and startingPageIndex
     * @param visibleThreshold  The minimum amount of items to have below your current scroll position
     *                          before loading more.
     * @param startPage Starting page index. When data at first load or was reset - current
     *                          page will be equal to param startingPageIndex.
     */
    public EndlessAdapterViewScrollListenerV2(int visibleThreshold, int startPage) {
        if (visibleThreshold < 0) {
            throw new IllegalArgumentException("Invalid EndlessRecyclerViewScrollListenerV2#visibleThreshold can't be less 0");
        }
        this.visibleThreshold = visibleThreshold;
        if (startPage < 0) {
            throw new IllegalArgumentException("Invalid EndlessRecyclerViewScrollListenerV2#startingPageIndex can't be less 0");
        }
        this.startingPageIndex = startPage;
        this.currentPage = startPage;
    }

    /**
     * Set {@link OnLoadMoreListener} listener which listens onLoadMore(int,int) actions
     * @param onLoadMoreListener instance of {@link OnLoadMoreListener}
     */
    public EndlessAdapterViewScrollListenerV2 setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
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
    public EndlessAdapterViewScrollListenerV2 setStartingPage(int startingPageIndex) {
        if (startingPageIndex < 0) {
            throw new IllegalArgumentException("Invalid EndlessAdapterViewScrollListenerV2#startingPageIndex can't be less 0");
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
    public EndlessAdapterViewScrollListenerV2 setVisibleThreshold(int visibleThreshold) {
        if (visibleThreshold < 0) {
            throw new IllegalArgumentException("Invalid EndlessAdapterViewScrollListenerV2#visibleThreshold can't be less 0");
        }
        this.visibleThreshold = visibleThreshold;
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
    public EndlessAdapterViewScrollListenerV2 setCurrentPage(int currentPage) {
        if (currentPage < 0) {
            throw new IllegalArgumentException("Invalid EndlessAdapterViewScrollListenerV2#currentPage can't be less 0");
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
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }
        // If it's still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }

        // If it isn't currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        if (!loading && (firstVisibleItem + visibleItemCount >= totalItemCount - visibleThreshold)) {
            currentPage++;
            if (onLoadMoreListener != null) {
                onLoadMoreListener.onLoadMore(currentPage, totalItemCount);
            }
            loading = true;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // Don't take any action on changed
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
