package com.nniett.kikaishin.app.service.crud.shelf;

import com.nniett.kikaishin.app.persistence.entity.ShelfEntity;
import com.nniett.kikaishin.app.service.construction.DeleteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ShelfDeleteService
        extends DeleteService<ShelfEntity, Integer>
{
    private static final Logger logger = LoggerFactory.getLogger(ShelfDeleteService.class);

    public ShelfDeleteService(ListCrudRepository<ShelfEntity, Integer> repository) {
        super(repository);
        logger.info("ShelfDeleteService initialized.");
    }

}
