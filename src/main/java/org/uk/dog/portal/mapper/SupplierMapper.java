package org.uk.dog.portal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.uk.dog.portal.bean.DogBean;
import org.uk.dog.portal.bean.DogSupplierBean;
import org.uk.dog.portal.entities.Dog;
import org.uk.dog.portal.entities.DogSupplier;

@Mapper
public interface SupplierMapper {

    SupplierMapper INSTANCE = Mappers.getMapper(SupplierMapper.class);

    DogSupplierBean toBean(DogSupplier dog);

    DogSupplier toEntity(DogSupplierBean dogBean);

}