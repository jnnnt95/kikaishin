package com.nniett.kikaishin.app.service.crud.questionreviewgrade;

import com.nniett.kikaishin.app.persistence.entity.QuestionReviewGradeEntity;
import com.nniett.kikaishin.app.persistence.repository.QuestionReviewGradeRepository;
import com.nniett.kikaishin.app.service.construction.DeleteService;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionReviewGradeDeleteService
        extends DeleteService<QuestionReviewGradeEntity, Integer>
{

    public QuestionReviewGradeDeleteService(QuestionReviewGradeRepository repository) {
        super(repository);
    }

}
