package cvv.udacity.popularmovies.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Caro Vaquero
 * Date: 25.06.2016
 * Project: PopularMovies
 *
 * For sample JSON:
 * @see ReviewFetch
 */
public class Review {

    @SerializedName("id")
    public String mId;
    @SerializedName("author")
    public String mAuthor;
    @SerializedName("content")
    public String mContent;
    @SerializedName("url")
    public String mUrl;

    public Review() {
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;

        return mId.equals(review.mId);

    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    @Override
    public String toString() {
        return "Review{" +
                "mId='" + mId + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mContent='" + mContent + '\'' +
                ", mUrl='" + mUrl + '\'' +
                '}';
    }
}