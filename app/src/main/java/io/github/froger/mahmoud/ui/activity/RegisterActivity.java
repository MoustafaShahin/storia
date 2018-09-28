package io.github.froger.mahmoud.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import io.github.froger.instamaterial.R;


public class RegisterActivity extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText pass;
    private EditText confirm;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    String user, passs;



    @Override
    public void onBackPressed()
    {
       Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
        finish();
       startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Firebase.setAndroidContext(this);


        name = (EditText)findViewById(R.id.namefield);
        email = (EditText)findViewById(R.id.emailfield);
        pass = (EditText)findViewById(R.id.passfield);
        confirm = (EditText) findViewById(R.id.edit_password_signUp_confirm);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    public void regbutclicked(View view) {
        final String name2 = name.getText().toString().trim();
        final String Email2 = email.getText().toString().trim();
        final String pass2 = pass.getText().toString().trim();
        if (pass.toString().equals(confirm.toString())) {
            if (!TextUtils.isEmpty(name2) && !TextUtils.isEmpty(Email2) && !TextUtils.isEmpty(pass2)) {
                mAuth.createUserWithEmailAndPassword(Email2, pass2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final ProgressDialog pd = new ProgressDialog(RegisterActivity.this);
                            pd.setMessage("Loading...");
                            pd.show();
                            String user_id = mAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = mDatabase.child(user_id);
                            current_user_db.child("Name").setValue(name2);
                            // current_user_db.child("E-mail").setValue(Email2);
                            //current_user_db.child("Password").setValue(pass2);
                            current_user_db.child("Image").setValue("default");

                            user = name.getText().toString();
                            passs = pass.getText().toString();
                            String url = "https://storia-ae8bf.firebaseio.com/user.json";

                            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String s) {
                                    Firebase reference = new Firebase("https://storia-ae8bf.firebaseio.com/user");

                                    if (s.equals("null")) {
                                        reference.child(user).child("password").setValue(passs);
                                        Toast.makeText(RegisterActivity.this, "registration successful", Toast.LENGTH_LONG).show();
                                        pd.dismiss();
                                    } else {
                                        try {
                                            JSONObject obj = new JSONObject(s);

                                            if (!obj.has(user)) {
                                                reference.child(user).child("password").setValue(passs);
                                                Toast.makeText(RegisterActivity.this, "registration successful", Toast.LENGTH_LONG).show();
                                                pd.dismiss();
                                            } else {
                                                Toast.makeText(RegisterActivity.this, "username already exists", Toast.LENGTH_LONG).show();
                                                pd.dismiss();
                                            }

                                        } catch (JSONException e) {
                                            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                                            e.printStackTrace();
                                            pd.dismiss();
                                        }
                                    }


                                }

                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    System.out.println("hiii" + volleyError);

                                }
                            });

                            RequestQueue rQueue = Volley.newRequestQueue(RegisterActivity.this);
                            rQueue.add(request);

                            Intent mainIntent = new Intent(RegisterActivity.this, SetupActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(mainIntent);

                        }

                    }
                });

            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"check password again",Toast.LENGTH_LONG).show();
        }
    }

    }
