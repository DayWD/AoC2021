package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
//        firstTask();
        secondTask();

    }

    private static void secondTask() {
        List<char[]> data = deleteCorruptedLines(readInput());
        var resultData = new ArrayList<Long>();
        int result = 0;
        var stacks = new ArrayList<Stack<Character>>();

        for (int i = 0; i < data.size(); i++) {
            stacks.add(new Stack<>());
            for (var c : data.get(i)) {
                if (c == '(' || c == '[' || c == '{' || c == '<')
                    stacks.get(i).push(c);
                else {
                    if (c == ')' && stacks.get(i).peek() == '(') stacks.get(i).pop();
                    else if (c == ']' && stacks.get(i).peek() == '[') stacks.get(i).pop();
                    else if (c == '}' && stacks.get(i).peek() == '{') stacks.get(i).pop();
                    else if (c == '>' && stacks.get(i).peek() == '<') stacks.get(i).pop();
                    else {
                        System.out.println(c);
                    }
                }
            }
        }

        for (int i = 0; i < stacks.size(); i++) {
            resultData.add(0L);
            for (int j = stacks.get(i).size() - 1; j >= 0; j--) {

                if (stacks.get(i).get(j) == '(') resultData.set(i, 5 * resultData.get(i) + 1);
                if (stacks.get(i).get(j) == '[') resultData.set(i, 5 * resultData.get(i) + 2);
                if (stacks.get(i).get(j) == '{') resultData.set(i, 5 * resultData.get(i) + 3);
                if (stacks.get(i).get(j) == '<') resultData.set(i, 5 * resultData.get(i) + 4);
            }
        }
        resultData.sort(Comparator.comparingLong(x -> x));

        System.out.println(resultData.get(resultData.size() / 2));
    }

    private static void firstTask() {
        List<char[]> data = readInput();
        var resultData = new ArrayList<Character>();
        int result = 0;

        for (var line : data) {
            var stack = new Stack<Character>();
            for (var c : line) {
                if (c == '(' || c == '[' || c == '{' || c == '<')
                    stack.push(c);
                else {
                    if (c == ')' && stack.peek() == '(') stack.pop();
                    else if (c == ']' && stack.peek() == '[') stack.pop();
                    else if (c == '}' && stack.peek() == '{') stack.pop();
                    else if (c == '>' && stack.peek() == '<') stack.pop();
                    else {
                        resultData.add(c);
                        break;
                    }
                }
            }
        }
        var map = resultData.stream().collect(Collectors.groupingBy(c -> c));

        for (var element : map.entrySet()) {
            if (element.getKey() == ')') result += 3 * element.getValue().size();
            if (element.getKey() == ']') result += 57 * element.getValue().size();
            if (element.getKey() == '}') result += 1197 * element.getValue().size();
            if (element.getKey() == '>') result += 25137 * element.getValue().size();
        }
        System.out.print(result);
    }

    private static List<char[]> deleteCorruptedLines(List<char[]> data) {
        var rowsToDelete = new ArrayList<Integer>();
        for (int i = 0; i < data.size(); i++) {
            var stack = new Stack<Character>();
            for (var c : data.get(i)) {
                if (c == '(' || c == '[' || c == '{' || c == '<')
                    stack.push(c);
                else {
                    if (c == ')' && stack.peek() == '(') stack.pop();
                    else if (c == ']' && stack.peek() == '[') stack.pop();
                    else if (c == '}' && stack.peek() == '{') stack.pop();
                    else if (c == '>' && stack.peek() == '<') stack.pop();
                    else {
                        rowsToDelete.add(i);
                        break;
                    }
                }
            }
        }
        var output = new ArrayList<char[]>();
        for (int i = 0; i < data.size(); i++) {
            if (!rowsToDelete.contains(i))
                output.add(data.get(i));
        }
        return output;
    }

    private static List<char[]> readInput() {
        List<String> allLines = new ArrayList<>();
        try {
            allLines = Files.readAllLines(Path.of("input.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        var result = new ArrayList<char[]>();
        for (var line : allLines) {
            result.add(line.toCharArray());
        }
        return result;
    }
}
