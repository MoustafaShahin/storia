package io.github.froger.mahmoud.ui.factoryPattern;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by micro on 17/12/2017.
 */

public class mastercard implements pay {

    @Override
    public void paynow(Context context) {
        Toast.makeText(context, "you had payed by master cared", Toast.LENGTH_LONG).show();
    }
}

