package cvv.udacity.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements OnMovieClickListener {

    private static final String DETAIL_TAG = DetailFragment.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (UiHelper.isXLargeTablet(this)) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.grid_container, new MovieGridFragment())
                    .add(R.id.detail_container, new DetailFragment(), DETAIL_TAG)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.grid_container, new MovieGridFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class
            ));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieClicked(Movie movie) {
        if (UiHelper.isXLargeTablet(MainActivity.this)) {
            DetailFragment fragment =
                    (DetailFragment) getSupportFragmentManager().findFragmentByTag(DETAIL_TAG);
            fragment.updateMovie(movie);
        } else {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra(DetailActivity.MOVIE_EXTRA, movie);
            startActivity(intent);
        }
    }
}
