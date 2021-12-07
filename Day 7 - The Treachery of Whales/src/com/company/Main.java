package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        task();
    }

    private static void task() {
        List<Integer> position = readInput();
        Collections.sort(position);
        var positionMap = new HashMap<Integer,Integer>();

        for (var pos : position) {
            if (positionMap.containsKey(pos))
                positionMap.replace(pos,positionMap.get(pos) + 1);
            else
                positionMap.put(pos, 1);
        }

//        int fuel = fuelOptimizerTask1(positionMap,position);
        int fuel = fuelOptimizerTask2(positionMap,position);

        System.out.print(fuel);
    }

    private static int fuelOptimizerTask1(Map<Integer,Integer> map, List<Integer> position){
        int cursor = position.get(position.size()/2);
        int middleFuel = howMuchFuelForPositionTask1(cursor,map);;
        int leftFuel = howMuchFuelForPositionTask1(cursor - 1,map);;
        int rightFuel = howMuchFuelForPositionTask1(cursor + 1,map);;

        while (cursor > 0 && cursor < position.size()){
            if (middleFuel <= leftFuel && middleFuel <= rightFuel){
                break;
            }
            if (leftFuel < rightFuel){
                rightFuel = middleFuel;
                middleFuel = leftFuel;
                cursor--;
                leftFuel = howMuchFuelForPositionTask1(cursor -1,map);
            }
            else{
                leftFuel = middleFuel;
                middleFuel = rightFuel;
                cursor++;
                rightFuel = howMuchFuelForPositionTask1(cursor + 1, map);
            }

        }
        return middleFuel;
    }

    private static int howMuchFuelForPositionTask1(int pos, Map<Integer,Integer> map) {
        int fuel = 0;

        for (var position : map.entrySet()) {
            fuel += Math.abs(position.getKey()-pos) * position.getValue();
        }
        return fuel;
    }

    private static int fuelOptimizerTask2(Map<Integer,Integer> map, List<Integer> position){
        int cursor = position.get(position.size()/2);
        int middleFuel = howMuchFuelForPositionTask2(cursor,map);;
        int leftFuel = howMuchFuelForPositionTask2(cursor - 1,map);;
        int rightFuel = howMuchFuelForPositionTask2(cursor + 1,map);;

        while (cursor > 0 && cursor < position.size()){
            if (middleFuel <= leftFuel && middleFuel <= rightFuel){
                break;
            }
            if (leftFuel < rightFuel){
                rightFuel = middleFuel;
                middleFuel = leftFuel;
                cursor--;
                leftFuel = howMuchFuelForPositionTask2(cursor -1,map);
            }
            else{
                leftFuel = middleFuel;
                middleFuel = rightFuel;
                cursor++;
                rightFuel = howMuchFuelForPositionTask2(cursor + 1, map);
            }

        }
        return middleFuel;
    }

    private static int howMuchFuelForPositionTask2(int pos, Map<Integer,Integer> map) {
        int fuel = 0;

        for (var position : map.entrySet()) {
            fuel += expensiveFuelCalc(position.getKey(),pos) * position.getValue();
        }
        return fuel;
    }

    private static int expensiveFuelCalc(int key, int pos){
        int result = 0;
        int cost = 1;
        for (int i = Math.min(key, pos); i < Math.max(key,pos); i++) {
            result += cost;
            cost++;
        }
        return result;
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
