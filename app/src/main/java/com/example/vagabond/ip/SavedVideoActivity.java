package com.example.vagabond.ip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.view.Window;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SavedVideoActivity extends AppCompatActivity {

    private DatabaseReference mVideoReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;
    Constants.AnimType type;

    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        ButterKnife.bind(this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        type = (Constants.AnimType) getIntent().getSerializableExtra(Constants.KEY_TYPE);
        setUpAnimation();
        mVideoReference = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_VIDEOS)
                .child(uid);
        setUpFirebaseAdapter();

    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Video, FirebaseVideoViewHolder>
                (Video.class, R.layout.video_card, FirebaseVideoViewHolder.class,
                        mVideoReference) {

            @Override
            protected void populateViewHolder(FirebaseVideoViewHolder viewHolder,
                                              Video model, int position) {
                viewHolder.bindVideo(model);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finishAfterTransition();
        return true;
    }
    private void setUpAnimation(){
        Explode enterTransition = new Explode();
        enterTransition.setDuration(1000);
        getWindow().setEnterTransition(enterTransition);
    }
}
