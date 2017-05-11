package com.example.vagabond.ip;

import android.app.ActivityOptions;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VideoPlay extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, View.OnClickListener {
    public static final String TAG = VideoPlay.class.getSimpleName();
    ArrayList<Video> mVideos = new ArrayList<>();
    String videoId;
    public FloatingActionButton mSave;
    public FloatingActionButton mSavedLibrary;
    public TextView title;
    private Video mVideo;
    public VideosAdapter mAdapter;
    public RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
//        ButterKnife.bind(this);
        title = (TextView) findViewById(R.id.title);
        mSave = (FloatingActionButton) findViewById(R.id.save_item);
        mSavedLibrary = (FloatingActionButton) findViewById(R.id.savedLibrary);
        mVideos = Parcels.unwrap(getIntent().getParcelableExtra("videos"));
        int positionOfVideo = getIntent().getIntExtra("position", 0);
        videoId = mVideos.get(positionOfVideo).getVideoId();
        mVideo = mVideos.get(positionOfVideo);
        title.setText(mVideos.get(positionOfVideo).getTitle());
        mSave.setOnClickListener(this);
        mSavedLibrary.setOnClickListener(this);

        //        Initializing youtube player view
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player);
        youTubePlayerView.initialize(Constants.YOUTUBE_KEY,this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new VideosAdapter(getApplicationContext(),mVideos);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager =
                new GridLayoutManager(VideoPlay.this, 2);
        mRecyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        //        add listeners to youtubeplayer instance

        player.setPlayerStateChangeListener(playerStateChange);
        player.setPlaybackEventListener(playbackEventListener);

//        Start buffering
        if(!wasRestored){
            player.cueVideo(videoId);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this,"Failed to Initialize!", Toast.LENGTH_LONG).show();
    }
    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChange = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };

    @Override
    public void onClick(View v) {

      if(v == mSave){
          FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
          String uid = user.getUid();

          DatabaseReference videoRef = FirebaseDatabase
                  .getInstance()
                  .getReference(Constants.FIREBASE_CHILD_VIDEOS)
                  .child(uid);
          DatabaseReference pushRef = videoRef.push();
          String pushId = pushRef.getKey();
          mVideo.setPushId(pushId);
          pushRef.setValue(mVideo);
          Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
      }
        if (v == mSavedLibrary) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
            Intent intent = new Intent(VideoPlay.this, SavedVideoActivity.class);
            intent.putExtra(Constants.KEY_TYPE,Constants.AnimType.ExplodeJava);

            startActivity(intent,options.toBundle());
        }
    }
}
