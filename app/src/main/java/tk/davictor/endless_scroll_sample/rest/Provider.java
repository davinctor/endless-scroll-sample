package tk.davictor.endless_scroll_sample.rest;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 03.06.16
 * Created by Victor Ponomarenko
 */
public abstract class Provider {

    private final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();
    private final Handler MAIN_THREAD_HANDLER = new Handler(Looper.getMainLooper());

    protected ExecutorService ioExecutor() {
        return EXECUTOR_SERVICE;
    }

    protected Handler mainThreadHandler() {
        return MAIN_THREAD_HANDLER;
    }

    /**
     * Action for receiving data from response
     */
    public interface ActionSuccess<TData> {
        void onSuccess(TData data);
    }

    /**
     * Action for receive errors
     */
    public interface ActionError {
        void onError(Throwable throwable);
    }
}
