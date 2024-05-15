package com.nniett.kikaishin.app.controller.web;

import com.nniett.kikaishin.app.controller.construction.ActivateableController;
import com.nniett.kikaishin.app.controller.construction.CanCheckOwnership;
import com.nniett.kikaishin.app.controller.construction.CanVerifyId;
import com.nniett.kikaishin.app.controller.construction.VerifiesIntegerId;
import com.nniett.kikaishin.app.persistence.entity.QuestionEntity;
import com.nniett.kikaishin.app.service.QuestionService;
import com.nniett.kikaishin.app.service.pojo.*;
import com.nniett.kikaishin.app.service.pojo.dto.answer.AnswerCreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.answer.AnswerUpdateDto;
import com.nniett.kikaishin.app.service.pojo.dto.clue.ClueCreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.clue.ClueUpdateDto;
import com.nniett.kikaishin.app.service.pojo.dto.question.QuestionCreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.question.QuestionUpdateDto;
import com.nniett.kikaishin.app.service.pojo.dto.questionreviewgrade.QuestionReviewGradeCreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.review.ReviewCreationDto;
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
@RequestMapping(QUESTION_PATH)
public class QuestionController
        extends ActivateableController
        <
                QuestionEntity,
                Integer,
                Question,
                QuestionCreationDto,
                QuestionUpdateDto,
                QuestionService
                >
        implements CanCheckOwnership<Integer>, CanVerifyId<Integer>, VerifiesIntegerId
{
    private final ShelfController shelfController;
    private final BookController bookController;
    private final TopicController parentController;
    private final AnswerController answerController;
    private final ClueController clueController;
    private final ReviewController reviewController;
    private final QuestionReviewGradeController questionReviewGradeController;

    @Autowired
    public QuestionController(
            QuestionService service,
            ShelfController shelfController,
            BookController bookController,
            TopicController topicController,
            AnswerController answerController,
            ClueController clueController,
            ReviewController reviewController,
            QuestionReviewGradeController questionReviewGradeController
    ) {
        super(service);
        this.shelfController = shelfController;
        this.bookController = bookController;
        this.parentController = topicController;
        this.answerController = answerController;
        this.clueController = clueController;
        this.reviewController = reviewController;
        this.questionReviewGradeController = questionReviewGradeController;
    }

    ///////////// Questions Control

    @Override
    @PostMapping()
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Question> persistNewEntity(@Valid @RequestBody QuestionCreationDto dto) {
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

    @GetMapping(REVIEWS_ENDPOINT + ID_PARAM_PATH)
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Review> getReview(@PathVariable("id") Integer reviewId) {
        if(reviewController.valid(reviewId)) {
            if(reviewController.own(reviewId)) {
                return reviewController.readById(reviewId);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @GetMapping(ID_PARAM_PATH)
    public ResponseEntity<Question> getEntityById(@PathVariable(ID) Integer id) {
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
    public ResponseEntity<Question> updateEntity(@Valid @RequestBody QuestionUpdateDto dto) {
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
    public ResponseEntity<Void> toggleActive(@Valid @RequestBody QuestionUpdateDto dto) {
        if(own(dto.getPK())) {
            return toggleStatus(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    ///////////// Answers Control


    @PostMapping(ANSWERS_ENDPOINT)
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Answer> persistNewAnswer(@Valid @RequestBody AnswerCreationDto dto) {
        if(valid(dto.getParentPK())) {
            if(own(dto.getParentPK())) {
                return answerController.persistNewEntity(dto);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(ANSWERS_ENDPOINT)
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Answer> updateAnswer(@Valid @RequestBody AnswerUpdateDto dto) {
        if(answerController.own(dto.getPK())) {
            return answerController.update(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(ANSWERS_ENDPOINT + ID_PARAM_PATH)
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Void> deleteAnswer(@PathVariable(ID) Integer id) {
        if(answerController.valid(id)) {
            if(answerController.own(id)) {
                return answerController.delete(id);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    ///////////// Clues Control


    @PostMapping(CLUES_ENDPOINT)
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Clue> persistNewClue(@Valid @RequestBody ClueCreationDto dto) {
        if(valid(dto.getParentPK())) {
            if(own(dto.getParentPK())) {
                return clueController.persistNewEntity(dto);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(CLUES_ENDPOINT)
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Clue> updateClue(@Valid @RequestBody ClueUpdateDto dto) {
        if(clueController.own(dto.getPK())) {
            return clueController.update(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(CLUES_ENDPOINT + ID_PARAM_PATH)
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Void> deleteClue(@PathVariable(ID) Integer id) {
        if(clueController.valid(id)) {
            if(clueController.own(id)) {
                return clueController.delete(id);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    ///////////// Reviews Control

    // review entities can only be created or deleted.
    // review deletion happens upon question deletion and not on demand.
    @PostMapping(REVIEWS_ENDPOINT)
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Review> persistNewReview(@Valid @RequestBody ReviewCreationDto dto) {
        List<Integer> questionIds = new ArrayList<>();
        dto.getQuestionGrades().forEach(g -> questionIds.add(g.getQuestionId()));
        if(own(questionIds)) {
            return reviewController.persistNewEntity(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    ///////////// Grades Control


    @PostMapping(GRADES_ENDPOINT)
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<QuestionReviewGrade> persistNewGrade(@Valid @RequestBody QuestionReviewGradeCreationDto dto) {
        if(this.valid(dto.getQuestionId()) && reviewController.valid(dto.getReviewId())) {
            if(this.own(dto.getQuestionId()) && reviewController.own(dto.getReviewId())) {
                return questionReviewGradeController.persistNewEntity(dto);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    ///////////// Review Set Control

    @GetMapping(REVIEWS_SET_ENDPOINT)
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<List<ReviewableQuestion>> requestReviewSet(
            @RequestParam(name = "level")
            String reviewLevel,
            @RequestParam(name = "id", required = false)
            Integer containerId,
            @RequestParam(name = "total")
            Integer totalQuestions,
            @RequestParam(name = "forced", defaultValue = "false")
            Boolean forced
    ) {
        boolean containerExists;

        if(reviewLevel != null) {
            if("all".equals(reviewLevel)) {
                containerExists = true;
            } else if(containerId != null && containerId > 0) {
                switch(reviewLevel) {
                    case "shelf": {
                        containerExists = shelfController.own(containerId);
                    } break;
                    case "book": {
                        containerExists = bookController.own(containerId);
                    } break;
                    case "topic": {
                        containerExists = parentController.own(containerId);
                    } break;
                    default: {
                        return ResponseEntity.badRequest().build();
                    }
                }
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }

        if(containerExists) {
            return new ResponseEntity<>(getService().getReviewSet(reviewLevel, containerId, totalQuestions, forced), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }

    }


    @GetMapping(CUSTOM_REVIEWS_SET_ENDPOINT + IDS_PARAM_PATH)
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<List<ReviewableQuestion>> requestCustomReviewSet(
            @PathVariable(IDS) Set<Integer> questionIds
    ) {
        if(valid(questionIds)) {
            List<Integer> ids = new ArrayList<>(questionIds);
            // all questions exist or request is rejected.
            if(own(ids)) {
                return new ResponseEntity<>(getService().getCustomReviewableList(ids), HttpStatus.OK);
            }
            else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    ///////////// Question Info Control


    @GetMapping(INFO_ENDPOINT + ID_PARAM_PATH)
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<QuestionInfo> requestQuestionInfo(@PathVariable(name = ID) Integer questionId) {
        if(valid(questionId)) {
            if(own(questionId)) {
                return new ResponseEntity<>(getService().getQuestionInfo(questionId), HttpStatus.OK);
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
    public ResponseEntity<List<QuestionInfo>> requestQuestionsInfo(
            @PathVariable(name = IDS) Set<Integer> questionIds
    ) {
        if(valid(questionIds)) {
            List<Integer> ids = new ArrayList<>(questionIds);
            // all questions exist or request is rejected.
            if(own(ids)) {
                return new ResponseEntity<>(getService().getQuestionsInfo(ids), HttpStatus.OK);
            }
            else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    // Recommendation Control


    @GetMapping(RECOMMENDED)
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<List<LabeledRecommendedQuestionList>> requestQuestionsInfo() {
        return ResponseEntity.ok(getService().getRecommendedQuestions());
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
