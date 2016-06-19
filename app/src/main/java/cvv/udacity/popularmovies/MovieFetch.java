package cvv.udacity.popularmovies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Caro Vaquero
 * Date: 19.06.2016
 * Project: PopularMovies
 */

public class MovieFetch {

    @SerializedName("results")
    private List<Movie> mMovies;

    public List<Movie> getMovies() {
        return mMovies;
    }

    public void setMovies(List<Movie> movies) {
        mMovies = movies;
    }
}
