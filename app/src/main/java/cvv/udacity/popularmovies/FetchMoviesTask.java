package cvv.udacity.popularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Caro Vaquero
 * Date: 12.06.2016
 * Project: PopularMovies
 */
public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {
    private final String TAG = FetchMoviesTask.class.getSimpleName();
    private Context mContext;
    private OnMovieFetchListener mOnMovieFetchListener;

    public FetchMoviesTask(Context context, OnMovieFetchListener onMovieFetchListener) {
        mContext = context;
        mOnMovieFetchListener = onMovieFetchListener;
    }

    @Nullable
    @Override
    protected List<Movie> doInBackground(String... params) {
        if (params != null) {
            return fetchMovies(mContext.getString(R.string.pref_sorting_popular).equals(params[0]));
        } else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        super.onPostExecute(movies);
        mOnMovieFetchListener.onMoviesFetched(movies);
    }

    @Nullable
    private List<Movie> fetchMovies(boolean sortOrderPopular) {
        BufferedReader reader = null;
        HttpURLConnection urlConnection = null;
        String movieJsonStr = null;

        try {
            String path = sortOrderPopular ? ApiHelper.PATH_POPULAR : ApiHelper.PATH_TOP_RATED;
            URL url = new URL(String.format(ApiHelper.API_BASE_URL, path, ApiHelper.APP_KEY));
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            urlConnection.connect();

            //From sunshine project
            int responseCode = urlConnection.getResponseCode();
            if (urlConnection.getResponseCode() >= 300 || responseCode < 200) {
                Log.e(TAG, "Request was unsuccessful. Error " + responseCode);
                return null;
            }

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder builder = new StringBuilder();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }

            if (builder.length() == 0) {
                return null;
            }

            movieJsonStr = builder.toString();
            Log.d(TAG, "fetchMovies: " + movieJsonStr);

        } catch (MalformedURLException e) {
            Log.e(TAG, "doInBackground: ", e);
        } catch (IOException e) {
            Log.e(TAG, "doInBackground: IOException", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ignore) {
            }

        }

        try {
            return parseMoviesJson(movieJsonStr);
        } catch (JSONException e) {
            Log.e(TAG, "fetchMovies: Could not parse JSON", e);
            return null;
        }
    }

    @Nullable
    private List<Movie> parseMoviesJson(String json) throws JSONException {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        final String MOVIE_LIST_KEY = "results";
        List<Movie> movies = new ArrayList<>();
        JSONObject responseJson = new JSONObject(json);
        JSONArray jsonArray = responseJson.getJSONArray(MOVIE_LIST_KEY);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            movies.add(new Movie(jsonObject));
        }

        return movies;
    }
}
