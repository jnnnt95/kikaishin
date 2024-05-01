package com.nniett.kikaishin.web.service.construction;

import com.nniett.kikaishin.web.persistence.entity.construction.ActivateableMutableEntity;
import com.nniett.kikaishin.web.service.pojo.dto.ActivateableUpdateDto;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.ListCrudRepository;

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

    @Transactional(Transactional.TxType.REQUIRED)
    default void changeStatus(UPDATE_DTO dto) {
        ENTITY entity = getActivateableRepository().findById(dto.getPK()).get();
        if(entity.getActive() != dto.getActive()) {
            entity.setActive(dto.getActive());
            getActivateableRepository().save(entity);
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    default void toggleActive(UPDATE_DTO dto) {
        ENTITY entity = getActivateableRepository().findById(dto.getPK()).get();
        entity.setActive(!entity.getActive());
        getActivateableRepository().save(entity);
    }
}
