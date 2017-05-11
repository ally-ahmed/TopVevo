package com.example.vagabond.ip;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by vagabond on 4/8/17.
 */

public class FirebaseVideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    View mView;
    Context mContext;

    public FirebaseVideoViewHolder(View itemView){
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindVideo(Video video){
        ImageView mVideoImageView = (ImageView) mView.findViewById(R.id.thumbnail);
        TextView mVideoTitle = (TextView) mView.findViewById(R.id.title);
        Picasso.with(mContext)
                .load(video.getImageUrl())
                .into(mVideoImageView);
        mVideoTitle.setText(video.getTitle());
    }

    @Override
    public void onClick(View v) {
        final ArrayList<Video> videos = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_VIDEOS);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    videos.add(snapshot.getValue(Video.class));
                }
                int itemPosition = getLayoutPosition();
                Intent intent = new Intent(mContext,VideoPlay.class);
                intent.putExtra("position", itemPosition);
                intent.putExtra("videos", Parcels.wrap(videos));
                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
