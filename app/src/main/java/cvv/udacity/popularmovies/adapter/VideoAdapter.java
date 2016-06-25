package cvv.udacity.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cvv.udacity.popularmovies.ApiService;
import cvv.udacity.popularmovies.BaseAdapter;
import cvv.udacity.popularmovies.OnItemClickListener;
import cvv.udacity.popularmovies.R;
import cvv.udacity.popularmovies.data.Video;

/**
 * Created by Caro Vaquero
 * Date: 25.06.2016
 * Project: PopularMovies
 */
public class VideoAdapter extends BaseAdapter<VideoAdapter.ViewHolder> {

    private static final String YOUTUBE_SITE = "youtube";

    private List<Video> mVideoList = new ArrayList<>();
    private OnItemClickListener<Video> mVideoOnItemClickListener;

    public VideoAdapter(Context context) {
        super(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.preview)
        ImageView mPreview;
        @BindView(R.id.name)
        TextView mName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mVideoOnItemClickListener.onItemClicked(mVideoList.get(getAdapterPosition()));
                }
            });
        }
    }

    public void setVideoList(List<Video> videoList) {
        mVideoList = videoList;
    }

    public void setVideoOnItemClickListener(OnItemClickListener<Video> videoOnItemClickListener) {
        mVideoOnItemClickListener = videoOnItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_video, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Video video = mVideoList.get(position);
        Picasso.with(mContext).cancelRequest(holder.mPreview);
        if (YOUTUBE_SITE.equals(video.getSite().toLowerCase())) {
            Picasso.with(mContext)
                    .load(String.format(ApiService.YOUTUBE_PREVIEW_URL_WITH_PLACEHOLDERS, video.getKey()))
                    .into(holder.mPreview);
        }
        holder.mPreview.setContentDescription(video.getSite());
        holder.mName.setText(video.getName());
    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

    @Override
    public long getItemId(int position) {
        return mVideoList.get(position).getId().hashCode();
    }
}
