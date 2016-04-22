package px500.pipoask.com.di.module;

import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import px500.pipoask.com.BuildConfig;
import px500.pipoask.com.data.api.PhotoApi;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

@Module
public class PixelApiModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(60 * 1000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(60 * 1000, TimeUnit.MILLISECONDS);
        return okHttpClient;
    }

    @Provides
    @Singleton
    PhotoApi provideRestAdapter(OkHttpClient okHttpClient) {
        if (BuildConfig.DEBUG) {
            okHttpClient.networkInterceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    String protocolPrefix = request.isHttps() ? "S" : "";
                    Log.i("OkHttpClient", String.format("---> HTTP%s %s %s",
                            protocolPrefix, request.method(), request.urlString()));
                    return chain.proceed(request);
                }
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
