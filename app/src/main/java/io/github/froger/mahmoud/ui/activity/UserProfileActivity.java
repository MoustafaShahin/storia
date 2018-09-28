package io.github.froger.mahmoud.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import io.github.froger.instamaterial.R;
import io.github.froger.mahmoud.ui.adapter.UserProfileAdapter;
import io.github.froger.mahmoud.ui.adapter.insta;
import io.github.froger.mahmoud.ui.view.RevealBackgroundView;

/**
 * Created by Miroslaw Stanek on 14.01.15.
 */
public class UserProfileActivity extends BaseDrawerActivity  {
    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";
    private RecyclerView mpostList;
    private DatabaseReference mDatabeseREF;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mCurrent;
    private DatabaseReference databaseReferenceUsers;
    private String post_key = null;
    private String uid = null;
    private static final int USER_OPTIONS_ANIMATION_DELAY = 300;
    private static final Interpolator INTERPOLATOR = new DecelerateInterpolator();

    @BindView(R.id.vRevealBackground)
    RevealBackgroundView vRevealBackground;


    @BindView(R.id.ivUserProfilePhoto)
    ImageView ivUserProfilePhoto;
    @BindView(R.id.vUserDetails)
    View vUserDetails;

    @BindView(R.id.vUserStats)
    View vUserStats;


    private int avatarSize;
    private String profilePhoto;
    private UserProfileAdapter userPhotosAdapter;

    public static void startUserProfileFromLocation(int[] startingLocation, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, UserProfileActivity.class);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        post_key = getIntent().getExtras().getString("PostId");

        mFirebaseAuth = FirebaseAuth.getInstance();
        mCurrent =mFirebaseAuth.getCurrentUser();
        mpostList = (RecyclerView)findViewById(R.id.profile);
        mpostList.setHasFixedSize(true);
        mpostList.setLayoutManager(new LinearLayoutManager(this));
        mDatabeseREF = FirebaseDatabase.getInstance().getReference().child("Insta");
        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrent.getUid());
        mDatabeseREF.child(post_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                uid = (String)dataSnapshot.child("uid").getValue();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        this.avatarSize = getResources().getDimensionPixelSize(R.dimen.user_profile_avatar_size);




      //  setupTabs();
       // setupUserProfileGrid();
       // setupRevealBackground(savedInstanceState);
    }

 @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<insta,SingleProfile.InstaViewHolder> FBRA =new FirebaseRecyclerAdapter<insta, SingleProfile.InstaViewHolder>(
                insta.class,
                R.layout.insta_row,
                SingleProfile.InstaViewHolder.class,
                mDatabeseREF
        ) {
            @Override
            protected void populateViewHolder(SingleProfile.InstaViewHolder viewHolder, insta model, int position) {

                   if(model.getUid().equals(uid)) {

                    viewHolder.setTitle(model.getTitle());
                    viewHolder.setDesc(model.getDesc());
                    viewHolder.setImage(getApplicationContext(), model.getImage());
                    viewHolder.setUsername(model.getUsername());
                  /*  viewHolder.setImageProfile(model.getImageprofile());*/
                }
            }
        };
        mpostList.setAdapter(FBRA);
    }
    public static class InstaViewHolder extends RecyclerView.ViewHolder{
        public InstaViewHolder(View item) {
            super(item);
            View mView = item;
        }
        public void setTitle(String title){
            TextView post_title = (TextView)itemView.findViewById(R.id.title);
            post_title.setText(title);

        }
        public void setUsername(String username){
            TextView Username = (TextView)itemView.findViewById(R.id.Username);
            Username.setText(username);
        }
        public void setDesc(String desc){
            TextView post_desc = (TextView)itemView.findViewById(R.id.descrip);
            post_desc.setText(desc);
        }
        public void setImage(Context ctx, String image)
        {
            ImageView post_image = (ImageView) itemView.findViewById(R.id.post_image2);
            Picasso.with(ctx).load(image).into(post_image);

        }
       /* public void setImageProfile(Context ctx, String image)
        {
            ImageView post_im = (ImageView) itemView.findViewById(R.id.imagepro);
            Picasso.with(ctx).load(image).into(post_im);

        }*/
    }
/*
 @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<insta,SingleProfile.InstaViewHolder> FBRA =new FirebaseRecyclerAdapter<insta, SingleProfile.InstaViewHolder>(
                insta.class,
                R.layout.insta_row,
                SingleProfile.InstaViewHolder.class,
                mDatabeseREF
        ) {
            @Override
            protected void populateViewHolder(SingleProfile.InstaViewHolder viewHolder, insta model, int position) {

                   if(model.getUid().equals(uid)) {

                    viewHolder.setTitle(model.getTitle());
                    viewHolder.setDesc(model.getDesc());
                    viewHolder.setImage(getApplicationContext(), model.getImage());
                    viewHolder.setUsername(model.getUsername());
                }
            }
        };
        mpostList.setAdapter(FBRA);
    }
    public static class InstaViewHolder extends RecyclerView.ViewHolder{
        public InstaViewHolder(View item) {
            super(item);
            View mView = item;
        }
        public void setTitle(String title){
            TextView post_title = (TextView)itemView.findViewById(R.id.title);
            post_title.setText(title);

        }
        public void setUsername(String username){
            TextView Username = (TextView)itemView.findViewById(R.id.Username);
            Username.setText(username);
        }
        public void setDesc(String desc){
            TextView post_desc = (TextView)itemView.findViewById(R.id.descrip);
            post_desc.setText(desc);
        }
        public void setImage(Context ctx, String image)
        {
            ImageView post_image = (ImageView) itemView.findViewById(R.id.post_image2);
            Picasso.with(ctx).load(image).into(post_image);

        }
    }




    private void setupTabs() {
        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_grid_on_white));
        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_list_white));
        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_place_white));
        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_label_white));
    }

    private void setupUserProfileGrid() {
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rvUserProfile.setLayoutManager(layoutManager);
        rvUserProfile.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                userPhotosAdapter.setLockedAnimations(true);
            }
        });
    }*/

   /* private void setupRevealBackground(Bundle savedInstanceState) {
        vRevealBackground.setOnStateChangeListener(this);
        if (savedInstanceState == null) {
            final int[] startingLocation = getIntent().getIntArrayExtra(ARG_REVEAL_START_LOCATION);
            vRevealBackground.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    vRevealBackground.getViewTreeObserver().removeOnPreDrawListener(this);
                    vRevealBackground.startFromLocation(startingLocation);
                    return true;
                }
            });
        } else {
            vRevealBackground.setToFinishedFrame();
            userPhotosAdapter.setLockedAnimations(true);
        }
    }*/
/*
    @Override
    public void onStateChange(int state) {
        if (RevealBackgroundView.STATE_FINISHED == state) {
            rvUserProfile.setVisibility(View.VISIBLE);
            tlUserProfileTabs.setVisibility(View.VISIBLE);
            vUserProfileRoot.setVisibility(View.VISIBLE);
            userPhotosAdapter = new UserProfileAdapter(this);
            rvUserProfile.setAdapter(userPhotosAdapter);
            animateUserProfileOptions();
            animateUserProfileHeader();
        } else {
            tlUserProfileTabs.setVisibility(View.INVISIBLE);
            rvUserProfile.setVisibility(View.INVISIBLE);
            vUserProfileRoot.setVisibility(View.INVISIBLE);
        }
    }

    private void animateUserProfileOptions() {
        tlUserProfileTabs.setTranslationY(-tlUserProfileTabs.getHeight());
        tlUserProfileTabs.animate().translationY(0).setDuration(300).setStartDelay(USER_OPTIONS_ANIMATION_DELAY).setInterpolator(INTERPOLATOR);
    }

    private void animateUserProfileHeader() {
           vUserProfileRoot.setTranslationY(-vUserProfileRoot.getHeight());
           ivUserProfilePhoto.setTranslationY(-ivUserProfilePhoto.getHeight());
           vUserDetails.setTranslationY(-vUserDetails.getHeight());
           vUserStats.setAlpha(0);

           vUserProfileRoot.animate().translationY(0).setDuration(300).setInterpolator(INTERPOLATOR);
           ivUserProfilePhoto.animate().translationY(0).setDuration(300).setStartDelay(100).setInterpolator(INTERPOLATOR);
           vUserDetails.animate().translationY(0).setDuration(300).setStartDelay(200).setInterpolator(INTERPOLATOR);
           vUserStats.animate().alpha(1).setDuration(200).setStartDelay(400).setInterpolator(INTERPOLATOR).start();
    }*/
}
