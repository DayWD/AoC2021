package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {

    public static void main(String[] args) {
//        firstTask();
        secondTask();

    }

    private static void secondTask(){
        List<Integer> fishInternalTimers = readInput();
        var fishes =new HashMap<Integer,Long>();

        for (int i = -1; i < 10; i++) {
            fishes.put(i,0L);
        }

        for (var fish : fishInternalTimers) {
            fishes.replace(fish,fishes.get(fish) + 1);
        }


        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < fishes.size() - 1; j++) {
                fishes.replace(j-1,fishes.get(j));
            }
            if (fishes.get(-1) != 0 ){
                fishes.replace(6,fishes.get(6) + fishes.get(-1));
                fishes.replace(8,fishes.get(-1));
            }
        }

        Long result = 0L;

        for (int i = 0; i < 9; i++) {
            result += fishes.get(i);
        }

        System.out.print(result);
    }

    private static void firstTask() {
        List<Integer> fishInternalTimers = readInput();

        for (int i = 0; i < 80; i++) {
            int size = fishInternalTimers.size();
            for (int j = 0; j < size; j++) {
                if (fishInternalTimers.get(j) == 0 ){
                    fishInternalTimers.set(j,6);
                    fishInternalTimers.add(8);
                }
                else
                    fishInternalTimers.set(j, fishInternalTimers.get(j)-1);
            }
        }

        System.out.print(fishInternalTimers.size());
    }

    private static List<Integer> readInput(){
        List<String> allLines = new ArrayList<>();
        List<Integer> result = new ArrayList<>();
        try {
            allLines = Files.readAllLines(Path.of("input.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] numbers = allLines.get(0).split(",");
        int[] ints = Arrays.stream(numbers).mapToInt(Integer::parseInt).toArray();
        for (var number: ints) {
            result.add(number);
        }

        return result;
    }
}
