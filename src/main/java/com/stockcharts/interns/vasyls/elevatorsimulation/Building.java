package com.stockcharts.interns.vasyls.elevatorsimulation;

import java.util.*;

public class Building {

    private static Random rand = new Random();

    private final int numFloors;
    private int peopleSpawned;
    private static final int MAX_PEOPLE = 30;
    public List<Elevator> elevators;
    public List<Person> people;
    public Map<Integer, boolean[]> requests;
    public ElevatorSimulationGraphics graphics;
    
    Building(int numFloors, int numElevators) {
        this.numFloors = numFloors;
        this.elevators = new ArrayList<>();
        for (int i = 0; i < numElevators; i++) {
            this.addElevator(5);
        }
        this.people = new ArrayList<>();
        this.requests = new HashMap<>();
        for (int i = 0; i <= this.numFloors; i++) {
            requests.put(i, new boolean[] {false,false});
        }
        graphics = new ElevatorSimulationGraphics(this);
        this.people.add(new Person(this));
        this.peopleSpawned = 1;
    }

    private Elevator addElevator(int maxCapacity) {
        Elevator newElevator = new Elevator(maxCapacity, this);
        this.elevators.add(newElevator);
        return newElevator;
    }

    public int getNumFloors() {
        return this.numFloors;
    }

    public void removePerson(Person person) {
        this.people.remove(person);
    }

    public void randomlySpawnPerson() {
        if (this.peopleSpawned < MAX_PEOPLE) {
            double probability = rand.nextDouble();
            if (probability < 0.4) {
                this.people.add(new Person(this));
                this.graphics.drawBuilding();
                this.peopleSpawned++;
            }
        }
    }

    public void generateDesiredTravel() {
        for (Person person : people) {
            if (!person.inElevator()) {
                person.generateDesiredTravel();
                person.pressButtons();
            }
        }
    }

    public void runElevatorDoors() {
        for (Elevator elevator : elevators) {
            elevator.openIfRequestMatchesDirection();
            this.graphics.drawBuilding();
        }
    }

    public void letPeopleOutOfElevator() {
        for (Elevator elevator : elevators) {
            elevator.letPeopleOut();
        }
    }

    public void peopleBoard() {
        for (Person person : people) {
            for (Elevator elevator : elevators) {
                if (person.tryEnteringElevator(elevator))
                    this.graphics.drawBuilding();
            }
            person.incrementLengthOfStay();
        }
    }

    public void moveElevators() {
        for (Elevator elevator : elevators) {
            elevator.searchForRequests();
            elevator.moveFloors();
        }
        this.graphics.drawBuilding();
    }

}


