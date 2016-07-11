package tk.davictor.endless_scroll_sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Collection;

import tk.davictor.endless_scroll_sample.adapter.ListViewAdapter;
import tk.davictor.endless_scroll_sample.model.User;

/**
 * 11.07.2016
 * Created by @davinctor.
 */
public class ListViewFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final int USERS_LIMIT = 30;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListViewAdapter adapter;
    private View emptyView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.content_main_list_view, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_refresh_layout);
        ListView listView = (ListView) root.findViewById(R.id.lv_list);
        emptyView = root.findViewById(R.id.empty_view);

        swipeRefreshLayout.setOnRefreshListener(this);
        adapter = new ListViewAdapter(getContext());
        listView.setAdapter(adapter);

        ImageButton btnEmpty = (ImageButton) emptyView.findViewById(R.id.btn_empty);
        btnEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRefresh();
            }
        });

        return root;
    }

    @Override
    public void onRefresh() {
        loadUsers(1);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        emptyView.setVisibility(View.VISIBLE);
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(Collection<User> users) {
        if (emptyView.getVisibility() == View.VISIBLE) {
            emptyView.setVisibility(View.GONE);
        }
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        adapter.addAll(users);
    }
}
