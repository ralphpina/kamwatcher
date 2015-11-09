package net.ralphpina.kamcordwatch.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response extends TransitObject {

    @JsonProperty("id")
    private String    mId;
    @JsonProperty("name")
    private String    mName;
    @JsonProperty("video_list")
    private VideoList mVideoList;

    // ===== ID ====================================================================================

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    // ===== NAME ==================================================================================

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    // ===== VIDEOS LIST ===========================================================================

    public VideoList getVideoList() {
        return mVideoList;
    }

    public void setVideoList(VideoList videoList) {
        mVideoList = videoList;
    }
}
