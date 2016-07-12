package tk.davictor.endless_scroll_sample;

import android.app.Application;

import tk.davictor.endless_scroll_sample.rest.Rest;
import tk.davictor.endless_scroll_sample.util.lorem_ipsum.Lorem;

/**
 * 12.07.2016
 * Created by @davinctor.
 */
public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Lorem.init(this);
        Rest.init();
    }
}
