package px500.pipoask.com.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PhotoList {
    @SerializedName("photos")
    public final List<Photo> photos = new ArrayList<>();
}
