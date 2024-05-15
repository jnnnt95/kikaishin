package com.nniett.kikaishin.app.controller.construction;

import com.nniett.kikaishin.app.persistence.entity.construction.ActivateableMutableEntity;
import com.nniett.kikaishin.app.service.construction.ActivateActionableService;
import com.nniett.kikaishin.app.service.pojo.dto.ActivateableUpdateDto;
import org.springframework.http.ResponseEntity;
import static com.nniett.kikaishin.app.controller.construction.ImmutableController.caughtOperate;

public interface ActivateActionableController
        <
                ENTITY extends ActivateableMutableEntity,
                PK,
                UPDATE_DTO extends ActivateableUpdateDto<PK>,
                SERVICE  extends ActivateActionableService
                        <
                                                ENTITY,
                                                PK,
                                                UPDATE_DTO
                                                >
                >

{

    ResponseEntity<Void> toggleActive(UPDATE_DTO dto);
    SERVICE getActivateableService();

    default ResponseEntity<Void> toggleStatus(UPDATE_DTO updateDto) {
        return caughtOperate(() -> {
            if(getActivateableService().entityExists(updateDto.getPK())) {
                if(updateDto.getActive() == null) {
                    getActivateableService().toggleActive(updateDto);
                }
                else {
                    getActivateableService().changeStatus(updateDto);
                }
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        });
    }

}
