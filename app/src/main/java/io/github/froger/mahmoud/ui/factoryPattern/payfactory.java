package io.github.froger.mahmoud.ui.factoryPattern;

/**
 * Created by micro on 17/12/2017.
 */

public class payfactory {
    public pay getmethodpay(String paytype)
    {
        if(paytype.equalsIgnoreCase("visa")){return new visa();}
        else if (paytype.equalsIgnoreCase("paypal")){return new paypall();}
        else if(paytype.equalsIgnoreCase("mastercard")){return new mastercard();}
        return null;
    }
}
