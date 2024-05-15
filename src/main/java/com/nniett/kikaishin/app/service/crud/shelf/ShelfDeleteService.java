package com.nniett.kikaishin.app.service.crud.shelf;

import com.nniett.kikaishin.app.persistence.entity.ShelfEntity;
import com.nniett.kikaishin.app.service.construction.DeleteService;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ShelfDeleteService
        extends DeleteService<ShelfEntity, Integer>
{

    public ShelfDeleteService(ListCrudRepository<ShelfEntity, Integer> repository) {
        super(repository);
    }

}
