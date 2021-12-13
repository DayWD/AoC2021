package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

record Cord(int x, int y) {
}

record Instruction(char axis, int line) {
}

class Input {
    LinkedHashSet<Cord> coords;
    List<Instruction> instructions;

    public Input(LinkedHashSet<Cord> coords, CopyOnWriteArrayList<Instruction> instructions) {
        this.coords = coords;
        this.instructions = instructions;
    }
}

public class Main {

    public static void main(String[] args) {
        var task = new Main();
//        task.firstTask();
        task.secondTask();
    }

    private void secondTask() {
        Input data = readInput();

        for (var instruction : data.instructions) {
            foldPaper(data, instruction);
        }

        printPaper(data.coords.stream().toList());
    }

    private static void printPaper(final List<Cord> paper) {
        int maxX = paper.stream().mapToInt(Cord::x).max().getAsInt();
        int maxY = paper.stream().mapToInt(Cord::y).max().getAsInt();
        for (int x = 0; x <= maxX; x++) {
            for (int y = 0; y <= maxY; y++) {
                if (paper.contains(new Cord(x, y))) {
                    System.out.print("X ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

    private void firstTask() {
        Input data = readInput();

        var instruction = data.instructions.get(0);
        foldPaper(data, instruction);
        System.out.println(data.coords.size());
    }

    private void foldPaper(Input data, Instruction instruction) {
        data.coords = switch (instruction.axis()) {
            case 'y':
                yield reflectionByY(data.coords, instruction.line());
            case 'x':
                yield reflectionByX(data.coords, instruction.line());
            default:
                throw new IllegalStateException("Unexpected value: " + instruction.axis());
        };
    }

    private LinkedHashSet<Cord> reflectionByX(LinkedHashSet<Cord> coords, int line) {
        var output = new LinkedHashSet<Cord>();
        for (var cord : coords) {
            if (cord.x() > line) {
                output.add(new Cord(line - (cord.x() - line), cord.y()));
            } else output.add(new Cord(cord.x(), cord.y()));
        }
        return output;
    }

    private LinkedHashSet<Cord> reflectionByY(LinkedHashSet<Cord> coords, int line) {
        var output = new LinkedHashSet<Cord>();
        for (var cord : coords) {
            if (cord.y() > line) {
                output.add(new Cord(cord.x(), line - (cord.y() - line)));
            } else output.add(new Cord(cord.x(), cord.y()));
        }
        return output;
    }

    private Input readInput() {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Path.of("input.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parser(lines);
    }

    private Input parser(List<String> lines) {
        var coords = new LinkedHashSet<Cord>();
        var instructions = new CopyOnWriteArrayList<Instruction>();
        int i;

        for (i = 0; i < lines.size(); i++) {
            if (lines.get(i).equals("")) {
                break;
            }
            var split = lines.get(i).split(",");
            coords.add(new Cord(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
        }
        for (i++; i < lines.size(); i++) {
            var split = lines.get(i).split("=");
            instructions.add(new Instruction(split[0].charAt(split[0].length() - 1), Integer.parseInt(split[1])));
        }
        return new Input(coords, instructions);
    }
}
