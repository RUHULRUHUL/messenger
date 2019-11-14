package com.example.finalapp;

public class Persionprofileinformationdata {

    String persionname,persionabount,persionphone;

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

    public String getPersionphone() {
        return persionphone;
    }

    public void setPersionphone(String persionphone) {
        this.persionphone = persionphone;
    }

    public Persionprofileinformationdata(String persionname, String persionabount, String persionphone) {
        this.persionname = persionname;
        this.persionabount = persionabount;
        this.persionphone = persionphone;
    }
}
