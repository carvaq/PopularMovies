package cvv.udacity.popularmovies;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cvv.udacity.popularmovies.adapter.MovieAdapter;
import cvv.udacity.popularmovies.data.Movie;
import cvv.udacity.popularmovies.data.MovieFetch;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Caro Vaquero
 * Date: 12.06.2016
 * Project: PopularMovies
 */
public class MovieGridFragment extends Fragment {

    private static final String LAST_POS = "last_selected_pos";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private MovieAdapter mMovieAdapter;
    private GridLayoutManager mGridLayoutManager;
    private boolean mShowingDetails;

    public MovieGridFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_movies_grid, container, false);

        //http://jakewharton.github.io/butterknife/
        ButterKnife.bind(this, rootView);

        mGridLayoutManager = new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.number_of_columns));
        mMovieAdapter = new MovieAdapter(getActivity(), mOnFavClickListener);
        mMovieAdapter.setHasStableIds(true);
        if (savedInstanceState != null) {
            mMovieAdapter.setLastSelectedMovie(savedInstanceState.getInt(LAST_POS, -1));
        } else if (mShowingDetails) {
            mMovieAdapter.setLastSelectedMovie(0);
        }
        mMovieAdapter.setDetailsShowing(mShowingDetails);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mMovieAdapter);

        setHasOptionsMenu(true);

        startMovieDataFetch();
        registerReceiver();

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_grid_moview, menu);
        String pref = getSortPreference();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            if (pref.equals(menuItem.getTitle())) {
                menuItem.setChecked(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String pref = getSortPreference();
        if (!pref.equals(item.getTitle())) {
            item.setChecked(true);
            setSortPreference(String.valueOf(item.getTitle()));
            if (mShowingDetails) {
                mMovieAdapter.setLastSelectedMovie(0);
            } else {
                mMovieAdapter.setLastSelectedMovie(-1);
            }
            startMovieDataFetch();
        }
        return true;
    }

    private void startMovieDataFetch() {
        //https://kmangutov.wordpress.com/2015/03/28/android-mvp-consuming-restful-apis/
        String sortPref = getSortPreference();


        if (getString(R.string.pref_sorting_favourites).equals(sortPref)) {

            Observable.just(getFavouriteItems())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(mMovieObserver);
        } else {

            ApiService apiService = new ApiService();
            Observable<MovieFetch> observable;

            if (getString(R.string.pref_sorting_popular).equals(sortPref)) {
                observable = apiService.getApi().getPopular(ApiService.APP_KEY);
            } else {
                observable = apiService.getApi().getTopRated(ApiService.APP_KEY);
            }

            observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mMovieObserver);
        }
    }

    private Observer<MovieFetch> mMovieObserver = new Observer<MovieFetch>() {
        public static final String TAG = "Observer";

        @Override
        public void onCompleted() {
            Log.d(TAG, "onCompleted: ");
        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(getActivity(), R.string.error_something_went_wrong, Toast.LENGTH_LONG).show();
            Log.e(TAG, "", e);
        }

        @Override
        public void onNext(MovieFetch movieFetch) {
            mMovieAdapter.setMovies(movieFetch.getMovies());
            if (mMovieAdapter.getLastSelectedMovie() != -1 && mMovieAdapter.getItemCount() > 0) {
                mGridLayoutManager.scrollToPosition(mMovieAdapter.getLastSelectedMovie());
                ((OnItemClickListener<Movie>) getActivity())
                        .onItemClicked(movieFetch.getMovies().get(mMovieAdapter.getLastSelectedMovie()));
            }
        }
    };

    private OnItemClickListener<Movie> mOnFavClickListener = new OnItemClickListener<Movie>() {
        @Override
        public void onItemClicked(Movie item) {
            if (item.exists()) {
                item.delete();
            } else {
                item.save();
            }

            Intent intent = new Intent(DetailFragment.INTENT_FAVOURITE_UPDATE);
            intent.putExtra(DetailActivity.MOVIE_EXTRA, item);
            LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
        }
    };

    private BroadcastReceiver mFavUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (isAdded()) {
                LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(this);
                updateAdapter(intent);
                registerReceiver();
            }
        }

        private void updateAdapter(Intent intent) {
            Movie movie = intent.getParcelableExtra(DetailActivity.MOVIE_EXTRA);
            if (getString(R.string.pref_sorting_favourites).equals(getSortPreference())) {
                boolean updateDetailsNeeded = mMovieAdapter.removeMovie(movie);
                if (updateDetailsNeeded) {
                    ((OnItemClickListener<Movie>) getActivity())
                            .onItemClicked(mMovieAdapter.getSelectedItems());
                } else if (mMovieAdapter.getItemCount() == 0) {
                    ((OnItemClickListener<Movie>) getActivity())
                            .onItemClicked(null);
                }
            } else {
                mMovieAdapter.updateMovie(movie);
            }
        }
    };

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter(DetailFragment.INTENT_FAVOURITE_UPDATE);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mFavUpdateReceiver, intentFilter);
    }

    private MovieFetch getFavouriteItems() {
        MovieFetch movieFetch = new MovieFetch();
        //https://github.com/Raizlabs/DBFlow/blob/master/usage2/GettingStarted.md
        List<Movie> movies = SQLite.select().from(Movie.class).queryList();
        movieFetch.setMovies(movies);
        return movieFetch;
    }

    @NonNull
    private String getSortPreference() {
        return PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString(getString(R.string.pref_sorting_key), getString(R.string.pref_sorting_popular));
    }

    private void setSortPreference(@NonNull String pref) {
        PreferenceManager.getDefaultSharedPreferences(getActivity())
                .edit()
                .putString(getString(R.string.pref_sorting_key), pref)
                .commit();
    }

    public void showingDetails(boolean showingDetails) {
        mShowingDetails = showingDetails;
        if (mMovieAdapter != null) {
            mMovieAdapter.setDetailsShowing(showingDetails);
            mMovieAdapter.setLastSelectedMovie(showingDetails ? 0 : -1);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(LAST_POS, mMovieAdapter.getLastSelectedMovie());
    }
}
