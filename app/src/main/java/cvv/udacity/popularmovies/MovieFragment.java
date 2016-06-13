package cvv.udacity.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MovieFragment extends Fragment {


    private Movie mMovie;


    public MovieFragment() {
    }

    public static MovieFragment newInstance(Movie param1) {
        MovieFragment fragment = new MovieFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        TextView synopsis = (TextView) view.findViewById(R.id.synopsis);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView releaseYear = (TextView) view.findViewById(R.id.release_year);
        TextView voteAverage = (TextView) view.findViewById(R.id.vote_average);
        TextView duration = (TextView) view.findViewById(R.id.duration);

        synopsis.setText(mMovie.getSynopsis());
        title.setText(mMovie.getOriginalTitle());
        voteAverage.setText(getString(R.string.detail_vote_average, mMovie.getVoteAverage()));
        releaseYear.setText(mMovie.getReleaseDate().substring(0, 4)); //This would be cleaner with SimpleDateFormat
        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(DetailActivity.MOVIE_EXTRA, mMovie);
    }
}
