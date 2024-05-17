package com.nniett.kikaishin.app.web.controller.construction;


import com.nniett.kikaishin.app.persistence.entity.construction.ActivateableMutableEntity;
import com.nniett.kikaishin.app.service.construction.ActivateableService;
import com.nniett.kikaishin.app.service.dto.common.Pojo;
import com.nniett.kikaishin.app.service.dto.write.ActivateableUpdateDto;
import com.nniett.kikaishin.app.service.dto.write.CreationDto;
import org.springframework.http.ResponseEntity;

public abstract class ActivateableController
        <
                ENTITY extends ActivateableMutableEntity,
                PK,
                POJO extends Pojo<PK>,
                CREATE_DTO extends CreationDto<PK>,
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
        extends Controller<ENTITY, PK, POJO, CREATE_DTO, UPDATE_DTO, SERVICE>
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

    @Override
    public SERVICE getActivateableService() {
        return getService();
    }
}
