package com.nniett.kikaishin.app.service.crud.shelf;

import com.nniett.kikaishin.app.persistence.entity.ShelfEntity;
import com.nniett.kikaishin.app.service.construction.UpdateService;
import com.nniett.kikaishin.app.service.dto.ShelfDto;
import com.nniett.kikaishin.app.service.mapper.ShelfMapper;
import com.nniett.kikaishin.app.service.mapper.dto.shelf.ShelfUpdateMapper;
import com.nniett.kikaishin.app.service.dto.write.shelf.ShelfUpdateDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ShelfUpdateService
        extends UpdateService
        <
                ShelfEntity,
                Integer,
                ShelfUpdateDto,
                ShelfDto
                >
{

    public ShelfUpdateService(
            ListCrudRepository<ShelfEntity, Integer> repository,
            ShelfMapper entityPojoMapper,
            @Qualifier("shelfUpdateMapperImpl")
            ShelfUpdateMapper updateMapper
    ) {
        super(repository, entityPojoMapper, updateMapper);
    }

    @Override
    public void populateEntityForUpdate(ShelfEntity entity, ShelfDto pojo) {
        if(pojo.getName() != null &&
                !pojo.getName().isEmpty() &&
                !pojo.getName().equals(entity.getName())) {
            entity.setName(pojo.getName());
        }
        if(pojo.getDescription() != null &&
                !pojo.getDescription().isEmpty() &&
                !pojo.getDescription().equals(entity.getDescription())) {
            entity.setDescription(pojo.getDescription());
        }
        if(pojo.getActive() != null &&
                pojo.getActive() != entity.getActive()) {
            entity.setActive(pojo.getActive());
        }
    }
}
