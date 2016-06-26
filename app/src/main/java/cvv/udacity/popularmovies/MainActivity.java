package cvv.udacity.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cvv.udacity.popularmovies.data.Movie;

public class MainActivity extends AppCompatActivity implements OnItemClickListener<Movie> {

    private static final String DETAIL_TAG = DetailFragment.class.getSimpleName();

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTwoPane = findViewById(R.id.detail_container) != null;

        MovieGridFragment fragment = (MovieGridFragment) getSupportFragmentManager()
                .findFragmentById(R.id.grid_fragment);

        fragment.showingDetails(mTwoPane);
    }

    @Override
    public void onItemClicked(Movie movie) {
        if (mTwoPane) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, DetailFragment.newInstance(movie), DETAIL_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra(DetailActivity.MOVIE_EXTRA, movie);
            startActivity(intent);
        }
    }
}

