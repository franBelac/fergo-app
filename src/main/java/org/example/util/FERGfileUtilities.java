package org.example.util;

import org.example.FerGoApplication;
import org.example.domain.RaceEvent;
import org.example.domain.Rower;
import org.example.domain.Team;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class FERGfileUtilities {

    public static String createFERGfileFromRaceEvent(RaceEvent event, String filename) throws IOException {
        Path fergFile = Paths.get(FerGoApplication.excelSavePath.toString().concat("\\").concat(filename).concat(".ferg"));
        //System.out.println(fergFile.toString());
        List<String> lines = new LinkedList<>();
        StringBuilder sb = new StringBuilder();

        //adding metadata
        lines.add(event.getName());
        lines.add(String.valueOf(event.getNumberOfTeams()));
        lines.add(String.valueOf(event.getNumberOfErgs()));
        lines.add(String.valueOf(event.getNumberOfRowers()));
        lines.add(String.valueOf(event.getLength()));
        lines.add(String.valueOf(event.getSplits()));

        //after metadata we will have three (3) $$$ used as a delimiter in reconstruction

        lines.add("$$$");

        /* we will add teams as follows:
           we will start with three (3) '-' signs (---)
           then we will have each "word" in its own line first being team name, then shortname, then rowers
           for example:
           ---
           team1
           t1
           rower1_1
           rower1_2
           rower1_3
           rower1_4
           ---
           team2
           t2
           rower2_1
           rower2_2
           rower2_3
           rower2_4
         */

        for (var team : event.getTeamList()){
            lines.add("---");
            lines.add(team.getName());
            lines.add(team.getShortName());
            for (var rower : team.getRowers()){
                if (rower.getName() == ""){break;} //zbog nečeg se stvori uvijek 8 veslača a neponujeni su s null imenom??
                lines.add(rower.getName());
            }
        }
        Files.write(fergFile,lines);
        return fergFile.toString();
    }

    public static RaceEvent createRaceEventFromFergFile(String pathName) throws IOException {
        Path path = Paths.get(pathName);
        List<String> lines = Files.readAllLines(path);

        //race event metadata

        String eventName = lines.get(0);
        int numberOfTeams = Integer.parseInt(lines.get(1));
        int numberOfErgs = Integer.parseInt(lines.get(2));
        int numberOfRowers = Integer.parseInt(lines.get(3));
        int length = Integer.parseInt(lines.get(4));
        int splits = Integer.parseInt(lines.get(5));

        RaceEvent event = new RaceEvent(numberOfTeams, numberOfRowers, numberOfErgs, eventName, length, splits);

        int index = 8;

        for (int i = 0; i < numberOfTeams; i++) {
            List<Rower> rowers = new LinkedList<>();
            String teamName = lines.get(index++);
            String shortName = lines.get(index++);

            while(index < lines.size() && lines.get(index).equals("---")){
                rowers.add(new Rower(lines.get(index++)));
            }

            Team team = new Team(teamName, rowers.toArray(new Rower[0]));

            team.setShortName(shortName);

        }

        return event;

    }
}
