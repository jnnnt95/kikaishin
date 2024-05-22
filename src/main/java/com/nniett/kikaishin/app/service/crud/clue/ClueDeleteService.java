package com.nniett.kikaishin.app.service.crud.clue;

import com.nniett.kikaishin.app.persistence.entity.ClueEntity;
import com.nniett.kikaishin.app.persistence.repository.ClueRepository;
import com.nniett.kikaishin.app.service.construction.DeleteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ClueDeleteService
        extends DeleteService<ClueEntity, Integer>
{
    private static final Logger logger = LoggerFactory.getLogger(ClueDeleteService.class);

    public ClueDeleteService(ClueRepository repository) {
        super(repository);
        logger.info("ClueDeleteService initialized.");
    }

}
