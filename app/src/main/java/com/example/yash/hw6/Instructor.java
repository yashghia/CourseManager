package com.example.yash.hw6;

import io.realm.RealmObject;

/**
 * Created by yash_ on 11/2/2017.
 */

public class Instructor extends RealmObject {
    String fname,email,website;
    byte[] pic;

    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
