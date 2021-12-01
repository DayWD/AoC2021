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
        List<Integer> data = readInput();
        int result = 0;

        int size = (data.size() / 3) * 3;

        int[] buckets = new int[size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < 3; j++) {
                buckets[i] += data.get(i+j);
            }
        }

        for (int i = 1; i < buckets.length; i++) {
            if (buckets[i] > buckets[i-1]){
                result++;
            }
        }

        System.out.print(result);
    }

    private static void firstTask(){
        List<Integer> data = readInput();
        int result = 0;

        for (int i = 1; i < data.size(); i++) {
            if (data.get(i) > data.get(i-1) ){
                result++;
            }
        }
        System.out.print(result);
    }

    private static List<Integer> readInput(){
        List<String> allLines = new ArrayList<>();
        try {
            allLines = Files.readAllLines(Path.of("input.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringToInt(allLines);
    }

    private static List<Integer> stringToInt(List<String> allLines){
        List<Integer> output = new ArrayList<>(allLines.size());
        for (var line : allLines) {
            output.add(Integer.parseInt(line));
        }
        return output;
    }
}
