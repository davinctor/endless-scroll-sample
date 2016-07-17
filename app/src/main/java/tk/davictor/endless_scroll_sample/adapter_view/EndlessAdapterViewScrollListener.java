package tk.davictor.endless_scroll_sample.adapter_view;

import android.widget.AbsListView;

/**
 * Endless scroll listener implementation for AdapterView successors
 * @see  <a href="http://davinctor.tk/endless_scrolling_listview_gridview_recyclerview">Link to article</a>
 *
 * Created by @davinctor.
 * 11.07.2016
 */
@SuppressWarnings("unused")
public abstract class EndlessAdapterViewScrollListener implements AbsListView.OnScrollListener {
    private static final int DEFAULT_VISIBLE_THRESHOLD = 5;
    private static final int DEFAULT_STARTING_PAGE_INDEX = 0;

    // The minimum number of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold;
    // The current offset index of data you have loaded
    private int currentPage;
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    // True if we are still waiting for the last set of data to load.
    private boolean loading = true;
    // Sets the starting page index
    private int startingPageIndex;

    /**
     * Create instance of EndlessAdapterViewScrollListener with defaults
     */
    public EndlessAdapterViewScrollListener() {
        this(DEFAULT_VISIBLE_THRESHOLD, DEFAULT_STARTING_PAGE_INDEX);
    }

    /**
     * Create instance of EndlessAdapterViewScrollListener with visibleThreshold
     * @param visibleThreshold  The minimum amount of items to have below your current scroll position
     *                          before loading more.
     */
    public EndlessAdapterViewScrollListener(int visibleThreshold) {
        this(visibleThreshold, DEFAULT_STARTING_PAGE_INDEX);
    }

    /**
     * Create instance of EndlessAdapterViewScrollListener with visibleThreshold and startingPageIndex
     * @param visibleThreshold  The minimum amount of items to have below your current scroll position
     *                          before loading more.
     * @param startingPageIndex Starting page index. When data at first load or was reset - current
     *                          page will be equal to param startingPageIndex.
     */
    public EndlessAdapterViewScrollListener(int visibleThreshold, int startingPageIndex) {
        this.visibleThreshold = visibleThreshold;
        this.startingPageIndex = startingPageIndex;
        this.currentPage = startingPageIndex;
    }

    /**
     * Set new starting page index. When data at first load or was reset - current page will be equal
     *  to param startingPageIndex.
     * @param startingPageIndex start page index
     */
    public void setStartingPage(int startingPageIndex) {
        if (startingPageIndex < 0) {
            throw new IllegalArgumentException("Invalid EndlessAdapterViewScrollListener#startingPageIndex can't be less 0");
        }
        this.startingPageIndex = startingPageIndex;
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
    public void setVisibleThreshold(int visibleThreshold) {
        if (visibleThreshold < 0) {
            throw new IllegalArgumentException("Invalid EndlessAdapterViewScrollListener#visibleThreshold can't be less 0");
        }
        this.visibleThreshold = visibleThreshold;
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
     * Get current page (analog of offset)
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * Update current page (analog of offset)
     * @param currentPage new current page
     */
    public void setCurrentPage(int currentPage) {
        if (currentPage < 0) {
            throw new IllegalArgumentException("Invalid EndlessAdapterViewScrollListener#currentPage can't be less 0");
        }
        this.currentPage = currentPage;
    }

    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
    {
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
            onLoadMore(currentPage, totalItemCount);
            loading = true;
        }
    }

    /**
     *  Defines the process for actually loading more data based on page.
     *  @param page current page at list
     *  @param totalItemsCount total items count at adapter
     */
    public abstract void onLoadMore(int page, int totalItemsCount);

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // Don't take any action on changed
    }
}

