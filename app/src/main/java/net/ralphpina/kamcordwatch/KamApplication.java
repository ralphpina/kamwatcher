package net.ralphpina.kamcordwatch;

import android.app.Application;

import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;

public class KamApplication extends Application {

    private static KamApplication mInstance;
    private        Retrofit       mClient;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        mClient = new Retrofit.Builder()
                .baseUrl("https://app.kamcord.com")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public static KamApplication get() {
        return mInstance;
    }

    public Retrofit getClient() {
        return mClient;
    }
}
