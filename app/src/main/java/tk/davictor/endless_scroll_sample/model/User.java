package tk.davictor.endless_scroll_sample.model;

import android.os.Parcelable;

/**
 * 03.06.16
 * Created by Victor Ponomarenko
 */
public interface User extends Parcelable {

    /**
     * Get user id
     */
    long id();

    /**
     * Get user login (nickname)
     */
    String login();

    /**
     * Get user avatar url
     */
    String avatarUrl();

    /**
     * Get user name
     */
    String name();

    /**
     * Get user company name
     */
    String company();

    /**
     * Get user location
     */
    String location();
}
