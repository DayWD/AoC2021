package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

record Table(String node, Integer repeat){
    public Table(String node) {
        this(node,2);
    }
}

public class Main {

    public static void main(String[] args) {
        var task = new Main();
        //TODO There is no task one D:
        task.secondTask();
    }

    private void secondTask(){
        ConcurrentHashMap<String, List<String>> data = readInput();
        data.remove("end");

        //Lists sorting
        for (var list : data.values()) {
            Collections.sort(list);
        }

        var starts = data.remove("start");

        var results = new ArrayList<List<String>>();

        var tempTable = data.keySet().stream().toList();
        var table = new CopyOnWriteArrayList<Table>();
        for (var el : tempTable) {
            table.add(new Table(el));
        }

        for (var start: starts) {
            var list = new ArrayList<String>();
            list.add(start);
            var tableWithoutStart = new ArrayList<>(List.copyOf(table));
            if (!Character.isUpperCase(start.charAt(0)))
                tableWithoutStart.set(table.indexOf(new Table(start)),new Table(start,1));
            recursiveListOfExists(results,list,data,tableWithoutStart,start, false);
        }
        System.out.println(results.size());
    }

    private void recursiveListOfExists(List<List<String>> results, List<String> result, ConcurrentHashMap<String, List<String>> data,List<Table> tempTable, String key, Boolean tempCaveVisitedTwice){
        for (var node : data.get(key)) {
            var table = new ArrayList<>(List.copyOf(tempTable));
            var caveVisitedTwice = tempCaveVisitedTwice;

            if (table.contains(new Table(node,1)) || table.contains(new Table(node,2)) || node.equals("end")){
                if (!Character.isUpperCase(node.charAt(0)) && !node.equals("end")) {
                    int index = table.indexOf(new Table(node));
                    if (index == -1)
                        index = table.indexOf(new Table(node,1));
                    table.set(index,new Table(node,table.get(index).repeat()-1));
                    if (table.get(index).repeat() == 0 ) {
                        if (caveVisitedTwice)
                            continue;
                        table.remove(index);
                        caveVisitedTwice = true;
                    }
                }
                var output = new ArrayList<>(result);
                output.add(node);
                if (node.equals("end"))
                    results.add(output);
                else
                    recursiveListOfExists(results,output,data,table,node, caveVisitedTwice);
            }
        }
    }

    private ConcurrentHashMap<String, List<String>> readInput(){
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Path.of("input.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createListOfNodes(lines);
    }

    private ConcurrentHashMap<String, List<String>> createListOfNodes(List<String> lines) {
        var map = new ConcurrentHashMap<String,List<String>>();

        for (var line : lines) {
            var keyAndValue = line.split("-");
            if (map.containsKey(keyAndValue[0])){
                var value = new ArrayList<String>(map.get(keyAndValue[0]));
                value.add(keyAndValue[1]);
                map.replace(keyAndValue[0],new ArrayList<String>(value));

                andViceVersa(map, keyAndValue);
            }
            else {
                map.put(keyAndValue[0],new ArrayList<String>(Collections.singleton(keyAndValue[1])));

                andViceVersa(map, keyAndValue);
            }
        }
        return map;
    }

    private void andViceVersa(ConcurrentHashMap<String, List<String>> map, String[] keyAndValue) {
        if (map.containsKey(keyAndValue[1])){
            var value2 = new ArrayList<String>(map.get(keyAndValue[1]));
            value2.add(keyAndValue[0]);
            map.replace(keyAndValue[1],new ArrayList<String>(value2));
        }
        else {
            map.put(keyAndValue[1],new ArrayList<String>(Collections.singleton(keyAndValue[0])));
        }
    }
}
