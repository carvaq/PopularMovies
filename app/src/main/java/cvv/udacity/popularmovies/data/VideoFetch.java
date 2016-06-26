package cvv.udacity.popularmovies.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 * Created by Caro Vaquero
 * Date: 25.06.2016
 * Project: PopularMovies
 * <p/>
 * <p/>
 * Sample JSON
 * {
 * "id": 550,
 * "results": [
 * {
 * "id": "533ec654c3a36854480003eb",
 * "iso_639_1": "en",
 * "key": "SUXWAEX2jlg",
 * "name": "Trailer 1",
 * "site": "YouTube",
 * "size": 720,
 * "type": "Trailer"
 * }
 * ]
 * }
 */

public class VideoFetch {

    @SerializedName("id")
    public Integer mId;
    @SerializedName("results")
    public List<Video> mVideos;

    public VideoFetch() {
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public List<Video> getVideos() {
        return mVideos;
    }

    public void setVideos(List<Video> videos) {
        mVideos = videos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VideoFetch that = (VideoFetch) o;

        return mId.equals(that.mId);

    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    @Override
    public String toString() {
        return "VideoFetch{" +
                "mId=" + mId +
                ", mVideos=" + mVideos +
                '}';
    }
}
