package org.uk.dog.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.uk.dog.portal.entities.DogSupplier;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<DogSupplier, Long> {

    Optional<DogSupplier> findByName(String name);
}