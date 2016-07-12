package tk.davictor.endless_scroll_sample.util.lorem_ipsum;

import tk.davictor.endless_scroll_sample.util.lorem_ipsum.model.TelephoneNumber;

/**
 * 15.06.16
 * Created by Victor Ponomarenko
 */
public interface TelephoneProvider {

    /**
     * Provide generated {@link TelephoneNumber} instance
     */
    TelephoneNumber provideNumber();

}
