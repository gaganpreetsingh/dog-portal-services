package org.uk.dog.portal.mapper;

import org.junit.jupiter.api.Test;
import org.uk.dog.portal.bean.DogBean;
import org.uk.dog.portal.bean.DogSupplierBean;
import org.uk.dog.portal.entities.Dog;
import org.uk.dog.portal.entities.DogSupplier;

import static org.junit.jupiter.api.Assertions.*;

class DogMapperTest {

    private final DogMapper dogMapper = DogMapper.INSTANCE;

    @Test
    void testToBean() {
        // Create sample Dog entity
        DogSupplier dogSupplier = new DogSupplier();
        dogSupplier.setId(1L);
        dogSupplier.setName("Trusted Supplier");

        Dog dog = new Dog();
        dog.setId(1L);
        dog.setName("Buddy");
        dog.setBreed("Golden Retriever");
        dog.setDogSupplier(dogSupplier);

        DogBean dogBean = dogMapper.toBean(dog);

        // Verify the mapping
        assertNotNull(dogBean);
        assertEquals(dog.getId(), dogBean.getId());
        assertEquals(dog.getName(), dogBean.getName());
        assertEquals(dog.getBreed(), dogBean.getBreed());
        assertNotNull(dogBean.getDogSupplier());
        assertEquals(dogSupplier.getId(), dogBean.getDogSupplier().getId());
        assertEquals(dogSupplier.getName(), dogBean.getDogSupplier().getName());
    }

    @Test
    void testToEntity() {
        DogSupplierBean dogSupplierBean = new DogSupplierBean();
        dogSupplierBean.setId(1L);
        dogSupplierBean.setName("Trusted Supplier");

        DogBean dogBean = new DogBean();
        dogBean.setId(1L);
        dogBean.setName("Max");
        dogBean.setBreed("Beagle");
        dogBean.setDogSupplier(dogSupplierBean);

        // Map bean to entity
        Dog dogEntity = dogMapper.toEntity(dogBean);

        // Verify the mapping
        assertNotNull(dogEntity);
        assertEquals(dogBean.getId(), dogEntity.getId());
        assertEquals(dogBean.getName(), dogEntity.getName());
        assertEquals(dogBean.getBreed(), dogEntity.getBreed());
        assertNotNull(dogEntity.getDogSupplier());
        assertEquals(dogSupplierBean.getId(), dogEntity.getDogSupplier().getId());
        assertEquals(dogSupplierBean.getName(), dogEntity.getDogSupplier().getName());
    }
}