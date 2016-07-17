package tk.davictor.endless_scroll_sample.recycler_view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Random;

/**
 * 17.07.2016
 * Created by @davinctor.
 */
public class StaggeredGridLayoutManagerRecyclerViewAdapter extends GridLayoutManagerRecyclerViewAdapter {

    private final Random rand = new Random();
    private static final int MIN_LINES = 1;
    private static final int MAX_LINES = 4;

    public StaggeredGridLayoutManagerRecyclerViewAdapter(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        // Demonstrate using StaggeredGridLayoutManager
        // StaggeredGridLayoutManager can manage dynamic height of elements
        //  (unlike GridLayoutManager - it places elements in one row,
        //   and height row defines by max element height)
        // but this doesn't break right work of EndlessRecyclerViewScrollListener
        int lines = rand.nextInt(MAX_LINES) + 1;
        lines = lines <= 0 ? MIN_LINES : lines;
        holder.tvCompany.setLines(lines);
        holder.tvCompany.setMaxLines(lines);
        holder.tvLogin.setLines(lines);
        holder.tvLogin.setMaxLines(lines);
        holder.tvLocation.setLines(lines);
        holder.tvLocation.setMaxLines(lines);
    }
}
