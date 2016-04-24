package px500.pipoask.com.data.api;

import px500.pipoask.com.data.model.Photo;
import px500.pipoask.com.data.model.PhotoDetail;
import px500.pipoask.com.data.model.PhotoList;
import px500.pipoask.com.data.model.VoteList;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface PhotoApi {

    @GET("/v1/photos?image_size=440&include_store=store_download&include_states=voted")
    Observable<PhotoList> getFeaturePhotos(@Query("page") int page, @Query("feature") String feature);

    @GET("/v1/photos/{id}?image_size=1080")
    Observable<PhotoDetail> getPhotoById(@Path("id") Integer id);

    @GET("/v1/photos/search")
    Observable<PhotoList> search(@Query("term") String keyword);

    @POST("/v1/photos")
    Observable<Photo> postPhoto(@Field("name") String name, @Field("description") String description, @Field("category") String category);

    @POST("/v1/photos/{id}/vote")
    Observable<Photo> postVote(@Path("id") Integer id, @Query("vote") int vote);

    @DELETE("/v1/photos/{id}/vote")
    Observable<Photo> deleteVote(@Path("id") Integer id);
}
