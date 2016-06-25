package cvv.udacity.popularmovies.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Caro Vaquero
 * Date: 25.06.2016
 * Project: PopularMovies
 *
 * Sample JSON
 * {
 "id": 49026,
 "page": 1,
 "results": [
 {
 "id": "5010553819c2952d1b000451",
 "author": "Travis Bell",
 "content": "I felt like this was a tremendous end to Nolan's Batman trilogy. The Dark Knight Rises may very well have been the weakest of all 3 films but when you're talking about a scale of this magnitude, it still makes this one of the best movies I've seen in the past few years.\r\n\r\nI expected a little more _Batman_ than we got (especially with a runtime of 2:45) but while the story around the fall of Bruce Wayne and Gotham City was good I didn't find it amazing. This might be in fact, one of my only criticisms—it was a long movie but still, maybe too short for the story I felt was really being told. I feel confident in saying this big of a story could have been split into two movies.\r\n\r\nThe acting, editing, pacing, soundtrack and overall theme were the same 'as-close-to-perfect' as ever with any of Christopher Nolan's other films. Man does this guy know how to make a movie!\r\n\r\nYou don't have to be a Batman fan to enjoy these movies and I hope any of you who feel this way re-consider. These 3 movies are without a doubt in my mind, the finest display of comic mythology ever told on the big screen. They are damn near perfect.",
 "url": "http://j.mp/QSjAK2"
 }
 ]
 }
 */
public class ReviewFetch {


    @SerializedName("id")
    public Integer mId;
    @SerializedName("page")
    public Integer mPage;
    @SerializedName("results")
    public List<Review> mReviews;
    @SerializedName("total_pages")
    public Integer mTotalPages;
    @SerializedName("total_results")
    public Integer mTotalResults;

    public ReviewFetch() {
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public Integer getPage() {
        return mPage;
    }

    public void setPage(Integer page) {
        mPage = page;
    }

    public List<Review> getReviews() {
        return mReviews;
    }

    public void setReviews(List<Review> reviews) {
        mReviews = reviews;
    }

    public Integer getTotalPages() {
        return mTotalPages;
    }

    public void setTotalPages(Integer totalPages) {
        mTotalPages = totalPages;
    }

    public Integer getTotalResults() {
        return mTotalResults;
    }

    public void setTotalResults(Integer totalResults) {
        mTotalResults = totalResults;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReviewFetch that = (ReviewFetch) o;

        return mId.equals(that.mId);

    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    @Override
    public String toString() {
        return "ReviewFetch{" +
                "mId=" + mId +
                ", mPage=" + mPage +
                ", mReviews=" + mReviews +
                ", mTotalPages=" + mTotalPages +
                ", mTotalResults=" + mTotalResults +
                '}';
    }

}
