package tk.davictor.endless_scroll_sample.util.lorem_ipsum;

import java.util.List;

/**
 * 14.06.16
 * Created by Victor Ponomarenko
 */
public interface DataProvider {

    /**
     * Get {@link String} by key
     * @param key key at storage
     * @return {@link String} value by key
     */
    String getString(String key);

    /**
     * Get int value by key
     * @param key key at storage
     * @return {@link Integer} value by key
     */
    Integer getInt(String key);

    /**
     * Get {@link List} of {@link String} by key
     * @param key key at storage
     * @return list of strings
     */
    List<String> getStringList(String key);

    /**
     * Get {@link List} of {@link Integer} by key
     * @param key key at storage
     * @return list of integers
     */
    List<Integer> getIntegerList(String key);

    /**
     * Get random {@link String} from list of values by key
     * @param key key at storage
     * @return random string
     */
    String getRandString(String key);

    /**
     * Get random {@link String} from list of values by key
     * @param key key at storage
     * @param subKey subKey of value, returned by key
     * @return random string
     */
    String getRandString(String key, String subKey);

    /**
     * Get random {@link Integer} from list of values by key
     * @param key key at storage
     * @return random int
     */
    Integer getRandInteger(String key);
}
