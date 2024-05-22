package com.nniett.kikaishin.app.service.crud.answer;

import com.nniett.kikaishin.app.persistence.entity.AnswerEntity;
import com.nniett.kikaishin.app.persistence.repository.AnswerRepository;
import com.nniett.kikaishin.app.service.construction.UpdateService;
import com.nniett.kikaishin.app.service.mapper.AnswerMapper;
import com.nniett.kikaishin.app.service.mapper.dto.answer.AnswerUpdateMapper;
import com.nniett.kikaishin.app.service.dto.AnswerDto;
import com.nniett.kikaishin.app.service.dto.write.answer.AnswerUpdateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerUpdateService
        extends UpdateService
        <
                AnswerEntity,
                Integer,
                AnswerUpdateDto,
                AnswerDto
                >
{
    private static final Logger logger = LoggerFactory.getLogger(AnswerUpdateService.class);

    public AnswerUpdateService(
            AnswerRepository repository,
            AnswerMapper entityPojoMapper,
            @Qualifier("answerUpdateMapperImpl")
            AnswerUpdateMapper updateMapper
    ) {
        super(repository, entityPojoMapper, updateMapper);
        logger.info("AnswerUpdateService initialized.");
    }

    @Override
    public void populateEntityForUpdate(AnswerEntity entity, AnswerDto pojo) {
        logger.debug("Populating answer's changed fields.");
        if(pojo.getBody() != null &&
                !pojo.getBody().isEmpty() &&
                !pojo.getBody().equals(entity.getBody())) {
            logger.trace("Populating answer's body with {}.", pojo.getBody());
            entity.setBody(pojo.getBody());
        }
        if(pojo.getOrderIndex() != null &&
                pojo.getOrderIndex() > 0 &&
                !pojo.getOrderIndex().equals(entity.getOrderIndex())) {
            logger.trace("Populating answer's order index with {}.", pojo.getOrderIndex());
            entity.setOrderIndex(pojo.getOrderIndex());
        }
    }
}
