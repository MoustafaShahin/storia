package io.github.froger.mahmoud.ui.adapter;

import android.media.Image;

/**
 * Created by ohood on 09/12/2017.
 */

public class insta {
    private  String title;
    private String desc;
    private String image;
    private String username;
    private String Uid;
    private String imageprofile;

    public insta(){

    }
    public insta(String title, String desc, String image, String Username,String uid,String Imageprofile){
        this.title = title;
        this.desc= desc;
        this.image = image;
        this.username = Username;
        this.Uid = uid;
        this.imageprofile= Imageprofile;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getImage() {
        return image;
    }

    public String getUid(){return Uid;}

    public String getImageprofile(){return imageprofile;}

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUid(String id) {this.Uid = id;}
    public void setImageprofile(String image){this.imageprofile=imageprofile;}
}
