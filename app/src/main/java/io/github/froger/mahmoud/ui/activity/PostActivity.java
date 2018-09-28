package io.github.froger.mahmoud.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import io.github.froger.instamaterial.R;

public class PostActivity extends AppCompatActivity {
    private static final int GALLERY_REQUEST=2;
    private Uri uri =null;
    private ImageButton imagebu ;
    private EditText  editName;
    private EditText  editdesc;
    private StorageReference storageRefrence;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceUsers;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrent;

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(PostActivity.this,MainActivity.class);
        finish();
        startActivity(i);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        editName = (EditText)findViewById(R.id.nametext);
        editdesc = (EditText)findViewById(R.id.desctext);
        storageRefrence = FirebaseStorage.getInstance().getReference();
        databaseReference = database.getInstance().getReference().child("Insta");
        mAuth = FirebaseAuth.getInstance();
        mCurrent =mAuth.getCurrentUser();
        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrent.getUid());

    }

    public void imagebuttonclicked(View view) {
        Intent galleryintent = new Intent();
        galleryintent.setType("image/*");
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(galleryintent,"select image"),GALLERY_REQUEST);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQUEST && resultCode==RESULT_OK)
        {
            uri = data.getData();
            imagebu = (ImageButton)findViewById(R.id.imageB);
            imagebu.setImageURI(uri);
        }
    }
    public void Post_onClick_clicked(View view) {
        final String titleval = editName.getText().toString().trim();
        final String titledesc = editdesc.getText().toString().trim();
        if(!TextUtils.isEmpty(titleval)&&!TextUtils.isEmpty(titledesc))
        {
            final ProgressDialog pd = new ProgressDialog(PostActivity.this);
            pd.setMessage("Loading...");
            pd.show();
            StorageReference filepath = storageRefrence.child("Post Image").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final Uri downloadurl = taskSnapshot.getDownloadUrl();
                    Toast.makeText(PostActivity.this,"upload completed",Toast.LENGTH_LONG).show();
                    final DatabaseReference newpost = databaseReference.push();
                    databaseReferenceUsers.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            newpost.child("title").setValue(titleval);
                            newpost.child("desc").setValue(titledesc);
                            newpost.child("image").setValue(downloadurl.toString());
                            newpost.child("uid").setValue(mCurrent.getUid());
                            newpost.child("username").setValue(dataSnapshot.child("name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        pd.dismiss();
                                        Intent x  = new Intent(PostActivity.this,MainActivity.class);
                                        startActivity(x);
                                    }
                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }) ;
        }
    }
}
