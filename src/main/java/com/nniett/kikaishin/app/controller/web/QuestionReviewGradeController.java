package com.nniett.kikaishin.app.controller.web;

import com.nniett.kikaishin.app.controller.construction.ImmutableController;
import com.nniett.kikaishin.app.persistence.entity.QuestionReviewGradeEntity;
import com.nniett.kikaishin.app.service.QuestionReviewGradeService;
import com.nniett.kikaishin.app.service.ReviewService;
import com.nniett.kikaishin.app.service.pojo.QuestionReviewGrade;
import com.nniett.kikaishin.app.service.pojo.dto.questionreviewgrade.QuestionReviewGradeCreationDto;
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
                QuestionReviewGrade,
                QuestionReviewGradeCreationDto,
                QuestionReviewGradeService
                >

{

    public QuestionReviewGradeController(QuestionReviewGradeService service) {
        super(service);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<QuestionReviewGrade> persistNewEntity(QuestionReviewGradeCreationDto dto) {
        return create(dto);
    }

    @Override
    public ResponseEntity<QuestionReviewGrade> getEntityById(Integer id) {
        return readById(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Void> deleteEntityById(Integer id) {
        return delete(id);
    }
}
