package io.github.froger.mahmoud.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import io.github.froger.instamaterial.R;

public class splash extends AppCompatActivity {

    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        final Intent i = new Intent(this, MainActivity.class);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
        ImageView iv=(ImageView)findViewById(R.id.image_Welcome);
        iv.setImageResource(R.drawable.storia);
        iv.setTranslationY(-1000f);
        iv.animate().translationYBy(1000f).setDuration(2000);

        tv = (TextView) findViewById(R.id.tv);
        tv.setTranslationX(-20000f);
        tv.animate().translationXBy(20000f).setDuration(4000);

    }
}
