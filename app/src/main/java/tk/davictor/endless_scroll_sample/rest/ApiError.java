package tk.davictor.endless_scroll_sample.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;

import retrofit2.Response;

/**
 * 03.06.16
 * Created by Victor Ponomarenko
 */
public class ApiError extends Exception {

    private int errorCode;

    @JsonProperty
    private String message;

    @JsonProperty("documentation_url")
    private String documentationUrl;

    public ApiError() {
    }

    public ApiError(String message) {
        super(message);
    }

    public ApiError errorCode(int code) {
        this.errorCode = code;
        return this;
    }

    public int errorCode() {
        return errorCode;
    }

    public String message() {
        return message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String documentationUrl() {
        return documentationUrl;
    }

    public static ApiError parseError(Response response) throws IOException {
        String message = response.errorBody().string();

        //noinspection ThrowableResultOfMethodCallIgnored
        ApiError apiError = Rest.objectMapper().readValue(message, ApiError.class);
        apiError.errorCode(response.code());

        return apiError;
    }
}
