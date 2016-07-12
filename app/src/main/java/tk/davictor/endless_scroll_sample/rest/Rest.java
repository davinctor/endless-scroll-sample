package tk.davictor.endless_scroll_sample.rest;

import android.util.Log;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import tk.davictor.endless_scroll_sample.BuildConfig;

/**
 * 11.07.16
 * Created by @davinctor
 */
public class Rest {
    private static final String TAG = "REST_API";
    private static final String BASE_URL_API = BuildConfig.REST_API_BASE_URL;

    private static Rest instance;

    private Retrofit retrofit;
    private ObjectMapper objectMapper;

    private UsersProvider usersProvider;

    public static void init() {
        if (instance == null) {
            instance = new Rest();
        }
    }

    private Rest() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            HttpLogger httpLogger = new HttpLogger(new HttpLogger.Logger() {
                @Override
                public void log(String message) {
                    Log.d(TAG, message);
                }
            });
            httpLogger.setLevel(HttpLogger.Level.BODY_PRETTY);
            okHttpClientBuilder.addInterceptor(httpLogger);
        }

        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(okHttpClientBuilder.build())
                .baseUrl(BASE_URL_API)
                .build();
    }

    public static UsersProvider users() {
        if (instance.usersProvider == null) {
            instance.usersProvider = new MockedUsersProviderImpl(instance.retrofit);
        }
        return instance.usersProvider;
    }

    public static ObjectMapper objectMapper() {
        return instance.objectMapper;
    }
}
