package cvv.udacity.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cvv.udacity.popularmovies.data.Movie;

public class DetailActivity extends AppCompatActivity {

    public static final String MOVIE_EXTRA = "movie_extra";

    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mMovie = (Movie) (savedInstanceState != null ?
                savedInstanceState.getParcelable(MOVIE_EXTRA) :
                getIntent().getParcelableExtra(MOVIE_EXTRA));

        getSupportFragmentManager().beginTransaction()
                .add(R.id.detail_container, DetailFragment.newInstance(mMovie))
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MOVIE_EXTRA, mMovie);
    }
}
