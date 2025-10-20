package org.uk.dog.portal.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.uk.dog.portal.bean.DogBean;
import org.uk.dog.portal.entities.Dog;

@Mapper(uses = SupplierMapper.class)
public interface DogMapper {

    DogMapper INSTANCE = Mappers.getMapper(DogMapper.class);

    @Mapping(source = "dogSupplier" , target = "dogSupplier")
    DogBean toBean(Dog dog);

    @Mapping(source = "dogSupplier" , target = "dogSupplier")
    Dog toEntity(DogBean dogBean);

}