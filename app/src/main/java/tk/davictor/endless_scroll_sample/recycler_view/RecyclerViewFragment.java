package tk.davictor.endless_scroll_sample.recycler_view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Collection;
import java.util.Random;

import tk.davictor.endless_scroll_sample.BaseFragment;
import tk.davictor.endless_scroll_sample.R;
import tk.davictor.endless_scroll_sample.recycler_view.adapter.RecyclerViewAdapter;
import tk.davictor.endless_scroll_sample.model.User;

/**
 * 17.07.2016
 * Created by @davinctor.
 */
public abstract class RecyclerViewFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener {

    static final Random rand = new Random();
    static final int GRID_VIEW_MIN_COLUMNS = 2;
    static final int GRID_VIEW_MAX_COLUMNS = 3;

    private static final int USERS_LIMIT = 15;
    static final int VISIBLE_THRESHOLD = 8;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerViewAdapter adapter;
    private View emptyView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.content_main_recycler_view, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_refresh_layout);
        RecyclerView rvList = (RecyclerView) root.findViewById(R.id.rv_list);

        initRecyclerView(rvList);

        adapter = initAndGetAdapter();
        rvList.setAdapter(adapter);

        emptyView = root.findViewById(R.id.empty_view);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.blue, R.color.red,
                R.color.orange, R.color.green);

        return root;
    }

    protected abstract void initRecyclerView(RecyclerView recyclerView);

    protected abstract RecyclerViewAdapter initAndGetAdapter();

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
        int lastItemPosition = adapter.getItemCount() - 1;
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

        int count = adapter.getItemCount();
        // if it was refresh -> delete old users from list
        if (getLastUserId() == null) {
            adapter.clear();
            adapter.notifyItemRangeRemoved(0, count);
            count = 0;
        }

        adapter.addAll(users);
        adapter.notifyItemRangeInserted(count, users.size());
    }
}
