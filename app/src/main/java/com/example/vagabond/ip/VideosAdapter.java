package com.example.vagabond.ip;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by vagabond on 3/30/17.
 */

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoViewHolder> {
    private ArrayList<Video> mVideos = new ArrayList<>();
    private Context mContext;


    public VideosAdapter(Context context, ArrayList<Video> videos) {
        mContext = context;
        mVideos = videos;
    }

    @Override
    public VideosAdapter.VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_card, parent, false);
        VideoViewHolder viewHolder = new VideoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VideosAdapter.VideoViewHolder holder, int position) {
        holder.bindVideo(mVideos.get(position));
    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @Bind(R.id.thumbnail) ImageView mVideoImageView;
        @Bind(R.id.title) TextView mVideoTitle;
//        @Bind(R.id.channelTitle) TextView mChannelTitle;

        private Context mContext;

        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindVideo(Video video) {
            mVideoTitle.setText(video.getTitle());
//            mChannelTitle.setText(video.getChannelTitle());
            Picasso.with(mContext)
                    .load(video.getImageUrl())
                    .into(mVideoImageView);


        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext,VideoPlay.class );
            intent.putExtra("position", itemPosition);
            intent.putExtra("videos", Parcels.wrap(mVideos));
            mContext.startActivity(intent);

        }
    }
}
