package tk.davictor.endless_scroll_sample.util.lorem_ipsum;

import tk.davictor.endless_scroll_sample.util.lorem_ipsum.model.Address;

/**
 * 14.06.16
 * Created by Victor Ponomarenko
 */
public interface AddressProvider {

    /**
     * Generate address
     * @return instance of {@link Address}
     */
    Address providerAddress();

}
