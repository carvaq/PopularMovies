package cvv.udacity.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cvv.udacity.popularmovies.data.Movie;

public class MainActivity extends AppCompatActivity implements OnItemClickListener<Movie> {

    private static final String DETAIL_TAG = DetailFragment.class.getSimpleName();

    private View mDetailContainer;

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDetailContainer = findViewById(R.id.detail_container);
        mTwoPane = mDetailContainer != null;
    }

    @Override
    public void onItemClicked(Movie movie) {

        if (mTwoPane) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, DetailFragment.newInstance(movie), DETAIL_TAG)
                    .commit();
            if (mDetailContainer.getVisibility() == View.GONE) {
                mDetailContainer.setVisibility(View.VISIBLE);
                MovieGridFragment fragment = (MovieGridFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.grid_fragment);
                fragment.updateColumnSize();
            }
        } else {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra(DetailActivity.MOVIE_EXTRA, movie);
            startActivity(intent);
        }
    }
}

