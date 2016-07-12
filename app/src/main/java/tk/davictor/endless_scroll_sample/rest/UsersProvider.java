package tk.davictor.endless_scroll_sample.rest;

import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import java.util.Collection;
import java.util.concurrent.Future;

import tk.davictor.endless_scroll_sample.model.User;

/**
 * 02.06.16
 * Created by @davinctor
 */
public interface UsersProvider {

    /**
     * Async getting users
     * @param limit restrictions number of users per page
     */
    @NonNull
    Future get(int limit, Provider.ActionSuccess<Collection<User>> actionSuccess, Provider.ActionError actionError);

    /**
     * Async getting users with receiving result from callback
     * @param limit restrictions number of users per page
     */
    @WorkerThread
    @NonNull
    Collection<User> get(int limit) throws Exception;

    /**
     * Async getting users
     * @param since The integer ID of the last User that you've seen.
     */
    @NonNull
    Future get(Long since, int limit, Provider.ActionSuccess<Collection<User>> actionSuccess, Provider.ActionError actionError);

    /**
     * Async getting users with receiving result from callback
     * @param since The integer ID of the last User that you've seen.
     */
    @WorkerThread
    @NonNull
    Collection<User> get(Long since, int limit) throws Exception;

    /**
     * Async search users by github login with receiving result from callback
     * @param login searched user login
     * @param page number of page
     * @param limit restrictions number of users per page
     */
    @NonNull
    Future search(@NonNull String login, int page, int limit, Provider.ActionSuccess<Collection<User>> actionSuccess, Provider.ActionError actionError);

    /**
     * Sync search users by github login
     * @param login searched user login
     * @param page number of page
     * @param limit restrictions number of users per page
     * @return collection of found users
     */
    @WorkerThread
    @NonNull
    Collection<User> search(@NonNull String login, int page, int limit) throws Exception;

    /**
     * Async get user by id with receiving result from callback
     * @param login login of searched user
     */
    Future userByLogin(@NonNull String login, Provider.ActionSuccess<User> actionSuccess, Provider.ActionError actionError);

    /**
     * Sync get user by id
     * @param login login of searched user
     * @return network user
     */
    @WorkerThread
    User userByLogin(@NonNull String login) throws Exception;
}
