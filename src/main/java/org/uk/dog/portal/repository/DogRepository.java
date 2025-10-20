package org.uk.dog.portal.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.uk.dog.portal.entities.Dog;

import java.net.ContentHandler;
import java.util.Optional;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long>, JpaSpecificationExecutor<Dog> {

    Page<Dog> findByIsDeleted(Boolean isDeleted, Pageable pageable);
    Optional<Dog> findByIdAndIsDeleted(Long id, Boolean isDeleted);

}