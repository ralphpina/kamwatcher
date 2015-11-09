package net.ralphpina.kamcordwatch.api;

import com.fasterxml.jackson.annotation.JsonGetter;

public class FeedEnvelope extends TransitObject {

    private Status mStatus;
    private Response mResponse;

    // ===== STATUS ================================================================================

    @JsonGetter
    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        mStatus = status;
    }

    // ===== RESPONSE ==============================================================================

    @JsonGetter
    public Response getResponse() {
        return mResponse;
    }

    public void setResponse(Response response) {
        mResponse = response;
    }
}
