package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {

    public static void main(String[] args) {
//        System.out.print(firstTask());
        System.out.print(secondTask());
    }

    private static int secondTask(){
        List<String> data = readInput();
        int result = 0;

        List<Integer> numbersPool = numbersPool(data);
        List<Integer[][]> grids = getGrids(data);
        while (numbersPool.size() > 0){
            int gridsSize = grids.size();
            for (int i = 0; i < gridsSize;i++) {
                markNumber(grids.get(i), numbersPool.get(0));
                if(grids.size() == 1 && findWinner(grids.get(0))){
                    int sum = sumUpPoints(grids.get(0));
                    result = sum*numbersPool.get(0);
                    return result;
                }
                else if (findWinner(grids.get(i))) {
                    grids.remove(i);
                    i--;
                    gridsSize--;
                }
            }
            numbersPool.remove(0);
        }
        return -1;
    }

    private static int firstTask() {
        List<String> data = readInput();
        int result = 0;

        List<Integer> numbersPool = numbersPool(data);
        List<Integer[][]> grids = getGrids(data);
        while (numbersPool.size() > 0){
            for (var grid : grids) {
                markNumber(grid, numbersPool.get(0));
                if(findWinner(grid)){
                    int sum = sumUpPoints(grid);
                    result = sum*numbersPool.get(0);
                    return result;
                }
            }
            numbersPool.remove(0);
        }
        return -1;
    }

    private static int sumUpPoints(Integer[][] grid) {
        int sum = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (grid[i][j] != -1)
                    sum+=grid[i][j];
            }
        }
        return sum;
    }

    private static boolean findWinner(Integer[][] grid) {
        int rowWin = 0;
        int columnWin = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (grid[i][j] == -1)
                    rowWin++;

                if (grid[j][i] == -1)
                    columnWin++;

                if (rowWin == 5 || columnWin ==5 )
                    return true;
            }
            rowWin = 0;
            columnWin = 0;
        }
        return false;
    }

    static void markNumber(Integer[][] grid, int number){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (grid[i][j] == number)
                    grid[i][j] = -1;
            }
        }
    }

    static List<Integer[][]> getGrids(List< String > data){
        List<Integer[][]> grids = new ArrayList<>();

        for (int i = 0; i < data.size(); i+= 6) {
            Integer[][] grid = new Integer[5][5];
            for (int j = 0; j < 5; j++) {
                String[] line = data.get(i+j).replace("  "," ").strip().split(" ");
                for (int k = 0; k < 5; k++) {
                    grid[j][k] = Integer.parseInt(line[k]);
                }
            }
            grids.add(grid);
        }

        return grids;
    }

    static List<Integer> numbersPool(List< String > data){
        List<Integer> result = new ArrayList<>();
        String[] temp = data.get(0).split(",");
        for (var s : temp) {
            result.add(Integer.parseInt(s));
        }
        data.remove(0);
        data.remove(0);

        return result;
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
