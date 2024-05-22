package com.nniett.kikaishin.app.service.crud.book;

import com.nniett.kikaishin.app.persistence.entity.BookEntity;
import com.nniett.kikaishin.app.service.construction.UpdateService;
import com.nniett.kikaishin.app.service.mapper.EntityPojoMapper;
import com.nniett.kikaishin.app.service.mapper.dto.book.BookUpdateMapper;
import com.nniett.kikaishin.app.service.dto.BookDto;
import com.nniett.kikaishin.app.service.dto.write.book.BookUpdateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class BookUpdateService
        extends UpdateService
        <
                BookEntity,
                Integer,
                BookUpdateDto,
                BookDto
                >
{
    private static final Logger logger = LoggerFactory.getLogger(BookUpdateService.class);

    public BookUpdateService(
            ListCrudRepository<BookEntity, Integer> repository,
            EntityPojoMapper<BookEntity, BookDto> entityPojoMapper,
            @Qualifier("bookUpdateMapperImpl")
            BookUpdateMapper updateMapper
    ) {
        super(repository, entityPojoMapper, updateMapper);
        logger.info("BookUpdateService initialized.");
    }

    @Override
    public void populateEntityForUpdate(BookEntity entity, BookDto pojo) {
        logger.debug("Populating book's changed fields.");
        if(pojo.getName() != null &&
                !pojo.getName().isEmpty() &&
                !pojo.getName().equals(entity.getName())) {
            logger.trace("Populating book's name with {}.", pojo.getName());
            entity.setName(pojo.getName());
        }
        if(pojo.getDescription() != null &&
                !pojo.getDescription().isEmpty() &&
                !pojo.getDescription().equals(entity.getDescription())) {
            logger.trace("Populating book's description with {}.", pojo.getDescription());
            entity.setDescription(pojo.getDescription());
        }
        if(pojo.getActive() != null &&
                pojo.getActive() != entity.getActive()) {
            logger.debug("Populating book's field active as {}.", pojo.getActive());
            entity.setActive(pojo.getActive());
        }
    }
}
