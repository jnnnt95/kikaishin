package com.nniett.kikaishin.app.service.construction;

import com.nniett.kikaishin.app.service.pojo.Pojo;
import com.nniett.kikaishin.app.service.pojo.dto.CreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.UpdateDto;
import lombok.Getter;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Getter
public abstract class Service
        <
                CREATE_DTO extends CreationDto<PK>,
                UPDATE_DTO extends UpdateDto<PK>,
                POJO extends Pojo<PK>,
                ENTITY,
                PK>
        extends ImmutableService
        <
                CREATE_DTO,
                POJO,
                ENTITY,
                PK
                >
        implements CanUpdate
        <
                ENTITY,
                UPDATE_DTO,
                POJO
                >

{

    private final UpdateService<ENTITY, PK, UPDATE_DTO, POJO> updateService;

    public Service(
            ListCrudRepository<ENTITY, PK> repository,
            CreateService<ENTITY, PK, POJO, CREATE_DTO> createService,
            ReadService<ENTITY, PK, POJO> readService,
            UpdateService<ENTITY, PK, UPDATE_DTO, POJO> updateService,
            DeleteService<ENTITY, PK> deleteService
    ) {
        super(repository, createService,readService, deleteService);
        this.updateService = updateService;
    }

    @Override
    public POJO create(POJO pojo) {
        ENTITY entity = getCreateService().getEntityPojoMapper().toEntity(pojo);
        return getCreateService().getEntityPojoMapper().toPojo(getRepository().save(entity));
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public POJO createFromDto(CREATE_DTO createDto) {
        return getCreateService().createFromDto(createDto);
    }

    @Override
    public abstract void populateAsDefaultForCreation(ENTITY entity);

    @Override
    public boolean delete(PK id) {
        return getDeleteService().delete(id);
    }

    @Override
    public boolean exists(PK id) {
        return getReadService().exists(id);
    }

    @Override
    public POJO readPojo(PK id) {
        return getReadService().readPojo(id);
    }

    @Override
    public List<POJO> readAll() {
        return getReadService().readAll();
    }

    @Override
    public ENTITY readEntity(PK id) {
        return getReadService().readEntity(id);
    }


    public ENTITY findEntityByDto(UPDATE_DTO updateDto) {
        return readEntity(updateDto.getPK());
    }

    @Override
    public POJO update(POJO pojo) {
        return updateService.update(pojo);
    }

    @Override
    public abstract void populateEntityForUpdate(ENTITY entity, POJO pojo);

    @Override
    public POJO updateFromDto(UPDATE_DTO updateDto) {
        return updateService.updateFromDto(updateDto);
    }

}
