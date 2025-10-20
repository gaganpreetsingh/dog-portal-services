package org.uk.dog.portal.service;

import io.micrometer.common.util.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.uk.dog.portal.bean.DogBean;
import org.uk.dog.portal.bean.DogSupplierBean;
import org.uk.dog.portal.entities.Dog;
import org.uk.dog.portal.entities.DogSupplier;
import org.uk.dog.portal.entities.FilterSpecification;
import org.uk.dog.portal.excepton.DogNotFoundException;
import org.uk.dog.portal.function.PentFunction;
import org.uk.dog.portal.mapper.DogMapper;
import org.uk.dog.portal.mapper.SupplierMapper;
import org.uk.dog.portal.repository.DogRepository;
import org.uk.dog.portal.repository.SupplierRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class DogService {

    private DogRepository dogRepository;

    public DogService(DogRepository dogRepository, SupplierRepository supplierRepository) {
        this.dogRepository = dogRepository;
        this.supplierRepository = supplierRepository;
    }

    private SupplierRepository supplierRepository;

    public Function<String, Long> getSupplierName = supplierName -> this.supplierRepository.findByName(supplierName)
            .orElseThrow(() -> new DogNotFoundException("Invalid Supplier Passed")).getId();

    // Method to resolve an existing supplier or create a new one
    private final Function<DogSupplierBean, DogSupplierBean> resolveOrCreateSupplier = dogSupplierBean -> {
        return supplierRepository.findByName(dogSupplierBean.getName())
                .map(SupplierMapper.INSTANCE::toBean) // If supplier exists, map to a bean
                .orElseGet(() -> { // Otherwise, create/save a new supplier and map it to a bean
                    DogSupplier newSupplierEntity = SupplierMapper.INSTANCE.toEntity(dogSupplierBean);
                    return SupplierMapper.INSTANCE.toBean(supplierRepository.save(newSupplierEntity));
                });
    };

    public Function<DogBean, DogBean> saveDogFunction = dogBean -> {
        // Resolve or create the dog supplier and set it in the DogBean
        DogSupplierBean resolvedSupplier = resolveOrCreateSupplier.apply(dogBean.getDogSupplier());
        dogBean.setDogSupplier(resolvedSupplier);

        // Convert DogBean to Dog entity and save it using the repository
        Dog savedDogEntity = dogRepository.save(DogMapper.INSTANCE.toEntity(dogBean));

        // Convert the saved Dog entity back to a DogBean and return
        return DogMapper.INSTANCE.toBean(savedDogEntity);
    };

    public BiFunction<Long, DogBean, DogBean> updateDogFunction = (dogId, dogBean) -> {
        Optional<Dog> dogOptional = this.dogRepository.findById(dogId);
        if (dogOptional.isPresent()) {
            dogBean.setId(dogId);
            dogBean.setDogSupplier(resolveOrCreateSupplier.apply(dogBean.getDogSupplier()));
            Dog dogEntity = this.dogRepository.save(DogMapper.INSTANCE.toEntity(dogBean));
            return DogMapper.INSTANCE.toBean(dogEntity);
        }
        throw new DogNotFoundException("Dog with id " + dogId + " not found");
    };

    public Function<Long, DogBean> fetchDogFunction = (dogId) -> {
        Optional<Dog> dogOptional = this.dogRepository.findByIdAndIsDeleted(dogId, false);
        if (dogOptional.isPresent()) {
            return DogMapper.INSTANCE.toBean(dogOptional.get());
        }
        throw new DogNotFoundException("Dog with id " + dogId + " not found");
    };

    public Supplier<List<DogBean>> fetchAllDogsFunction = () -> {
        List<Dog> dogList = this.dogRepository.findAll();
        return dogList.stream().map(DogMapper.INSTANCE::toBean)
                .collect(Collectors.toList());
    };

    public Function<Long, DogBean> deleteDogFunction = (dogId) -> {
        Optional<Dog> dogOptional = this.dogRepository.findById(dogId);
        if (dogOptional.isPresent()) {
            dogOptional.get().setDeleted(true);
            this.dogRepository.save(dogOptional.get());
            return DogMapper.INSTANCE.toBean(dogOptional.get());
        }
        throw new DogNotFoundException("Dog with id " + dogId + " not found");
    };

    public PentFunction<String, String, String, Integer, Integer, List<DogBean>> fetchDogsByPageFunction =
            (name, breed, supplier, pageNumber, pageSize) -> {

                PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

                Long supplierId = StringUtils.isNotBlank(supplier) ? getGetSupplierName().apply(supplier) : null;
                Specification<Dog> spec = FilterSpecification.filterByAll(name, breed, supplierId, false);

                return this.dogRepository.findAll(spec, pageRequest)
                        .getContent()
                        .stream()
                        .map(DogMapper.INSTANCE::toBean)
                        .collect(Collectors.toList());
            };

    public Function<DogBean, DogBean> getSaveDogFunction() {
        return saveDogFunction;
    }

    public BiFunction<Long, DogBean, DogBean> getUpdateDogFunction() {
        return updateDogFunction;
    }

    public Function<Long, DogBean> getFetchDogFunction() {
        return fetchDogFunction;
    }

    public Supplier<List<DogBean>> getFetchAllDogsFunction() {
        return fetchAllDogsFunction;
    }

    public Function<Long, DogBean> getDeleteDogFunction() {
        return deleteDogFunction;
    }

    public PentFunction<String, String, String, Integer, Integer, List<DogBean>> getFetchDogsByPageFunction() {
        return fetchDogsByPageFunction;
    }

    public Function<String, Long> getGetSupplierName() {
        return getSupplierName;
    }
}