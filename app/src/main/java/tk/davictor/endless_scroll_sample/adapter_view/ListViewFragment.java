package tk.davictor.endless_scroll_sample.adapter_view;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import tk.davictor.endless_scroll_sample.R;
import tk.davictor.endless_scroll_sample.adapter_view.adapter.ListViewAdapter;
import tk.davictor.endless_scroll_sample.model.User;

/**
 *
 * Sample with using {@link ListView} with {@link EndlessAdapterViewScrollListener}
 *
 * 11.07.2016
 * Created by @davinctor.
 */
public class ListViewFragment extends AdapterViewFragment {

    @Override
    protected int getLayoutResId() {
        return R.layout.content_main_list_view;
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    @Override
    protected AbsListView initAndGetAdapterView(View rootView) {
        ListView listView = (ListView) rootView.findViewById(R.id.lv_list);
        return listView;
    }

    @Override
    protected ArrayAdapter<User> initAndGetAdapter() {
        return new ListViewAdapter(getContext());
    }
}
