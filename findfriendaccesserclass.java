package com.example.finalapp;

public class findfriendaccesserclass  {
    public String imageurl,persionname,persionabount;

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getPersionname() {
        return persionname;
    }

    public void setPersionname(String persionname) {
        this.persionname = persionname;
    }

    public String getPersionabount() {
        return persionabount;
    }

    public void setPersionabount(String persionabount) {
        this.persionabount = persionabount;
    }

    public findfriendaccesserclass(String imageurl, String persionname, String persionabount) {
        this.imageurl = imageurl;
        this.persionname = persionname;
        this.persionabount = persionabount;
    }
}
