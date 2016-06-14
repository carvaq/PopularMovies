package cvv.udacity.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

/**
 * Created by Caro Vaquero
 * Date: 12.06.2016
 * Project: PopularMovies
 */

public class Movie implements Parcelable {
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    private static final transient String ID = "id";
    private static final transient String ORIGINAL_TITLE = "original_title";
    private static final transient String TITLE = "title";
    private static final transient String POSTER_PATH = "poster_path";
    private static final transient String VOTE_AVERAGE = "vote_average";
    private static final transient String OVERVIEW = "overview";
    private static final transient String RELEASE_DATE = "release_date";
    private Integer mId;
    private String mTitle;
    private String mOriginalTitle;
    private String mPosterPath;
    private String mSynopsis;
    private String mReleaseDate;
    private Double mVoteAverage;

    public Movie() {
    }

    public Movie(JSONObject jsonObject) {
        try {
            mId = jsonObject.getInt(ID);
            mTitle = jsonObject.getString(TITLE);
            mOriginalTitle = jsonObject.getString(ORIGINAL_TITLE);
            mPosterPath = jsonObject.getString(POSTER_PATH);
            mSynopsis = jsonObject.getString(OVERVIEW);
            mVoteAverage = jsonObject.getDouble(VOTE_AVERAGE);
            mReleaseDate = jsonObject.getString(RELEASE_DATE);
        } catch (JSONException e) {
            Log.e(TAG, "Movie: Could not parse json");
        }
    }

    protected Movie(Parcel in) {
        mId = in.readInt();
        mTitle = in.readString();
        mOriginalTitle = in.readString();
        mPosterPath = in.readString();
        mSynopsis = in.readString();
        mVoteAverage = in.readDouble();
        mReleaseDate = in.readString();
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        this.mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.mOriginalTitle = originalTitle;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        this.mPosterPath = posterPath;
    }

    public String getSynopsis() {
        return mSynopsis;
    }

    public void setSynopsis(String synopsis) {
        this.mSynopsis = synopsis;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.mReleaseDate = releaseDate;
    }

    public Double getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.mVoteAverage = voteAverage;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "mId=" + mId +
                ", mTitle='" + mTitle + '\'' +
                ", mOriginalTitle='" + mOriginalTitle + '\'' +
                ", mPosterPath='" + mPosterPath + '\'' +
                ", mSynopsis='" + mSynopsis + '\'' +
                ", mReleaseDate='" + mReleaseDate + '\'' +
                ", mVoteAverage=" + mVoteAverage +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        return mId.equals(movie.mId) && (mOriginalTitle != null ?
                mOriginalTitle.equals(movie.mOriginalTitle) : movie.mOriginalTitle == null);

    }

    @Override
    public int hashCode() {
        int result = mId.hashCode();
        result = 31 * result + (mOriginalTitle != null ? mOriginalTitle.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mTitle);
        dest.writeString(mOriginalTitle);
        dest.writeString(mPosterPath);
        dest.writeString(mSynopsis);
        dest.writeDouble(mVoteAverage);
        dest.writeString(mReleaseDate);
    }
}
