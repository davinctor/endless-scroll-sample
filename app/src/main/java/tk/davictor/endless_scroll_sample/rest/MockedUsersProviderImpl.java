package tk.davictor.endless_scroll_sample.rest;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;
import tk.davictor.endless_scroll_sample.model.User;
import tk.davictor.endless_scroll_sample.model.network.NetworkUser;
import tk.davictor.endless_scroll_sample.util.lorem_ipsum.Lorem;
import tk.davictor.endless_scroll_sample.util.lorem_ipsum.model.Person;

/**
 * 12.07.16
 */
class MockedUsersProviderImpl extends Provider implements UsersProvider {

    private static final int MAX_SLEEP_TIME = 4000;
    private final Random rand;
    private final ArrayList<User> users;

    @SuppressWarnings("UnusedParameters")
    MockedUsersProviderImpl(@NonNull Retrofit retrofit) {
        rand = new Random();
        users = new ArrayList<>();
    }

    @NonNull
    @Override
    public Future get(final int limit,
                      final Provider.ActionSuccess<Collection<User>> actionSuccess,
                      final Provider.ActionError actionError) {
        return ioExecutor().submit(new Runnable() {
            @Override
            public void run() {
                sleep();
                mainThreadHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        actionSuccess.onSuccess(appendToUsersAndGet(limit));
                    }
                });
            }
        });
    }

    @NonNull
    @Override
    public Collection<User> get(int limit) throws Exception {
        sleep();
        return appendToUsersAndGet(limit);
    }

    @NonNull
    @Override
    public Future get(final Long since, final int limit,
                      final Provider.ActionSuccess<Collection<User>> actionSuccess,
                      final Provider.ActionError actionError) {
        return ioExecutor().submit(new Runnable() {
            @Override
            public void run() {
                sleep();
                mainThreadHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        actionSuccess.onSuccess(appendToUsersAndGet(limit));
                    }
                });
            }
        });
    }

    @NonNull
    @Override
    public Collection<User> get(Long since, int limit) throws Exception {
        sleep();
        return appendToUsersAndGet(limit);
    }

    private Collection<User> get(int page, int limit) {
        int requestedSize = page * limit;
        if (requestedSize > users.size()) {
            appendToUsersAndGet(requestedSize - users.size());
        }

        final Collection<User> result = new ArrayList<>(limit);
        int count = 0;
        int skipAmount = (page - 1) * limit;
        for (User user : users) {
            if (count++ > skipAmount) {
                result.add(user);
            }
        }

        return result;
    }

    private Collection<User> appendToUsersAndGet(int size) {
        final Collection<User> newPortion = generateCollection(size);
        users.addAll(newPortion);
        return newPortion;
    }

    @NonNull
    @Override
    public Future search(@NonNull String login, final int page, final int limit,
                         final Provider.ActionSuccess<Collection<User>> actionSuccess,
                         final Provider.ActionError actionError) {
        return ioExecutor().submit(new Runnable() {
            @Override
            public void run() {
                sleep();
                mainThreadHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        actionSuccess.onSuccess(get(page, limit));
                    }
                });
            }
        });
    }

    @NonNull
    @Override
    public Collection<User> search(@NonNull String login, int page, int limit) throws Exception {
        sleep();
        return get(page, limit);
    }

    @Override
    public Future userByLogin(@NonNull final String login, final Provider.ActionSuccess<User> actionSuccess,
                              final Provider.ActionError actionError) {
        return ioExecutor().submit(new Runnable() {
            @Override
            public void run() {
                sleep();
                mainThreadHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        actionSuccess.onSuccess(findByLogin(login));
                    }
                });
            }
        });
    }

    @Override
    public User userByLogin(@NonNull String login) throws Exception {
        sleep();
        return findByLogin(login);
    }

    @Nullable
    private User findByLogin(String login) {
        for (User user : users) {
            if (TextUtils.equals(login, user.login())) {
                return user;
            }
        }

        return null;
    }

    private static Collection<User> generateCollection(int size) {
        final Collection<User> users = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            users.add(generate());
        }
        return users;
    }

    private void sleep() {
        try {
            TimeUnit.MILLISECONDS.sleep((long) rand.nextInt(MAX_SLEEP_TIME));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static NetworkUser generate() {
        Person person = Lorem.instance().person().providePerson();
        String location = person.address().city() + " " + person.address().country();
        return NetworkUser.newBuilder()
                .id(person.id())
                .login(person.login())
                .name(person.firstName() + " " + person.lastName())
                .avatarUrl(person.avatar())
                .company(person.company().name())
                .location(location)
                .build();
    }
}
