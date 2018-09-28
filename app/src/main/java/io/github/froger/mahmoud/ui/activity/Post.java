package io.github.froger.mahmoud.ui.activity;

import android.net.Uri;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

/**
 * Created by Moustafa on 12/15/2017.
 */

public class Post {
    private static final int GALLERY_REQUEST=2;
    private Uri uri =null;
    private StorageReference storageRefrence;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();


}
