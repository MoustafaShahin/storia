package io.github.froger.mahmoud.ui.chain;

/**
 * Created by micro on 17/12/2017.
 */

public class emailchecker implements aprover {

    @Override
    public boolean check(String email, String password) {
        if(email.contains("@"))
        {
         return true;
        }
        return false;
    }
}
