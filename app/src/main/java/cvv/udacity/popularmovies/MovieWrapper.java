package cvv.udacity.popularmovies;

import cvv.udacity.popularmovies.data.Movie;

/**
 * Created by Caro Vaquero
 * Date: 26.06.2016
 * Project: PopularMovies
 */
public class MovieWrapper {
    private Long mId;
    private String mPosterPath;
    private boolean mIsCurrentlySelected;
    private boolean mFavourite;
    private Movie mMovie;

    public MovieWrapper(Movie movie) {
        mMovie = movie;
        mId = movie.getId();
        mPosterPath = movie.getPosterPath();
        mFavourite = movie.exists();
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    public boolean isCurrentlySelected() {
        return mIsCurrentlySelected;
    }

    public void setCurrentlySelected(boolean currentlySelected) {
        mIsCurrentlySelected = currentlySelected;
    }

    public boolean isFavourite() {
        return mFavourite;
    }

    public void setFavourite(boolean favourite) {
        mFavourite = favourite;
    }

    public Movie getMovie() {
        return mMovie;
    }

    public void setMovie(Movie movie) {
        mMovie = movie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieWrapper that = (MovieWrapper) o;

        return mId.equals(that.mId);

    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }
}
