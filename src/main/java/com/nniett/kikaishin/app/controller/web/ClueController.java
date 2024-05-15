package com.nniett.kikaishin.app.controller.web;

import com.nniett.kikaishin.app.controller.construction.CanCheckOwnership;
import com.nniett.kikaishin.app.controller.construction.CanVerifyId;
import com.nniett.kikaishin.app.controller.construction.Controller;
import com.nniett.kikaishin.app.controller.construction.VerifiesIntegerId;
import com.nniett.kikaishin.app.persistence.entity.ClueEntity;
import com.nniett.kikaishin.app.service.ClueService;
import com.nniett.kikaishin.app.service.pojo.Clue;
import com.nniett.kikaishin.app.service.pojo.dto.clue.ClueCreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.clue.ClueUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
//@RequestMapping(REVIEW_PATH)
public class ClueController extends Controller
        <
                ClueEntity,
                Integer,
                Clue,
                ClueCreationDto,
                ClueUpdateDto,
                ClueService
                >
        implements CanCheckOwnership<Integer>, CanVerifyId<Integer>, VerifiesIntegerId
{

    @Autowired
    public ClueController(ClueService service) {
        super(service);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Clue> updateEntity(ClueUpdateDto creationDto) {
        return update(creationDto);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Clue> persistNewEntity(ClueCreationDto dto) {
        return create(dto);
    }

    @Override
    public ResponseEntity<Clue> getEntityById(Integer id) {
        return readById(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Void> deleteEntityById(Integer id) {
        return delete(id);
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
