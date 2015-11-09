package net.ralphpina.kamcordwatch.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Status extends TransitObject {

    @JsonProperty("status_code")
    private int    mStatusCode;
    @JsonProperty("status_reason")
    private String mStatusReason;

    // ===== STATUS CODE ===========================================================================

    public int getStatusCode() {
        return mStatusCode;
    }

    public void setStatusCode(int statusCode) {
        this.mStatusCode = statusCode;
    }

    // ===== STATUS REASON =========================================================================

    public String getStatusReason() {
        return mStatusReason;
    }

    public void setStatusReason(String statusReason) {
        this.mStatusReason = statusReason;
    }
}
