package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        var task = new Main();
        task.firstTask();
//        task.secondTask();
    }

    private void secondTask(){

    }

    private void firstTask(){
        List<String> data = readInput();
    }

    private List<String> readInput(){
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Path.of("input.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
