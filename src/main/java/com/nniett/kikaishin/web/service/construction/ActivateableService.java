package com.nniett.kikaishin.web.service.construction;

import com.nniett.kikaishin.web.persistence.entity.construction.ActivateableMutableEntity;
import com.nniett.kikaishin.web.service.pojo.Pojo;
import com.nniett.kikaishin.web.service.pojo.dto.ActivateableUpdateDto;
import org.springframework.data.repository.ListCrudRepository;

public abstract class ActivateableService
        <
                ENTITY extends ActivateableMutableEntity,
                PK,
                POJO extends Pojo<PK>,
                CREATE_DTO,
                UPDATE_DTO extends ActivateableUpdateDto<PK>
                >
        extends Service<CREATE_DTO, UPDATE_DTO, POJO, ENTITY, PK>
        implements ActivateActionableService<ENTITY, PK, UPDATE_DTO>
{
    public ActivateableService(
            ListCrudRepository<ENTITY, PK> repository,
            CreateService<ENTITY, PK, POJO, CREATE_DTO> createService,
            ReadService<ENTITY, UPDATE_DTO, PK, POJO> readService,
            UpdateService<ENTITY, PK, UPDATE_DTO, POJO> updateService,
            DeleteService<ENTITY, PK> deleteService
    ) {
        super(repository, createService, readService, updateService, deleteService);
    }

    //toggle active actions are in ActivateActionableService

    @Override
    public abstract void populateAsDefaultPojoForCreation(POJO pojo);

    @Override
    public abstract void populateEntityForUpdate(ENTITY entity, POJO pojo);

}
