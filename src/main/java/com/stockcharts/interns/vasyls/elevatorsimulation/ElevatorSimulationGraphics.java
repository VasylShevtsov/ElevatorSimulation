package com.stockcharts.interns.vasyls.elevatorsimulation;

import java.awt.Color;
import java.awt.Font;

import edu.princeton.cs.introcs.*;
import java.util.*;


public class ElevatorSimulationGraphics {
    
    private static final int WIDTH = 350;
    private static final int HEIGHT = 200;
    private static final int SCALING_FACTOR = 4;

    private static final Picture screen = new Picture(WIDTH * SCALING_FACTOR, HEIGHT * SCALING_FACTOR);

    private static final int LEFT_WALL = 20;
    private static final int RIGHT_WALL = 150;
    private static final int BUILDING_WIDTH = RIGHT_WALL - LEFT_WALL;
    private static final int FLOOR_HEIGHT = 20;


    private Building building;

    public ElevatorSimulationGraphics(Building building) {
        this.building = building;
        this.createGrid();
    }

    public void createGrid() {
        for (int j = 0; j < HEIGHT; j++) {
                for (int i = 0; i < WIDTH; i++) {
                    Color c = Color.WHITE;
                    drawBox(i, j, c);
                }
            }
    }

    public void drawFloors() {
        Color c = Color.BLACK;
        drawHorizontal(LEFT_WALL, 0, BUILDING_WIDTH, c);
        for (int i = 0; i <= this.building.getNumFloors(); i++) {
            int startingHeight = (FLOOR_HEIGHT * i);
            int endingHeight = startingHeight + FLOOR_HEIGHT;
            drawVertical(LEFT_WALL, startingHeight, FLOOR_HEIGHT, c);
            drawVertical(RIGHT_WALL, startingHeight, FLOOR_HEIGHT, c);
            drawHorizontal(LEFT_WALL, endingHeight, BUILDING_WIDTH, c); 
        }
    
    }

    public void drawDoors() {
        for (Elevator elevator : this.building.elevators) {
            for (int i = 0; i <= this.building.getNumFloors(); i++) {
                int startingHeight = (FLOOR_HEIGHT * i);
                if (elevator.getCurrentFloor() == i) {
                    drawOneDoor(LEFT_WALL + 10, startingHeight+1, elevator.getDoorStatus());
                    drawElevator(LEFT_WALL + 10, startingHeight+1);
                    drawNumber(elevator.getNumPeopleInside(), LEFT_WALL-5, startingHeight+4);
                }
                else {
                    drawOneDoor(LEFT_WALL + 10, startingHeight+1, false);
                }
            }
        }
    }

    public void drawOneDoor(int x, int y, boolean doorStatus) {
        Color c = Color.BLACK;
        if (!doorStatus) {
            drawVertical(x, y, 10, c);
            drawVertical(x+10, y, 10, c);
            drawVertical(x+5, y, 10, c);
            drawHorizontal(x, y+10, 10, c);
        }
        else {
            drawVertical(x, y, 10, c);
            drawVertical(x+10, y, 10, c);
            drawVertical(x+2, y, 10, c);
            drawVertical(x+8, y, 10, c);
            drawHorizontal(x, y+10, 10, c);
        }
    }

    public void drawPeople() {
        Map<Integer, List<Person>> peopleWaiting = new HashMap<>();
        Map<Integer, List<Person>> peopleWorking = new HashMap<>();


        for (int i = 0; i <= this.building.getNumFloors(); i++) {
            peopleWaiting.put(i, new ArrayList<>());
            peopleWorking.put(i, new ArrayList<>());
        }

        for (Person person : this.building.people) {
            if (!person.inElevator()) {
                if (person.getDirectionTraveling() == 0)
                    peopleWorking.get(person.getCurrentFloor()).add(person);
                else
                    peopleWaiting.get(person.getCurrentFloor()).add(person);
            }
        }

        for (int i = 0; i <= this.building.getNumFloors(); i++) {
            int waitCount = 0;
            int workCount = 0;
            for (Person person : peopleWaiting.get(i)) {
                if (person.getDirectionTraveling() == 1) {
                    drawUp((LEFT_WALL + 30) + (waitCount * 4), i * FLOOR_HEIGHT);
                    waitCount++;
                }
                else if (person.getDirectionTraveling() == -1) {
                    drawDown((LEFT_WALL + 30) + (waitCount * 4), i * FLOOR_HEIGHT);
                    waitCount++;
                }
            }

            for (Person person : peopleWorking.get(i)) {
                drawVertical((RIGHT_WALL - 5) - (workCount * 3), i * FLOOR_HEIGHT, 8, Color.BLACK);
                workCount++;
                }
            
            }
        

    }


    public void drawVertical(int x, int y, int length, Color c) {
        int startingHeight = HEIGHT - y - 1;
        int endingHeight = startingHeight - length;

        for (int j = startingHeight; j > endingHeight; j--) {
            drawBox(x, j, c);
        }
    }

    public void drawUp(int x, int y) {
        Color c = Color.BLACK;
        int startingHeight = HEIGHT - y - 1;
        
        drawVertical(x, y, 8, Color.BLACK);
        drawBox(x-1, startingHeight-5, c);
        drawBox(x-2, startingHeight-5, c);
        drawBox(x-2, startingHeight-6, c);
        drawBox(x-2, startingHeight-7, c);
    }

    public void drawNumber(int num, int x, int y) {
        Color c = Color.BLACK;
        switch (num) {
            case 0:
                drawVertical(x, y, 5, c);
                drawVertical(x+2, y, 5, c);
                drawHorizontal(x, y, 2, c);
                drawHorizontal(x, y+4, 2, c);
                break;
            case 1:
                drawVertical(x, y, 5, c);
                break;
            case 2: 
                drawHorizontal(x, y, 2, c);
                drawVertical(x, y, 3, c);
                drawHorizontal(x, y+2, 2, c);
                drawVertical(x+2, y+2, 3, c);
                drawHorizontal(x, y+4, 2, c);
                break;
            case 3:
                drawHorizontal(x, y, 2, c);
                drawHorizontal(x, y+2, 2, c);
                drawHorizontal(x, y+4, 2, c);
                drawVertical(x+2, y, 5, c);
                break;
            case 4:
                drawVertical(x+2, y, 5, c);
                drawVertical(x, y+2, 3, c);
                drawHorizontal(x, y+2, 2, c);
                break;
            case 5:
                drawHorizontal(x, y, 2, c);
                drawHorizontal(x, y+2, 2, c);
                drawHorizontal(x, y+4, 2, c);
                drawVertical(x, y+2, 3, c);
                drawVertical(x+2, y, 3, c);
                break;
            case 6:
                drawVertical(x, y, 5, c);
                drawVertical(x+2, y, 3, c);
                drawHorizontal(x, y, 2, c);
                drawHorizontal(x, y+2, 2, c);
                drawHorizontal(x, y+4, 2, c);
                break;
            case 7:
                drawHorizontal(x, y+4, 2, c);
                drawVertical(x+2, y, 5, c);
                break;
            case 8:
                drawVertical(x, y, 5, c);
                drawVertical(x+2, y, 5, c);
                drawHorizontal(x, y, 2, c);
                drawHorizontal(x, y+2, 2, c);
                drawHorizontal(x, y+4, 2, c);
                break;
            case 9:
                drawVertical(x, y+2, 3, c);
                drawVertical(x+2, y, 5, c);
                drawHorizontal(x, y, 2, c);
                drawHorizontal(x, y+2, 2, c);
                drawHorizontal(x, y+4, 2, c);
                break;
        }
    }

    public void drawDown(int x, int y) {
        Color c = Color.BLACK;
        int startingHeight = HEIGHT - y - 1;
        
        drawVertical(x, y, 8, Color.BLACK);
        drawBox(x-1, startingHeight-5, c);
        drawBox(x-2, startingHeight-5, c);
        drawBox(x-2, startingHeight-4, c);
        drawBox(x-2, startingHeight-3, c);
    }

    public void drawHorizontal(int x, int y, int length, Color c) {
        int startingHeight = HEIGHT - y - 1;
        int endingRight = x + length;

        for (int i = x; i <= endingRight; i++) {
            drawBox(i, startingHeight, c);
        }
    }

    public void drawElevator(int x, int y) {
        Color c = Color.BLUE;
        drawVertical(x-1, y, 11, c);
        drawVertical(x+11, y, 11, c);
        drawHorizontal(x-1, y+11, 12, c);
    }

    public void drawBuilding() {
        createGrid();
        drawFloors();
        drawDoors();
        drawPeople();
        screen.show();
        try {
            Thread.sleep(ElevatorSimulationApp.SIMULATION_DELAY_MILLIS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void drawBox(int x, int y, Color c) {
        int i = x * SCALING_FACTOR;
        int j = y * SCALING_FACTOR;

        for (int a = 0; a < SCALING_FACTOR; a++) {
            for (int b = 0; b < SCALING_FACTOR; b++) {
                screen.set(i+a, j+b, c);
            }
        }
    }

    public static void main(String[] args) {
        Building building = new Building(9, 1);
        ElevatorSimulationGraphics el = new ElevatorSimulationGraphics(building);

        el.drawNumber(9, 20, 20);
        screen.show();
    
    }

}

