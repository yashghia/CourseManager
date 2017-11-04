package com.example.yash.hw6;
import io.realm.RealmObject;

/**
 * Created by yash_ on 11/2/2017.
 */

public class Course extends RealmObject {

    String title,instName,day,hours,minutes,creditHours,sem;
    private byte[] instPic;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(String creditHours) {
        this.creditHours = creditHours;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public byte[] getInstPic() {
        return instPic;
    }

    public void setInstPic(byte[] instPic) {
        this.instPic = instPic;
    }
}