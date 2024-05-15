package com.nniett.kikaishin.app.service.construction;

import lombok.Getter;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Getter
public abstract class DeleteService<ENTITY, PK>
        implements CanDelete<PK>

{

    private final ListCrudRepository<ENTITY, PK> repository;

    public DeleteService(ListCrudRepository<ENTITY, PK> repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(PK id) {
        try {
            repository.deleteById(id);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

}
