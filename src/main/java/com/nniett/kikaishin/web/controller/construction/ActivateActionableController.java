package com.nniett.kikaishin.web.controller.construction;

import com.nniett.kikaishin.web.persistence.entity.construction.ActivateableMutableEntity;
import com.nniett.kikaishin.web.service.construction.ActivateActionableService;
import com.nniett.kikaishin.web.service.pojo.dto.ActivateableUpdateDto;
import org.springframework.http.ResponseEntity;
import static com.nniett.kikaishin.web.controller.construction.Controller.catchedOperate;

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
        return catchedOperate(() -> {
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
