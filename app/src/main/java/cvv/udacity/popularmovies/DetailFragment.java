package cvv.udacity.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DetailFragment extends Fragment {
    private TextView synopsis;
    private TextView title;
    private TextView releaseYear;
    private TextView voteAverage;
    private TextView duration;

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

        synopsis = (TextView) view.findViewById(R.id.synopsis);
        title = (TextView) view.findViewById(R.id.title);
        releaseYear = (TextView) view.findViewById(R.id.release_year);
        voteAverage = (TextView) view.findViewById(R.id.vote_average);
        duration = (TextView) view.findViewById(R.id.duration);

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
        synopsis.setText(mMovie.getSynopsis());
        title.setText(mMovie.getOriginalTitle());
        voteAverage.setText(getString(R.string.detail_vote_average, mMovie.getVoteAverage()));
        releaseYear.setText(mMovie.getReleaseDate().substring(0, 4)); //This would be cleaner with SimpleDateFormat
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(DetailActivity.MOVIE_EXTRA, mMovie);
    }
}
