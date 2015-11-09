package net.ralphpina.kamcordwatch.api.video;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.ralphpina.kamcordwatch.api.TransitObject;

public class Thumbnails extends TransitObject implements Parcelable {

    @JsonProperty("small")
    private String mSmall;
    @JsonProperty("regular")
    private String mRegular;

    public Thumbnails() {
    }

    // ===== SMALL =================================================================================

    public String getSmall() {
        return mSmall;
    }

    public void setSmall(String small) {
        this.mSmall = small;
    }

    // ===== REGULAR ===============================================================================

    public String getRegular() {
        return mRegular;
    }

    public void setRegular(String regular) {
        mRegular = regular;
    }

    // ===== PARCELABLE ============================================================================

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mSmall);
        dest.writeString(this.mRegular);
    }

    protected Thumbnails(Parcel in) {
        this.mSmall = in.readString();
        this.mRegular = in.readString();
    }

    public static final Parcelable.Creator<Thumbnails> CREATOR = new Parcelable.Creator<Thumbnails>() {
        public Thumbnails createFromParcel(Parcel source) {
            return new Thumbnails(source);
        }

        public Thumbnails[] newArray(int size) {
            return new Thumbnails[size];
        }
    };
}
