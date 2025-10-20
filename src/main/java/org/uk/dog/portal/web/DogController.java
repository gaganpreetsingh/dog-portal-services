package org.uk.dog.portal.web;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.uk.dog.portal.bean.DogBean;
import org.uk.dog.portal.bean.ResponseBean;
import org.uk.dog.portal.service.DogService;

import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class DogController {

    private static final Logger log = LoggerFactory.getLogger(DogController.class);
    private final DogService dogService;

    public DogController(DogService dogService) {
        this.dogService = dogService;
    }

    @PostMapping("/dog")
    public ResponseBean<DogBean> createDog(@Valid @RequestBody DogBean dogBean) {
        log.info("Creating dog with details: {}", dogBean);
        DogBean savedDog = dogService.getSaveDogFunction().apply(dogBean);
        ResponseBean<DogBean> response = new ResponseBean<>();
        response.setData(savedDog);
        response.setMessage("Dog created successfully");
        return response;
    }

    @PutMapping("/dog/{dogId}")
    public ResponseBean<DogBean> updateDog(@PathVariable(name = "dogId") Long dogId, @Valid @RequestBody DogBean dogBean) {
        log.info("Updating dog with id: {} and details: {}", dogId, dogBean);
        DogBean updatedDog = dogService.getUpdateDogFunction().apply(dogId, dogBean);
        ResponseBean<DogBean> response = new ResponseBean<>();
        response.setData(updatedDog);
        response.setMessage("Dog updated successfully");
        return response;
    }

    @GetMapping("/dog/{dogId}")
    public ResponseBean<DogBean> getDog(@PathVariable(name = "dogId") Long dogId) {
        log.info("Fetching dog with id: {}", dogId);
        DogBean dogBean = dogService.getFetchDogFunction().apply(dogId);
        ResponseBean<DogBean> response = new ResponseBean<>();
        response.setData(dogBean);
        response.setMessage("Dog fetched successfully");
        return response;
    }

    @DeleteMapping("/dog/{dogId}")
    public ResponseBean<DogBean> deleteDog(@PathVariable(name = "dogId") Long dogId) {
        log.info("Deleting dog with id: {}", dogId);
        DogBean dogBean = dogService.getDeleteDogFunction().apply(dogId);
        ResponseBean<DogBean> response = new ResponseBean<>();
        response.setData(dogBean);
        response.setMessage("Dog deleted successfully");
        return response;
    }

    @GetMapping("/dogs")
    public ResponseBean<List<DogBean>> getPaginated(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "breed", required = false) String breed,
            @RequestParam(name = "supplierName", required = false) String supplierName,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Fetching paginated dogs: page {}, size {}", page, size);
        List<DogBean> dogBeans = dogService.getFetchDogsByPageFunction().apply(name, breed, supplierName, page, size);
        ResponseBean<List<DogBean>> response = new ResponseBean<>();
        response.setData(dogBeans);
        response.setMessage("Paginated dogs fetched successfully");
        return response;
    }
}