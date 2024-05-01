package com.nniett.kikaishin.web.controller.web;

import com.nniett.kikaishin.web.controller.construction.ActivateableWithChildrenController;
import com.nniett.kikaishin.web.persistence.entity.BookEntity;
import com.nniett.kikaishin.web.persistence.entity.TopicEntity;
import com.nniett.kikaishin.web.service.BookService;
import com.nniett.kikaishin.web.service.pojo.Book;
import com.nniett.kikaishin.web.service.pojo.Topic;
import com.nniett.kikaishin.web.service.pojo.dto.book.BookCreationDto;
import com.nniett.kikaishin.web.service.pojo.dto.book.BookUpdateDto;
import com.nniett.kikaishin.web.service.pojo.dto.topic.TopicCreationDto;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.nniett.kikaishin.common.Constants.*;

@RestController
@RequestMapping(BOOK_PATH)
public class BookController
        extends ActivateableWithChildrenController
        <
                BookEntity,
                Integer,
                Book,
                BookCreationDto,
                BookUpdateDto,
                TopicEntity,
                Integer,
                Topic,
                TopicCreationDto,
                BookService
                >
{

    @Autowired
    public BookController(BookService service) {
        super(service);
    }

    @Override
    @PostMapping()
    @Transactional(Transactional.TxType.REQUIRED)
    public ResponseEntity<Book> persistNewEntity(@Valid @RequestBody BookCreationDto dto) {
        return createWithChildren(dto);
    }

    @Override
    @GetMapping(ID_PARAM_PATH)
    public ResponseEntity<Book> getEntityById(@PathVariable(ID) Integer id) {
        return readById(id);
    }

    @Override
    @PutMapping()
    @Transactional(Transactional.TxType.REQUIRED)
    public ResponseEntity<Book> updateEntity(@Valid @RequestBody BookUpdateDto dto) {
        return updateWithChildren(dto);
    }

    @DeleteMapping(ID_PARAM_PATH)
    @Transactional(Transactional.TxType.REQUIRED)
    public ResponseEntity<Void> deleteEntityById(@PathVariable(ID) Integer id) {
        return delete(id);
    }

    @Override
    @PutMapping(TOGGLE_STATUS_PATH)
    @Transactional(Transactional.TxType.REQUIRED)
    public ResponseEntity<Void> toggleActive(@Valid @RequestBody BookUpdateDto dto) {
        return toggleStatus(dto);
    }

}
