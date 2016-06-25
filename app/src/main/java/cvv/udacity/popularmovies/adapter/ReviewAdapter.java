package cvv.udacity.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cvv.udacity.popularmovies.BaseAdapter;
import cvv.udacity.popularmovies.OnItemClickListener;
import cvv.udacity.popularmovies.R;
import cvv.udacity.popularmovies.data.Review;

/**
 * Created by Caro Vaquero
 * Date: 25.06.2016
 * Project: PopularMovies
 */
public class ReviewAdapter extends BaseAdapter<ReviewAdapter.ViewHolder> {

    private List<Review> mReviews = new ArrayList<>();
private OnItemClickListener<Review> mReviewOnItemClickListener;

    public ReviewAdapter(Context context) {
        super(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView mAuthor;
        @BindView(R.id.content)
        TextView mContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mReviewOnItemClickListener.onItemClicked(mReviews.get(getAdapterPosition()));
                }
            });
        }
    }

    public void setReviews(List<Review> reviews) {
        mReviews = reviews;
        notifyDataSetChanged();
    }

    public void setReviewOnItemClickListener(OnItemClickListener<Review> reviewOnItemClickListener) {
        mReviewOnItemClickListener = reviewOnItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_review, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Review review = mReviews.get(position);
        holder.mAuthor.setText(review.getAuthor());
        holder.mContent.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    @Override
    public long getItemId(int position) {
        return mReviews.get(position).getId().hashCode();
    }
}
