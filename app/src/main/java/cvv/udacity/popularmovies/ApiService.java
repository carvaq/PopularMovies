package cvv.udacity.popularmovies;

import cvv.udacity.popularmovies.data.MovieFetch;
import cvv.udacity.popularmovies.data.ReviewFetch;
import cvv.udacity.popularmovies.data.VideoFetch;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
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

    //http://www.joshuawinn.com/get-the-thumbnails-of-a-youtube-video-standard-url-and-file-names/
    public static final String YOUTUBE_THUMB_URL_WITH_PLACEHOLDERS = "http://img.youtube.com/vi/%s/default.jpg";
    public static final String YOUTUBE_WATCH_URL_WITH_PLACEHOLDERS = "https://www.youtube.com/watch?v=%s";


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

        @Headers("Accept:application/json")
        @GET("{id}/videos")
        Observable<VideoFetch> getVideosForMovie(@Path("id") long id, @Query("api_key") String appKey);

        @Headers("Accept:application/json")
        @GET("{id}/reviews")
        Observable<ReviewFetch> getReviewsForMovie(@Path("id") long id, @Query("api_key") String appKey);


    }
}
