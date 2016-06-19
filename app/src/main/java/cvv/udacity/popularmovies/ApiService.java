package cvv.udacity.popularmovies;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Caro Vaquero
 * Date: 13.06.2016
 * Project: PopularMovies
 */

public class ApiService {

    public static final String APP_KEY = "";
    public static final String API_BASE_URL = "https://api.themoviedb.org/3/movie/";
    public static final String IMAGE_URL_WITH_PLACEHOLDERS = "http://image.tmdb.org/t/p/w185%s";


    private MovieApi mMovieApi;

    public ApiService() {

        Retrofit restAdapter = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(API_BASE_URL)
                .build();

        mMovieApi = restAdapter.create(MovieApi.class);
    }

    public MovieApi getApi() {

        return mMovieApi;
    }

    public interface MovieApi {

        @Headers("Accept:application/json")
        @GET("popular")
        Observable<MovieFetch> getPopular(@Query("api_key") String appKey);

        @Headers("Accept:application/json")
        @GET("top_rated")
        Observable<MovieFetch> getTopRated(@Query("api_key") String appKey);

    }
}
