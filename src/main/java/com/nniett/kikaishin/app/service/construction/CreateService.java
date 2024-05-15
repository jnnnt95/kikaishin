package com.nniett.kikaishin.app.service.construction;

import com.nniett.kikaishin.app.service.mapper.EntityPojoMapper;
import com.nniett.kikaishin.app.service.mapper.dto.DtoPojoMapper;
import com.nniett.kikaishin.app.service.pojo.common.Activateable;
import com.nniett.kikaishin.app.service.pojo.dto.CreationDto;
import lombok.Getter;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Getter
public abstract class CreateService
        <
                ENTITY,
                PK,
                POJO,
                CREATE_DTO extends CreationDto<PK>
                >
        implements CanCreate
        <
                POJO,
                CREATE_DTO,
                ENTITY
                >
{

    private final EntityPojoMapper<ENTITY, POJO> entityPojoMapper;
    private final ListCrudRepository<ENTITY, PK> repository;
    private final DtoPojoMapper<CREATE_DTO, POJO> createMapper;

    public CreateService(
            ListCrudRepository<ENTITY, PK> repository,
            EntityPojoMapper<ENTITY, POJO> entityPojoMapper,
            DtoPojoMapper<CREATE_DTO, POJO> createMapper
    ) {
        this.entityPojoMapper = entityPojoMapper;
        this.repository = repository;
        this.createMapper = createMapper;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public POJO create(POJO pojo) {
        if(pojo instanceof Activateable) {
            ((Activateable) pojo).initForCreate();
        }
        ENTITY entity = entityPojoMapper.toEntity(pojo);
        populateAsDefaultForCreation(entity);
        entity = repository.save(entity);
        pojo = entityPojoMapper.toPojo(entity);
        return pojo;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public POJO createFromDto(CREATE_DTO dto) {
        return create(createMapper.toPojo(dto));
    }

    @Override
    public abstract void populateAsDefaultForCreation(ENTITY pojo);
}
