package org.example.util;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;
import org.example.domain.RaceEvent;
import org.example.domain.Rower;
import org.example.domain.Team;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class FileUtilities {


    public static String createExcelFileForRaceEvent(int numberOfTeams, int numberOfRowers, int numberOfErgs, String eventName, int length, int splits, String folderName) throws IOException {
        //workbook and sheet creation;
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Race Data");
        //border cell style creation -> we want them red and glowy
        CellStyle styleForBorderCells = workbook.createCellStyle();
        styleForBorderCells.setFillBackgroundColor(IndexedColors.RED.getIndex());
        styleForBorderCells.setFillForegroundColor(IndexedColors.RED.getIndex());
        styleForBorderCells.setFillPattern(FillPatternType.forInt((short)2));
        styleForBorderCells.setFillPattern(FillPatternType.BIG_SPOTS);


        int j = 0;
        //info rows creation
        XSSFRow infoLabelsRow = sheet.createRow(j++); // creating first row with info labels
        int ilRowCol = 0;
        infoLabelsRow.createCell(ilRowCol++).setCellValue("Race event name");
        infoLabelsRow.createCell(ilRowCol++).setCellValue("Race distance");
        infoLabelsRow.createCell(ilRowCol++).setCellValue("Number of splits");
        infoLabelsRow.createCell(ilRowCol++).setCellValue("Number of ergs");
        infoLabelsRow.createCell(ilRowCol++).setCellValue("Number of Teams");
        infoLabelsRow.createCell(ilRowCol++).setCellValue("Number of Rowers");

        XSSFRow infoDataRow = sheet.createRow(j++); // creating row with data labels
        int idRowCol = 0;
        infoDataRow.createCell(idRowCol++).setCellValue(eventName);
        infoDataRow.createCell(idRowCol++).setCellValue(length);
        infoDataRow.createCell(idRowCol++).setCellValue(splits);
        infoDataRow.createCell(idRowCol++).setCellValue(numberOfErgs);
        infoDataRow.createCell(idRowCol++).setCellValue(numberOfTeams);
        infoDataRow.createCell(idRowCol++).setCellValue(numberOfRowers);

        //creating first border row manually;
        XSSFRow row = sheet.createRow(j++);
        row.createCell(0).setCellValue("Team Name");
        row.getCell(0).setCellStyle(styleForBorderCells);
        row.createCell(1).setCellValue("Team Abbreviation");
        row.getCell(1).setCellStyle(styleForBorderCells);


        int i = 2;
        for (i = 2; i < numberOfRowers+2; i++) {
            row.createCell(i).setCellValue("Rower_".concat(String.valueOf(i-1)));
            row.getCell(i).setCellStyle(styleForBorderCells);
        }
        row.createCell(i).setCellValue("######");
        row.getCell(i).setCellStyle(styleForBorderCells);

        //creating border next to data cells;
        for (; j < numberOfTeams+3; j++) {
            sheet.createRow(j);
            sheet.getRow(j).createCell(numberOfRowers+2).setCellValue("#######");
            sheet.getRow(j).getCell(numberOfRowers+2).setCellStyle(styleForBorderCells);
        }
        XSSFRow lastDataRow = sheet.createRow(j++);
        for (int k = 0; k < numberOfRowers+3; k++) {
            lastDataRow.createCell(k).setCellValue("########");
            lastDataRow.getCell(k).setCellStyle(styleForBorderCells);
        }

        //comment creation
        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        XSSFClientAnchor anchor = drawing.createAnchor(0,0,0,0,numberOfRowers+3,numberOfTeams+1,numberOfRowers+7,numberOfTeams+5);
        XSSFComment cmt = drawing.createCellComment(anchor);
        cmt.setVisible(true);
        cmt.setString("You may only edit inside the red border. Please add team names, team abbreviations and rower names.\n You will use this file to create .rac files later");



        //making columns fit the text
        for (int k = 0; k < numberOfRowers + 2; k++) {
            sheet.autoSizeColumn(k);
        }

        //Create file system using specific name
        FileOutputStream out = new FileOutputStream(new File(folderName.concat(File.separator).concat(eventName).concat(".xlsx")));

        //write operation workbook using file out object
        workbook.write(out);
        return eventName;
    }


    public static RaceEvent createRacFilesFromExcelSheet(File absExcelFilename) throws IOException {
        FileInputStream inputStream = new FileInputStream(absExcelFilename);
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheetAt(0);
        int numberOfTeams = (int) sheet.getRow(1).getCell(4).getNumericCellValue();
        int numberOfRowers = (int) sheet.getRow(1).getCell(5).getNumericCellValue();
        int numberOfErgs = (int) sheet.getRow(1).getCell(3).getNumericCellValue();
        int numberOfSplits = (int) sheet.getRow(1).getCell(2).getNumericCellValue();
        int raceDistance =  (int) sheet.getRow(1).getCell(1).getNumericCellValue();
        String eventName = sheet.getRow(1).getCell(0).getStringCellValue();


        RaceEvent event = new RaceEvent(numberOfTeams,numberOfRowers,numberOfErgs,eventName,raceDistance,numberOfSplits);

        for (int i = 3; i < 3 + numberOfTeams; i++) {
            XSSFRow row = sheet.getRow(i);
            String teamName = row.getCell(0).getStringCellValue();

            Team currTeam = new Team(teamName);
            currTeam.setShortName(row.getCell(1).getStringCellValue());

            for (int j = 2; j < numberOfRowers + 2; j++) {
                Rower rower = new Rower(row.getCell(j).getStringCellValue().concat(" - ").concat(currTeam.getShortName()));
                currTeam.getRowers().add(rower);
            }

            event.getTeamList().add(currTeam);

        }
        return event;
    }

    public static void generateRacFilesFromRaceEvent(RaceEvent event, String folderName) throws IOException {
        int m = (int) Math.ceil((double)event.getNumberOfTeams()/event.getNumberOfErgs());
        List<Integer> raceSizes = new LinkedList<>();
        int initSize = (int) Math.floor((double) event.getNumberOfTeams()/m);
        int sum = event.getNumberOfTeams() - initSize*m ;
        for (int i = 0; i < m; i++) {
            if (i < sum){raceSizes.add(initSize+1);}
            else {raceSizes.add(initSize);}
        }
        int StartingIndex = 0;
        int EndingIndex = 0;
        for (int i = 0; i < m; i++) {
            StartingIndex = EndingIndex;
            EndingIndex = StartingIndex + raceSizes.get(i);
            for (int j = 0; j < event.getNumberOfRowers(); j++) {
                StringBuilder sb = new StringBuilder();
                sb.append(folderName).append(File.separator);
                StringBuilder sb2 = new StringBuilder();
                sb.append(event.getName()).append("_Group_").append(i+1).append("_Race_").append(j+1).append(".rac");
                sb2.append(event.getName()).append("_Group_").append(i+1).append("_Race_").append(j+1).append(".rac");
                Path racFile = Paths.get(sb.toString());
                List<String> lines = new LinkedList<>();
                lines.add("RACE");
                lines.add("108");
                lines.add("0");
                lines.add(sb2.append("").toString());
                //sb = new StringBuilder();

                lines.add(String.valueOf(event.getLength()));
                lines.add("0");
                lines.add("0");
                lines.add("0");
                lines.add(String.valueOf(event.getLength()/event.getSplits()));
                lines.add("120");
                lines.add(String.valueOf(raceSizes.get(i))+"");

                for (int k = StartingIndex; k < EndingIndex; k++) {
                    lines.add(event.getTeamList().get(k).getRowers().get(j).getName().concat(""));
                    lines.add("0");
                    lines.add("");
                    lines.add("AFG");
                    lines.add("");
                }
                lines.add("0\n");
                Files.write(racFile,lines);
            }
            int pom = StartingIndex;
        }
    }

}
