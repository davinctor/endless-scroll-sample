package tk.davictor.endless_scroll_sample.rest;

/**
 * 02.06.16
 * Created by Victor Ponomarenko
 */
class Endpoints {
    static final String USERS = "users";
    static final String SEARCH_USERS = "search" + "/" + USERS;
    static final String USER_BY_LOGIN = USERS + "/{login}";
}
