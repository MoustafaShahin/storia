package io.github.froger.mahmoud.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import io.github.froger.instamaterial.R;
import io.github.froger.mahmoud.ui.adapter.insta;


public class MainActivity extends AppCompatActivity{
    //////////////////////////
    private RecyclerView mpostList;
    private DatabaseReference mDatabeseREF;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    ImageView show;
    ImageView show2;
    ImageView show3;
    ImageView show4;
    LinearLayout linear;

    boolean a = true;
    ////////////////////////////
//    public static final String ACTION_SHOW_LOADING_ITEM = "action_show_loading_item";

    //private static final int ANIM_DURATION_TOOLBAR = 300;
    //private static final int ANIM_DURATION_FAB = 400;

    /*@BindView(R.id.rvFeed)
    RecyclerView rvFeed;
    @BindView(R.id.btnCreate)
    FloatingActionButton fabCreate;
    @BindView(R.id.content)
    CoordinatorLayout clContent;
*/
    //private FeedAdapter feedAdapter;

    //private boolean pendingIntroAnimation;


    FloatingActionButton fabCreate;
    private void doExit() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                MainActivity.this);

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(500);


        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                System.exit(0);
            }
        });

        alertDialog.setNegativeButton("No", null);

        alertDialog.setMessage("Do you want to exit?");
        alertDialog.setTitle("Storia");
        alertDialog.show();
    }
    @Override
    public void onBackPressed()
    {
        doExit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        show4= (ImageView) findViewById(R.id.profilepicture4);
        show3= (ImageView) findViewById(R.id.profilepicture3);
        show2= (ImageView) findViewById(R.id.profilepicture2);
        show= (ImageView) findViewById(R.id.profilepicture);
        linear=(LinearLayout) findViewById(R.id.linear1);
        show4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(a==true) {
                    Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.slide_up);
                    linear.setVisibility(View.VISIBLE);
                    linear.startAnimation(slide_down);
                    a=false;
                    /*RotateAnimation rotate = new RotateAnimation(0, 45,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                            0.5f);

                    rotate.setDuration(400);
                    rotate.setInterpolator(new LinearInterpolator());
                    show4.setAnimation(rotate);*/

                    show4.setRotation(45);
                }
                else
                {
                    Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.slide_down);
                    linear.setVisibility(View.GONE);
                    linear.startAnimation(slide_up);
                    a=true;

                    show4.setRotation(90);

                }
            }
        });
        show3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  int[] startingLocation = new int[2];
                fabCreate.getLocationOnScreen(startingLocation);
                startingLocation[0] += fabCreate.getWidth() / 2;
                TakePhotoActivity.startCameraFromLocation(startingLocation, this);
                overridePendingTransition(0, 0);*/
                Toast.makeText(MainActivity.this, "this fature is not available in your country", Toast.LENGTH_SHORT).show();
            }
        });
        show2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent xx = new Intent(MainActivity.this , PostActivity.class);
                finish();
                startActivity(xx);
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent xxx = new Intent(MainActivity.this , mapActivity.class);
                finish();
                startActivity(xxx);
            }
        });

        //      setupFeed();
//////////////////////////////////////////
        mpostList = (RecyclerView) findViewById(R.id.rvFeed);
        mpostList.setHasFixedSize(true);
        mpostList.setLayoutManager(new LinearLayoutManager(this));
        mDatabeseREF = FirebaseDatabase.getInstance().getReference().child("Insta");
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Intent loginintent = new Intent(MainActivity.this, LoginActivity.class);
                    loginintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    finish();
                    startActivity(loginintent);
                }
            }
        };
    }
 //////////////////////////////////////////
/*
        if (savedInstanceState == null) {
            pendingIntroAnimation = true;
        } else {
            feedAdapter.updateItems(false);
        }
    }*/
    //////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
        FirebaseRecyclerAdapter<insta,InstaViewHolder> FBRA =new FirebaseRecyclerAdapter<insta, InstaViewHolder>(
                insta.class,
                R.layout.insta_row,
                InstaViewHolder.class,
                mDatabeseREF
        ) {
            @Override
            protected void populateViewHolder(InstaViewHolder viewHolder, insta model, int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(getApplicationContext(),model.getImage());
                viewHolder.setUsername(model.getUsername());
                final String post_key = getRef(position).getKey().toString();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent singleprofileactivity = new Intent(MainActivity.this,UserProfileActivity.class);
                        singleprofileactivity.putExtra("PostId",post_key);
                        startActivity(singleprofileactivity);
                    }
                });
            }
        };
        mpostList.setAdapter(FBRA);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public static class InstaViewHolder extends RecyclerView.ViewHolder {
        public InstaViewHolder(View item) {
            super(item);
            View mView = item;
        }

        public void setTitle(String title) {
            TextView post_title = (TextView) itemView.findViewById(R.id.title);
            post_title.setText(title);

        }

        public void setUsername(String username) {
            TextView Username = (TextView) itemView.findViewById(R.id.Username);
            Username.setText(username);
        }

        public void setDesc(String desc) {
            TextView post_desc = (TextView) itemView.findViewById(R.id.descrip);
            post_desc.setText(desc);
        }

        public void setImage(Context ctx, String image) {
            ImageView post_image = (ImageView) itemView.findViewById(R.id.post_image2);
            Picasso.with(ctx).load(image).into(post_image);

        }
    }
        /*
    private void setupFeed() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        rvFeed.setLayoutManager(linearLayoutManager);

        feedAdapter = new FeedAdapter(this);
        feedAdapter.setOnFeedItemClickListener(this);
        rvFeed.setAdapter(feedAdapter);
        rvFeed.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                FeedContextMenuManager.getInstance().onScrolled(recyclerView, dx, dy);
            }
        });
        rvFeed.setItemAnimator(new FeedItemAnimator());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (ACTION_SHOW_LOADING_ITEM.equals(intent.getAction())) {
            showFeedLoadingItemDelayed();
        }
    }

    private void showFeedLoadingItemDelayed() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rvFeed.smoothScrollToPosition(0);
                feedAdapter.showLoadingView();
            }
        }, 500);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (pendingIntroAnimation) {
            pendingIntroAnimation = false;
            startIntroAnimation();
        }
        return true;
    }

    private void startIntroAnimation() {
        fabCreate.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.btn_fab_size));

        int actionbarSize = Utils.dpToPx(56);
        getToolbar().setTranslationY(-actionbarSize);
        getIvLogo().setTranslationY(-actionbarSize);
        getInboxMenuItem().getActionView().setTranslationY(-actionbarSize);

        getToolbar().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);
        getIvLogo().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(400);
        getInboxMenuItem().getActionView().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        startContentAnimation();
                    }
                })
                .start();
    }

    private void startContentAnimation() {
        fabCreate.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setStartDelay(300)
                .setDuration(ANIM_DURATION_FAB)
                .start();
        feedAdapter.updateItems(true);
    }

    @Override
    public void onCommentsClick(View v, int position) {
        final Intent intent = new Intent(this, CommentsActivity.class);
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        intent.putExtra(CommentsActivity.ARG_DRAWING_START_LOCATION, startingLocation[1]);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onMoreClick(View v, int itemPosition) {
        FeedContextMenuManager.getInstance().toggleContextMenuFromView(v, itemPosition, this);
    }

    @Override
    public void onProfileClick(View v) {
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        startingLocation[0] += v.getWidth() / 2;
        UserProfileActivity.startUserProfileFromLocation(startingLocation, this);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onReportClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onSharePhotoClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onCopyShareUrlClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onCancelClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @OnClick(R.id.btnCreate)
    public void onTakePhotoClick() {
        int[] startingLocation = new int[2];
        fabCreate.getLocationOnScreen(startingLocation);
        startingLocation[0] += fabCreate.getWidth() / 2;
        TakePhotoActivity.startCameraFromLocation(startingLocation, this);
        overridePendingTransition(0, 0);
    }

    public void showLikedSnackbar() {
        Snackbar.make(clContent, "Liked!", Snackbar.LENGTH_SHORT).show();
    }
*/
    //////////////////////////////////////////////////////////
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.action_inbox)
        {
            Intent i = new Intent(MainActivity.this,Users.class);
            finish();
            startActivity(i);
        }
        if(id==R.id.setting)
        {
            Intent I = new Intent(MainActivity.this,settingactivity.class);
            finish();
            startActivity(I);
        }
        if(id == R.id.signout)
        {
           mFirebaseAuth.signOut();
        }
        return super.onOptionsItemSelected(item);
    }
    ////////////////////////////////////////////////////////////
}