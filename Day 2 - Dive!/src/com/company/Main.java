package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
//        firstTask();
        secondTask();
    }


    private static void secondTask(){
        List<String[]> data = readInput();

        int depth = 0;
        int aim = 0;
        int horizontal = 0;

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i)[0].equals("forward")) {
                horizontal += Integer.parseInt(data.get(i)[1]);
                depth += Integer.parseInt(data.get(i)[1]) * aim;
            }
            else if (data.get(i)[0].equals("down"))
                aim+=Integer.parseInt(data.get(i)[1]);
            else if (data.get(i)[0].equals("up"))
                aim-=Integer.parseInt(data.get(i)[1]);
        }
        System.out.print(horizontal*depth);
    }

    private static void firstTask(){
        List<String[]> data = readInput();

        int depth = 0;
        int horizontal = 0;

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i)[0].equals("forward"))
                horizontal+=Integer.parseInt(data.get(i)[1]);
            else if (data.get(i)[0].equals("down"))
                depth+=Integer.parseInt(data.get(i)[1]);
            else if (data.get(i)[0].equals("up"))
                depth-=Integer.parseInt(data.get(i)[1]);
        }
        System.out.print(horizontal*depth);
    }

    private static List<String[]> readInput(){
        List<String> allLines = new ArrayList<>();
        List<String[]> output = new ArrayList<>();
        try {
            allLines = Files.readAllLines(Path.of("input.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < allLines.size(); i++) {
            output.add(i,allLines.get(i).split(" "));
        }
        return output;
    }

    private static List<Integer> stringToInt(List<String> allLines){
        List<Integer> output = new ArrayList<>(allLines.size());
        for (var line : allLines) {
            output.add(Integer.parseInt(line));
        }
        return output;
    }
}
