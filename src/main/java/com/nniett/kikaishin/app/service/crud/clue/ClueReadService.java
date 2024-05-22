package com.nniett.kikaishin.app.service.crud.clue;

import com.nniett.kikaishin.app.persistence.entity.ClueEntity;
import com.nniett.kikaishin.app.persistence.repository.ClueRepository;
import com.nniett.kikaishin.app.service.construction.ReadService;
import com.nniett.kikaishin.app.service.mapper.ClueMapper;
import com.nniett.kikaishin.app.service.dto.ClueDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ClueReadService
        extends ReadService
        <
                ClueEntity,
                Integer,
                ClueDto
                >
{
    private static final Logger logger = LoggerFactory.getLogger(ClueReadService.class);

    public ClueReadService(
            ClueRepository repository,
            ClueMapper entityPojoMapper
    ) {
        super(repository, entityPojoMapper);
        logger.info("ClueReadService initialized.");
    }
    
}
