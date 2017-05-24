package com.alina.medical;

/**
 * Created by ulan on 5/22/17.
 */

public class Appointment {
    String name,phone,time;

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
