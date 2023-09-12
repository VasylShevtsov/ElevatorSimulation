package com.stockcharts.interns.vasyls.elevatorsimulation;

import java.util.*;

public class Elevator {

    private final int maxCapacity;
    private int directionTraveling;
    private int currentFloor;
    private boolean doorStatus;
    private List<Person> peopleInElevator;
    private Building building;
    
    public Elevator(int maxCapacity, Building building) {
        this.maxCapacity = maxCapacity;
        this.directionTraveling = 0;
        this.currentFloor = 0;
        this.doorStatus = false;
        this.building = building;
        this.peopleInElevator = new ArrayList<>();
    }

    public int getNumPeopleInside() {
        return this.peopleInElevator.size();
    }

    public int getCurrentFloor() {
        return this.currentFloor;
    }

    public int getDirectionTraveling() {
        return this.directionTraveling;
    }

    public void moveFloors() {
        this.closeDoor();
        this.building.graphics.drawBuilding();
        this.currentFloor += this.directionTraveling;
        for (Person person : peopleInElevator) {
            person.setCurrentFloor(this.currentFloor);
        }
        if ((this.currentFloor == 0 && this.directionTraveling == -1) || 
        (this.currentFloor == this.building.getNumFloors() && this.directionTraveling  == 1)) {
            this.directionTraveling = 0;
        }
    }

    private void setDirectionTraveling(int destinationFloor) {
        if (destinationFloor > this.getCurrentFloor())
            this.directionTraveling = 1;
        else if (destinationFloor < this.getCurrentFloor())
            this.directionTraveling = -1;
        else this.directionTraveling = 0;
    }

    public boolean addPerson(Person person) {
        if (peopleInElevator.size() < maxCapacity) {
            this.peopleInElevator.add(person);
            return true;
        }
        else {
            System.out.println(person + " cannot enter because elevator is at max capacity.");
            return false;
        }
    }

    public void letPeopleOut() {
        for (int i = 0; i < this.peopleInElevator.size(); i++) {
            Person person = this.peopleInElevator.get(i);
            if (person.getDestinationFloor() == this.getCurrentFloor()) {
                this.openDoor();
                this.building.graphics.drawBuilding();
                person.leaveElevator();
                this.building.graphics.drawBuilding();
                i--;
            }
            if (this.isEmpty()) {
                this.directionTraveling = 0;
            }
        }
    }

    public void openIfRequestMatchesDirection() {
        boolean[] currentFloorButtons = this.building.requests.get(this.getCurrentFloor());
            if (currentFloorButtons[0] && (this.getDirectionTraveling() == 0 || this.getDirectionTraveling() == -1)) {
                this.openDoor();
                this.directionTraveling = -1;
                currentFloorButtons[0] = false;
            }
            else if (currentFloorButtons[1] && (this.getDirectionTraveling() == 0 || this.getDirectionTraveling() == 1)) {
                this.openDoor();
                this.directionTraveling = 1;
                currentFloorButtons[1] = false;
            }
            else {
                this.closeDoor();
            }
    }

    public void searchForRequests() {
        if (this.getDirectionTraveling() == 0) {
            for (int i = 0; i <= this.building.getNumFloors(); i++) {
                boolean[] request = this.building.requests.get(i);
                if (request[0] || request[1]) {
                    this.setDirectionTraveling(i);
                    break;
                }
            }
        }
        if (this.getDirectionTraveling() == 0) {
            this.setDirectionTraveling(0);
        }
    }

    public void removePerson(Person person) {
        peopleInElevator.remove(person);
    }

    private void openDoor() {
        this.doorStatus = true;
    }

    private void closeDoor() {
        this.doorStatus = false;
    }

    public boolean getDoorStatus() {
        return this.doorStatus;
    }

    private boolean isEmpty() {
        return (this.peopleInElevator.size() == 0);
    }
    
}
