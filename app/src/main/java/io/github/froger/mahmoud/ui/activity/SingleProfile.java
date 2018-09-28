package io.github.froger.mahmoud.ui.activity;



import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

import io.github.froger.instamaterial.R;
import io.github.froger.mahmoud.ui.adapter.insta;

public class SingleProfile extends AppCompatActivity {
    private RecyclerView mpostList;
    private DatabaseReference mDatabeseREF;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mCurrent;
    private DatabaseReference databaseReferenceUsers;
    private String post_key = null;
    private String uid = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_profile);
        post_key = getIntent().getExtras().getString("PostId");

        mFirebaseAuth =FirebaseAuth.getInstance();
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



}
