package cvv.udacity.popularmovies;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    public static final String INTENT_FAVOURITE_UPDATE = "ifu";

    private static final String TAG = DetailFragment.class.getSimpleName();

    @BindView(R.id.content)
    ViewGroup mContent;
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
    @BindView(R.id.header_videos)
    View mHeaderVideos;
    @BindView(R.id.header_reviews)
    View mHeaderReviews;
    @BindView(R.id.view_reviews)
    ViewGroup mReviewsView;
    @BindView(R.id.recycler_view_videos)
    RecyclerView mVideosRecyclerView;
    @BindView(R.id.favourite)
    ImageView mFavourite;

    private Movie mMovie;

    private VideoAdapter mVideoAdapter;

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

        registerReceiver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);

        if (mMovie != null) {
            setMovieValues();
            prepareRecyclerView();
            startVideosAndReviewsFetch();
        } else {
            mContent.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    private void prepareRecyclerView() {
        mVideoAdapter = new VideoAdapter(getActivity());
        mVideoAdapter.setHasStableIds(true);
        mVideoAdapter.setVideoOnItemClickListener(new OnItemClickListener<Video>() {
            @Override
            public void onItemClicked(Video item) {
                if (Video.YOUTUBE_SITE.equals(item.getSite().toLowerCase())) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        String url = String.format(ApiService.YOUTUBE_WATCH_URL_WITH_PLACEHOLDERS, item.getKey());
                        Log.d(TAG, "onItemClicked: " + url);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getActivity(), R.string.error_video_cannot_open, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), R.string.error_video_cannot_open, Toast.LENGTH_LONG).show();
                }
            }
        });
        mVideosRecyclerView.setHasFixedSize(true);
        mVideosRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mVideosRecyclerView.setAdapter(mVideoAdapter);
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
                        Log.e(TAG, "onError: Didn't work out as expected", e);
                        hideReviewsSection();
                    }

                    private void hideReviewsSection() {
                        mReviewsView.setVisibility(View.GONE);
                        mHeaderReviews.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(ReviewFetch reviewFetch) {
                        if (reviewFetch.getReviews() != null && !reviewFetch.getReviews().isEmpty()) {
                            createReviewViews(reviewFetch.getReviews());
                        } else {
                            hideReviewsSection();
                        }
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
                        Log.e(TAG, "onError: Didn't work out as expected", e);
                        hideVideosSection();
                    }

                    private void hideVideosSection() {
                        mVideosRecyclerView.setVisibility(View.GONE);
                        mHeaderVideos.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(VideoFetch videoFetch) {
                        if (videoFetch.getVideos() != null && !videoFetch.getVideos().isEmpty()) {
                            mVideoAdapter.setVideoList(videoFetch.getVideos());
                        } else {
                            mVideosRecyclerView.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void createReviewViews(List<Review> reviews) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        for (final Review review : reviews) {
            View view = layoutInflater.inflate(R.layout.item_review, mReviewsView, false);
            TextView author = (TextView) view.findViewById(R.id.title);
            TextView content = (TextView) view.findViewById(R.id.content);
            author.setText(review.getAuthor());
            content.setText(review.getContent());
            view.findViewById(R.id.title_container).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri uri = Uri.parse(review.getUrl());
                    intent.setData(uri);
                    startActivity(intent);
                }
            });
            mReviewsView.addView(view);
        }
    }

    private void setMovieValues() {
        mContent.setVisibility(View.VISIBLE);
        mFavourite.setActivated(mMovie.exists());
        mFavourite.setOnClickListener(mOnFavClicked);
        mSynopsis.setText(mMovie.getSynopsis());
        mTitle.setText(mMovie.getOriginalTitle());
        mVoteAverage.setText(getString(R.string.detail_vote_average, mMovie.getVoteAverage()));
        mReleaseYear.setText(mMovie.getReleaseDate().substring(0, 4)); //This would be cleaner with SimpleDateFormat
        Picasso.with(getActivity())
                .load(String.format(ApiService.IMAGE_URL_WITH_PLACEHOLDERS, mMovie.getPosterPath()))
                .into(mPoster);
    }

    private View.OnClickListener mOnFavClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mFavourite.isActivated()) {
                mMovie.delete();
            } else {
                mMovie.save();
            }
            mFavourite.setActivated(!mFavourite.isActivated());

            Intent intent = new Intent(INTENT_FAVOURITE_UPDATE);
            intent.putExtra(DetailActivity.MOVIE_EXTRA, mMovie);
            LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
        }
    };

    private BroadcastReceiver mFavUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Movie movie = intent.getParcelableExtra(DetailActivity.MOVIE_EXTRA);
            if (movie.equals(mMovie) && isAdded()) {
                LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(this);
                mFavourite.setActivated(mMovie.exists());
                registerReceiver();
            }
        }
    };

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter(INTENT_FAVOURITE_UPDATE);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mFavUpdateReceiver, intentFilter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(DetailActivity.MOVIE_EXTRA, mMovie);
    }
}
