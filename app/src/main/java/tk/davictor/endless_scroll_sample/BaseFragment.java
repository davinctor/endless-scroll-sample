package tk.davictor.endless_scroll_sample;

import android.os.Bundle;
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
 * 11.07.2016
 * Created by @davinctor.
 */
public abstract class BaseFragment extends Fragment
        implements Provider.ActionSuccess<Collection<User>>, Provider.ActionError {

    private List<Future> futures;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        futures = new ArrayList<>();
    }

    protected void loadUsers(int lastUserId) {
        Future requestFuture = Rest.users().get(lastUserId, this, this);
        // save request to list
        futures.add(requestFuture);
    }
    @Override
    public void onDestroy() {
        // cancel all current requests
        for (Future future : futures) {
            future.cancel(true); // true means force stop
        }
        super.onDestroy();
    }

}
