package com.test.repository;


import com.test.entities.VehicleEntity;

import java.util.*;

/**
 * An in-memory implementation of the Repository interface for ProductEntity.
 * This class uses a HashMap to store data and is primarily intended for
 * testing and development purposes, allowing the business logic to be tested
 * without a live database connection.
 */
public class InMemoryVehicleRepository implements Repository<VehicleEntity> {

    private final Map<Long, VehicleEntity> vehicles = new HashMap<>();
    private Long nextId = 1L;


    @Override
    public Optional<VehicleEntity> findById(Long id) {
       return Optional.ofNullable(vehicles.get(id));
    }

    @Override
    public List<VehicleEntity> findAll() {
        return new ArrayList<>(vehicles.values());
    }


    @Override
    public VehicleEntity save(VehicleEntity entity) {
        if (entity.getId() == null || entity.getId() == 0L) {
            entity.setId(nextId++);
        }
        vehicles.put(entity.getId(), entity);
        return entity;
    }


    @Override
    public void deleteById(Long id) {
        vehicles.remove(id);
    }


    public void clear() {
        vehicles.clear();
        nextId = 1L;
    }
}