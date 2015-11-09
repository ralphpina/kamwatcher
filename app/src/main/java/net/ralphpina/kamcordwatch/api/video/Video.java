package net.ralphpina.kamcordwatch.api.video;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.ralphpina.kamcordwatch.api.TransitObject;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Video extends TransitObject implements Parcelable {

    @JsonProperty("thumbnails")
    private Thumbnails mThumbnails;
    @JsonProperty("title")
    private String     mTitle;
    @JsonProperty("description")
    private String     mDescription;
    @JsonProperty("duration")
    private Double     mDuration;
    @JsonProperty("views")
    private Integer    mViews;
    @JsonProperty("likes")
    private Integer    mLikes;
    @JsonProperty("upload_time")
    private Long       mUploadTime;
    @JsonProperty("video_url")
    private String     mVideoUrl;
    @JsonProperty("user")
    private User       mUser;

    // ===== TITLE =================================================================================

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    // ===== THUMBNAILS ============================================================================

    public Thumbnails getThumbnails() {
        return mThumbnails;
    }

    public void setThumbnails(Thumbnails thumbnails) {
        mThumbnails = thumbnails;
    }

    // ===== DESCRIPTION ===========================================================================

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    // ===== DURATION ==============================================================================

    public Double getDuration() {
        return mDuration;
    }

    public void setDuration(Double duration) {
        mDuration = duration;
    }

    // ===== VIEWS =================================================================================

    public Integer getViews() {
        return mViews;
    }

    public void setViews(Integer views) {
        mViews = views;
    }

    // ===== LIKES =================================================================================

    public Integer getLikes() {
        return mLikes;
    }

    public void setLikes(Integer likes) {
        mLikes = likes;
    }

    // ===== UPLOAD ================================================================================

    public Long getUploadTime() {
        return mUploadTime;
    }

    public void setUploadTime(Long uploadTime) {
        mUploadTime = uploadTime;
    }

    // ===== VIDEO URL =============================================================================

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        mVideoUrl = videoUrl;
    }

    // ===== USER ==================================================================================

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    // ===== PARCELABLE ============================================================================

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mThumbnails, 0);
        dest.writeString(this.mTitle);
        dest.writeString(this.mDescription);
        dest.writeValue(this.mDuration);
        dest.writeValue(this.mViews);
        dest.writeValue(this.mLikes);
        dest.writeValue(this.mUploadTime);
        dest.writeString(this.mVideoUrl);
        dest.writeParcelable(this.mUser, 0);
    }

    public Video() {
    }

    protected Video(Parcel in) {
        this.mThumbnails = in.readParcelable(Thumbnails.class.getClassLoader());
        this.mTitle = in.readString();
        this.mDescription = in.readString();
        this.mDuration = (Double) in.readValue(Double.class.getClassLoader());
        this.mViews = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mLikes = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mUploadTime = (Long) in.readValue(Long.class.getClassLoader());
        this.mVideoUrl = in.readString();
        this.mUser = in.readParcelable(User.class.getClassLoader());
    }

    public static final Parcelable.Creator<Video> CREATOR = new Parcelable.Creator<Video>() {
        public Video createFromParcel(Parcel source) {
            return new Video(source);
        }

        public Video[] newArray(int size) {
            return new Video[size];
        }
    };
}
