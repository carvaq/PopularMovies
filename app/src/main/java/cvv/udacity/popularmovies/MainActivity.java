package cvv.udacity.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnMovieClickListener {

    private static final String DETAIL_TAG = DetailFragment.class.getSimpleName();

    @BindView(R.id.detail_container)
    View mDetailContainer;

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //http://jakewharton.github.io/butterknife/

        mTwoPane = findViewById(R.id.detail_container) != null;
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
            startActivity(new Intent(this, SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieClicked(Movie movie) {

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
