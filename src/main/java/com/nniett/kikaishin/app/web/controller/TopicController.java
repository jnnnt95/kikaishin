package com.nniett.kikaishin.app.web.controller;

import com.nniett.kikaishin.app.service.dto.TopicDto;
import com.nniett.kikaishin.app.service.dto.TopicInfoDto;
import com.nniett.kikaishin.app.service.dto.write.question.QuestionCreationDto;
import com.nniett.kikaishin.app.service.dto.write.question.QuestionUpdateDto;
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
    private final BookController parentController;

    @Autowired
    public TopicController(TopicService service, BookController bookController) {
        super(service);
        this.parentController = bookController;
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
        if(valid(id)) {
            if(own(id)) {
                return readById(id);
            }
            else {
                return ResponseEntity.notFound().build();
            }
        } else {
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
        if(own(dto.getPK())) {
            return update(dto);
        }
        else {
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
        if(valid(id)) {
            if(own(id)) {
                return delete(id);
            }
            else {
                return ResponseEntity.notFound().build();
            }
        } else {
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
        if(own(dto.getPK())) {
            return toggleStatus(dto);
        } else {
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
        if(valid(topicId)) {
            if(own(topicId)) {
                return new ResponseEntity<>(getService().getTopicInfo(topicId), HttpStatus.OK);
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
        if(valid(topicIds)) {
            List<Integer> ids = new ArrayList<>(topicIds);
            // all topics exist or request is rejected.
            if(own(ids)) {
                return new ResponseEntity<>(getService().getTopicsInfo(ids), HttpStatus.OK);
            }
            else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
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
