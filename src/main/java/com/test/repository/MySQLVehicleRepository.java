package com.test.repository;

import com.test.entities.VehicleEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


import java.util.List;
import java.util.Optional;



public class MySQLVehicleRepository implements Repository<VehicleEntity> {


    private final EntityManagerFactory emf;



    public MySQLVehicleRepository() {
        // The name "default" must match the persistence-unit name for MySQL in persistence.xml
        this.emf = Persistence.createEntityManagerFactory("default");
    }


    @Override
    public Optional<VehicleEntity> findById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            VehicleEntity entity = em.find(VehicleEntity.class, id);
            return Optional.ofNullable(entity);
        }
    }


    @Override
    public List<VehicleEntity> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT p FROM ProductEntity p", VehicleEntity.class)
                    .getResultList();
        }
    }


    @Override
    public VehicleEntity save(VehicleEntity entity) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            VehicleEntity savedEntity = em.merge(entity);
            em.getTransaction().commit();
            return savedEntity;
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }


    @Override
    public void deleteById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            VehicleEntity entityToRemove = em.find(VehicleEntity.class, id);
            if (entityToRemove != null) {
                em.remove(entityToRemove);
            }
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }


    @Override
    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
