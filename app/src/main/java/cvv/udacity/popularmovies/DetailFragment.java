package cvv.udacity.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class DetailFragment extends Fragment {
    private TextView mSynopsis;
    private TextView mTitle;
    private TextView mReleaseYear;
    private TextView mVoteAverage;
    private ImageView mPoster;

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

        mSynopsis = (TextView) view.findViewById(R.id.synopsis);
        mTitle = (TextView) view.findViewById(R.id.title);
        mReleaseYear = (TextView) view.findViewById(R.id.release_year);
        mVoteAverage = (TextView) view.findViewById(R.id.vote_average);
        mPoster = (ImageView) view.findViewById(R.id.poster);

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
