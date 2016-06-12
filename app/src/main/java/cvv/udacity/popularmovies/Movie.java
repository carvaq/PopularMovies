package cvv.udacity.popularmovies;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import static android.content.ContentValues.TAG;

/**
 * Created by Caro Vaquero
 * Date: 12.06.2016
 * Project: PopularMovies
 */

public class Movie implements Serializable {
    private static final transient String ID = "id";
    private static final transient String ORIGINAL_TITLE = "original_title";
    private static final transient String TITLE = "title";
    private static final transient String POSTER_PATH = "poster_path";
    private static final transient String VOTE_AVERAGE = "vote_average";
    private static final transient String OVERVIEW = "overview";


    private Integer id;
    private String title;
    private String originalTitle;
    private String posterPath;
    private String description;
    private Double voteAverage;

    public Movie() {
    }

    public Movie(JSONObject jsonObject) {
        try {
            id = jsonObject.getInt(ID);
            title = jsonObject.getString(TITLE);
            originalTitle = jsonObject.getString(ORIGINAL_TITLE);
            posterPath = jsonObject.getString(POSTER_PATH);
            description = jsonObject.getString(OVERVIEW);
            voteAverage = jsonObject.getDouble(VOTE_AVERAGE);
        } catch (JSONException e) {
            Log.e(TAG, "Movie: Could not parse json");
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", description='" + description + '\'' +
                ", voteAverage=" + voteAverage +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (!id.equals(movie.id)) return false;
        return originalTitle != null ? originalTitle.equals(movie.originalTitle) : movie.originalTitle == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (originalTitle != null ? originalTitle.hashCode() : 0);
        return result;
    }
}
