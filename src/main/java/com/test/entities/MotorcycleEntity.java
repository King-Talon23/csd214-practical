package com.test.entities;

import java.util.*;
import jakarta.persistence.*;
@Entity
@Table(name = "motorcycles")
public class MotorcycleEntity extends VehicleEntity {

    @Column(nullable = false)
    private boolean hasSidecar;


    public MotorcycleEntity() {}

    public MotorcycleEntity(String make, String model, int year, boolean hasSidecar) {
        super(make, model, year);
        this.hasSidecar = hasSidecar;
    }

    @Override
    public void edit(Scanner input) {
        super.edit(input);

        System.out.print("Has sidecar? (" + (hasSidecar ? "yes" : "no") + "): ");
        String sidecarStr = input.nextLine();
        if (!sidecarStr.isBlank()) {
            this.hasSidecar = sidecarStr.equalsIgnoreCase("yes");
        }
    }


    public boolean isHasSidecar() { return hasSidecar; }
    public void setHasSidecar(boolean hasSidecar) { this.hasSidecar = hasSidecar; }
}
