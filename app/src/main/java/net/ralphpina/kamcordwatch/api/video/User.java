package net.ralphpina.kamcordwatch.api.video;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.ralphpina.kamcordwatch.api.TransitObject;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends TransitObject implements Parcelable {

    @JsonProperty("id")
    private String mId;
    @JsonProperty("username")
    private String mUserName;

    public User() {
    }

    // ===== ID ====================================================================================

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    // ===== USER NAME =============================================================================

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    // ===== PARCELABLE ============================================================================

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mId);
        dest.writeString(this.mUserName);
    }

    protected User(Parcel in) {
        this.mId = in.readString();
        this.mUserName = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
