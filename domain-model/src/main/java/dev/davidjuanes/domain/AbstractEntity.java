package dev.davidjuanes.domain;

import lombok.AccessLevel;
import lombok.Getter;
import org.modelmapper.ModelMapper;

public abstract class AbstractEntity<ENTITY extends AbstractEntity, DTO  extends AbstractDto> {
    @Getter(value = AccessLevel.PUBLIC) //For unit tests logging
    protected ModelMapper modelMapper = new ModelMapper();

    private final Class<ENTITY> entityClass;
    private final Class<DTO> dtoClass;


    protected AbstractEntity(Class<ENTITY> entityClass, Class<DTO> dtoClass) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    public DTO mapToDto(ENTITY entity) {
        return modelMapper.map(entity, dtoClass);
    }

    public ENTITY mapToEntity(DTO dto) {
        return modelMapper.map(dto, entityClass);
    }

}
