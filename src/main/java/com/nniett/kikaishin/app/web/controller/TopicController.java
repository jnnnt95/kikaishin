package com.nniett.kikaishin.app.web.controller;

import com.nniett.kikaishin.app.service.dto.TopicDto;
import com.nniett.kikaishin.app.service.dto.TopicInfoDto;
import com.nniett.kikaishin.app.web.controller.construction.ActivateableController;
import com.nniett.kikaishin.app.web.controller.construction.CanCheckOwnership;
import com.nniett.kikaishin.app.web.controller.construction.CanVerifyId;
import com.nniett.kikaishin.app.web.controller.construction.VerifiesIntegerId;
import com.nniett.kikaishin.app.persistence.entity.TopicEntity;
import com.nniett.kikaishin.app.service.TopicService;
import com.nniett.kikaishin.app.service.dto.write.topic.TopicCreationDto;
import com.nniett.kikaishin.app.service.dto.write.topic.TopicUpdateDto;
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
@RequestMapping(TOPIC_PATH)
public class TopicController
        extends ActivateableController
        <
                TopicEntity,
                Integer,
                TopicDto,
                TopicCreationDto,
                TopicUpdateDto,
                TopicService
                >
        implements CanCheckOwnership<Integer>, CanVerifyId<Integer>, VerifiesIntegerId
{
    private static final Logger logger = LoggerFactory.getLogger(TopicController.class);

    private final BookController parentController;

    @Autowired
    public TopicController(TopicService service, BookController bookController) {
        super(service);
        this.parentController = bookController;
        logger.info("TopicController initialized.");
    }

    @Override
    @PostMapping()
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Creates a new topic object for currently logged in user appended to provided book id.")
    @ApiResponses({
            @ApiResponse(description = "Topic created successfully.", responseCode = "200"),
            @ApiResponse(description = "Dto validation failed.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided book id is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = "TopicCreationDto", description = "Name must be provided. " +
                    "For every subsequent child in hierarchy, parent Id must be omitted. " +
                    "For example, if a answer is appended to question creation, question id will be omitted and appended to newly created question object.",
                    schema = @Schema(implementation = TopicCreationDto.class))
    })
    public ResponseEntity<TopicDto> persistNewEntity(@Valid @RequestBody TopicCreationDto dto) {
        logger.debug("Topic requested to be created using method: {}.", "persistNewEntity(TopicCreationDto)");
        logger.trace("Topic creation request body: {}.", dto.toString());
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

    @Override
    @GetMapping(ID_PARAM_PATH)
    @Operation(description = "Retrieves a topic object if owned by logged user.")
    @ApiResponses({
            @ApiResponse(description = "Topic retrieved successfully.", responseCode = "200"),
            @ApiResponse(description = "Provided topic id not valid.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided topic id is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = ID, description = "Topic Id")
    })
    public ResponseEntity<TopicDto> getEntityById(@PathVariable(ID) Integer id) {
        logger.debug("TopicDto requested using method: {}.", "getEntityById(Integer)");
        logger.trace("Expected TopicDto id {}.", id);
        if(valid(id)) {
            if(own(id)) {
                logger.debug("Returning expected positive response.");
                return readById(id);
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

    @Override
    @PutMapping()
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Updates and retrieves a topic object if owned by logged user.")
    @ApiResponses({
            @ApiResponse(description = "Topic updated successfully.", responseCode = "200"),
            @ApiResponse(description = "Dto validation failed.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided topic id is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = "TopicUpdateDto", schema = @Schema(implementation = TopicUpdateDto.class))
    })
    public ResponseEntity<TopicDto> updateEntity(@Valid @RequestBody TopicUpdateDto dto) {
        logger.debug("Topic update requested using method: {}.", "updateEntity(TopicUpdateDto)");
        logger.trace("Topic update request body: {}.", dto.toString());
        if(own(dto.getPK())) {
            logger.debug("Returning expected positive response.");
            return update(dto);
        }
        else {
            logger.debug("Returning not found response.");
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(ID_PARAM_PATH)
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Deletes topic object if owned by logged user and all related children such as questions, answers, clues, review model, reviews and grades.")
    @ApiResponses({
            @ApiResponse(description = "Deletion performed successfully.", responseCode = "200"),
            @ApiResponse(description = "Provided topic id not valid.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided topic id is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = ID, description = "Topic Id")
    })
    public ResponseEntity<Void> deleteEntityById(@PathVariable(ID) Integer id) {
        logger.debug("Topic deletion requested using method: {}.", "deleteEntityById(Integer)");
        logger.trace("Expected deletion happening on id {}.", id);
        if(valid(id)) {
            if(own(id)) {
                logger.debug("Returning expected positive response.");
                return delete(id);
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

    @Override
    @PutMapping(TOGGLE_STATUS_PATH)
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Change active status of topic entity.")
    @ApiResponses({
            @ApiResponse(description = "Status change performed successfully.", responseCode = "200"),
            @ApiResponse(description = "Dto validation failed.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided topic id is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = "TopicUpdateDto", schema = @Schema(implementation = TopicUpdateDto.class))
    })
    public ResponseEntity<Void> toggleActive(@Valid @RequestBody TopicUpdateDto dto) {
        logger.debug("Topic toggle action using method: {}.", "toggleActive(TopicUpdateDto)");
        logger.trace("Expected toggle with id {}.", dto.getTopicId());
        if(own(dto.getPK())) {
            logger.debug("Returning expected positive response.");
            return toggleStatus(dto);
        } else {
            logger.debug("Returning not found response.");
            return ResponseEntity.notFound().build();
        }
    }


    ///////////// Topic Info Control


    @GetMapping(INFO_ENDPOINT + ID_PARAM_PATH)
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Retrieves summary information regarding topic and children objects.")
    @ApiResponses({
            @ApiResponse(description = "Information retrieved successfully.", responseCode = "200"),
            @ApiResponse(description = "Provided topic id not valid.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided topic id is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = ID, description = "Topic Id")
    })
    public ResponseEntity<TopicInfoDto> requestTopicInfo(
            @PathVariable(name = ID) Integer topicId
    ) {
        logger.debug("TopicInfoDto requested using method: {}.", "requestTopicInfo(Integer)");
        logger.trace("Expected TopicInfoDto id {}.", topicId);
        if(valid(topicId)) {
            if(own(topicId)) {
                logger.debug("Returning expected positive response.");
                return new ResponseEntity<>(getService().getTopicInfo(topicId), HttpStatus.OK);
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
    @Operation(description = "Retrieves summary information regarding topics and children objects.")
    @ApiResponses({
            @ApiResponse(description = "Information retrieved successfully.", responseCode = "200"),
            @ApiResponse(description = "At least one of provided topic ids not valid.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "At least one of provided topic ids is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = IDS, description = "List of Topic Ids")
    })
    public ResponseEntity<List<TopicInfoDto>> requestTopicsInfo(
            @PathVariable(name = IDS) Set<Integer> topicIds
    ) {
        logger.debug("TopicInfoDto list requested using method: {}.", "requestTopicsInfo(Set<Integer>)");
        logger.trace("Expected TopicInfoDto ids {}.", topicIds);
        if(valid(topicIds)) {
            List<Integer> ids = new ArrayList<>(topicIds);
            // all topics exist or request is rejected.
            if(own(ids)) {
                logger.debug("Returning expected positive response.");
                return new ResponseEntity<>(getService().getTopicsInfo(ids), HttpStatus.OK);
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

    @Override
    public boolean own(List<Integer> ids) {
        logger.debug("Topic ids validation for ownership.");
        logger.trace("Checking if ids are own. Ids: {}.", ids.toString());
        boolean ownCheck = getService().countExistingIds(ids) == ids.size();
        if(ownCheck) {
            logger.trace("Ids are own. Ids: {}.", ids);
        } else {
            logger.trace("One or more values in list are not own. Ids: {}.", ids);
        }
        logger.debug("Topic ids validation for ownership completed.");
        return ownCheck;
    }

    @Override
    public boolean valid(Collection<Integer> ids) {
        logger.debug("Topic ids validation.");
        logger.trace("Checking if ids are valid. Ids: {}", ids);
        boolean validCheck = validIntegers(ids);
        if(validCheck) {
            logger.trace("Ids are valid. Ids: {}.", ids);
        } else {
            logger.trace("One or more values in list are not valid. Ids: {}.", ids);
        }
        logger.debug("Topic ids validation completed.");
        return validCheck;
    }
}
