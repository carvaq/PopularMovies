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
import cvv.udacity.popularmovies.OnItemClickListener;
import cvv.udacity.popularmovies.R;
import cvv.udacity.popularmovies.data.Movie;

/**
 * Created by Caro Vaquero
 * Date: 12.06.2016
 * Project: PopularMovies
 */

public class MovieAdapter extends BaseAdapter<MovieAdapter.ViewHolder> {

    private List<Movie> mMovies = new ArrayList<>();
    private OnItemClickListener mOnMovieClickListener;

    public MovieAdapter(Context context) {
        super(context);
        mOnMovieClickListener = (OnItemClickListener) context;
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
                    mOnMovieClickListener.onItemClicked(mMovies.get(getAdapterPosition()));
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
