package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {

    public static void main(String[] args) {
        var task = new Main();
        task.firstTask();
//        task.secondTask();
    }

    private void secondTask(){

    }

    private void firstTask(){
        ConcurrentHashMap<String, List<String>> data = readInput();
        removeOrCreateConnection(data);
        createMissingConnection(data);

        var starts = data.remove("start");

        var results = new ArrayList<List<String>>();

        var tempTable = data.keySet().stream().toList();
        var table = new CopyOnWriteArrayList<>(tempTable);

        for (var start: starts) {
            var list = new ArrayList<String>();
            list.add(start);
            var tableWithoutStart = new ArrayList<>(List.copyOf(table));
            if (!Character.isUpperCase(start.charAt(0)))
                tableWithoutStart.remove(start);
            recursiveListOfExists(results,list,data,tableWithoutStart,start);
        }
        for (var r :
                results) {
            System.out.println(r);
        }
        System.out.println(results.size());
    }

/*
    start,A,b,A,c,A,end
            start,A,b,A,end
    start,A,b,end
            start,A,c,A,b,A,end
    start,A,c,A,b,end
            start,A,c,A,end
    start,A,end
            start,b,A,c,A,end
    start,b,A,end
            start,b,end
*/

    private void recursiveListOfExists(List<List<String>> results, List<String> result, ConcurrentHashMap<String, List<String>> data,List<String> tempTable, String key){
// TODO jak A ma do b, to b musi mieć do A.
        for (var node : data.get(key)) { // list
            var table = new ArrayList<>(List.copyOf(tempTable));
            if (table.contains(node) || node.equals("end")){
                if (!Character.isUpperCase(node.charAt(0)))
                    table.remove(node);
                var output = new ArrayList<>(result);
                output.add(node);
                if (node.equals("end"))
                    results.add(output);
                else
                    recursiveListOfExists(results,output,data,table,node);
            }
        }
    }

    private void createMissingConnection(ConcurrentHashMap<String, List<String>> data) {
        for (var keyNode : data.entrySet()) {
            if (Character.isUpperCase(keyNode.getKey().charAt(0))) {
                for (var node: keyNode.getValue()) {
                    if (data.containsKey(node) && !data.get(node).contains(keyNode.getKey())){
                        data.get(node).add(keyNode.getKey());
                    }
                }
            }
        }
    }

    private void removeOrCreateConnection(ConcurrentHashMap<String, List<String>> data) {
        for (var caves : data.entrySet()) {
            var cavesValue = List.copyOf(caves.getValue());
            for (var cave: cavesValue) {
                if (!data.containsKey(cave) && !cave.equals("end")){
                    if (Character.isUpperCase(caves.getKey().charAt(0))) {
                        data.put(cave, new ArrayList<>(Collections.singleton(caves.getKey())));
                    }
                    else
                        caves.getValue().remove(cave);
                }
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

        for (var line :
                lines) {
            var keyAndValue = line.split("-");
            if (map.containsKey(keyAndValue[0])){
                var value = new ArrayList<String>(map.get(keyAndValue[0]));
                value.add(keyAndValue[1]);
                map.replace(keyAndValue[0],new ArrayList<String>(value));
            }
            else {
                map.put(keyAndValue[0],new ArrayList<String>(Collections.singleton(keyAndValue[1])));
            }
        }
        return map;
    }
}
