package com.nniett.kikaishin.web.service;

import com.nniett.kikaishin.web.persistence.entity.BookEntity;
import com.nniett.kikaishin.web.persistence.entity.TopicEntity;
import com.nniett.kikaishin.web.persistence.repository.BookRepository;
import com.nniett.kikaishin.web.service.construction.*;
import com.nniett.kikaishin.web.service.crud.book.BookCreateService;
import com.nniett.kikaishin.web.service.crud.book.BookDeleteService;
import com.nniett.kikaishin.web.service.crud.book.BookReadService;
import com.nniett.kikaishin.web.service.crud.book.BookUpdateService;
import com.nniett.kikaishin.web.service.pojo.Book;
import com.nniett.kikaishin.web.service.pojo.Topic;
import com.nniett.kikaishin.web.service.pojo.dto.book.BookCreationDto;
import com.nniett.kikaishin.web.service.pojo.dto.book.BookUpdateDto;
import com.nniett.kikaishin.web.service.pojo.dto.topic.TopicCreationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class BookService
        extends ActivateableWithChildrenService
        <
                        BookEntity,
                        Integer,
                        Book,
                        BookCreationDto,
                        BookUpdateDto,
                        TopicEntity,
                        Integer,
                        Topic,
                        TopicCreationDto
                        >
{

    @Autowired
    public BookService(
            BookRepository repository,
            BookCreateService createService,
            BookReadService readService,
            BookUpdateService updateService,
            BookDeleteService deleteService,
            CreateService<TopicEntity, Integer, Topic, TopicCreationDto>
                    childService
    ) {
        super(repository, createService, readService, updateService, deleteService, childService);
    }


    @Override
    public void populateAsDefaultPojoForCreation(Book book) {
        book.setActive(true);
    }

    @Override
    public void populateEntityForUpdate(BookEntity entity, Book pojo) {
        getUpdateService().populateEntityForUpdate(entity, pojo);
    }

    @Override
    public BookEntity findEntityByDto(BookUpdateDto updateDto) {
        int bookId = updateDto.getBookId();
        return getRepository().findById(bookId).get();
    }

    public Book getShelfById(int id) {
        return read(id);
    }

    @Override
    public ListCrudRepository<BookEntity, Integer> getActivateableRepository() {
        return getRepository();
    }
}