package com.nniett.kikaishin.app.service.construction;

import com.nniett.kikaishin.app.persistence.entity.construction.ActivateableMutableEntity;
import com.nniett.kikaishin.app.service.pojo.Pojo;
import com.nniett.kikaishin.app.service.pojo.dto.ActivateableUpdateDto;
import com.nniett.kikaishin.app.service.pojo.dto.CreationDto;
import org.springframework.data.repository.ListCrudRepository;

public abstract class ActivateableService
        <
                ENTITY extends ActivateableMutableEntity,
                PK,
                POJO extends Pojo<PK>,
                CREATE_DTO extends CreationDto<PK>,
                UPDATE_DTO extends ActivateableUpdateDto<PK>
                >
        extends Service<CREATE_DTO, UPDATE_DTO, POJO, ENTITY, PK>
        implements ActivateActionableService<ENTITY, PK, UPDATE_DTO>
{
    public ActivateableService(
            ListCrudRepository<ENTITY, PK> repository,
            CreateService<ENTITY, PK, POJO, CREATE_DTO> createService,
            ReadService<ENTITY, PK, POJO> readService,
            UpdateService<ENTITY, PK, UPDATE_DTO, POJO> updateService,
            DeleteService<ENTITY, PK> deleteService
    ) {
        super(repository, createService, readService, updateService, deleteService);
    }

    //toggle active actions are in ActivateActionableService

    @Override
    public abstract void populateAsDefaultForCreation(ENTITY entity);

    @Override
    public abstract void populateEntityForUpdate(ENTITY entity, POJO pojo);

}
