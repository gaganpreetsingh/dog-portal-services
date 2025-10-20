package org.uk.dog.portal.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.uk.dog.portal.bean.DogBean;
import org.uk.dog.portal.bean.DogSupplierBean;
import org.uk.dog.portal.service.DogService;

@SpringBootTest
@AutoConfigureMockMvc
public class DogControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DogService dogService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testCreateDog() throws Exception {
        DogBean dogBean = new DogBean();
        dogBean.setName("Buddy");
        dogBean.setBreed("Golden Retriever");

        DogSupplierBean dogSupplierBean = new DogSupplierBean();
        dogSupplierBean.setName("Top Dog Breeders");
        dogBean.setDogSupplier(dogSupplierBean);

        String dogJson = objectMapper.writeValueAsString(dogBean);
        mockMvc.perform(post("/api/dog")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dogJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Buddy"))
                .andExpect(jsonPath("$.data.breed").value("Golden Retriever"))
                .andExpect(jsonPath("$.data.dogSupplier.name").value("Top Dog Breeders"));
    }

    @Test
    void testCreateDog_throws400() throws Exception {
        DogBean dogBean = new DogBean();
        dogBean.setName("Buddy");
        dogBean.setBreed("Golden Retriever");

        String dogJson = objectMapper.writeValueAsString(dogBean);
        mockMvc.perform(post("/api/dog")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dogJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateDog() throws Exception {
        DogBean dogBean = new DogBean();
        dogBean.setName("Max");
        dogBean.setBreed("Poodle");

        DogSupplierBean dogSupplierBean = new DogSupplierBean();
        dogSupplierBean.setName("West Midlands Breeder");
        dogBean.setDogSupplier(dogSupplierBean);
        DogBean savedDog = dogService.getSaveDogFunction().apply(dogBean);
        Long dogId = savedDog.getId();

        DogBean updatedDogBean = new DogBean();
        updatedDogBean.setName("Bella");
        updatedDogBean.setBreed("Poodle");
        updatedDogBean.setDogSupplier(dogSupplierBean);

        String updatedDogJson = objectMapper.writeValueAsString(updatedDogBean);

        mockMvc.perform(put("/api/dog/{dogId}", dogId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedDogJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Bella"))
                .andExpect(jsonPath("$.data.breed").value("Poodle"));
    }

    @Test
    void testGetDog() throws Exception {
        DogBean dogBean = new DogBean();
        dogBean.setName("Charlie");
        dogBean.setBreed("Labrador");
        DogSupplierBean dogSupplierBean = new DogSupplierBean();
        dogSupplierBean.setName("West Midlands Breeder");
        dogBean.setDogSupplier(dogSupplierBean);

        DogBean savedDog = dogService.getSaveDogFunction().apply(dogBean);
        Long dogId = savedDog.getId();

        mockMvc.perform(get("/api/dog/{dogId}", dogId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Charlie"))
                .andExpect(jsonPath("$.data.breed").value("Labrador"));
    }

    @Test
    void testDeleteDog() throws Exception {
        DogBean dogBean = new DogBean();
        dogBean.setName("Rocky");
        dogBean.setBreed("Boxer");
        DogSupplierBean dogSupplierBean = new DogSupplierBean();
        dogSupplierBean.setName("West Midlands Breeder");
        dogBean.setDogSupplier(dogSupplierBean);

        DogBean savedDog = dogService.getSaveDogFunction().apply(dogBean);
        Long dogId = savedDog.getId();

        mockMvc.perform(delete("/api/dog/{dogId}", dogId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Rocky"));

        mockMvc.perform(get("/api/dog/{dogId}", dogId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetPaginatedDogs() throws Exception {
        DogBean dog1 = new DogBean();
        dog1.setName("Buddy");
        dog1.setBreed("Poodle");

        DogSupplierBean dogSupplierBean = new DogSupplierBean();
        dogSupplierBean.setName("West Midlands Breeder");
        dog1.setDogSupplier(dogSupplierBean);
        dogService.getSaveDogFunction().apply(dog1);

        DogBean dog2 = new DogBean();
        dog2.setName("Max");
        dog2.setBreed("Poodle");
        dog2.setDogSupplier(dogSupplierBean);
        dogService.getSaveDogFunction().apply(dog2);

        DogBean dog3 = new DogBean();
        dog3.setName("Alex");
        dog3.setBreed("Bing");

        DogSupplierBean dogSupplierBean2 = new DogSupplierBean();
        dogSupplierBean.setName("Husky Musky");
        dog3.setDogSupplier(dogSupplierBean2);
        dogService.getSaveDogFunction().apply(dog3);

        mockMvc.perform(get("/api/dogs")
                        .param("page", "0")
                        .param("size", "3")
                        .param("breed", "Poodle")
                        .param("supplierName", "West Midlands Breeder"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()").value(2))
                .andExpect(jsonPath("$.data[0].name").value("Buddy"))
                .andExpect(jsonPath("$.data[1].name").value("Max"));
    }

    // 6. Test GET /api/dogs with Filter (Name)
    @Test
    void testGetPaginatedDogsByName() throws Exception {
        DogBean dog1 = new DogBean();
        dog1.setName("Buddy");
        dog1.setBreed("Poodle");

        DogSupplierBean dogSupplierBean = new DogSupplierBean();
        dogSupplierBean.setName("West Midlands Breeder");
        dog1.setDogSupplier(dogSupplierBean);
        dogService.getSaveDogFunction().apply(dog1);

        DogBean dog2 = new DogBean();
        dog2.setName("Max");
        dog2.setBreed("Poodle");
        dog2.setDogSupplier(dogSupplierBean);
        dogService.getSaveDogFunction().apply(dog2);

        DogBean dog3 = new DogBean();
        dog3.setName("Alex");
        dog3.setBreed("Bing");

        DogSupplierBean dogSupplierBean2 = new DogSupplierBean();
        dogSupplierBean.setName("Husky Musky");
        dog3.setDogSupplier(dogSupplierBean2);
        dogService.getSaveDogFunction().apply(dog3);

        mockMvc.perform(get("/api/dogs")
                        .param("page", "0")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()").value(2))
                .andExpect(jsonPath("$.data[0].name").value("Buddy"))
                .andExpect(jsonPath("$.data[1].name").value("Max"));
    }
}

