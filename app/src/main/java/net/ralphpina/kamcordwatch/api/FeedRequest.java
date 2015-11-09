package net.ralphpina.kamcordwatch.api;

import java.util.Map;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.QueryMap;

public interface FeedRequest {
    @Headers("device-token: jhsdjhsajhjksahlndjkajkkf")
    @GET("/app/v3/feeds/featured_feed")
    Call<FeedEnvelope> getFeed();

    @Headers("device-token: jhsdjhsajhjksahlndjkajkkf")
    @GET("/app/v3/feeds/featured_feed")
    Call<FeedEnvelope> getFeed(@QueryMap Map<String, String> options);
}
