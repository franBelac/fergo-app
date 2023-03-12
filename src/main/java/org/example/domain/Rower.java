package org.example.domain;

public class Rower {
    String name;
    double time;
    String concept2TimeFormat;


    public Rower (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
}
