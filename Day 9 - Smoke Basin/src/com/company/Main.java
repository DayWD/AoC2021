package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

record Coords(int row, int column) {
}

class Pair {
    int value = -1;
    int basinID = 0;
}

public class Main {

    public static void main(String[] args) {
//        firstTask();
        secondTask();

    }

    private static void secondTask() {
        Pair[][] data = readInput2();
        int pool = 0;

        int columnLength = data.length;
        int rowLength = data[0].length;
        for (int x = 1; x < columnLength - 1; x++) {
            for (int y = 1; y < rowLength - 1; y++) {
                Coords cord = findMinValue(data);
                if (cord.row() == -1 || cord.column() == -1)
                    break;

                if (data[cord.row()][cord.column() + 1].basinID != 0)
                    data[cord.row()][cord.column()].basinID = data[cord.row()][cord.column() + 1].basinID;
                else if (data[cord.row()][cord.column() - 1].basinID != 0)
                    data[cord.row()][cord.column()].basinID = data[cord.row()][cord.column() - 1].basinID;
                else if (data[cord.row() + 1][cord.column()].basinID != 0)
                    data[cord.row()][cord.column()].basinID = data[cord.row() + 1][cord.column()].basinID;
                else if (data[cord.row() - 1][cord.column()].basinID != 0)
                    data[cord.row()][cord.column()].basinID = data[cord.row() - 1][cord.column()].basinID;
                else {
                    pool++;
                    data[cord.row()][cord.column()].basinID = pool;
                }
            }
        }

        Map<Integer, Integer> result = new HashMap<Integer, Integer>();

        for (int i = 1; i < columnLength - 1; i++) {
            for (int k = 1; k < rowLength - 1; k++) {
                if (data[i][k].basinID != 0) {
                    if (result.containsKey(data[i][k].basinID))
                        result.replace(data[i][k].basinID, result.get(data[i][k].basinID) + 1);
                    else result.put(data[i][k].basinID, 1);
                }
            }
        }


        int output = 1;
        var list = result.values().stream().toList();
        var list2 = new ArrayList<>(list);
        list2.sort(Collections.reverseOrder());
        output = list2.get(0) * list2.get(1) * list2.get(2);


        System.out.print(output);
    }

    private static Coords findMinValue(Pair[][] data) {
        int columnLength = data.length;
        int rowLength = data[0].length;

        for (int i = 1; i < columnLength - 1; i++) {
            for (int k = 1; k < rowLength - 1; k++) {
                var it = data[i][k];
                if (it.value != 9 && it.value <= data[i][k + 1].value && it.value <= data[i][k - 1].value && it.value <= data[i + 1][k].value && it.value <= data[i - 1][k].value) {
                    data[i][k].value = 9;
                    return new Coords(i, k);
                }
            }
        }
        return new Coords(-1, -1);
    }

    private static void firstTask() {
        int[][] data = readInput();
        int result = 0;

        int columnLength = data.length;
        int rowLength = data[0].length;

        for (int i = 1; i < columnLength - 1; i++) {
            for (int k = 1; k < rowLength - 1; k++) {
                var it = data[i][k];
                if (it < data[i][k + 1] && it < data[i][k - 1] && it < data[i + 1][k] && it < data[i - 1][k])
                    result += it + 1;

            }
        }

        System.out.print(result);
    }

    private static Pair[][] readInput2() {
        List<String> allLines = new ArrayList<>();
        try {
            allLines = Files.readAllLines(Path.of("input.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringToInt2(allLines);
    }

    private static Pair[][] stringToInt2(List<String> allLines) {
        int columnLength = allLines.size() + 2;
        int rowLength = allLines.get(0).length() + 2;
        Pair[][] output = new Pair[columnLength][rowLength];

        for (int i = 0; i < columnLength; i++) {
            for (int j = 0; j < rowLength; j++) {
                output[i][j] = new Pair();
            }

        }

        for (int i = 0; i < columnLength; i++) {
            for (int k = 0; k < rowLength; k++) {
                if (i == 0 || i == columnLength - 1 || k == 0 || k == rowLength - 1)
                    output[i][k].value = 9;
            }
        }

        for (int i = 1; i < columnLength - 1; i++) {
            var numbers = allLines.get(i - 1).toCharArray();
            for (int k = 1; k < rowLength - 1; k++) {
                output[i][k].value = numbers[k - 1] - '0';
            }
        }
        return output;
    }

    private static int[][] readInput() {
        List<String> allLines = new ArrayList<>();
        try {
            allLines = Files.readAllLines(Path.of("input.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringToInt(allLines);
    }

    private static int[][] stringToInt(List<String> allLines) {
        int columnLength = allLines.size() + 2;
        int rowLength = allLines.get(0).length() + 2;
        int[][] output = new int[columnLength][rowLength];

        for (int i = 0; i < columnLength; i++) {
            for (int k = 0; k < rowLength; k++) {
                if (i == 0 || i == columnLength - 1 || k == 0 || k == rowLength - 1)
                    output[i][k] = 9;
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
