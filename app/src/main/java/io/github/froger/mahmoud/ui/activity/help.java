package io.github.froger.mahmoud.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import io.github.froger.instamaterial.R;

public class help extends AppCompatActivity {

    public void onBackPressed()
    {
        Intent i = new Intent(getApplicationContext(),settingactivity.class);
        finish();
        startActivity(i);
    }
    ImageView image;
    Button btn;
    int cnt=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        image = (ImageView) findViewById(R.id.imghelp);
        btn = (Button) findViewById(R.id.btnhelp);
        image.setImageResource(R.drawable.one);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cnt==2) {
                    image.setImageResource(R.drawable.two);
                    cnt++;
                }
                else if (cnt == 3)
                {
                    image.setImageResource(R.drawable.three);
                    cnt++;
                }
                else if (cnt == 4)
                {
                    image.setImageResource(R.drawable.four);
                    cnt++;
                }
                else if (cnt == 5)
                {
                    image.setImageResource(R.drawable.five);
                    cnt++;
                }
                else if (cnt == 6)
                {
                    image.setImageResource(R.drawable.eight);
                    cnt++;
                }
                else if (cnt == 7)
                {
                    image.setImageResource(R.drawable.nine);
                    cnt++;
                }
                else if (cnt == 8)
                {
                    image.setImageResource(R.drawable.ten);
                    cnt++;
                }
                else
                {
                    Intent i = new Intent (getApplicationContext(),settingactivity.class);
                    finish();
                    startActivity(i);
                }

            }
        });
    }
}
