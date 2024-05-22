package com.nniett.kikaishin.app.service.construction;

import com.nniett.kikaishin.app.persistence.entity.construction.ActivateableMutableEntity;
import com.nniett.kikaishin.app.service.dto.write.ActivateableUpdateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface ActivateActionableService
        <
                ENTITY extends ActivateableMutableEntity,
                PK,
                UPDATE_DTO extends ActivateableUpdateDto<PK>
                >
{
    Logger logger = LoggerFactory.getLogger(ActivateActionableService.class);

    ListCrudRepository<ENTITY, PK> getActivateableRepository();

    default boolean entityExists(PK id) {
        return getActivateableRepository().existsById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    default void changeStatus(UPDATE_DTO dto) {
        logger.debug("Changing object status.");
        ENTITY entity = getActivateableRepository().findById(dto.getPK()).orElseThrow();
        if(entity.getActive() != dto.getActive()) {
            logger.trace("Status being changed to {}.", dto.getActive());
            entity.setActive(dto.getActive());
            getActivateableRepository().save(entity);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    default void toggleActive(UPDATE_DTO dto) {
        logger.debug("Changing object status as toggle.");
        ENTITY entity = getActivateableRepository().findById(dto.getPK()).orElseThrow();
        boolean toggleValue = !entity.getActive();
        logger.trace("Status being changed to {}.", toggleValue);
        entity.setActive(toggleValue);
        getActivateableRepository().save(entity);
    }
}
