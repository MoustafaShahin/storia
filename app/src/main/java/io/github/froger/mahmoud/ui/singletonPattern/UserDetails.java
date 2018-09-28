package io.github.froger.mahmoud.ui.singletonPattern;

/**
 * Created by Moustafa on 12/17/2017.
 */


public class UserDetails {
    private static UserDetails instance;
    public static String username = "";
    public static String password = "";
    public static String chatWith = "";
    private UserDetails(){}


    public static UserDetails create ()
    {
        if(instance == null)
            instance=new UserDetails();
        return instance;
    }
}

