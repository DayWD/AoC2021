package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
//        firstTask();
        secondTask();

    }

    private static void secondTask(){
        List<String> data = readInput();

        System.out.print(co2ScrubberRating(new ArrayList<>(data))*oxygenGeneratorRating(new ArrayList<>(data)));
    }

    @SuppressWarnings("SuspiciousListRemoveInLoop")
    private static int co2ScrubberRating(List<String> data){

        for (int i = 0, bitOne=0,bitZero=0; i < data.get(0).length(); i++) {
            for (int j = 0; j < data.size(); j++) {
                if (data.get(j).charAt(i) == '1')
                    bitOne++;
                else
                    bitZero++;
            }

            if (bitOne>=bitZero) {
                int finalI = i;
                data.removeIf(x -> x.charAt(finalI) == '1');
            }
            else {
                int finalI = i;
                data.removeIf(x -> x.charAt(finalI) == '0');
            }
            bitZero=0;
            bitOne=0;
            if (data.size() == 1) break;
        }

        return Integer.parseInt(data.get(0),2);
    }

    @SuppressWarnings("SuspiciousListRemoveInLoop")
    private static int oxygenGeneratorRating(List<String> data){

        for (int i = 0, bitOne=0,bitZero=0; i < data.get(0).length(); i++) {
            for (int j = 0; j < data.size(); j++) {
                if (data.get(j).charAt(i) == '1')
                    bitOne++;
                else
                    bitZero++;
            }

            if (bitOne>=bitZero) {
                int finalI = i;
                data.removeIf(x -> x.charAt(finalI) == '0');
            }
            else {
                int finalI = i;
                data.removeIf(x -> x.charAt(finalI) == '1');
            }
            bitZero=0;
            bitOne=0;
        }

        return Integer.parseInt(data.get(0),2);
    }

    private static void firstTask(){
        List<String> data = readInput();
        StringBuilder gammaRate = new StringBuilder();
        StringBuilder epsilonRate = new StringBuilder();

        for (int i = 0, bitOne=0,bitZero=0; i < data.get(0).length(); i++) {
            for (int j = 0; j < data.size(); j++) {
                if (data.get(j).charAt(i) == '1')
                    bitOne++;
                else
                    bitZero++;
            }

            if (bitOne>bitZero) {
                gammaRate.append("1");
                epsilonRate.append("0");
            }
            else {
                gammaRate.append("0");
                epsilonRate.append("1");
            }
            bitZero=0;
            bitOne=0;
        }

        int decimalGamma=Integer.parseInt(gammaRate.toString(),2);
        int decimalEpsilon=Integer.parseInt(epsilonRate.toString(),2);


        System.out.print(decimalEpsilon*decimalGamma);
    }

    private static List<String> readInput(){
        List<String> allLines = new ArrayList<>();
        try {
            allLines = Files.readAllLines(Path.of("input.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allLines;
    }

    private static List<Integer> stringToInt(List<String> allLines){
        List<Integer> output = new ArrayList<>(allLines.size());
        for (var line : allLines) {
            output.add(Integer.parseInt(line));
        }
        return output;
    }
}
