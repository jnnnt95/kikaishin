package com.nniett.kikaishin.app.service.construction;

import com.nniett.kikaishin.app.persistence.entity.construction.ActivateableMutableEntity;
import com.nniett.kikaishin.app.service.pojo.dto.ActivateableUpdateDto;
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

    ListCrudRepository<ENTITY, PK> getActivateableRepository();

    default boolean entityExists(PK id) {
        return getActivateableRepository().existsById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    default void changeStatus(UPDATE_DTO dto) {
        ENTITY entity = getActivateableRepository().findById(dto.getPK()).get();
        if(entity.getActive() != dto.getActive()) {
            entity.setActive(dto.getActive());
            getActivateableRepository().save(entity);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    default void toggleActive(UPDATE_DTO dto) {
        ENTITY entity = getActivateableRepository().findById(dto.getPK()).get();
        entity.setActive(!entity.getActive());
        getActivateableRepository().save(entity);
    }
}
