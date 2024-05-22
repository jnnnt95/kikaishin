package com.nniett.kikaishin.app.service.construction;

import com.nniett.kikaishin.app.service.mapper.EntityPojoMapper;
import com.nniett.kikaishin.app.service.mapper.dto.DtoPojoMapper;
import com.nniett.kikaishin.app.service.dto.common.Activateable;
import com.nniett.kikaishin.app.service.dto.write.CreationDto;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(CreateService.class);

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
        logger.debug("Creating new object.");
        if(pojo instanceof Activateable) {
            ((Activateable) pojo).initForCreate();
        }
        ENTITY entity = entityPojoMapper.toEntity(pojo);
        populateAsDefaultForCreation(entity);
        logger.debug("Saving new object.");
        entity = repository.save(entity);
        pojo = entityPojoMapper.toPojo(entity);
        return pojo;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public POJO createFromDto(CREATE_DTO dto) {
        logger.debug("Creating new object from dto.");
        return create(createMapper.toPojo(dto));
    }

    @Override
    public abstract void populateAsDefaultForCreation(ENTITY pojo);
}
