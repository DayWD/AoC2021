package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

record coordinate(int x, int y){}
record coordinateForLines(int x1, int y1, int x2, int y2){}

public class Main {

    public static void main(String[] args) {
//        firstTask();
        secondTask();

    }

    private static void secondTask(){
        List<coordinateForLines> data = readInput();

        var diagram = getPointsForCoords(data);

        for (var a : diagram.values()) {
            if (a>1){
                System.out.print(a);
            }
        }
    }

    private static void firstTask(){
        List<coordinateForLines> data = readInput();
        int result = 0;
        removeDiagonalCoords(data);
        var diagram = getPointsForCoords(data);

        for (var a : diagram.values()) {
            if (a>1){
                System.out.print(a);
            }
        }
    }

    private static Map<coordinate, Integer> getPointsForCoords(List<coordinateForLines> data){
        var diagram = new HashMap<coordinate,Integer>();

        for (var coords : data) {
            int i = coords.x1();
            int k = coords.y1();

            for (; true;){
                var coordinate = new coordinate(i,k);
                if (diagram.containsKey(coordinate)){
                    diagram.replace(coordinate,diagram.get(coordinate) + 1);
                }
                else{
                    diagram.put(coordinate,1);
                }
                if (i == coords.x2() && k == coords.y2())
                    break;

                if (i < coords.x2())
                    i++;
                else if (i > coords.x2())
                    i--;
                if (k < coords.y2())
                    k++;
                else if ( k > coords.y2())
                    k--;
            }
        }
        return diagram;

    }
    private static void removeDiagonalCoords(List<coordinateForLines> data) {
        int size = data.size();
        for (int i = 0; i < size; i++) {
            if (data.get(i).x1() != data.get(i).x2() && data.get(i).y1() != data.get(i).y2()) {
                data.remove(i);
                i--;
                size--;
            }
        }
    }

    private static List<coordinateForLines> readInput(){
        List<String> allLines = new ArrayList<>();
        try {
            allLines = Files.readAllLines(Path.of("input.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringToCoordinates(allLines);
    }

    private static List<coordinateForLines> stringToCoordinates(List<String> allLines){
        List<coordinateForLines> output = new ArrayList<>(allLines.size());
        for (var line : allLines) {
            String firstPart = line.substring(0,line.indexOf("->")).trim();
            String secondPart = line.substring(line.indexOf("->") + 2).trim();
            int x1 =  Integer.parseInt(firstPart.substring(0, firstPart.indexOf(',')));
            int y1 =  Integer.parseInt(firstPart.substring(firstPart.indexOf(',') + 1));
            int x2 =  Integer.parseInt(secondPart.substring(0, secondPart.indexOf(',')));
            int y2 =  Integer.parseInt(secondPart.substring(secondPart.indexOf(',') + 1));
            output.add(new coordinateForLines(x1,y1,x2,y2));
        }
        return output;
    }
}
