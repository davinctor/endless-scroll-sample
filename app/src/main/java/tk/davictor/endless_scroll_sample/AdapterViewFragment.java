package tk.davictor.endless_scroll_sample;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.Collection;

import tk.davictor.endless_scroll_sample.model.User;

/**
 *
 * Fragment with base list functionality
 *
 * 15.07.2016
 * Created by @davinctor.
 */
public abstract class AdapterViewFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final int USERS_LIMIT = 15;
    private static final int VISIBLE_THRESHOLD = 8;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayAdapter<User> adapter;
    private View emptyView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(getLayoutResId(), container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_refresh_layout);
        AbsListView absListView = initAndGetAdapterView(root);

        emptyView = root.findViewById(R.id.empty_view);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.blue, R.color.red,
                R.color.orange, R.color.green);

        adapter = initAndGetAdapter();
        absListView.setAdapter(adapter);

        /*******************************************************************************************
         *                                  MAIN PART OF THIS ALL SAMPLE
         *******************************************************************************************/
        absListView.setOnScrollListener(new EndlessScrollListener(VISIBLE_THRESHOLD) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadNextPortion(page, totalItemsCount);
            }
        });
        /*******************************************************************************************
         *                                         END
         ******************************************************************************************/

        return root;
    }

    @LayoutRes
    protected abstract int getLayoutResId();

    protected abstract AbsListView initAndGetAdapterView(View rootView);

    protected abstract ArrayAdapter<User> initAndGetAdapter();

    @Override
    public void onStart() {
        super.onStart();
        if (adapter.isEmpty()) {
            onRefresh();
        }
    }

    @Override
    public void onRefresh() {
        // Bug in library, this is workaround
        // based on: http://stackoverflow.com/questions/26858692/swiperefreshlayout-setrefreshing-not-showing-indicator-initially
        getHandler().post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        loadUsers(USERS_LIMIT);
    }

    @Override
    protected void loadNextPortion(int page, int offset) {
        // pagination by last item id
        int lastItemPosition = adapter.getCount() - 1;
        if (lastItemPosition > 0) {
            // get last user id
            long lastUserId = adapter.getItem(lastItemPosition).id();
            // load other users begin from user with 'lastUserId'
            swipeRefreshLayout.setRefreshing(true);
            loadUsers(USERS_LIMIT, lastUserId);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        if (adapter.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
        }
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(Collection<User> users) {
        // if server returns empty result with first page
        if (getLastUserId() == null && users.size() == 0) {
            emptyView.setVisibility(View.GONE);
        } else if (emptyView.getVisibility() == View.VISIBLE) {
            emptyView.setVisibility(View.GONE);
        }

        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }

        if (getLastUserId() == null) {
            adapter.clear();
        }

        adapter.addAll(users);
    }

}
