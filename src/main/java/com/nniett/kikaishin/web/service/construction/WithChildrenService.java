package com.nniett.kikaishin.web.service.construction;

import com.nniett.kikaishin.common.ChildrenPersistable;
import com.nniett.kikaishin.web.service.pojo.Pojo;
import com.nniett.kikaishin.web.service.pojo.dto.UpdateDto;
import lombok.Getter;
import org.springframework.data.repository.ListCrudRepository;

@Getter
public abstract class WithChildrenService
        <
                ENTITY,
                PK,
                POJO extends Pojo<PK>,
                CREATE_DTO,
                UPDATE_DTO extends UpdateDto<PK>,
                CHILD_ENTITY,
                CHILD_PK,
                CHILD_POJO,
                CHILD_CREATE_DTO
                >
        extends Service
        <CREATE_DTO, UPDATE_DTO, POJO, ENTITY, PK>
        implements ChildrenPersistable<CHILD_POJO, CHILD_CREATE_DTO>
{

    private final CreateService<CHILD_ENTITY, CHILD_PK, CHILD_POJO, CHILD_CREATE_DTO> childService;

    public WithChildrenService(
            ListCrudRepository<ENTITY, PK> repository,
            CreateService<ENTITY, PK, POJO, CREATE_DTO> createService,
            ReadService<ENTITY, UPDATE_DTO, PK, POJO> readService,
            UpdateService<ENTITY, PK, UPDATE_DTO, POJO> updateService,
            DeleteService<ENTITY, PK> deleteService,
            CreateService<CHILD_ENTITY, CHILD_PK, CHILD_POJO, CHILD_CREATE_DTO> childService
    ) {
        super(repository, createService, readService, updateService, deleteService);
        this.childService = childService;
    }
}
