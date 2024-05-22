package com.nniett.kikaishin.app.service.crud.question;

import com.nniett.kikaishin.app.persistence.entity.QuestionEntity;
import com.nniett.kikaishin.app.persistence.repository.QuestionRepository;
import com.nniett.kikaishin.app.service.construction.UpdateService;
import com.nniett.kikaishin.app.service.mapper.QuestionMapper;
import com.nniett.kikaishin.app.service.mapper.dto.question.QuestionUpdateMapper;
import com.nniett.kikaishin.app.service.dto.QuestionDto;
import com.nniett.kikaishin.app.service.dto.write.question.QuestionUpdateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionUpdateService
        extends UpdateService
        <
                QuestionEntity,
                Integer,
                QuestionUpdateDto,
                QuestionDto
                >
{
    private static final Logger logger = LoggerFactory.getLogger(QuestionUpdateService.class);

    public QuestionUpdateService(
            QuestionRepository repository,
            QuestionMapper entityPojoMapper,
            @Qualifier("questionUpdateMapperImpl")
            QuestionUpdateMapper updateMapper
    ) {
        super(repository, entityPojoMapper, updateMapper);
        logger.info("QuestionUpdateService initialized.");
    }

    @Override
    public void populateEntityForUpdate(QuestionEntity entity, QuestionDto pojo) {
        logger.debug("Populating question's changed fields.");
        if(pojo.getBody() != null &&
                !pojo.getBody().isEmpty() &&
                !pojo.getBody().equals(entity.getBody())) {
            logger.trace("Populating question's body with {}.", pojo.getBody());
            entity.setBody(pojo.getBody());
        }
        if(pojo.getActive() != null &&
                pojo.getActive() != entity.getActive()) {
            logger.trace("Populating question's field active as {}.", pojo.getActive());
            entity.setActive(pojo.getActive());
        }
    }
}
