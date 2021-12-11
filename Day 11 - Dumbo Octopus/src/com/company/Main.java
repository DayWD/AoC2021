package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

record Coords(int row, int column) {
}

public class Main {

    public static void main(String[] args) {
        var task = new Main();
//        task.firstTask();
        task.secondTask();
    }

    private void secondTask() {
        int[][] data = readInput();
        int step = 0;

        for (int i = 0; i < 10000; i++) {
            upEnergy(data);
            int totalFlashes = flashChainReaction(data);
            if (totalFlashes == 100) {
                step = i;
                break;
            }
        }

        System.out.println(step + 1);
    }

    private void firstTask() {
        int[][] data = readInput();
        int totalFlashes = 0;

        for (int i = 0; i < 100; i++) {
            upEnergy(data);
            totalFlashes += flashChainReaction(data);
        }

        System.out.println(totalFlashes);
    }

    int flashChainReaction(int[][] data) {
        int totalFlashes = 0;
        boolean[][] flashedInStep = new boolean[data.length][data[0].length];

        while (true) {
            Coords cord = findGreaterThanNineAndNotFlashed(data, flashedInStep);
            if (cord.row() == -1 && cord.column() == -1)
                break;
            flashedInStep[cord.row()][cord.column()] = true;
            totalFlashes++;
            data[cord.row()][cord.column() + 1]++;
            data[cord.row()][cord.column() - 1]++;
            data[cord.row() + 1][cord.column() + 1]++;
            data[cord.row() - 1][cord.column() - 1]++;
            data[cord.row() - 1][cord.column() + 1]++;
            data[cord.row() + 1][cord.column() - 1]++;
            data[cord.row() + 1][cord.column()]++;
            data[cord.row() - 1][cord.column()]++;
        }

        for (int i = 1; i < data.length - 1; i++) {
            for (int j = 1; j < data[i].length - 1; j++) {
                if (flashedInStep[i][j]) data[i][j] = 0;
            }
        }
        return totalFlashes;
    }

    private Coords findGreaterThanNineAndNotFlashed(int[][] data, boolean[][] flashedInStep) {
        for (int i = 1; i < data.length - 1; i++) {
            for (int j = 1; j < data[i].length - 1; j++) {
                if (data[i][j] > 9 && !flashedInStep[i][j]) {
                    flashedInStep[i][j] = true;
                    return new Coords(i, j);
                }
            }
        }
        return new Coords(-1, -1);
    }

    void upEnergy(int[][] data) {
        for (int i = 1; i < data.length - 1; i++) {
            for (int j = 1; j < data[i].length - 1; j++) {
                data[i][j]++;
            }
        }
    }

    private int[][] readInput() {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Path.of("input.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringToInt(lines);
    }

    private int[][] stringToInt(List<String> allLines) {
        int columnLength = allLines.size() + 2;
        int rowLength = allLines.get(0).length() + 2;
        int[][] output = new int[columnLength][rowLength];

        for (int i = 0; i < columnLength; i++) {
            for (int k = 0; k < rowLength; k++) {
                if (i == 0 || i == columnLength - 1 || k == 0 || k == rowLength - 1)
                    output[i][k] = 0;
            }
        }

        for (int i = 1; i < columnLength - 1; i++) {
            var numbers = allLines.get(i - 1).toCharArray();
            for (int k = 1; k < rowLength - 1; k++) {
                output[i][k] = numbers[k - 1] - '0';
            }
        }
        return output;
    }
}