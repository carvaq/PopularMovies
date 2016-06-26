package cvv.udacity.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cvv.udacity.popularmovies.ApiService;
import cvv.udacity.popularmovies.BaseAdapter;
import cvv.udacity.popularmovies.MovieWrapper;
import cvv.udacity.popularmovies.OnItemClickListener;
import cvv.udacity.popularmovies.R;
import cvv.udacity.popularmovies.data.Movie;

/**
 * Created by Caro Vaquero
 * Date: 12.06.2016
 * Project: PopularMovies
 */

public class MovieAdapter extends BaseAdapter<MovieAdapter.ViewHolder> {

    private List<MovieWrapper> mViewItems = new ArrayList<>();
    private OnItemClickListener<Movie> mOnMovieClickListener;
    private OnItemClickListener<Movie> mOnFavClickListener;
    private boolean mDetailsShowing;
    private int mLastSelectedMovie = -1;

    public MovieAdapter(Context context, OnItemClickListener<Movie> onFavClickListener) {
        super(context);
        mOnMovieClickListener = (OnItemClickListener<Movie>) context;
        mOnFavClickListener = onFavClickListener;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        ImageView mPoster;
        @BindView(R.id.favourite)
        ImageView mFavourite;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mPoster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MovieWrapper movieWrapper = mViewItems.get(getAdapterPosition());
                    mOnMovieClickListener.onItemClicked(movieWrapper.getMovie());
                    if (mDetailsShowing) {
                        if (mLastSelectedMovie != -1) {
                            mViewItems.get(mLastSelectedMovie).setCurrentlySeclected(false);
                            notifyItemChanged(mLastSelectedMovie);
                        }
                        mLastSelectedMovie = mViewItems.indexOf(movieWrapper);
                        movieWrapper.setCurrentlySeclected(true);
                        notifyItemChanged(mLastSelectedMovie);
                    }
                }
            });
            mFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MovieWrapper movieWrapper = mViewItems.get(getAdapterPosition());
                    mOnFavClickListener.onItemClicked(movieWrapper.getMovie());
                    movieWrapper.setFavourite(!mFavourite.isActivated());
                    notifyItemChanged(mViewItems.indexOf(movieWrapper));
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_movie_thumb, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieWrapper movieWrapper = mViewItems.get(position);

        holder.mPoster.setActivated(movieWrapper.isCurrentlySeclected());
        Picasso.with(mContext)
                .load(String.format(ApiService.IMAGE_URL_WITH_PLACEHOLDERS, movieWrapper.getPosterPath()))
                .into(holder.mPoster);

        holder.mFavourite.setActivated(movieWrapper.isFavourite());
    }

    @Override
    public int getItemCount() {
        return mViewItems.size();
    }

    @Override
    public long getItemId(int position) {
        return mViewItems.get(position).getId();
    }

    public void setMovies(List<Movie> movies) {
        mViewItems.clear();
        for (int i = 0; i < movies.size(); i++) {
            MovieWrapper movieWrapper = new MovieWrapper(movies.get(i));
            movieWrapper.setCurrentlySeclected(mLastSelectedMovie == i);
            mViewItems.add(movieWrapper);
        }
        notifyDataSetChanged();
    }

    public void setDetailsShowing(boolean detailsShowing) {
        mDetailsShowing = detailsShowing;
    }

    public void updateMovie(Movie movie) {
        MovieWrapper movieWrapper = new MovieWrapper(movie);
        int index = mViewItems.indexOf(movieWrapper);
        mViewItems.get(index).setFavourite(movie.exists());
        notifyItemChanged(index);
    }

    public int getLastSelectedMovie() {
        return mLastSelectedMovie;
    }

    public void setLastSelectedMovie(int lastSelectedMovie) {
        mLastSelectedMovie = lastSelectedMovie;
    }
}
