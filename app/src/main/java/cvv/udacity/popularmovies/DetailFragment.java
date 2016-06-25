package cvv.udacity.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import cvv.udacity.popularmovies.adapter.ReviewAdapter;
import cvv.udacity.popularmovies.adapter.VideoAdapter;
import cvv.udacity.popularmovies.data.Movie;
import cvv.udacity.popularmovies.data.Review;
import cvv.udacity.popularmovies.data.ReviewFetch;
import cvv.udacity.popularmovies.data.Video;
import cvv.udacity.popularmovies.data.VideoFetch;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class DetailFragment extends Fragment {
    @BindView(R.id.synopsis)
    TextView mSynopsis;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.release_year)
    TextView mReleaseYear;
    @BindView(R.id.vote_average)
    TextView mVoteAverage;
    @BindView(R.id.poster)
    ImageView mPoster;
    @BindView(R.id.recycler_view_reviews)
    RecyclerView mReviewsRecyclerView;
    @BindView(R.id.recycler_view_videos)
    RecyclerView mVideosRecyclerView;

    private Movie mMovie;

    private VideoAdapter mVideoAdapter;
    private ReviewAdapter mReviewAdapter;

    public DetailFragment() {
    }

    public static DetailFragment newInstance(Movie param1) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(DetailActivity.MOVIE_EXTRA, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMovie = getArguments().getParcelable(DetailActivity.MOVIE_EXTRA);
        }
        if (savedInstanceState != null) {
            mMovie = savedInstanceState.getParcelable(DetailActivity.MOVIE_EXTRA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);

        if (mMovie != null) {
            setMovieValue();
            prepareRecyclerViews();
        }
        return view;
    }

    private void prepareRecyclerViews() {
        mVideoAdapter = new VideoAdapter(getActivity());
        mVideoAdapter.setHasStableIds(true);
        mVideoAdapter.setVideoOnItemClickListener(new OnItemClickListener<Video>() {
            @Override
            public void onItemClicked(Video item) {

            }
        });
        mVideosRecyclerView.setHasFixedSize(true);
        mVideosRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mVideosRecyclerView.setAdapter(mVideoAdapter);

        mReviewAdapter = new ReviewAdapter(getActivity());
        mReviewAdapter.setHasStableIds(true);
        mReviewAdapter.setReviewOnItemClickListener(new OnItemClickListener<Review>() {
            @Override
            public void onItemClicked(Review item) {

            }
        });
        mReviewsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mReviewsRecyclerView.setHasFixedSize(true);
        mReviewsRecyclerView.setAdapter(mReviewAdapter);

        startVideosAndReviewsFetch();
    }


    private void startVideosAndReviewsFetch() {
        ApiService apiService = new ApiService();

        Observable<ReviewFetch> reviewsObservable = apiService.getApi()
                .getReviewsForMovie(mMovie.getId(), ApiService.APP_KEY);

        reviewsObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReviewFetch>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mReviewsRecyclerView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(ReviewFetch reviewFetch) {
                        mReviewAdapter.setReviews(reviewFetch.getReviews());
                    }
                });

        Observable<VideoFetch> videoObservable = apiService.getApi()
                .getVideosForMovie(mMovie.getId(), ApiService.APP_KEY);

        videoObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VideoFetch>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mVideosRecyclerView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(VideoFetch videoFetch) {
                        mVideoAdapter.setVideoList(videoFetch.getVideos());
                    }
                });
    }

    private void setMovieValue() {
        mSynopsis.setText(mMovie.getSynopsis());
        mTitle.setText(mMovie.getOriginalTitle());
        mVoteAverage.setText(getString(R.string.detail_vote_average, mMovie.getVoteAverage()));
        mReleaseYear.setText(mMovie.getReleaseDate().substring(0, 4)); //This would be cleaner with SimpleDateFormat
        Picasso.with(getActivity())
                .load(String.format(ApiService.IMAGE_URL_WITH_PLACEHOLDERS, mMovie.getPosterPath()))
                .into(mPoster);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(DetailActivity.MOVIE_EXTRA, mMovie);
    }
}
