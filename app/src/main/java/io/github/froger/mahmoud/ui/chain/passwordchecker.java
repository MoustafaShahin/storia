package io.github.froger.mahmoud.ui.chain;

/**
 * Created by micro on 17/12/2017.
 */

public class passwordchecker implements aprover {

    @Override
    public boolean check(String email, String password) {
        if(password.length()>5)
        {
            return true;
        }
      else {  return false;}
    }
}
