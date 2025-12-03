package com;

import com.test.entities.*;
import com.test.services.VehicleService;


import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class App {
    private final String menu = "\n***********************\n"
            + " 1. Add vehicles\n"
            + " 2. Edit vehicles\n"
            + " 3. Delete vehicles\n"
            + " 4. List vehicles\n"
            + "99. Quit\n"
            + "***********************\n"
            + "Enter choice: ";

    private final int currentVehicles = 0;


    private final Scanner input;
    private final PrintStream out;
    private final VehicleService vehicleService;


    public App(InputStream in, PrintStream out, VehicleService vehicleService) {
        this.input = new Scanner(in);
        this.out = out;
        this.vehicleService = vehicleService;
    }


    public void run() {
        while (true) {
            out.print(menu);
            String choiceStr = input.nextLine();
            int choice;

            try {
                choice = Integer.parseInt(choiceStr);
            } catch (NumberFormatException e) {
                out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    addVehicle();
                    break;
                case 2:
                    editVehicle();
                    break;
                case 3:
                    deleteVehicle();
                    break;
                case 4:
                    listVehicles();
                    break;
                case 99:
                    out.println("Exiting application. Goodbye!");
                    return;
                default:
                    out.println("Invalid choice, please try again.");
            }
        }
    }
    public void editVehicle() {
        listVehicles();

        out.print("\nEnter the ID of the vehicle to edit: ");
        String idStr = input.nextLine();
        long id;

        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            out.println("Invalid ID format. Please enter a number.");
            return;
        }

        Optional<VehicleEntity> vehicleOpt = vehicleService.getVehicleById(id);

        if (vehicleOpt.isPresent()) {
            VehicleEntity vehicleToEdit = vehicleOpt.get();
            vehicleToEdit.edit(input);
            vehicleService.saveVehicle(vehicleToEdit);
            out.println("Vehicle updated successfully!");
        } else {
            out.println("Vehicle with ID " + id + " not found.");
        }
    }

   public void deleteVehicle(){
        listVehicles(); // Show the user all available items
        out.print("\nEnter the ID of the Vehicle to delete: ");
        String idStr = input.nextLine();
        long id;

        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            out.println("Invalid ID format. Please enter a number.");
            return;
        }

        Optional<VehicleEntity> vehicleOpt = vehicleService.getVehicleById(id);

        if (vehicleOpt.isPresent()) {
            VehicleEntity vehicleToDelete = vehicleOpt.get();
            out.println("You are about to delete the following vehicle:");
            if (vehicleToDelete instanceof CarEntity car) {
                out.printf(" -> ID: %d | ID: %d | Type: Car | make: %s | model: %s | year: %s | doors: %s", car.getId(), car.getMake(), car.getModel(), car.getYear(), car.getNumDoors());
            } else if (vehicleToDelete instanceof MotorcycleEntity motorcycle) {
                out.printf(" -> ID: %d | ID: %d | Type: Car | make: %s | model: %s | year: %s | year: %s | hasSideCar %s", motorcycle.getId(), motorcycle.getMake(), motorcycle.getModel(), motorcycle.getYear(), motorcycle.isHasSidecar());
            }

            out.print("Are you sure you want to delete this Vehicle? (y/n): ");
            String confirmation = input.nextLine();

            if (confirmation.equalsIgnoreCase("y")) {
                vehicleService.deleteVehicle(id);
                out.println("Item with ID " + id + " was deleted successfully.");
            } else {
                out.println("Deletion canceled.");
            }
        } else {
            out.println("Item with ID " + id + " not found.");
        }
    }
    public void populateVehicles() {
        out.println("\n--- Populating with initial vehicle data... ---");

        vehicleService.saveVehicle(new CarEntity("Toyota", "Corolla", 2020, 4));
        vehicleService.saveVehicle(new CarEntity("Honda", "Civic", 2019, 4));
        vehicleService.saveVehicle(new CarEntity("Ford", "Mustang", 2021, 2));

        vehicleService.saveVehicle(new MotorcycleEntity("Harley-Davidson", "Sportster", 2022, false));
        vehicleService.saveVehicle(new MotorcycleEntity("Kawasaki", "Ninja", 2023, false));
        vehicleService.saveVehicle(new MotorcycleEntity("BMW", "R1250", 2021, true));

        out.println("--- Vehicle population complete. ---");
    }

    public void listVehicles() {
        out.println("\n--- All Vehicles in Inventory ---");
        List<VehicleEntity> vehicles = vehicleService.getAllVehicles();

        if (vehicles.isEmpty()) {
            out.println("No vehicles found.");
            return;
        }

        for (VehicleEntity v : vehicles) {
            if (v instanceof CarEntity car) {
                out.printf("ID: %d | Type: Car        | Make: %s | Model: %s | Year: %d | Doors: %d%n",
                        car.getId(), car.getMake(), car.getModel(), car.getYear(), car.getNumDoors());
            } else if (v instanceof MotorcycleEntity m) {
                out.printf("ID: %d | Type: Motorcycle | Make: %s | Model: %s | Year: %d | Sidecar: %b%n",
                        m.getId(), m.getMake(), m.getModel(), m.getYear(), m.isHasSidecar());
            }
        }
        out.println("------------------------------");
    }

    public void addVehicle() {
        out.println("\n--- Add New Vehicle ---");
        out.println("1. Car");
        out.println("2. Motorcycle");
        out.print("Enter choice: ");
        String choiceStr = input.nextLine();
        int choice;

        try {
            choice = Integer.parseInt(choiceStr);
        } catch (NumberFormatException e) {
            out.println("Invalid choice. Please enter a number.");
            return;
        }

        try {
            switch (choice) {
                case 1:
                    addCar();
                    break;
                case 2:
                    addMotorcycle();
                    break;
                default:
                    out.println("Invalid choice. Returning to main menu.");
            }
        } catch (Exception e) {
            out.println("An error occurred while adding the item: " + e.getMessage());
            out.println("Please try again.");
        }
    }

    private void addCar() {
        out.println("\n--- Add New Car ---");
        out.print("Enter make: ");
        String make = input.nextLine();

        out.print("Enter model: ");
        String model = input.nextLine();

        int year = 0;
        while (true) {
            try {
                out.print("Enter year: ");
                year = Integer.parseInt(input.nextLine());
                break;
            } catch (NumberFormatException e) {
                out.println("Invalid year. Please enter a number.");
            }
        }

        int numDoors = 0;
        while (true) {
            try {
                out.print("Enter number of doors: ");
                numDoors = Integer.parseInt(input.nextLine());
                break;
            } catch (NumberFormatException e) {
                out.println("Invalid input. Please enter a number.");
            }
        }

        VehicleEntity newCar = new CarEntity(make, model, year, numDoors);
        VehicleEntity savedCar = vehicleService.saveVehicle(newCar);
        out.println("Car added successfully with ID: " + savedCar.getId());
    }

    private void addMotorcycle() {
        out.println("\n--- Add New Motorcycle ---");
        out.print("Enter make: ");
        String make = input.nextLine();

        out.print("Enter model: ");
        String model = input.nextLine();

        int year = 0;
        while (true) {
            try {
                out.print("Enter year: ");
                year = Integer.parseInt(input.nextLine());
                break;
            } catch (NumberFormatException e) {
                out.println("Invalid year. Please enter a number.");
            }
        }

        out.print("Does it have a sidecar? (yes/no): ");
        boolean hasSidecar = input.nextLine().equalsIgnoreCase("yes");

        VehicleEntity newMotorcycle = new MotorcycleEntity(make, model, year, hasSidecar);
        VehicleEntity savedMotorcycle = vehicleService.saveVehicle(newMotorcycle);
        out.println("Motorcycle added successfully with ID: " + savedMotorcycle.getId());
    }

}