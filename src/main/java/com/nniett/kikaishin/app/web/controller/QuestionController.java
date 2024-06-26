package com.nniett.kikaishin.app.web.controller;

import com.nniett.kikaishin.app.web.controller.construction.ActivateableController;
import com.nniett.kikaishin.app.web.controller.construction.CanCheckOwnership;
import com.nniett.kikaishin.app.web.controller.construction.CanVerifyId;
import com.nniett.kikaishin.app.web.controller.construction.VerifiesIntegerId;
import com.nniett.kikaishin.app.persistence.entity.QuestionEntity;
import com.nniett.kikaishin.app.service.QuestionService;
import com.nniett.kikaishin.app.service.dto.*;
import com.nniett.kikaishin.app.service.dto.write.answer.AnswerCreationDto;
import com.nniett.kikaishin.app.service.dto.write.answer.AnswerUpdateDto;
import com.nniett.kikaishin.app.service.dto.write.clue.ClueCreationDto;
import com.nniett.kikaishin.app.service.dto.write.clue.ClueUpdateDto;
import com.nniett.kikaishin.app.service.dto.write.question.QuestionCreationDto;
import com.nniett.kikaishin.app.service.dto.write.question.QuestionUpdateDto;
import com.nniett.kikaishin.app.service.dto.write.questionreviewgrade.QuestionReviewGradeCreationDto;
import com.nniett.kikaishin.app.service.dto.write.review.ReviewCreationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
                QuestionDto,
                QuestionCreationDto,
                QuestionUpdateDto,
                QuestionService
                >
        implements CanCheckOwnership<Integer>, CanVerifyId<Integer>, VerifiesIntegerId
{
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

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
        logger.info("QuestionController initialized.");
    }

    ///////////// Questions Control

    @Override
    @PostMapping()
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Creates a new question object for currently logged in user appended to provided topic id.")
    @ApiResponses({
            @ApiResponse(description = "Question created successfully.", responseCode = "200"),
            @ApiResponse(description = "Dto validation failed.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided topic id is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = "QuestionCreationDto", description = "Question body (question text) must be provided. " +
                    "Clue list (clues) can be omitted (null). " +
                    "At least one answer must be provided for creating a question. " +
                    "Review model must be provided for creating a question. This must contain with at least value for x0. " +
                    "For every subsequent child in hierarchy, parent Id must be omitted. " +
                    "For example, if a clue is appended to question creation, clue id will be omitted and appended to newly created question object.",
                    schema = @Schema(implementation = QuestionCreationDto.class))
    })
    public ResponseEntity<QuestionDto> persistNewEntity(@Valid @RequestBody QuestionCreationDto dto) {
        logger.debug("Question requested to be created using method: {}.", "persistNewEntity(QuestionCreationDto)");
        logger.trace("Question creation request body: {}.", dto.toString());
        if(parentController.valid(dto.getParentPK())) {
            if(parentController.own(dto.getParentPK())) {
                logger.debug("Returning expected positive response.");
                return create(dto);
            } else {
                logger.debug("Returning not found response.");
                return ResponseEntity.notFound().build();
            }
        } else {
            logger.debug("Returning bad request response.");
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(REVIEWS_ENDPOINT + ID_PARAM_PATH)
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Retrieves a review object if owned by logged user.")
    @ApiResponses({
            @ApiResponse(description = "Review retrieved successfully.", responseCode = "200"),
            @ApiResponse(description = "Provided review id not valid.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided review id is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = ID, description = "Review Id")
    })
    public ResponseEntity<ReviewDto> getReview(@PathVariable(ID) Integer reviewId) {
        logger.debug("ReviewDto requested using method: {}.", "getReview(Integer)");
        logger.trace("Expected ReviewDto id {}.", reviewId);
        if(reviewController.valid(reviewId)) {
            if(reviewController.own(reviewId)) {
                logger.debug("Returning expected positive response.");
                return reviewController.readById(reviewId);
            } else {
                logger.debug("Returning not found response.");
                return ResponseEntity.notFound().build();
            }
        } else {
            logger.debug("Returning bad request response.");
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @GetMapping(ID_PARAM_PATH)
    @Operation(description = "Retrieves a question object if owned by logged user.")
    @ApiResponses({
            @ApiResponse(description = "Question retrieved successfully.", responseCode = "200"),
            @ApiResponse(description = "Provided question id not valid.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided question id is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = ID, description = "Question Id")
    })
    public ResponseEntity<QuestionDto> getEntityById(@PathVariable(ID) Integer id) {
        logger.debug("QuestionDto requested using method: {}.", "getEntityById(Integer)");
        logger.trace("Expected QuestionDto id {}.", id);
        if(valid(id)) {
            if(own(id)) {
                logger.debug("Returning expected positive response.");
                return readById(id);
            } else {
                logger.debug("Returning not found response.");
                return ResponseEntity.notFound().build();
            }
        } else {
            logger.debug("Returning bad request response.");
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @PutMapping()
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Updates and retrieves a book object if owned by logged user.")
    @ApiResponses({
            @ApiResponse(description = "Question updated successfully.", responseCode = "200"),
            @ApiResponse(description = "Dto validation failed.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided questions id is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = "QuestionUpdateDto", schema = @Schema(implementation = QuestionUpdateDto.class))
    })
    public ResponseEntity<QuestionDto> updateEntity(@Valid @RequestBody QuestionUpdateDto dto) {
        logger.debug("Question update requested using method: {}.", "updateEntity(QuestionUpdateDto)");
        logger.trace("Question update request body: {}.", dto.toString());
        if(own(dto.getPK())) {
            logger.debug("Returning expected positive response.");
            return update(dto);
        } else {
            logger.debug("Returning not found response.");
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(ID_PARAM_PATH)
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Deletes question object if owned by logged user and all related children such as answers, clues, review model, reviews and grades.")
    @ApiResponses({
            @ApiResponse(description = "Deletion performed successfully.", responseCode = "200"),
            @ApiResponse(description = "Provided question id not valid.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided question id is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = ID, description = "Question Id")
    })
    public ResponseEntity<Void> deleteEntityById(@PathVariable(ID) Integer id) {
        logger.debug("Question deletion requested using method: {}.", "deleteEntityById(Integer)");
        logger.trace("Expected deletion happening on id {}.", id);
        if(valid(id)) {
            if(own(id)) {
                logger.debug("Returning expected positive response.");
                return delete(id);
            } else {
                logger.debug("Returning not found response.");
                return ResponseEntity.notFound().build();
            }
        } else {
            logger.debug("Returning bad request response.");
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @PutMapping(TOGGLE_STATUS_PATH)
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Change active status of question entity.")
    @ApiResponses({
            @ApiResponse(description = "Status change performed successfully.", responseCode = "200"),
            @ApiResponse(description = "Dto validation failed.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided question id is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = "QuestionUpdateDto", schema = @Schema(implementation = QuestionUpdateDto.class))
    })
    public ResponseEntity<Void> toggleActive(@Valid @RequestBody QuestionUpdateDto dto) {
        logger.debug("Question toggle action using method: {}.", "toggleActive(QuestionUpdateDto)");
        logger.trace("Expected toggle with id {}.", dto.getQuestionId());
        if(own(dto.getPK())) {
            logger.debug("Returning expected positive response.");
            return toggleStatus(dto);
        } else {
            logger.debug("Returning not found response.");
            return ResponseEntity.notFound().build();
        }
    }


    ///////////// Answers Control


    @PostMapping(ANSWERS_ENDPOINT)
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Creates a new answer object for currently logged in user appended to provided question id.")
    @ApiResponses({
            @ApiResponse(description = "Answer created successfully.", responseCode = "200"),
            @ApiResponse(description = "Dto validation failed.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided question id is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = "AnswerCreationDto", description = "Body (answer text) must be provided. " +
                    "Answer order index must be provided. ",
                    schema = @Schema(implementation = AnswerCreationDto.class))
    })
    public ResponseEntity<AnswerDto> persistNewAnswer(@Valid @RequestBody AnswerCreationDto dto) {
        logger.debug("Answer requested to be created using method: {}.", "persistNewAnswer(AnswerCreationDto)");
        logger.trace("Answer creation request body: {}.", dto.toString());
        if(valid(dto.getParentPK())) {
            if(own(dto.getParentPK())) {
                logger.debug("Returning expected positive response.");
                return answerController.persistNewEntity(dto);
            } else {
                logger.debug("Returning not found response.");
                return ResponseEntity.notFound().build();
            }
        } else {
            logger.debug("Returning bad request response.");
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(ANSWERS_ENDPOINT)
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Updates and retrieves an answer object if owned by logged user.")
    @ApiResponses({
            @ApiResponse(description = "Answer updated successfully.", responseCode = "200"),
            @ApiResponse(description = "Dto validation failed.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided answer id is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = "AnswerUpdateDto", schema = @Schema(implementation = AnswerUpdateDto.class))
    })
    public ResponseEntity<AnswerDto> updateAnswer(@Valid @RequestBody AnswerUpdateDto dto) {
        logger.debug("Answer update requested using method: {}.", "updateAnswer(AnswerUpdateDto)");
        logger.trace("Answer update request body: {}.", dto.toString());
        if(answerController.own(dto.getPK())) {
            logger.debug("Returning expected positive response.");
            return answerController.update(dto);
        } else {
            logger.debug("Returning not found response.");
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(ANSWERS_ENDPOINT + ID_PARAM_PATH)
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Deletes answer object if owned by logged user.")
    @ApiResponses({
            @ApiResponse(description = "Deletion performed successfully.", responseCode = "200"),
            @ApiResponse(description = "Provided answer id not valid.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided answer id is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = ID, description = "Answer Id")
    })
    public ResponseEntity<Void> deleteAnswer(@PathVariable(ID) Integer id) {
        logger.debug("Answer deletion requested using method: {}.", "deleteAnswer(Integer)");
        logger.trace("Expected deletion happening on id {}.", id);
        if(answerController.valid(id)) {
            if(answerController.own(id)) {
                logger.debug("Returning expected positive response.");
                return answerController.delete(id);
            } else {
                logger.debug("Returning not found response.");
                return ResponseEntity.notFound().build();
            }
        } else {
            logger.debug("Returning bad request response.");
            return ResponseEntity.badRequest().build();
        }
    }


    ///////////// Clues Control


    @PostMapping(CLUES_ENDPOINT)
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Creates a new clue object for currently logged in user appended to provided question id.")
    @ApiResponses({
            @ApiResponse(description = "Clue created successfully.", responseCode = "200"),
            @ApiResponse(description = "Dto validation failed.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided question id is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = "ClueCreationDto", description = "Body (clue text) must be provided. " +
                    "Clue order index must be provided. ",
                    schema = @Schema(implementation = ClueCreationDto.class))
    })
    public ResponseEntity<ClueDto> persistNewClue(@Valid @RequestBody ClueCreationDto dto) {
        logger.debug("Clue requested to be created using method: {}.", "persistNewClue(ClueCreationDto)");
        logger.trace("Clue creation request body: {}.", dto.toString());
        if(valid(dto.getParentPK())) {
            if(own(dto.getParentPK())) {
                logger.debug("Returning expected positive response.");
                return clueController.persistNewEntity(dto);
            } else {
                logger.debug("Returning not found response.");
                return ResponseEntity.notFound().build();
            }
        } else {
            logger.debug("Returning bad request response.");
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(CLUES_ENDPOINT)
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Updates and retrieves a clue object if owned by logged user.")
    @ApiResponses({
            @ApiResponse(description = "Clue updated successfully.", responseCode = "200"),
            @ApiResponse(description = "Dto validation failed.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided clue id is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = "ClueUpdateDto", schema = @Schema(implementation = ClueUpdateDto.class))
    })
    public ResponseEntity<ClueDto> updateClue(@Valid @RequestBody ClueUpdateDto dto) {
        logger.debug("Clue update requested using method: {}.", "updateClue(ClueUpdateDto)");
        logger.trace("Clue update request body: {}.", dto.toString());
        if(clueController.own(dto.getPK())) {
            logger.debug("Returning expected positive response.");
            return clueController.update(dto);
        } else {
            logger.debug("Returning not found response.");
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(CLUES_ENDPOINT + ID_PARAM_PATH)
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Deletes clue object if owned by logged user.")
    @ApiResponses({
            @ApiResponse(description = "Deletion performed successfully.", responseCode = "200"),
            @ApiResponse(description = "Provided clue id not valid.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided clue id is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = ID, description = "Clue Id")
    })
    public ResponseEntity<Void> deleteClue(@PathVariable(ID) Integer id) {
        logger.debug("Clue deletion requested using method: {}.", "deleteClue(Integer)");
        logger.trace("Expected deletion happening on id {}.", id);
        if(clueController.valid(id)) {
            if(clueController.own(id)) {
                logger.debug("Returning expected positive response.");
                return clueController.delete(id);
            } else {
                logger.debug("Returning not found response.");
                return ResponseEntity.notFound().build();
            }
        } else {
            logger.debug("Returning bad request response.");
            return ResponseEntity.badRequest().build();
        }
    }


    ///////////// Reviews Control

    // review entities can only be created or deleted.
    // review deletion happens upon question deletion and not on demand.
    @PostMapping(REVIEWS_ENDPOINT)
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Creates a new review object for currently logged in user appended to provided question id.")
    @ApiResponses({
            @ApiResponse(description = "Review created successfully.", responseCode = "200"),
            @ApiResponse(description = "Dto validation failed.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided question id is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = "ReviewCreationDto", description = "At least one question grade (see QuestionReviewGradeCreationDto) must be provided for creating a review. ",
                    schema = @Schema(implementation = ReviewCreationDto.class))
    })
    public ResponseEntity<ReviewDto> persistNewReview(@Valid @RequestBody ReviewCreationDto dto) {
        logger.debug("Review requested to be created using method: {}.", "persistNewReview(ReviewCreationDto)");
        logger.trace("Review creation request body: {}.", dto.toString());
        List<Integer> questionIds = new ArrayList<>();
        dto.getQuestionGrades().forEach(g -> questionIds.add(g.getQuestionId()));
        if(valid(questionIds)) {
            if(own(questionIds)) {
                logger.debug("Returning expected positive response.");
                return reviewController.persistNewEntity(dto);
            } else {
                logger.debug("Returning not found response.");
                return ResponseEntity.notFound().build();
            }
        } else {
            logger.debug("Returning bad request response.");
            return ResponseEntity.badRequest().build();
        }
    }


    ///////////// Grades Control


    @PostMapping(GRADES_ENDPOINT)
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Creates a new question grade object for currently logged in user appended to provided question id.")
    @ApiResponses({
            @ApiResponse(description = "Question Grade created successfully.", responseCode = "200"),
            @ApiResponse(description = "Dto validation failed. Question or review id not valid.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided question id and/or review id not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = "QuestionReviewGradeCreationDto", description = "Question Id must be provided. " +
                    "Review Id must be provided. " +
                    "Grade value must be provided. " +
                    "Grade value can vary within integer range 0 to 5 (inclusive both).",
                    schema = @Schema(implementation = QuestionReviewGradeCreationDto.class))
    })
    public ResponseEntity<QuestionReviewGradeDto> persistNewGrade(
            @Valid @RequestBody QuestionReviewGradeCreationDto dto
    ) {
        logger.debug("Question Review Grade requested to be created using method: {}.", "persistNewGrade(QuestionReviewGradeCreationDto)");
        logger.trace("Question Review Grade creation request body: {}.", dto.toString());
        if(this.valid(dto.getQuestionId()) && reviewController.valid(dto.getReviewId())) {
            if(this.own(dto.getQuestionId()) && reviewController.own(dto.getReviewId())) {
                logger.debug("Returning expected positive response.");
                return questionReviewGradeController.persistNewEntity(dto);
            } else {
                logger.debug("Returning not found response.");
                return ResponseEntity.notFound().build();
            }
        } else {
            logger.debug("Returning bad request response.");
            return ResponseEntity.badRequest().build();
        }
    }

    ///////////// Review Set Control

    @GetMapping(REVIEWS_SET_ENDPOINT)
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Retrieves list of user's questions that are ready to be reviewed.")
    @ApiResponses({
            @ApiResponse(description = "Review questions retrieved successfully.", responseCode = "200"),
            @ApiResponse(description = "Review level or container id is not valid. ", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided container id is not existent (if not \"all\") or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = "level", description = "One of the following options: all, shelf, book, topic. These options are containers subject to analysis to provide list of reviewable questions."),
            @Parameter(name = "id", description = "Id of container to be analyzed. Must coincide to provided \"level\". " +
                    "For example, if level is book and id is 1, book with id 1 will be used."),
            @Parameter(name = "total", description = "Maximum number of reviewable questions that can contain response list."),
            @Parameter(name = "forced", description =
                    "Flag indicating if property \"is to review\" of a question should be considered. If set to true, " +
                            "the question will be added to response reviewable question list even if not considered as " +
                            "such for the session.")
    })
    public ResponseEntity<List<ReviewableQuestionDto>> requestReviewSet(
            @RequestParam(name = "level")
            String reviewLevel,
            @RequestParam(name = "id", required = false)
            Integer containerId,
            @RequestParam(name = "total")
            Integer totalQuestions,
            @RequestParam(name = "forced", defaultValue = "false")
            Boolean forced
    ) {
        logger.debug("ReviewableQuestionDto requested using method: {}.", "requestReviewSet(String, Integer, Integer, Boolean)");
        logger.trace("Expected ReviewableQuestionDto reviewLevel {}, containerId {}, totalQuestions {}, forced {}.", reviewLevel, containerId, totalQuestions, forced);

        boolean containerExists;

        if(reviewLevel != null) {
            if("ALL".equalsIgnoreCase(reviewLevel)) {
                logger.trace("All level selected.");
                containerExists = true;
            } else if(containerId != null && containerId > 0) {
                switch(reviewLevel.toUpperCase()) {
                    case "SHELF": {
                        logger.trace("Shelf level selected.");
                        containerExists = shelfController.own(containerId);
                    } break;
                    case "BOOK": {
                        logger.trace("Book level selected.");
                        containerExists = bookController.own(containerId);
                    } break;
                    case "TOPIC": {
                        logger.trace("Topic level selected.");
                        containerExists = parentController.own(containerId);
                    } break;
                    default: {
                        logger.debug("Returning bad request response.");
                        return ResponseEntity.badRequest().build();
                    }
                }
            } else {
                logger.debug("Returning bad request response.");
                return ResponseEntity.badRequest().build();
            }
        } else {
            logger.trace("No review level provided. value: null.");
            logger.debug("Returning bad request response.");
            return ResponseEntity.badRequest().build();
        }

        if(containerExists) {
            logger.debug("Returning expected positive response.");
            return new ResponseEntity<>(getService().getReviewSet(reviewLevel, containerId, totalQuestions, forced), HttpStatus.OK);
        } else {
            logger.debug("Returning not found response.");
            return ResponseEntity.notFound().build();
        }

    }


    @GetMapping(CUSTOM_REVIEWS_SET_ENDPOINT + IDS_PARAM_PATH)
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Retrieves custom list of user's as reviewable list.")
    @ApiResponses({
            @ApiResponse(description = "Review questions retrieved successfully.", responseCode = "200"),
            @ApiResponse(description = "At least one of the listed question ids is not valid. ", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "At least one of the listed question ids is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = IDS, description = "List of Question Ids"),
    })
    public ResponseEntity<List<ReviewableQuestionDto>> requestCustomReviewSet(
            @PathVariable(IDS) Set<Integer> questionIds
    ) {
        logger.debug("ReviewableQuestionDto custom list requested using method: {}.", "requestCustomReviewSet(Set<Integer>)");
        logger.trace("Expected ReviewableQuestionDto ids {}.", questionIds);
        if(valid(questionIds)) {
            List<Integer> ids = new ArrayList<>(questionIds);
            // all questions exist or request is rejected.
            if(own(ids)) {
                logger.debug("Returning expected positive response.");
                return new ResponseEntity<>(getService().getCustomReviewableList(ids), HttpStatus.OK);
            }
            else {
                logger.debug("Returning not found response.");
                return ResponseEntity.notFound().build();
            }
        } else {
            logger.debug("Returning bad request response.");
            return ResponseEntity.badRequest().build();
        }
    }


    ///////////// Question Info Control


    @GetMapping(INFO_ENDPOINT + ID_PARAM_PATH)
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Retrieves summary information regarding question and children objects.")
    @ApiResponses({
            @ApiResponse(description = "Information retrieved successfully.", responseCode = "200"),
            @ApiResponse(description = "Provided question id not valid.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided question id is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = ID, description = "Question Id")
    })
    public ResponseEntity<QuestionInfoDto> requestQuestionInfo(@PathVariable(name = ID) Integer id) {
        logger.debug("QuestionInfoDto requested using method: {}.", "requestQuestionInfo(Integer)");
        logger.trace("Expected QuestionInfoDto id {}.", id);
        if(valid(id)) {
            if(own(id)) {
                logger.debug("Returning expected positive response.");
                return new ResponseEntity<>(getService().getQuestionInfo(id), HttpStatus.OK);
            }
            else {
                logger.debug("Returning not found response.");
                return ResponseEntity.notFound().build();
            }
        } else {
            logger.debug("Returning bad request response.");
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(MULTI_INFO_ENDPOINT + IDS_PARAM_PATH)
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Retrieves summary information regarding questions and children objects.")
    @ApiResponses({
            @ApiResponse(description = "Information retrieved successfully.", responseCode = "200"),
            @ApiResponse(description = "At least one of provided question ids not valid.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "At least one of provided question ids is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = IDS, description = "List of Question Ids")
    })
    public ResponseEntity<List<QuestionInfoDto>> requestQuestionsInfo(
            @PathVariable(name = IDS) Set<Integer> questionIds
    ) {
        logger.debug("QuestionInfoDto list requested using method: {}.", "requestQuestionsInfo(Set<Integer>)");
        logger.trace("Expected QuestionInfoDto ids {}.", questionIds);
        if(valid(questionIds)) {
            List<Integer> ids = new ArrayList<>(questionIds);
            // all questions exist or request is rejected.
            if(own(ids)) {
                logger.debug("Returning expected positive response.");
                return new ResponseEntity<>(getService().getQuestionsInfo(ids), HttpStatus.OK);
            }
            else {
                logger.debug("Returning not found response.");
                return ResponseEntity.notFound().build();
            }
        } else {
            logger.debug("Returning bad request response.");
            return ResponseEntity.badRequest().build();
        }
    }


    // Recommendation Control


    @GetMapping(RECOMMENDED)
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Retrieves recommended questions to review and reason why.")
    @ApiResponses({
            @ApiResponse(description = "Recommended list retrieved successfully.", responseCode = "200")
    })
    public ResponseEntity<List<LabeledRecommendedQuestionListDto>> requestRecommendedQuestions() {
        logger.debug("LabeledRecommendedQuestionListDto list requested using method: {}.", "requestRecommendedQuestions()");
        return ResponseEntity.ok(getService().getRecommendedQuestions());
    }

    @Override
    public boolean own(List<Integer> ids) {
        logger.debug("Question ids validation for ownership.");
        logger.trace("Checking if ids are own. Ids: {}.", ids.toString());
        boolean ownCheck = getService().countExistingIds(ids) == ids.size();
        if(ownCheck) {
            logger.trace("Ids are own. Ids: {}.", ids);
        } else {
            logger.trace("One or more values in list are not own. Ids: {}.", ids);
        }
        logger.debug("Question ids validation for ownership completed.");
        return ownCheck;
    }

    @Override
    public boolean valid(Collection<Integer> ids) {
        logger.debug("Question ids validation.");
        logger.trace("Checking if ids are valid. Ids: {}", ids);
        boolean validCheck = validIntegers(ids);
        if(validCheck) {
            logger.trace("Ids are valid. Ids: {}.", ids);
        } else {
            logger.trace("One or more values in list are not valid. Ids: {}.", ids);
        }
        logger.debug("Question ids validation completed.");
        return validCheck;
    }
}
