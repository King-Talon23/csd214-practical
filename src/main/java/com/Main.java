package com;

import com.test.repository.*;
import com.test.services.VehicleService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose data source: 1 for In-Memory, 2 for MySQL");
        int choice = scanner.nextInt();
        scanner.nextLine();

        Repository repository;
        if (choice == 1) {
            repository = new InMemoryVehicleRepository();
        } else if (choice == 2) {
            repository = new MySQLVehicleRepository();
        } else {
            System.out.println("Invalid choice. Defaulting to In-Memory repository.");
            repository = new InMemoryVehicleRepository();
        }

        VehicleService vehicleService = new VehicleService(repository);

        App app = new App(System.in, System.out, vehicleService);
        app.run();

        scanner.close();
    }
}
