package com.nniett.kikaishin.app.web.controller;

import com.nniett.kikaishin.app.service.dto.QuestionReviewGradeDto;
import com.nniett.kikaishin.app.web.controller.construction.ImmutableController;
import com.nniett.kikaishin.app.persistence.entity.QuestionReviewGradeEntity;
import com.nniett.kikaishin.app.service.QuestionReviewGradeService;
import com.nniett.kikaishin.app.service.dto.write.questionreviewgrade.QuestionReviewGradeCreationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping(REVIEW_PATH)
public class QuestionReviewGradeController extends ImmutableController
        <
                QuestionReviewGradeEntity,
                Integer,
                QuestionReviewGradeDto,
                QuestionReviewGradeCreationDto,
                QuestionReviewGradeService
                >

{

    public QuestionReviewGradeController(QuestionReviewGradeService service) {
        super(service);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<QuestionReviewGradeDto> persistNewEntity(QuestionReviewGradeCreationDto dto) {
        return create(dto);
    }

    @Override
    public ResponseEntity<QuestionReviewGradeDto> getEntityById(Integer id) {
        return readById(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Void> deleteEntityById(Integer id) {
        return delete(id);
    }
}
