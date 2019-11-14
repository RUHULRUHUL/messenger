package com.example.finalapp;

public class Userloggingclass {
    private String userphonenumber;
    private String acountpassword;




    public Userloggingclass(String userphonenumber, String acountpassword) {
        this.userphonenumber = userphonenumber;
        this.acountpassword = acountpassword;
    }

    public String getUserphonenumber() {
        return userphonenumber;
    }

    public void setUserphonenumber(String userphonenumber) {
        this.userphonenumber = userphonenumber;
    }

    public String getAcountpassword() {
        return acountpassword;
    }

    public void setAcountpassword(String acountpassword) {
        this.acountpassword = acountpassword;
    }




}
