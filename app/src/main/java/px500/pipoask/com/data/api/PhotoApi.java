package px500.pipoask.com.data.api;

import px500.pipoask.com.BuildConfig;
import px500.pipoask.com.data.model.PhotoDetail;
import px500.pipoask.com.data.model.PhotoList;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

public interface PhotoApi {

    @GET("/v1/photos?image_size=440&include_store=store_download&include_states=voted&consumer_key=" + BuildConfig.CONSUMER_KEY)
    Observable<PhotoList> getFeaturePhotos(@Query("page") int page, @Query("feature") String feature);

    @GET("/v1/photos/{id}?image_size=1080&consumer_key=" + BuildConfig.CONSUMER_KEY)
    Observable<PhotoDetail> getPhotoById(@Path("id") String id);

    @GET("/v1/photos/search?consumer_key=" + BuildConfig.CONSUMER_KEY)
    Observable<PhotoList> search(@Query("term") String keyword);

}
