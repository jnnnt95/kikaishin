package com.nniett.kikaishin.web.service.construction;

import com.nniett.kikaishin.common.ChildrenPersistable;
import com.nniett.kikaishin.web.persistence.entity.construction.ActivateableMutableEntity;
import com.nniett.kikaishin.web.service.pojo.Pojo;
import com.nniett.kikaishin.web.service.pojo.dto.ActivateableUpdateDto;
import lombok.Getter;
import org.springframework.data.repository.ListCrudRepository;

@Getter
public abstract class ActivateableWithChildrenService
        <
                ENTITY extends ActivateableMutableEntity,
                PK,
                POJO extends Pojo<PK>,
                CREATE_DTO,
                UPDATE_DTO extends ActivateableUpdateDto<PK>,
                CHILD_ENTITY,
                CHILD_PK,
                CHILD_POJO,
                CHILD_CREATE_DTO
                >
        extends WithChildrenService
        <
                        ENTITY,
                        PK,
                        POJO,
                        CREATE_DTO,
                        UPDATE_DTO,
                        CHILD_ENTITY,
                        CHILD_PK,
                        CHILD_POJO,
                        CHILD_CREATE_DTO
                        >
        implements
        ChildrenPersistable<CHILD_POJO, CHILD_CREATE_DTO>,
        ActivateActionableService<ENTITY, PK, UPDATE_DTO>
{

    public ActivateableWithChildrenService(
            ListCrudRepository<ENTITY, PK> repository,
            CreateService<ENTITY, PK, POJO, CREATE_DTO> createService,
            ReadService<ENTITY, UPDATE_DTO, PK, POJO> readService,
            UpdateService<ENTITY, PK, UPDATE_DTO, POJO> updateService,
            DeleteService<ENTITY, PK> deleteService,
            CreateService<CHILD_ENTITY, CHILD_PK, CHILD_POJO, CHILD_CREATE_DTO> childService) {
        super(repository, createService, readService, updateService, deleteService, childService);
    }

    @Override
    public CHILD_POJO createChild(CHILD_CREATE_DTO dto) {
        return getChildService().createFromDto(dto);
    }

    @Override
    public abstract void populateAsDefaultPojoForCreation(POJO pojo);

    @Override
    public abstract void populateEntityForUpdate(ENTITY entity, POJO pojo);
}
