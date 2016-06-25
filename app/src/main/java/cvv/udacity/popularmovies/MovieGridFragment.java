package cvv.udacity.popularmovies;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import cvv.udacity.popularmovies.adapter.MovieAdapter;
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

        mMovieAdapter = new MovieAdapter(getActivity());
        mMovieAdapter.setHasStableIds(true);
        mGridLayoutManager = new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.number_of_columns));

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mMovieAdapter);

        startMovieDataFetch();

        return rootView;
    }

    private void startMovieDataFetch() {
        //https://kmangutov.wordpress.com/2015/03/28/android-mvp-consuming-restful-apis/
        String orderPref = PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString(getString(R.string.pref_sorting_key), getString(R.string.pref_sorting_popular));

        ApiService apiService = new ApiService();

        Observable<MovieFetch> observable;
        if (getString(R.string.pref_sorting_popular).equals(orderPref)) {
            observable = apiService.getApi().getPopular(ApiService.APP_KEY);
        } else {
            observable = apiService.getApi().getTopRated(ApiService.APP_KEY);
        }

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieFetch>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), R.string.error_something_went_wrong, Toast.LENGTH_LONG).show();
                        Log.e(getTag(), "", e);
                    }

                    @Override
                    public void onNext(MovieFetch movieFetch) {
                        mMovieAdapter.setMovies(movieFetch.getMovies());
                    }
                });
    }

    public void updateColumnSize() {
        mGridLayoutManager.setSpanCount(getResources().getInteger(R.integer.number_of_columns_with_fragment));
    }
}
