package net.ralphpina.kamcordwatch.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.ralphpina.kamcordwatch.api.video.Video;

import java.util.List;

public class VideoList {

    @JsonProperty("page")
    private int         mPage;
    @JsonProperty("last_page")
    private int         mLastPage;
    @JsonProperty("next_page")
    private String      mNextPage;
    @JsonProperty("video_list")
    private List<Video> mVideos;

    // ===== PAGE ==================================================================================

    public int getPage() {
        return mPage;
    }

    public void setPage(int page) {
        mPage = page;
    }

    // ===== LAST PAGE =============================================================================

    public int getLastPage() {
        return mLastPage;
    }

    public void setLastPage(int lastPage) {
        mLastPage = lastPage;
    }

    // ===== NEXT PAGE =============================================================================

    public String getNextPage() {
        return mNextPage;
    }

    public void setNextPage(String nextPage) {
        mNextPage = nextPage;
    }

    // ===== VIDEOS ================================================================================

    public List<Video> getVideos() {
        return mVideos;
    }

    public void setVideos(List<Video> videos) {
        mVideos = videos;
    }
}
