package io.github.froger.mahmoud.ui.factoryPattern;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by micro on 17/12/2017.
 */

public class paypall implements pay {


    @Override
    public void paynow(Context context) {

        Toast.makeText(context, "you are payed by paypall", Toast.LENGTH_LONG).show();
    }
}
