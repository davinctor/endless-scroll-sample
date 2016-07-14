package tk.davictor.endless_scroll_sample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;

import tk.davictor.endless_scroll_sample.model.User;
import tk.davictor.endless_scroll_sample.rest.Provider;
import tk.davictor.endless_scroll_sample.rest.Rest;

/**
 *
 * BaseFragment for all samples with access to request method
 *
 * 11.07.2016
 * Created by @davinctor.
 */
public abstract class BaseFragment extends Fragment
        implements Provider.ActionSuccess<Collection<User>>, Provider.ActionError {

    private static final long NO_USER_ID = -1L;

    private static final String EXTRA_LAST_ID = "EXTRA_LAST_ID";
    private static final String EXTRA_LAST_OFFSET = "EXTRA_LAST_OFFSET";

    private List<Future> futures;
    private int lastOffset;
    private Long lastUserId;
    private Handler handler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        futures = new ArrayList<>();
        handler = new Handler(Looper.getMainLooper());
        if (savedInstanceState != null) {
            lastOffset = savedInstanceState.getInt(EXTRA_LAST_OFFSET, 0);
            if (savedInstanceState.containsKey(EXTRA_LAST_ID)) {
                lastUserId = savedInstanceState.getLong(EXTRA_LAST_ID);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_LAST_OFFSET, lastOffset);
        if (lastUserId != null) {
            outState.putLong(EXTRA_LAST_ID, lastUserId);
        }
    }

    protected Long getLastUserId() {
        return lastUserId;
    }

    protected int lastOffset() {
        return lastOffset;
    }

    protected abstract void loadNextPortion(int page, int offset);

    protected void loadUsers(int limit, long... lastUserId) {
        if (lastUserId != null && lastUserId.length > 0) {
            this.lastUserId = lastUserId[0];
        } else {
            this.lastUserId = null;
        }
        Future requestFuture = Rest.users().get(this.lastUserId, limit, this, this);
        // save request to list
        futures.add(requestFuture);
    }

    public Handler getHandler() {
        return handler;
    }

    @Override
    public void onDestroy() {
        // cancel all current requests
        for (Future future : futures) {
            future.cancel(true); // true means force stop
        }
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

}
