package px500.pipoask.com.di.module;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import px500.pipoask.com.BuildConfig;
import px500.pipoask.com.data.api.PhotoApi;
import px500.pipoask.com.data.local.ConstKV;
import px500.pipoask.com.data.local.SharedPreferenceHelper;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

@Module
public class PixelApiModule {

    private static final String OAUTH_TOKEN = "oauth_token";
    private static final String OAUTH_TOKEN_SECRET = "oauth_token_secret";
    private static final String CONSUMER_KEY = "consumer_key";

    @Provides
    @Singleton
    OkHttpClient.Builder provideOkHttpClient() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.connectTimeout(60 * 1000, TimeUnit.MILLISECONDS);
        okHttpClientBuilder.readTimeout(60 * 1000, TimeUnit.MILLISECONDS);

        //TODO: Save it in local memory
        String token = SharedPreferenceHelper.getSharedPreferenceString(ConstKV.USER_500PX_TOKEN, null);
        String token_secret = SharedPreferenceHelper.getSharedPreferenceString(ConstKV.USER_500PX_TOKEN_SECRET, null);
        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(BuildConfig.CONSUMER_KEY, BuildConfig.CONSUMER_KEY_SECRET);

        consumer.setTokenWithSecret(token, token_secret);
        Interceptor interceptor = new SigningInterceptor(consumer);
        okHttpClientBuilder.addInterceptor(interceptor);

        return okHttpClientBuilder;
    }

    @Provides
    @Singleton
    PhotoApi provideRestAdapter(OkHttpClient.Builder okHttpClient) {
        if (BuildConfig.DEBUG) {
            okHttpClient.interceptors().add(chain -> {
                Request request = chain.request();
                String protocolPrefix = request.isHttps() ? "S" : "";
                Log.i("OkHttpClient", String.format("---> HTTP%s %s %s",
                        protocolPrefix, request.method(), request.url()));
                return chain.proceed(request);
            });

        }
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.ENDPOINT)
                .client(okHttpClient.build())
                .build();
        return retrofit.create(PhotoApi.class);
    }
}
