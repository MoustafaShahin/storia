package io.github.froger.mahmoud.ui.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import io.github.froger.instamaterial.R;
import io.github.froger.mahmoud.ui.chain.aprover;
import io.github.froger.mahmoud.ui.chain.emailchecker;
import io.github.froger.mahmoud.ui.chain.emptychecker;
import io.github.froger.mahmoud.ui.chain.passwordchecker;
import io.github.froger.mahmoud.ui.singletonPattern.UserDetails;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class LoginActivity extends AppCompatActivity {
    private EditText loginmail;
    private EditText pass;
    private Button log;
    private FirebaseAuth mAuth;
    private DatabaseReference mdatabase;
    String user, passs;
    UserDetails x;
    private LinearLayout forget;

    private void doExit() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
               LoginActivity.this);

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
        setContentView(R.layout.activity_login);
        log = (Button)findViewById(R.id.loginb);
        loginmail = (EditText) findViewById(R.id.mailtext);
        pass = (EditText) findViewById(R.id.spassword);
        mAuth= FirebaseAuth.getInstance();
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        forget= (LinearLayout) findViewById(R.id.forgetpassword);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loginmail.toString().equals(""))
                    Toast.makeText(getApplicationContext(),"please write youe E-mail",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(),"we will send an E-mail to you",Toast.LENGTH_LONG).show();
            }
        });


    }

    public void loginbuttonclicked(View view) {
        String Email = loginmail.getText().toString().trim();
        String Pass = pass.getText().toString().trim();



        boolean statepass=true;
        boolean stateemail=true;
        boolean stateempty=true;
        aprover check=new passwordchecker();
        aprover check2=new emptychecker();
        aprover check3=new emailchecker();
        statepass=  check.check(Email,Pass);
        stateemail=check3.check(Email,Pass);
        stateempty=check2.check(Email,Pass);
        if(!stateemail&&!stateempty&&!statepass)
        {
            Toast.makeText(this,"please check youe data",Toast.LENGTH_SHORT).show();

        }
        else if (!stateemail)
        {
            Toast.makeText(this,"please check email it is not valid",Toast.LENGTH_SHORT).show();
        }
        else if(!stateempty)
        {
            Toast.makeText(this,"please enter your data",Toast.LENGTH_SHORT).show();
        }
        else if (!statepass)
        {
            Toast.makeText(this,"please enter your pass more than 5 chracters",Toast.LENGTH_SHORT).show();
        }
        else {

        //----------------------------------- new
        if(Email.equals("zyad_mg@yahoo.com"))
            user = "zyadgalal";
        else if (Email.equals("mosetafa1@gmail.com"))
            user ="Mostafashahin";
        else if (Email.equals("mahmoud@gmail.com"))
            user = "mahmoudshhata";

        //-----------------------------------
        passs = pass.getText().toString();
        if (!TextUtils.isEmpty(Email) && !TextUtils.isEmpty(Pass)) {
            mAuth.signInWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        checkUserExist();
                        String url = "https://storia-ae8bf.firebaseio.com/user.json";
                        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
                        pd.setMessage("Loading...");
                        pd.show();

                        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                if (s.equals("null")) {
                                    Toast.makeText(LoginActivity.this, "user not found", Toast.LENGTH_LONG).show();
                                } else {
                                    try {
                                        JSONObject obj = new JSONObject(s);

                                        if (!obj.has(user)) {
                                            Toast.makeText(LoginActivity.this, "user not found", Toast.LENGTH_LONG).show();
                                        } else if (obj.getJSONObject(user).getString("password").equals(pass)) {
                                            x.username = user;
                                            x.password = passs;
                                            finish();
                                            startActivity(new Intent(LoginActivity.this, Users.class));
                                        } else {
                                            Toast.makeText(LoginActivity.this, "incorrect password", Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                pd.dismiss();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                System.out.println("" + volleyError);
                                pd.dismiss();
                            }
                        });

                        RequestQueue rQueue = Volley.newRequestQueue(LoginActivity.this);
                        rQueue.add(request);
                    }

                }


            });

        }
    }}


    public void checkUserExist() {
        final String user_id = mAuth.getCurrentUser().getUid();
        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user_id))
                {
                    Intent loginIntent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(loginIntent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void regbuttonclicked(View view) {
    Intent z = new Intent(LoginActivity.this,RegisterActivity.class);
    startActivity(z);
    }
}

