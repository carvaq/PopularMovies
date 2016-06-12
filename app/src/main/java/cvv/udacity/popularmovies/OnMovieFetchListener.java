package cvv.udacity.popularmovies;

import java.util.List;

/**
 * Created by Caro Vaquero
 * Date: 12.06.2016
 * Project: PopularMovies
 */

public interface OnMovieFetchListener {
    void onMoviesFetched(List<Movie> movies);
}
