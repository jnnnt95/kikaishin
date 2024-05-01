package com.nniett.kikaishin.web.service.crud.book;

import com.nniett.kikaishin.web.persistence.entity.BookEntity;
import com.nniett.kikaishin.web.service.construction.UpdateService;
import com.nniett.kikaishin.web.service.mapper.EntityPojoMapper;
import com.nniett.kikaishin.web.service.mapper.dto.DtoPojoMapper;
import com.nniett.kikaishin.web.service.mapper.dto.book.BookUpdateMapper;
import com.nniett.kikaishin.web.service.pojo.Book;
import com.nniett.kikaishin.web.service.pojo.dto.book.BookUpdateDto;
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
                Book
                >
{

    public BookUpdateService(
            ListCrudRepository<BookEntity, Integer> repository,
            EntityPojoMapper<BookEntity, Book> entityPojoMapper,
            BookUpdateMapper updateMapper
    ) {
        super(repository, entityPojoMapper, updateMapper);
    }

    @Override
    public void populateEntityForUpdate(BookEntity entity, Book pojo) {
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
