package io.github.froger.mahmoud.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import io.github.froger.instamaterial.R;

/**
 * Created by micro on 17/12/2017.
 */

public class settingactivity extends AppCompatActivity{
    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(settingactivity.this,MainActivity.class);
        finish();
        startActivity(i);
    }
    TextView pay;
    TextView help;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        pay=(TextView) findViewById(R.id.textopenpay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(settingactivity.this,payActivity.class);
                startActivity(i);
            }
        });
        help =(TextView) findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), help.class);
                startActivity(i);
            }
        });
    }
}
