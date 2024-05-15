package com.nniett.kikaishin.app.service.construction;

import com.nniett.kikaishin.app.service.mapper.EntityPojoMapper;
import com.nniett.kikaishin.app.service.mapper.dto.DtoPojoMapper;
import com.nniett.kikaishin.app.service.pojo.Pojo;
import lombok.Getter;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Getter
public abstract class UpdateService
        <
                ENTITY,
                PK,
                UPDATE_DTO,
                POJO extends Pojo<PK>
                >
        implements CanUpdate
        <
                ENTITY,
                UPDATE_DTO,
                POJO
                > {

    private final ListCrudRepository<ENTITY, PK> repository;
    private final EntityPojoMapper<ENTITY, POJO> entityPojoMapper;
    private final DtoPojoMapper<UPDATE_DTO, POJO> updateMapper;

    public UpdateService(
            ListCrudRepository<ENTITY, PK> repository,
            EntityPojoMapper<ENTITY, POJO> entityPojoMapper,
            DtoPojoMapper<UPDATE_DTO, POJO> updateMapper
    ) {
        this.repository = repository;
        this.entityPojoMapper = entityPojoMapper;
        this.updateMapper = updateMapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public POJO update(POJO pojo) {
        ENTITY entity = getRepository().findById(pojo.getPK()).orElseThrow();
        populateEntityForUpdate(entity, pojo);
        return entityPojoMapper.toPojo(repository.save(entity));
    }

    @Override
    public abstract void populateEntityForUpdate(ENTITY entity, POJO pojo);

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public POJO updateFromDto(UPDATE_DTO dto) {
        return update(updateMapper.toPojo(dto));
    }

}
