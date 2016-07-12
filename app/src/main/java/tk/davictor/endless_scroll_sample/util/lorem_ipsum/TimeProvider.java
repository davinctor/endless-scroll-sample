package tk.davictor.endless_scroll_sample.util.lorem_ipsum;

import tk.davictor.endless_scroll_sample.util.lorem_ipsum.model.Time;

/**
 * 14.06.16
 * Created by Victor Ponomarenko
 */
public interface TimeProvider {

    /**
     * Get generated instance of {@link Time}
     */
    Time provideTime();
}
