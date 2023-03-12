package org.example.domain;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class RaceEvent {

    private final int numberOfTeams;
    private final int numberOfRowers;
    private final int numberOfErgs;
    private final String name;
    private final int length;
    private final int splits;
    private final List<Team> teamList;



    public RaceEvent (int numberOfTeams, int numberOfRowers, int numberOfErgs,String name, int length, int splits) {
        this.name = name;
        this.splits = splits;
        this.length = length;
        this.numberOfTeams = numberOfTeams;
        this.numberOfRowers = numberOfRowers;
        this.numberOfErgs = numberOfErgs;

        teamList = new LinkedList<>();
    }

    public List<Team> getTeamList() {
        return teamList;
    }

    // GETTERS
    public int getNumberOfErgs() {
        return numberOfErgs;
    }

    public int getNumberOfRowers() {
        return numberOfRowers;
    }

    public int getNumberOfTeams() {
        return numberOfTeams;
    }

    public String getName() {
        return name;
    }

    public void addTeam (Team team) {
        teamList.add(team);
    }

    public void removeTeam () {
        teamList.remove(teamList.size() - 1);
    }

    public int getLength() {
        return length;
    }

    public int getSplits() {
        return splits;
    }


    /* displays results like this:
        teamName   formattedTime
     */
    public String displayResults(){
        Collections.sort(teamList);
        StringBuilder sb = new StringBuilder();
        for (var team : teamList){
            sb.append(team.getName()).append("   ");
            sb.append(team.displayTime()). append("\n");
        }
        return sb.toString();
    }


}
