package cvv.udacity.popularmovies;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Caro Vaquero
 * Date: 12.06.2016
 * Project: PopularMovies
 */
public class MovieGridFragment extends Fragment {
    private MovieAdapter mMovieAdapter;
    private GridLayoutManager mGridLayoutManager;

    public MovieGridFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_movies_grid, container, false);
        mMovieAdapter = new MovieAdapter(getActivity());
        mMovieAdapter.setHasStableIds(true);
        mGridLayoutManager = new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.number_of_columns));

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mGridLayoutManager);
        recyclerView.setAdapter(mMovieAdapter);


        FetchMoviesTask fetchMoviesTask = new FetchMoviesTask(getActivity(), mOnMovieFetchListener);
        fetchMoviesTask.execute(PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString(getString(R.string.pref_sorting_key), getString(R.string.pref_sorting_popular)));

        return rootView;
    }


    private OnMovieFetchListener mOnMovieFetchListener = new OnMovieFetchListener() {
        @Override
        public void onMoviesFetched(List<Movie> movies) {
            if (movies != null) {
                mMovieAdapter.setMovies(movies);
            }
        }
    };

}
