package org.uk.dog.portal.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.uk.dog.portal.bean.DogBean;
import org.uk.dog.portal.bean.DogSupplierBean;
import org.uk.dog.portal.entities.Dog;
import org.uk.dog.portal.entities.DogSupplier;
import org.uk.dog.portal.excepton.DogNotFoundException;
import org.uk.dog.portal.repository.DogRepository;
import org.uk.dog.portal.repository.SupplierRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class DogServiceTest {

    @InjectMocks
    private DogService dogService;

    @Mock
    private DogRepository dogRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFetchDogFunction_Success() {
        
        Dog mockDog = new Dog();
        mockDog.setId(1L);
        mockDog.setName("Buddy");
        mockDog.setDeleted(false);

        when(dogRepository.findByIdAndIsDeleted(1L, false)).thenReturn(Optional.of(mockDog));

        
        DogBean result = dogService.getFetchDogFunction().apply(1L);

       
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Buddy");
        verify(dogRepository, times(1)).findByIdAndIsDeleted(1L, false);
    }

    @Test
    void testFetchDogFunction_DogNotFoundException() {
        
        when(dogRepository.findByIdAndIsDeleted(1L, false)).thenReturn(Optional.empty());

        
        assertThatThrownBy(() -> dogService.getFetchDogFunction().apply(1L))
                .isInstanceOf(DogNotFoundException.class)
                .hasMessage("Dog with id 1 not found");
    }

    @Test
    void testSaveDogFunction_Success() {

        DogBean dogBean = new DogBean();
        dogBean.setName("Buddy");

        // Create and set DogSupplierBean on dogBean
        DogSupplierBean dogSupplierBean = new DogSupplierBean();
        dogSupplierBean.setName("Animal Supplies Co");
        dogBean.setDogSupplier(dogSupplierBean);

        DogSupplier mockSupplier = new DogSupplier();
        mockSupplier.setName("Animal Supplies Co");
        when(supplierRepository.findByName("Animal Supplies Co")).thenReturn(Optional.of(mockSupplier));

        Dog mockDog = new Dog();
        mockDog.setName("Buddy");
        mockDog.setDogSupplier(mockSupplier);
        when(dogRepository.save(any())).thenReturn(mockDog);

        DogBean result = dogService.getSaveDogFunction().apply(dogBean);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Buddy");
        verify(supplierRepository, times(1)).findByName("Animal Supplies Co");
        verify(dogRepository, times(1)).save(any(Dog.class));
    }


    @Test
    void testFetchAllDogsFunction_Success() {
        
        Dog mockDog1 = new Dog();
        mockDog1.setId(1L);
        mockDog1.setName("Buddy");

        Dog mockDog2 = new Dog();
        mockDog2.setId(2L);
        mockDog2.setName("Max");

        when(dogRepository.findAll()).thenReturn(List.of(mockDog1, mockDog2));

        
        List<DogBean> result = dogService.getFetchAllDogsFunction().get();

       
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Buddy");
        assertThat(result.get(1).getName()).isEqualTo("Max");
        verify(dogRepository, times(1)).findAll();
    }

    @Test
    void testDeleteDogFunction_Success() {
        
        Dog mockDog = new Dog();
        mockDog.setId(1L);
        mockDog.setName("Buddy");

        when(dogRepository.findById(1L)).thenReturn(Optional.of(mockDog));
        when(dogRepository.save(any(Dog.class))).thenReturn(mockDog);

        
        DogBean result = dogService.getDeleteDogFunction().apply(1L);

       
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Buddy");
        verify(dogRepository, times(1)).findById(1L);
        verify(dogRepository, times(1)).save(mockDog);
        assertThat(mockDog.getDeleted()).isTrue();
    }

    @Test
    void testDeleteDogFunction_DogNotFoundException() {
        
        when(dogRepository.findById(1L)).thenReturn(Optional.empty());

        
        assertThatThrownBy(() -> dogService.getDeleteDogFunction().apply(1L))
                .isInstanceOf(DogNotFoundException.class)
                .hasMessage("Dog with id 1 not found");
    }
}