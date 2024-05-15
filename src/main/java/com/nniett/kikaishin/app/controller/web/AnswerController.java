package com.nniett.kikaishin.app.controller.web;

import com.nniett.kikaishin.app.controller.construction.*;
import com.nniett.kikaishin.app.persistence.entity.AnswerEntity;
import com.nniett.kikaishin.app.persistence.entity.ReviewEntity;
import com.nniett.kikaishin.app.service.AnswerService;
import com.nniett.kikaishin.app.service.ReviewService;
import com.nniett.kikaishin.app.service.pojo.Answer;
import com.nniett.kikaishin.app.service.pojo.Review;
import com.nniett.kikaishin.app.service.pojo.dto.answer.AnswerCreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.answer.AnswerUpdateDto;
import com.nniett.kikaishin.app.service.pojo.dto.review.ReviewCreationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
//@RequestMapping(REVIEW_PATH)
public class AnswerController extends Controller
        <
                AnswerEntity,
                Integer,
                Answer,
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
    public ResponseEntity<Answer> updateEntity(AnswerUpdateDto creationDto) {
        return update(creationDto);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Answer> persistNewEntity(AnswerCreationDto dto) {
        return create(dto);
    }

    @Override
    public ResponseEntity<Answer> getEntityById(Integer id) {
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
