package com.nniett.kikaishin.app.web.controller.construction;

import com.nniett.kikaishin.app.persistence.entity.construction.ActivateableMutableEntity;
import com.nniett.kikaishin.app.service.construction.ActivateActionableService;
import com.nniett.kikaishin.app.service.dto.write.ActivateableUpdateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import static com.nniett.kikaishin.app.web.controller.construction.ImmutableController.caughtOperate;

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
    Logger logger = LoggerFactory.getLogger(ActivateActionableController.class);

    ResponseEntity<Void> toggleActive(UPDATE_DTO dto);
    SERVICE getActivateableService();

    default ResponseEntity<Void> toggleStatus(UPDATE_DTO updateDto) {
        return caughtOperate(() -> {
            logger.debug("Toggling status of object.");
            logger.trace("Object body under status change {}.", updateDto);
            if(getActivateableService().entityExists(updateDto.getPK())) {
                if(updateDto.getActive() == null) {
                    getActivateableService().toggleActive(updateDto);
                }
                else {
                    getActivateableService().changeStatus(updateDto);
                }
                logger.debug("Toggle status operation successful.");
                return ResponseEntity.ok().build();
            } else {
                logger.debug("Toggle status operation failed because no object was found to provided id.");
                return ResponseEntity.notFound().build();
            }
        });
    }

}
