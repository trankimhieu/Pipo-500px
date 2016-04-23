package px500.pipoask.com.di.module;

import android.util.Log;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import px500.pipoask.com.BuildConfig;
import px500.pipoask.com.data.api.PhotoApi;
import px500.pipoask.com.data.local.ConstKV;
import px500.pipoask.com.data.local.SharedPreferenceHelper;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

@Module
public class PixelApiModule {

    private static final String OAUTH_TOKEN = "oauth_token";
    private static final String OAUTH_TOKEN_SECRET = "oauth_token_secret";
    private static final String CONSUMER_KEY = "consumer_key";

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(60 * 1000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(60 * 1000, TimeUnit.MILLISECONDS);
        okHttpClient.interceptors().add(chain -> {
            //TODO: Save it in local memory
            String token = SharedPreferenceHelper.getSharedPreferenceString(ConstKV.USER_500PX_TOKEN, null);
            String token_secret = SharedPreferenceHelper.getSharedPreferenceString(ConstKV.USER_500PX_TOKEN_SECRET, null);
            Request request = chain.request();
            HttpUrl.Builder builder = request.httpUrl().newBuilder();
            builder.addQueryParameter(CONSUMER_KEY, BuildConfig.CONSUMER_KEY);
            if (token != null && token_secret != null) {
                builder.addQueryParameter(OAUTH_TOKEN, token)
                        .addQueryParameter(OAUTH_TOKEN_SECRET, token_secret);
            }
            HttpUrl url = builder.build();
            request = request.newBuilder().url(url).build();
            return chain.proceed(request);
        });
        return okHttpClient;
    }

    @Provides
    @Singleton
    PhotoApi provideRestAdapter(OkHttpClient okHttpClient) {
        if (BuildConfig.DEBUG) {
            okHttpClient.networkInterceptors().add(chain -> {
                Request request = chain.request();
                String protocolPrefix = request.isHttps() ? "S" : "";
                Log.i("OkHttpClient", String.format("---> HTTP%s %s %s",
                        protocolPrefix, request.method(), request.urlString()));
                return chain.proceed(request);
            });
        }
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.ENDPOINT)
                .client(okHttpClient)
                .build();
        return retrofit.create(PhotoApi.class);
    }
}
