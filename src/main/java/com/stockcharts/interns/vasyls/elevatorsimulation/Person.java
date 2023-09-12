package com.stockcharts.interns.vasyls.elevatorsimulation;

import java.util.*;

public class Person {

    private static Random rand = new Random();
    
    private final int maxFloor;
    private int currentFloor;
    private int destinationFloor;
    private Building building;
    private boolean inElevator;
    private int directionTraveling;
    private int turnsTraveled;
    private Elevator elevator;
    private int lengthOfStay;

    public Person(Building building) {
        this.maxFloor = building.getNumFloors();
        this.currentFloor = 0;
        this.destinationFloor = 0;
        this.building = building;
        this.inElevator = false;
        this.directionTraveling = 0;
        this.turnsTraveled = 0;
        this.lengthOfStay = 3;
        this.generateDesiredTravel();
    }

    public int getCurrentFloor() {
        return this.currentFloor;
    }

    public int getDestinationFloor() {
        return this.destinationFloor;
    }

    public int getDirectionTraveling() {
        return this.directionTraveling;
    }

    public boolean inElevator() {
        return this.inElevator;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public void generateDesiredTravel() {
        if (this.destinationFloor == this.currentFloor && this.lengthOfStay > 2) {
            int randomFloor = this.chooseRandomFloor();
            this.destinationFloor = randomFloor;
            this.setDirectionTraveling(randomFloor);
        }
    }

    private void setDirectionTraveling(int destinationFloor) {
        if (destinationFloor > this.currentFloor)
            this.directionTraveling = 1;
        else if (destinationFloor < this.currentFloor)
            this.directionTraveling = -1;
        else this.directionTraveling = 0;
    }

    private int chooseRandomFloor() {
        double probability = rand.nextDouble();
        if (probability < 0.3) {
            return rand.nextInt(this.maxFloor + 1);
        }
        else
            return this.currentFloor;
        
    }

    public void pressButtons() {
        boolean[] requestButtons = building.requests.get(this.currentFloor);
        if (this.directionTraveling > 0)
            requestButtons[1] = true;
        else if (this.directionTraveling < 0)
            requestButtons[0] = true;
    }

    public boolean tryEnteringElevator(Elevator elevator) {
        if (this.inElevator) {
            return false;
        }
        else if (elevator.getCurrentFloor() == this.currentFloor && elevator.getDoorStatus() 
                    && elevator.getDirectionTraveling() == this.directionTraveling && elevator.getNumPeopleInside() < 5) {
            if (elevator.addPerson(this)) {
                this.elevator = elevator;
                this.inElevator = true;
                return true;
            }
        }
        return false;
    }

    public void leaveElevator() {
        if (this.inElevator) {
            this.elevator.removePerson(this);
            this.elevator = null;
            this.inElevator = false;
            this.directionTraveling = 0;
            this.lengthOfStay = 0;
            }
        else {
        }
        if (this.currentFloor == 0) {
            this.leaveBuilding();
        }
    }

    public void incrementTurnsTraveled() {
        this.turnsTraveled++;
    }

    public void incrementLengthOfStay() {
        this.lengthOfStay++;
    }

    public int getTurnsTraveled() {
        return this.turnsTraveled;
    }

    public void resetTurnsTraveled() {
        this.turnsTraveled = 0;
    }

    public void leaveBuilding() {
        if (this.currentFloor == 0) {
            this.building.removePerson(this);
            System.out.println(this + "has left the building.");
        }
        else
            System.out.println("You can't leave the building because you're on floor " + this.currentFloor + "!");
    }
}
