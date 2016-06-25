package cvv.udacity.popularmovies.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Caro Vaquero
 * Date: 25.06.2016
 * Project: PopularMovies
 *
 * For sample JSON :
 * @see VideoFetch
 */
public class Video {

    @SerializedName("id")
    public String mId;
    @SerializedName("iso_639_1")
    public String mIso6391;
    @SerializedName("key")
    public String mKey;
    @SerializedName("name")
    public String mName;
    @SerializedName("site")
    public String mSite;
    @SerializedName("size")
    public Integer mSize;
    @SerializedName("type")
    public String mType;

    public Video() {
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getIso6391() {
        return mIso6391;
    }

    public void setIso6391(String iso6391) {
        mIso6391 = iso6391;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSite() {
        return mSite;
    }

    public void setSite(String site) {
        mSite = site;
    }

    public Integer getSize() {
        return mSize;
    }

    public void setSize(Integer size) {
        mSize = size;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Video video = (Video) o;

        return mId.equals(video.mId);

    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    @Override
    public String toString() {
        return "Video{" +
                "mId='" + mId + '\'' +
                ", mIso6391='" + mIso6391 + '\'' +
                ", mKey='" + mKey + '\'' +
                ", mName='" + mName + '\'' +
                ", mSite='" + mSite + '\'' +
                ", mSize=" + mSize +
                ", mType='" + mType + '\'' +
                '}';
    }
}