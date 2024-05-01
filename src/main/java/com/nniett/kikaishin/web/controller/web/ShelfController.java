package com.nniett.kikaishin.web.controller.web;

import com.nniett.kikaishin.web.controller.construction.ActivateableWithChildrenController;
import com.nniett.kikaishin.web.persistence.entity.BookEntity;
import com.nniett.kikaishin.web.persistence.entity.ShelfEntity;
import com.nniett.kikaishin.web.service.ShelfService;
import com.nniett.kikaishin.web.service.pojo.Book;
import com.nniett.kikaishin.web.service.pojo.Shelf;
import com.nniett.kikaishin.web.service.pojo.dto.book.BookCreationDto;
import com.nniett.kikaishin.web.service.pojo.dto.shelf.ShelfCreationDto;
import com.nniett.kikaishin.web.service.pojo.dto.shelf.ShelfUpdateDto;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.nniett.kikaishin.common.Constants.*;

@RestController
@RequestMapping(SHELF_PATH)
public class ShelfController
        extends ActivateableWithChildrenController
        <
                ShelfEntity,
                Integer,
                Shelf,
                ShelfCreationDto,
                ShelfUpdateDto,
                BookEntity,
                Integer,
                Book,
                BookCreationDto,
                ShelfService
                >
{

    @Autowired
    public ShelfController(ShelfService service) {
        super(service);
    }

    @Override
    @PostMapping()
    @Transactional(Transactional.TxType.REQUIRED)
    public ResponseEntity<Shelf> persistNewEntity(@Valid @RequestBody ShelfCreationDto dto) {
        return createWithChildren(dto);
    }

    @Override
    @GetMapping(ID_PARAM_PATH)
    public ResponseEntity<Shelf> getEntityById(@PathVariable(ID) Integer id) {
        return readById(id);
    }

    @Override
    @PutMapping()
    @Transactional(Transactional.TxType.REQUIRED)
    public ResponseEntity<Shelf> updateEntity(@Valid @RequestBody ShelfUpdateDto dto) {
        return updateWithChildren(dto);
    }

    @DeleteMapping(ID_PARAM_PATH)
    @Transactional(Transactional.TxType.REQUIRED)
    public ResponseEntity<Void> deleteEntityById(@PathVariable(ID) Integer id) {
        return delete(id);
    }

    @Override
    @PutMapping("/toggle_status")
    @Transactional(Transactional.TxType.REQUIRED)
    public ResponseEntity<Void> toggleActive(@Valid @RequestBody ShelfUpdateDto dto) {
        return toggleStatus(dto);
    }

}
