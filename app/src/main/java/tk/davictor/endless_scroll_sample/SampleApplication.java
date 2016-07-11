package tk.davictor.endless_scroll_sample;

import android.app.Application;

import tk.davictor.endless_scroll_sample.rest.Rest;

/**
 * 12.07.2016
 * Created by @davinctor.
 */
public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Rest.init();
    }
}
