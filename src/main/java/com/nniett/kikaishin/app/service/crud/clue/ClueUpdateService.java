package com.nniett.kikaishin.app.service.crud.clue;

import com.nniett.kikaishin.app.persistence.entity.ClueEntity;
import com.nniett.kikaishin.app.persistence.repository.ClueRepository;
import com.nniett.kikaishin.app.service.construction.UpdateService;
import com.nniett.kikaishin.app.service.mapper.ClueMapper;
import com.nniett.kikaishin.app.service.mapper.dto.clue.ClueUpdateMapper;
import com.nniett.kikaishin.app.service.dto.ClueDto;
import com.nniett.kikaishin.app.service.dto.write.clue.ClueUpdateDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class ClueUpdateService
        extends UpdateService
        <
                ClueEntity,
                Integer,
                ClueUpdateDto,
                ClueDto
                >
{

    public ClueUpdateService(
            ClueRepository repository,
            ClueMapper entityPojoMapper,
            @Qualifier("clueUpdateMapperImpl")
            ClueUpdateMapper updateMapper
    ) {
        super(repository, entityPojoMapper, updateMapper);
    }

    @Override
    public void populateEntityForUpdate(ClueEntity entity, ClueDto pojo) {
        if(pojo.getBody() != null &&
                !pojo.getBody().isEmpty() &&
                !pojo.getBody().equals(entity.getBody())) {
            entity.setBody(pojo.getBody());
        }
        if(pojo.getOrderIndex() != null &&
                pojo.getOrderIndex() > 0 &&
                !pojo.getOrderIndex().equals(entity.getOrderIndex())) {
            entity.setOrderIndex(pojo.getOrderIndex());
        }
    }
}
