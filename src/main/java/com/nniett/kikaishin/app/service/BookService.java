package com.nniett.kikaishin.app.service;

import com.nniett.kikaishin.app.persistence.entity.BookEntity;
import com.nniett.kikaishin.app.persistence.repository.BookRepository;
import com.nniett.kikaishin.app.persistence.repository.virtual.BookInfoVirtualRepository;
import com.nniett.kikaishin.app.service.construction.*;
import com.nniett.kikaishin.app.service.crud.book.BookCreateService;
import com.nniett.kikaishin.app.service.crud.book.BookDeleteService;
import com.nniett.kikaishin.app.service.crud.book.BookReadService;
import com.nniett.kikaishin.app.service.crud.book.BookUpdateService;
import com.nniett.kikaishin.app.service.mapper.BookInfoMapper;
import com.nniett.kikaishin.app.service.pojo.Book;
import com.nniett.kikaishin.app.service.pojo.BookInfo;
import com.nniett.kikaishin.app.service.pojo.dto.book.BookCreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.book.BookUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class BookService
        extends ActivateableService
        <
                BookEntity,
                Integer,
                Book,
                BookCreationDto,
                BookUpdateDto
                >
{

    private final BookInfoVirtualRepository bookInfoRepository;
    private final BookInfoMapper bookInfoMapper;

    @Autowired
    public BookService(
            BookRepository repository,
            BookCreateService createService,
            BookReadService readService,
            BookUpdateService updateService,
            BookDeleteService deleteService,
            BookInfoVirtualRepository bookInfoRepository,
            BookInfoMapper bookInfoMapper
    ) {
        super(repository, createService, readService, updateService, deleteService);
        this.bookInfoRepository = bookInfoRepository;
        this.bookInfoMapper = bookInfoMapper;
    }

    @Override
    public void populateAsDefaultForCreation(BookEntity entity) {
        entity.setActive(true);
    }

    @Override
    public void populateEntityForUpdate(BookEntity entity, Book pojo) {
        getUpdateService().populateEntityForUpdate(entity, pojo);
    }

    @Override
    public BookEntity findEntityByDto(BookUpdateDto updateDto) {
        int bookId = updateDto.getBookId();
        return getRepository().findById(bookId).orElseThrow();
    }

    @Override
    public ListCrudRepository<BookEntity, Integer> getActivateableRepository() {
        return getRepository();
    }



    public BookInfo getBookInfo(Integer bookId) {
        //TODO: username should be retrieved appropriately from JWT.
        String username = "1";
        return bookInfoMapper.
                toBookInfo(
                        bookInfoRepository.
                                getBooksInfoById(username, Collections.singletonList(bookId)).get(0)
                );
    }

    public List<BookInfo> getBooksInfo(List<Integer> bookIds) {
        //TODO: username should be retrieved appropriately from JWT.
        String username = "1";
        return bookInfoMapper.toBooksInfo(bookInfoRepository.getBooksInfoById(username, bookIds));
    }

    public Integer countExistingIds(List<Integer> bookIds) {
        //TODO: username should be retrieved appropriately from JWT.
        String username = "1";
        return ((BookRepository) getRepository()).countByIdIn(username, bookIds);
    }


}