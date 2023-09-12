package com.stockcharts.interns.vasyls.elevatorsimulation;

public class ElevatorSimulationApp {

    public static final int SIMULATION_DELAY_MILLIS = 200;

    public static void main(String[] args) {

        Building building = new Building(5,1);

        building.graphics.drawBuilding();
        while (!building.people.isEmpty()) {
    
            building.randomlySpawnPerson();
            System.out.println("The building has " + building.people.size() + " people.");
            building.generateDesiredTravel();
            building.letPeopleOutOfElevator();
            building.runElevatorDoors();
            building.peopleBoard();
            building.moveElevators();
        }
    
    }


}
