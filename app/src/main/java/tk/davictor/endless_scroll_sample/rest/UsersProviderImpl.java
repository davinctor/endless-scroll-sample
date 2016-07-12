package tk.davictor.endless_scroll_sample.rest;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Future;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tk.davictor.endless_scroll_sample.model.User;
import tk.davictor.endless_scroll_sample.model.network.NetworkUser;

/**
 * 11.07.16
 * Created by @davinctor
 */
class UsersProviderImpl extends Provider implements UsersProvider {

    private final UsersApi usersApi;

    UsersProviderImpl(@NonNull Retrofit retrofit) {
        this.usersApi = retrofit.create(UsersApi.class);
    }

    @NonNull
    @Override
    public Future get(int limit, ActionSuccess<Collection<User>> actionSuccess, ActionError actionError) {
        final Call<Collection<NetworkUser>> call = usersApi.get(limit);
        return get(call, actionSuccess, actionError);
    }

    @NonNull
    @Override
    public Collection<User> get(int limit) throws Exception {
        Response<Collection<NetworkUser>> usersResponse = usersApi.get(limit).execute();
        if (usersResponse.isSuccessful()) {
            return fetchUsersDetails(new ArrayList<User>(usersResponse.body()));
        } else {
            throw ApiError.parseError(usersResponse);
        }
    }

    @NonNull
    @Override
    public Future get(Long since, int limit, ActionSuccess<Collection<User>> actionSuccess, ActionError actionError) {
        final Call<Collection<NetworkUser>> call;
        if (since == null) {
            call = usersApi.get(limit);
        } else {
            call = usersApi.get(since, limit);
        }
        return get(call, actionSuccess, actionError);
    }

    @NonNull
    @Override
    public Collection<User> get(Long since, int limit) throws Exception {
        Response<Collection<NetworkUser>> usersResponse;
        if (since == null) {
            usersResponse = usersApi.get(limit).execute();
        } else {
            usersResponse = usersApi.get(since, limit).execute();
        }
        if (usersResponse.isSuccessful()) {
            return fetchUsersDetails(new ArrayList<User>(usersResponse.body()));
        } else {
            throw ApiError.parseError(usersResponse);
        }
    }

    private Future get(final Call<Collection<NetworkUser>> call,
                     final ActionSuccess<Collection<User>> actionSuccess,
                     final ActionError actionError) {
        return ioExecutor().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    final Response<Collection<NetworkUser>> response = call.execute();
                    if (response.isSuccessful()) {
                        final Collection<User> users = fetchUsersDetails(new ArrayList<User>(response.body()));
                        mainThreadHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                actionSuccess.onSuccess(users);
                            }
                        });
                    } else {
                        throw ApiError.parseError(response);
                    }
                } catch (final Throwable throwable) {
                    mainThreadHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            actionError.onError(throwable);
                        }
                    });
                }
            }
        });
    }

    @NonNull
    @Override
    public Future search(@NonNull String login, int page, int limit, final ActionSuccess<Collection<User>> actionSuccess, final ActionError actionError) {
        final Call<SearchUsersResponse> call = usersApi.search(login, page, limit);
        return ioExecutor().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    final Response<SearchUsersResponse> response = call.execute();
                    if (response.isSuccessful()) {
                        final Collection<User> users = fetchUsersDetails(response.body().users());
                        mainThreadHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                actionSuccess.onSuccess(users);
                            }
                        });
                    } else {
                        throw ApiError.parseError(response);
                    }
                } catch (final Throwable throwable) {
                    mainThreadHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            actionError.onError(throwable);
                        }
                    });
                }
            }
        });
    }

    @NonNull
    @Override
    public Collection<User> search(@NonNull String login, int page, int limit) throws Exception {
        Response<SearchUsersResponse> usersResponse = usersApi.search(login, page, limit).execute();
        if (usersResponse.isSuccessful()) {
            return fetchUsersDetails(usersResponse.body().users());
        } else {
            throw ApiError.parseError(usersResponse);
        }
    }

    private Collection<User> fetchUsersDetails(Collection<? extends User> users) throws Exception {
        Collection<User> userCollection = new ArrayList<>(users.size());

        for (User user : users) {
            userCollection.add(userByLogin(user.login()));
        }

        return userCollection;
    }

    @Override
    public Future userByLogin(@NonNull String login, final ActionSuccess<User> actionSuccess, final ActionError actionError) {
        final Call<NetworkUser> call = usersApi.userByLogin(login);
        return ioExecutor().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    final Response<NetworkUser> response = call.execute();
                    if (response.isSuccessful()) {
                        mainThreadHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                actionSuccess.onSuccess(response.body());
                            }
                        });
                    }
                    throw ApiError.parseError(response);
                } catch (final Throwable throwable) {
                    mainThreadHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            actionError.onError(throwable);
                        }
                    });
                }
            }
        });
    }

    @NonNull
    @Override
    public User userByLogin(@NonNull String login) throws Exception {
        Response<NetworkUser> response = usersApi.userByLogin(login).execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw ApiError.parseError(response);
        }
    }

    private interface UsersApi {
        @GET(Endpoints.USERS)
        Call<Collection<NetworkUser>> get(@Query("limit") int limit);

        @GET(Endpoints.USERS)
        Call<Collection<NetworkUser>> get(@Query("since") long lastId, @Query("limit") int limit);

        @GET(Endpoints.SEARCH_USERS)
        Call<SearchUsersResponse> search(@Query("q") String login, @Query("page") int page, @Query("per_page") int perPage);

        @GET(Endpoints.USER_BY_LOGIN)
        Call<NetworkUser> userByLogin(@Path("login") String login);

    }

    /**
     * Response from server on search user
     */
    @SuppressWarnings("all")
    private static class SearchUsersResponse {

        @JsonProperty("total_count")
        private int totalCount;

        @JsonProperty
        private Collection<NetworkUser> items;

        private SearchUsersResponse() {
        }

        public int totalCount() {
            return totalCount;
        }

        public Collection<User> users() {
            return new ArrayList<User>(items);
        }
    }
}
