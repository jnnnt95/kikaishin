package com.nniett.kikaishin.app.controller.web;

import com.nniett.kikaishin.app.controller.construction.ActivateableController;
import com.nniett.kikaishin.app.controller.construction.CanCheckOwnership;
import com.nniett.kikaishin.app.controller.construction.CanVerifyId;
import com.nniett.kikaishin.app.controller.construction.VerifiesIntegerId;
import com.nniett.kikaishin.app.persistence.entity.ShelfEntity;
import com.nniett.kikaishin.app.service.ShelfService;
import com.nniett.kikaishin.app.service.pojo.Shelf;
import com.nniett.kikaishin.app.service.pojo.ShelfInfo;
import com.nniett.kikaishin.app.service.pojo.dto.shelf.ShelfCreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.shelf.ShelfUpdateDto;
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
@RequestMapping(SHELF_PATH)
public class ShelfController
        extends ActivateableController
        <
                ShelfEntity,
                Integer,
                Shelf,
                ShelfCreationDto,
                ShelfUpdateDto,
                ShelfService
                >
    implements CanCheckOwnership<Integer>, CanVerifyId<Integer>, VerifiesIntegerId
{

    @Autowired
    public ShelfController(ShelfService service) {
        super(service);
    }

    @Override
    @PostMapping()
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Shelf> persistNewEntity(@Valid @RequestBody ShelfCreationDto dto) {
        return create(dto);
    }

    @Override
    @GetMapping(ID_PARAM_PATH)
    public ResponseEntity<Shelf> getEntityById(@PathVariable(ID) Integer id) {
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
    public ResponseEntity<Shelf> updateEntity(@Valid @RequestBody ShelfUpdateDto dto) {
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
    @PutMapping("/toggle_status")
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Void> toggleActive(@Valid @RequestBody ShelfUpdateDto dto) {
        if(own(dto.getPK())) {
            return toggleStatus(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    ///////////// Shelf Info Control


    @GetMapping(INFO_ENDPOINT + ID_PARAM_PATH)
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<ShelfInfo> requestShelfInfo(
            @PathVariable(name = ID) Integer shelfId
    ) {
        if(valid(shelfId)) {
            if(own(shelfId)) {
                return new ResponseEntity<>(getService().getShelfInfo(shelfId), HttpStatus.OK);
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
    public ResponseEntity<List<ShelfInfo>> requestShelvesInfo(
            @PathVariable(name = IDS) Set<Integer> shelfIds
    ) {
        if(valid(shelfIds)) {
            List<Integer> ids = new ArrayList<>(shelfIds);
            // all ids exist and are owned or request is rejected.
            if(own(ids)) {
                return new ResponseEntity<>(getService().getShelvesInfo(ids), HttpStatus.OK);
            }
            else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public boolean own(List<Integer> shelfIds) {
        return getService().countExistingIds(shelfIds) == shelfIds.size();
    }

    @Override
    public boolean valid(Collection<Integer> shelfIds) {
        return validIntegers(shelfIds);
    }
}
