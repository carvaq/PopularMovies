package cvv.udacity.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cvv.udacity.popularmovies.data.Movie;

/**
 * Created by Caro Vaquero
 * Date: 12.06.2016
 * Project: PopularMovies
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context mContext;
    private List<Movie> mMovies = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private OnMovieClickListener mOnMovieClickListener;

    public MovieAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mOnMovieClickListener = (OnMovieClickListener) context;
    }

    public void addMovie(Movie movie) {
        mMovies.add(movie);
        notifyItemInserted(mMovies.size() - 1);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnMovieClickListener.onMovieClicked(mMovies.get(getAdapterPosition()));
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
        Movie movie = mMovies.get(position);

        Picasso.with(mContext)
                .load(String.format(ApiService.IMAGE_URL_WITH_PLACEHOLDERS, movie.getPosterPath()))
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    @Override
    public long getItemId(int position) {
        return mMovies.get(position).getId();
    }

    public void setMovies(List<Movie> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }
}
