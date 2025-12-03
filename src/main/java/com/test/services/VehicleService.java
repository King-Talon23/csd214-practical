package com.test.services;

import com.test.entities.*;
import com.test.repository.Repository;


import java.util.List;
import java.util.Optional;



public class VehicleService {

private final Repository<VehicleEntity> vehicleRepository;



    public VehicleService(Repository<VehicleEntity> vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }



    public CarEntity createCar(String make, String model, int year, int doors) {
        CarEntity newCar = new CarEntity(make, model, year, doors);
        return (CarEntity) vehicleRepository.save(newCar);
    }



    public MotorcycleEntity createMotorcycle(String make, String model, int year, boolean hasSideCar) {
        MotorcycleEntity newMotorcycle = new MotorcycleEntity(make, model, year, hasSideCar);
        return (MotorcycleEntity) vehicleRepository.save(newMotorcycle);
    }

    public VehicleEntity saveVehicle(VehicleEntity entity) {
        return vehicleRepository.save(entity);
    }


    public List<VehicleEntity> getAllVehicles() {
        return vehicleRepository.findAll();
    }



    public Optional<VehicleEntity> getVehicleById(Long id) {
        return vehicleRepository.findById(id);
    }

    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }
}