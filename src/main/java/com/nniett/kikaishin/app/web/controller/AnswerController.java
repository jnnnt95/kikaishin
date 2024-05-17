package com.nniett.kikaishin.app.web.controller;

import com.nniett.kikaishin.app.service.dto.AnswerDto;
import com.nniett.kikaishin.app.persistence.entity.AnswerEntity;
import com.nniett.kikaishin.app.service.AnswerService;
import com.nniett.kikaishin.app.service.dto.write.answer.AnswerCreationDto;
import com.nniett.kikaishin.app.service.dto.write.answer.AnswerUpdateDto;
import com.nniett.kikaishin.app.web.controller.construction.CanCheckOwnership;
import com.nniett.kikaishin.app.web.controller.construction.CanVerifyId;
import com.nniett.kikaishin.app.web.controller.construction.Controller;
import com.nniett.kikaishin.app.web.controller.construction.VerifiesIntegerId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
public class AnswerController extends Controller
        <
                AnswerEntity,
                Integer,
                AnswerDto,
                AnswerCreationDto,
                AnswerUpdateDto,
                AnswerService
                >
        implements CanCheckOwnership<Integer>, CanVerifyId<Integer>, VerifiesIntegerId
{

    @Autowired
    public AnswerController(AnswerService service) {
        super(service);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<AnswerDto> updateEntity(AnswerUpdateDto creationDto) {
        return update(creationDto);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<AnswerDto> persistNewEntity(AnswerCreationDto dto) {
        return create(dto);
    }

    @Override
    public ResponseEntity<AnswerDto> getEntityById(Integer id) {
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
