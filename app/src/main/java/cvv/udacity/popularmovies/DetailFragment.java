package cvv.udacity.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import cvv.udacity.popularmovies.data.Movie;


public class DetailFragment extends Fragment {
    @BindView(R.id.synopsis)
    TextView mSynopsis;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.release_year)
    TextView mReleaseYear;
    @BindView(R.id.vote_average)
    TextView mVoteAverage;
    @BindView(R.id.poster)
    ImageView mPoster;

    private Movie mMovie;


    public DetailFragment() {
    }

    public static DetailFragment newInstance(Movie param1) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(DetailActivity.MOVIE_EXTRA, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMovie = getArguments().getParcelable(DetailActivity.MOVIE_EXTRA);
        }
        if (savedInstanceState != null) {
            mMovie = savedInstanceState.getParcelable(DetailActivity.MOVIE_EXTRA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        ButterKnife.bind(this, view);

        if (mMovie != null) {
            setMovieValue();
        }
        return view;
    }

    public void updateMovie(Movie movie) {
        this.mMovie = movie;

        if (mMovie != null) {
            setMovieValue();
        }
    }

    private void setMovieValue() {
        mSynopsis.setText(mMovie.getSynopsis());
        mTitle.setText(mMovie.getOriginalTitle());
        mVoteAverage.setText(getString(R.string.detail_vote_average, mMovie.getVoteAverage()));
        mReleaseYear.setText(mMovie.getReleaseDate().substring(0, 4)); //This would be cleaner with SimpleDateFormat
        Picasso.with(getActivity())
                .load(String.format(ApiService.IMAGE_URL_WITH_PLACEHOLDERS, mMovie.getPosterPath()))
                .into(mPoster);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(DetailActivity.MOVIE_EXTRA, mMovie);
    }
}
