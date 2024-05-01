package com.nniett.kikaishin.web.controller.construction;

import com.nniett.kikaishin.web.persistence.entity.construction.ActivateableMutableEntity;
import com.nniett.kikaishin.web.service.construction.ActivateableWithChildrenService;
import com.nniett.kikaishin.web.service.pojo.Pojo;
import com.nniett.kikaishin.web.service.pojo.common.HasChildren;
import com.nniett.kikaishin.web.service.pojo.dto.ActivateableUpdateDto;
import com.nniett.kikaishin.web.service.pojo.dto.CreationDtoWithChildren;
import com.nniett.kikaishin.web.service.pojo.dto.CreationDtoWithParent;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;

public abstract class ActivateableWithChildrenController
        <
                ENTITY extends ActivateableMutableEntity,
                PK,
                POJO extends Pojo<PK> & HasChildren<CHILD_POJO>,
                CREATE_DTO extends CreationDtoWithChildren<CHILD_CREATE_DTO>,
                UPDATE_DTO extends ActivateableUpdateDto<PK>,
                CHILD_ENTITY,
                CHILD_PK,
                CHILD_POJO,
                CHILD_CREATE_DTO extends CreationDtoWithParent<PK>,
                SERVICE extends ActivateableWithChildrenService
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
        >
        extends WithChildrenController
        <
                        ENTITY,
                        PK,
                        POJO,
                        CREATE_DTO,
                        UPDATE_DTO,
                        CHILD_ENTITY,
                        CHILD_PK,
                        CHILD_POJO,
                        CHILD_CREATE_DTO,
                        SERVICE
                        >
        implements ActivateActionableController<ENTITY, PK, UPDATE_DTO, SERVICE>
{

    public ActivateableWithChildrenController(SERVICE service) {
        super(service);
    }

    @Override
    @Transactional(Transactional.TxType.MANDATORY)
    public abstract ResponseEntity<Void> toggleActive(UPDATE_DTO dto);

    @Override
    @Transactional(Transactional.TxType.MANDATORY)
    public ResponseEntity<Void> toggleStatus(UPDATE_DTO updateDto) {
        return ActivateActionableController.super.toggleStatus(updateDto);
    }

    @Override
    public SERVICE getActivateableService() {
        return getService();
    }

}
