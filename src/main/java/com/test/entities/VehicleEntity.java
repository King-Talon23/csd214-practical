package com.test.entities;

import jakarta.persistence.*;


@Entity
@Table(name = "vehicles")
@Inheritance(strategy = InheritanceType.JOINED) // Each subclass will have its own table
public abstract class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private int year;

    // Constructors
    public VehicleEntity() {}

    public VehicleEntity(String make, String model, int year) {
        this.make = make;
        this.model = model;
        this.year = year;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
}
