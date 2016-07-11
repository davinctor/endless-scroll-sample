package tk.davictor.endless_scroll_sample.model.network;

/**
 * 02.06.16
 * Created by Victor Ponomarenko
 */

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

import tk.davictor.endless_scroll_sample.model.User;

/**
 * Model of user from network
 */
public class NetworkUser implements User {

    @JsonProperty
    private long id;

    @JsonProperty
    private String login;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty
    private String name;

    @JsonProperty
    private String company;

    @JsonProperty
    private String location;

    public NetworkUser() {
    }

    protected NetworkUser(Parcel in) {
        id = in.readLong();
        login = in.readString();
        avatarUrl = in.readString();
        name = in.readString();
        company = in.readString();
        location = in.readString();
    }

    public static final Parcelable.Creator<NetworkUser> CREATOR = new Parcelable.Creator<NetworkUser>() {
        @Override
        public NetworkUser createFromParcel(Parcel in) {
            return new NetworkUser(in);
        }

        @Override
        public NetworkUser[] newArray(int size) {
            return new NetworkUser[size];
        }
    };

    public long id() {
        return id;
    }

    public String login() {
        return login;
    }

    public String avatarUrl() {
        return avatarUrl;
    }

    public String name() {
        return name;
    }

    public String company() {
        return company;
    }

    public String location() {
        return location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(login);
        dest.writeString(avatarUrl);
        dest.writeString(name);
        dest.writeString(company);
        dest.writeString(location);
    }

    public static Builder newBuilder() {
        return new NetworkUser(). new Builder();
    }

    public class Builder {
        public Builder id(long id) {
            NetworkUser.this.id = id;
            return this;
        }

        public Builder login(@NonNull String login) {
            NetworkUser.this.login = login;
            return this;
        }

        public Builder avatarUrl(@Nullable String avatarUrl) {
            NetworkUser.this.avatarUrl = avatarUrl;
            return this;
        }

        public Builder name(@Nullable String name) {
            NetworkUser.this.name = name;
            return this;
        }

        public Builder company(@Nullable String company) {
            NetworkUser.this.company = company;
            return this;
        }

        public Builder location(@Nullable String location) {
            NetworkUser.this.location = location;
            return this;
        }

        public NetworkUser build() {
            return NetworkUser.this;
        }
    }
}
