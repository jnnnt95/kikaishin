package com.nniett.kikaishin.web.service.construction;

import jakarta.transaction.Transactional;
import lombok.Getter;
import org.springframework.data.repository.ListCrudRepository;

@Getter
public abstract class DeleteService<ENTITY, PK>
        implements CanDelete<PK>

{

    private final ListCrudRepository<ENTITY, PK> repository;

    public DeleteService(ListCrudRepository<ENTITY, PK> repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public boolean delete(PK id) {
        try {
            repository.deleteById(id);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

}
