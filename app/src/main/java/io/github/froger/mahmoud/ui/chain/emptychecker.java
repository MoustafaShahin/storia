package io.github.froger.mahmoud.ui.chain;

/**
 * Created by micro on 17/12/2017.
 */

public class emptychecker implements aprover {

    @Override
    public boolean check(String email, String password) {
        if(email.equals(null)&&password.equals(null))
        {
            return false;
        }else
        {
            return true;
        }
    }
}
