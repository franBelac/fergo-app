package org.example.domain;


import java.util.*;

public class Team implements Comparable<Team>{
    String name;
    List<Rower> rowers;
    Set<Rower> rowerSet;
    String shortName;
    double totalTime = 0.; //in seconds

    public Team (String name, Rower... rowers) {
        this.name = name;

        this.rowers = new LinkedList<>();

        this.rowers.addAll(Arrays.asList(rowers));
        rowerSet = new HashSet<Rower>(this.rowers);

    }

    public double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }

    public Team (String name) {
        this.name = name;

        this.rowers = new LinkedList<>();
    }

    public List<Rower> getRowers() {
        return rowers;
    }

    public void setRower (Rower rower) {
        rowers.add(rower);
        totalTime += rower.getTime();
    }

    public String getName() {
        return name;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    @Override
    public int compareTo(Team o) {
        return (int) Math.round(this.totalTime - o.totalTime);
    }
    public String displayTime(){
        int minutes = (int) (this.totalTime/60.);
        double seconds = this.totalTime - minutes * 60.;
        StringBuilder sb = new StringBuilder();
        sb.append(minutes);
        sb.append(":"); //14:21.1 for example
        sb.append(seconds);
        return sb.toString();
    }

    @Override
    public String toString() {
        return name + " " +
                displayTime();
    }
}
