package com.nniett.kikaishin.app.controller.web;

import com.nniett.kikaishin.app.controller.construction.ActivateableController;
import com.nniett.kikaishin.app.controller.construction.CanCheckOwnership;
import com.nniett.kikaishin.app.controller.construction.CanVerifyId;
import com.nniett.kikaishin.app.controller.construction.VerifiesIntegerId;
import com.nniett.kikaishin.app.persistence.entity.BookEntity;
import com.nniett.kikaishin.app.service.BookService;
import com.nniett.kikaishin.app.service.pojo.Book;
import com.nniett.kikaishin.app.service.pojo.BookInfo;
import com.nniett.kikaishin.app.service.pojo.TopicInfo;
import com.nniett.kikaishin.app.service.pojo.dto.book.BookCreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.book.BookUpdateDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.nniett.kikaishin.common.Constants.*;

@RestController
@RequestMapping(BOOK_PATH)
public class BookController
        extends ActivateableController
        <
                BookEntity,
                Integer,
                Book,
                BookCreationDto,
                BookUpdateDto,
                BookService
                >
        implements CanCheckOwnership<Integer>, CanVerifyId<Integer>, VerifiesIntegerId
{
    private final ShelfController parentController;

    @Autowired
    public BookController(BookService service, ShelfController shelfController) {
        super(service);
        this.parentController = shelfController;
    }

    @Override
    @PostMapping()
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Book> persistNewEntity(@Valid @RequestBody BookCreationDto dto) {
        if(parentController.valid(dto.getParentPK())) {
            if(parentController.own(dto.getParentPK())) {
                return create(dto);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @GetMapping(ID_PARAM_PATH)
    public ResponseEntity<Book> getEntityById(@PathVariable(ID) Integer id) {
        if(valid(id)) {
            if(own(id)) {
                return readById(id);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @PutMapping()
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Book> updateEntity(@Valid @RequestBody BookUpdateDto dto) {
        if(own(dto.getPK())) {
            return update(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(ID_PARAM_PATH)
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Void> deleteEntityById(@PathVariable(ID) Integer id) {
        if(valid(id)) {
            if(own(id)) {
                return delete(id);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @PutMapping(TOGGLE_STATUS_PATH)
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Void> toggleActive(@Valid @RequestBody BookUpdateDto dto) {
        if(own(dto.getPK())) {
            return toggleStatus(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    ///////////// Book Info Control


    @GetMapping(INFO_ENDPOINT + ID_PARAM_PATH)
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<BookInfo> requestBookInfo(
            @PathVariable(name = ID) Integer bookId
    ) {
        if(valid(bookId)) {
            if(own(bookId)) {
                return new ResponseEntity<>(getService().getBookInfo(bookId), HttpStatus.OK);
            }
            else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(MULTI_INFO_ENDPOINT + IDS_PARAM_PATH)
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<List<BookInfo>> requestBooksInfo(
            @PathVariable(name = IDS) Set<Integer> bookIds
    ) {
        if(valid(bookIds)) {
            List<Integer> ids = new ArrayList<>(bookIds);
            // all books are owned or request is rejected.
            if(own(ids)) {
                return new ResponseEntity<>(getService().getBooksInfo(ids), HttpStatus.OK);
            }
            else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @Override
    public boolean own(List<Integer> ids) {
        return getService().countExistingIds(ids) == ids.size();
    }

    @Override
    public boolean valid(Collection<Integer> ids) {
        return validIntegers(ids);
    }

}
