package cvv.udacity.popularmovies;

import cvv.udacity.popularmovies.data.Movie;

/**
 * Created by Caro Vaquero
 * Date: 26.06.2016
 * Project: PopularMovies
 */
public interface OnMovieFavouriteStatusChangedListener {
    void onStatusChanged(Movie movie, boolean favourite);
}
