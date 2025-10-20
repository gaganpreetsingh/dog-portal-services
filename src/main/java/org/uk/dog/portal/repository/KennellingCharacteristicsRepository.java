package org.uk.dog.portal.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uk.dog.portal.entities.KennellingCharacteristics;

@Repository
public interface KennellingCharacteristicsRepository extends JpaRepository<KennellingCharacteristics, Long> {
}