package com.nniett.kikaishin.web.controller.construction;


import com.nniett.kikaishin.web.persistence.entity.construction.ActivateableMutableEntity;
import com.nniett.kikaishin.web.service.construction.ActivateableService;
import com.nniett.kikaishin.web.service.pojo.Pojo;
import com.nniett.kikaishin.web.service.pojo.dto.ActivateableUpdateDto;
import org.springframework.http.ResponseEntity;

public abstract class ActivateableController
        <
                PK,
                ENTITY extends ActivateableMutableEntity,
                POJO extends Pojo<PK>,
                CREATE_DTO,
                UPDATE_DTO extends ActivateableUpdateDto<PK>,
                SERVICE extends ActivateableService
                        <
                                ENTITY,
                                PK,
                                POJO,
                                CREATE_DTO,
                                UPDATE_DTO
                                >
                >
        extends Controller<PK, ENTITY, POJO, CREATE_DTO, UPDATE_DTO, SERVICE>
        implements ActivateActionableController<ENTITY, PK, UPDATE_DTO, SERVICE>
{

    public ActivateableController(SERVICE service) {
        super(service);
    }

    @Override
    public ResponseEntity<Void> toggleActive(UPDATE_DTO dto) {
        return null;
    }

    @Override
    public ResponseEntity<Void> toggleStatus(UPDATE_DTO updateDto) {
        return ActivateActionableController.super.toggleStatus(updateDto);
    }
}
