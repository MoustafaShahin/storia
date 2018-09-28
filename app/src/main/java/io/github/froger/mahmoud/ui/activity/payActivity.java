package io.github.froger.mahmoud.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import io.github.froger.instamaterial.R;
import io.github.froger.mahmoud.ui.factoryPattern.pay;
import io.github.froger.mahmoud.ui.factoryPattern.payfactory;

public class payActivity extends AppCompatActivity {

    RadioButton pay ;
    RadioButton visa;
    Button action;
    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(payActivity.this,settingactivity.class);
        finish();
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        pay = (RadioButton) findViewById(R.id.paypal);
        visa = (RadioButton) findViewById(R.id.visa);
        action = (Button) findViewById(R.id.btnpay);

        action.setOnClickListener(new View.OnClickListener() {
            payfactory Payfactory = new payfactory();
            @Override
            public void onClick(View view) {
                if(pay.isChecked()==true)
                {
                    io.github.froger.mahmoud.ui.factoryPattern.pay pay1 = Payfactory.getmethodpay("paypal");
                    pay1.paynow(getApplicationContext());
                }
                else if(visa.isChecked()==true)
                {
                    pay pay2 = Payfactory.getmethodpay("visa");
                    pay2.paynow(getApplicationContext());
                }
            }
        });

    }
}
