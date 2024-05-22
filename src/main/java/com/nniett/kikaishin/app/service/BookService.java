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
import com.nniett.kikaishin.app.service.dto.BookDto;
import com.nniett.kikaishin.app.service.dto.BookInfoDto;
import com.nniett.kikaishin.app.service.dto.write.book.BookCreationDto;
import com.nniett.kikaishin.app.service.dto.write.book.BookUpdateDto;
import com.nniett.kikaishin.app.web.controller.construction.UsesHttpServletRequest;
import com.nniett.kikaishin.app.web.security.CanRetrieveUsernameFromJWT;
import com.nniett.kikaishin.app.web.security.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
                BookDto,
                BookCreationDto,
                BookUpdateDto
                >
        implements UsesHttpServletRequest, CanRetrieveUsernameFromJWT
{
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    private final BookInfoVirtualRepository bookInfoRepository;
    private final BookInfoMapper bookInfoMapper;
    private final JwtUtils jwtUtils;

    @Autowired
    public BookService(
            BookRepository repository,
            BookCreateService createService,
            BookReadService readService,
            BookUpdateService updateService,
            BookDeleteService deleteService,
            BookInfoVirtualRepository bookInfoRepository,
            BookInfoMapper bookInfoMapper,
            JwtUtils jwtUtils
    ) {
        super(repository, createService, readService, updateService, deleteService);
        this.bookInfoRepository = bookInfoRepository;
        this.bookInfoMapper = bookInfoMapper;
        this.jwtUtils = jwtUtils;
        logger.info("BookService initialized.");
    }

    @Override
    public void populateAsDefaultForCreation(BookEntity entity) {
        entity.setActive(true);
    }

    @Override
    public void populateEntityForUpdate(BookEntity entity, BookDto pojo) {
        getUpdateService().populateEntityForUpdate(entity, pojo);
    }

    @Override
    public BookEntity findEntityByDto(BookUpdateDto updateDto) {
        return getRepository().findById(updateDto.getBookId()).orElseThrow();
    }

    @Override
    public ListCrudRepository<BookEntity, Integer> getActivateableRepository() {
        return getRepository();
    }



    public BookInfoDto getBookInfo(Integer bookId) {
        logger.debug("Retrieving book info.");
        String username = getUsernameFromJWT(getHttpServletRequest(), this.jwtUtils);
        logger.trace("Book info being gathered for username {}.", username);
        return bookInfoMapper.
                toBookInfo(
                        bookInfoRepository.
                                getBooksInfoById(username, Collections.singletonList(bookId)).get(0)
                );
    }

    public List<BookInfoDto> getBooksInfo(List<Integer> bookIds) {
        logger.debug("Retrieving books info.");
        String username = getUsernameFromJWT(getHttpServletRequest(), this.jwtUtils);
        logger.trace("Books info being gathered for username {}.", username);
        return bookInfoMapper.toBooksInfo(bookInfoRepository.getBooksInfoById(username, bookIds));
    }

    public Integer countExistingIds(List<Integer> bookIds) {
        logger.debug("Retrieving count of books by ids.");
        String username = getUsernameFromJWT(getHttpServletRequest(), this.jwtUtils);
        logger.trace("Counting questions by ids for username {}.", username);
        return ((BookRepository) getRepository()).countByIdIn(username, bookIds);
    }


}