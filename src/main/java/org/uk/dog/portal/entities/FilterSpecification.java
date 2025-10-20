package org.uk.dog.portal.entities;

import org.springframework.data.jpa.domain.Specification;

public class FilterSpecification {
    public static Specification<Dog> hasName(String name) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("name"), name);
    }

    public static Specification<Dog> hasBreed(String breed) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("breed"), breed);
    }

    public static Specification<Dog> isDeleted(Boolean isDeleted) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("isDeleted"), isDeleted);
    }

    public static Specification<Dog> hasSupplierId(Long supplierId) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("dogSupplier").get("id"), supplierId);
    }

    public static Specification<Dog> filterByAll( String name, String breed,Long supplierId, Boolean isDeleted) {
        return Specification.<Dog>unrestricted().and(name != null ? hasName(name) : null)
                .and(breed != null ? hasBreed(breed) : null)
                .and(supplierId != null ? hasSupplierId(supplierId) : null)
                .and(isDeleted != null ? isDeleted(isDeleted) : null);
    }
}
