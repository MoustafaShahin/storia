package io.github.froger.mahmoud.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import io.github.froger.instamaterial.R;

public class SetupActivity extends AppCompatActivity {
    private EditText Usernametext;
    private ImageButton setupImage;

    private static final int GALLERY_REQUEST=1;
    private StorageReference storageRefrence;
    private FirebaseDatabase database;
    private DatabaseReference databaseReferenceUsers;
    private FirebaseAuth mAuth;
    private Uri mImageUri = null;


    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(SetupActivity.this,RegisterActivity.class);
        finish();
        startActivity(i);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        Usernametext = (EditText)findViewById(R.id.setuptext);
        setupImage = (ImageButton)findViewById(R.id.setupImageButton);
        storageRefrence = FirebaseStorage.getInstance().getReference().child("Ptofile_Image");
        mAuth = FirebaseAuth.getInstance();
        databaseReferenceUsers = database.getInstance().getReference().child("Users");
    }

    public void setubonClick(View view) {
        final  String UserName = Usernametext.getText().toString().trim();
        final String User_id = mAuth.getCurrentUser().getUid().toString();
        if(!TextUtils.isEmpty(UserName)&& mImageUri != null){
            final ProgressDialog pd = new ProgressDialog(SetupActivity.this);
            pd.setMessage("Loading...");
            pd.show();
            StorageReference filepath = storageRefrence.child(mImageUri.getLastPathSegment());
            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String downloadUri = taskSnapshot.getDownloadUrl().toString();
                    databaseReferenceUsers.child(User_id).child("name").setValue(UserName);
                    databaseReferenceUsers.child(User_id).child("Image").setValue(downloadUri);
                    pd.dismiss();
                    Intent xx = new Intent(SetupActivity.this,LoginActivity.class);
                    startActivity(xx);
                }
            });

        }

    }

    public void profileImageOnclick(View view) {
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
        /*    Uri ImageUri = data.getData();
            CropImage.activity(ImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);*/
            mImageUri = data.getData();
            setupImage.setImageURI(mImageUri);
        }
        /*if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(requestCode == RESULT_OK)
            {

            }
            else if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
            {
                Exception error = result.getError();

            }
        }*/
    }

}
