package com.test.entities;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "cars")
public class CarEntity extends VehicleEntity {

    @Column(nullable = false)
    private int numDoors;


    public CarEntity() {}

    public CarEntity(String make, String model, int year, int numDoors) {
        super(make, model, year);
        this.numDoors = numDoors;
    }

    @Override
    public void edit(Scanner input) {
        super.edit(input);

        System.out.print("Enter new number of doors (" + numDoors + "): ");
        String doorsStr = input.nextLine();
        if (!doorsStr.isBlank()) {
            try {
                this.numDoors = Integer.parseInt(doorsStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Keeping existing value.");
            }
        }
    }

    public int getNumDoors() { return numDoors; }
    public void setNumDoors(int numDoors) { this.numDoors = numDoors; }
}
