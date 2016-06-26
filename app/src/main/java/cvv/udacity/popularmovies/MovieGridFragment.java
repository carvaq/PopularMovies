package cvv.udacity.popularmovies;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private MovieAdapter mMovieAdapter;
    private GridLayoutManager mGridLayoutManager;

    public MovieGridFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_movies_grid, container, false);

        //http://jakewharton.github.io/butterknife/
        ButterKnife.bind(this, rootView);

        mMovieAdapter = new MovieAdapter(getActivity(), mOnFavClickListener);
        mMovieAdapter.setHasStableIds(true);
        mGridLayoutManager = new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.number_of_columns));

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mMovieAdapter);

        setHasOptionsMenu(true);

        startMovieDataFetch();

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
        }
    };

    private MovieFetch getFavouriteItems() {
        MovieFetch movieFetch = new MovieFetch();
        //https://github.com/Raizlabs/DBFlow/blob/master/usage2/GettingStarted.md
        List<Movie> movies = SQLite.select().from(Movie.class).queryList();
        movieFetch.setMovies(movies);
        return movieFetch;
    }


    public void updateColumnSize() {
        mGridLayoutManager.setSpanCount(getResources().getInteger(R.integer.number_of_columns_with_fragment));
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
}
