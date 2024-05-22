package com.nniett.kikaishin.app.web.controller;

import com.nniett.kikaishin.app.service.dto.ShelfDto;
import com.nniett.kikaishin.app.service.dto.ShelfInfoDto;
import com.nniett.kikaishin.app.web.controller.construction.ActivateableController;
import com.nniett.kikaishin.app.web.controller.construction.CanCheckOwnership;
import com.nniett.kikaishin.app.web.controller.construction.CanVerifyId;
import com.nniett.kikaishin.app.web.controller.construction.VerifiesIntegerId;
import com.nniett.kikaishin.app.persistence.entity.ShelfEntity;
import com.nniett.kikaishin.app.service.ShelfService;
import com.nniett.kikaishin.app.service.dto.write.shelf.ShelfCreationDto;
import com.nniett.kikaishin.app.service.dto.write.shelf.ShelfUpdateDto;
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
@RequestMapping(SHELF_PATH)
public class ShelfController
        extends ActivateableController
        <
                ShelfEntity,
                Integer,
                ShelfDto,
                ShelfCreationDto,
                ShelfUpdateDto,
                ShelfService
                >
    implements CanCheckOwnership<Integer>, CanVerifyId<Integer>, VerifiesIntegerId
{

    private static final Logger logger = LoggerFactory.getLogger(ShelfController.class);

    @Autowired
    public ShelfController(ShelfService service) {
        super(service);
        logger.info("ShelfController initialized.");
    }

    @Override
    @PostMapping()
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Creates a new shelf object for currently logged in user.")
    @ApiResponses({
            @ApiResponse(description = "Shelf created successfully.", responseCode = "200"),
            @ApiResponse(description = "Dto validation failed.", responseCode = "400", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = "ShelfCreationDto", description = "Name must be provided. " +
                    "For every subsequent child in hierarchy, parent Id must be omitted. " +
                    "For example, if a topic is appended to book creation, topic id will be omitted and appended to newly created book object.",
                    schema = @Schema(implementation = ShelfCreationDto.class))
    })
    public ResponseEntity<ShelfDto> persistNewEntity(@Valid @RequestBody ShelfCreationDto dto) {
        logger.debug("Shelf requested to be created using method: {}.", "persistNewEntity(ShelfCreationDto)");
        logger.trace("Shelf creation request body: {}.", dto.toString());
        return create(dto);
    }

    @Override
    @GetMapping(ID_PARAM_PATH)
    @Operation(description = "Retrieves a shelf object if owned by logged user.")
    @ApiResponses({
            @ApiResponse(description = "Shelf retrieved successfully.", responseCode = "200"),
            @ApiResponse(description = "Provided shelf id not valid.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided shelf id is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = ID, description = "Shelf Id")
    })
    public ResponseEntity<ShelfDto> getEntityById(@PathVariable(ID) Integer id) {
        logger.debug("ShelfDto requested using method: {}.", "getEntityById(Integer)");
        logger.trace("Expected ShelfDto with id {}.", id);
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
    @Operation(description = "Updates and retrieves a shelf object if owned by logged user.")
    @ApiResponses({
            @ApiResponse(description = "Shelf updated successfully.", responseCode = "200"),
            @ApiResponse(description = "Dto validation failed.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided shelf id is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = "ShelfUpdateDto", schema = @Schema(implementation = ShelfUpdateDto.class))
    })
    public ResponseEntity<ShelfDto> updateEntity(@Valid @RequestBody ShelfUpdateDto dto) {
        logger.debug("Shelf update requested using method: {}.", "updateEntity(ShelfUpdateDto)");
        logger.trace("Shelf update request body: {}.", dto.toString());
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
    @Operation(description = "Deletes shelf object if owned by logged user and all related children such as book, topics, answers, clues, review model, reviews and grades.")
    @ApiResponses({
            @ApiResponse(description = "Deletion performed successfully.", responseCode = "200"),
            @ApiResponse(description = "Provided shelf id not valid.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided shelf id is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = ID, description = "Shelf Id")
    })
    public ResponseEntity<Void> deleteEntityById(@PathVariable(ID) Integer id) {
        logger.debug("Shelf deletion requested using method: {}.", "deleteEntityById(Integer)");
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
    @PutMapping("/toggle_status")
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Change active status of shelf entity.")
    @ApiResponses({
            @ApiResponse(description = "Status change performed successfully.", responseCode = "200"),
            @ApiResponse(description = "Dto validation failed.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided shelf id is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = "ShelfUpdateDto", schema = @Schema(implementation = ShelfUpdateDto.class))
    })
    public ResponseEntity<Void> toggleActive(@Valid @RequestBody ShelfUpdateDto dto) {
        logger.debug("Shelf toggle action using method: {}.", "toggleActive(ShelfUpdateDto)");
        logger.trace("Expected toggle with id {}.", dto.getShelfId());
        if(own(dto.getPK())) {
            logger.debug("Returning expected positive response.");
            return toggleStatus(dto);
        } else {
            logger.debug("Returning not found response.");
            return ResponseEntity.notFound().build();
        }
    }


    ///////////// Shelf Info Control


    @GetMapping(INFO_ENDPOINT + ID_PARAM_PATH)
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Retrieves summary information regarding shelf and children objects.")
    @ApiResponses({
            @ApiResponse(description = "Information retrieved successfully.", responseCode = "200"),
            @ApiResponse(description = "Provided shelf id not valid.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided shelf id is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = ID, description = "Shelf Id")
    })
    public ResponseEntity<ShelfInfoDto> requestShelfInfo(
            @PathVariable(name = ID) Integer id
    ) {
        logger.debug("ShelfInfoDto requested using method: {}.", "requestShelfInfo(Integer)");
        logger.trace("Expected ShelfInfoDto with id {}.", id);
        if(valid(id)) {
            if(own(id)) {
                logger.debug("Returning expected positive response.");
                return new ResponseEntity<>(getService().getShelfInfo(id), HttpStatus.OK);
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
    @Operation(description = "Retrieves summary information regarding shelves and children objects.")
    @ApiResponses({
            @ApiResponse(description = "Information retrieved successfully.", responseCode = "200"),
            @ApiResponse(description = "At least one of provided shelf ids not valid.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "At least one of provided shelf ids is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = IDS, description = "List of Shelf Ids")
    })
    public ResponseEntity<List<ShelfInfoDto>> requestShelvesInfo(
            @PathVariable(name = IDS) Set<Integer> shelfIds
    ) {
        logger.debug("ShelfInfoDto list requested using method: {}.", "getEntityById(Integer)");
        logger.trace("Expected ShelfInfoDto list with ids {}.", shelfIds);
        if(valid(shelfIds)) {
            List<Integer> ids = new ArrayList<>(shelfIds);
            // all ids exist and are owned or request is rejected.
            if(own(ids)) {
                logger.debug("Returning expected positive response.");
                return new ResponseEntity<>(getService().getShelvesInfo(ids), HttpStatus.OK);
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
    public boolean own(List<Integer> shelfIds) {
        logger.debug("Shelf ids validation for ownership.");
        logger.trace("Checking if ids are own. Ids: {}.", shelfIds.toString());
        boolean ownCheck = getService().countExistingIds(shelfIds) == shelfIds.size();
        if(ownCheck) {
            logger.trace("Ids are own. Ids: {}.", shelfIds);
        } else {
            logger.trace("One or more values in list are not own. Ids: {}.", shelfIds);
        }
        logger.debug("Shelf ids validation for ownership completed.");
        return ownCheck;
    }

    @Override
    public boolean valid(Collection<Integer> shelfIds) {
        logger.debug("Shelf ids validation.");
        logger.trace("Checking if ids are valid. Ids: {}", shelfIds);
        boolean validCheck = validIntegers(shelfIds);
        if(validCheck) {
            logger.trace("Ids are valid. Ids: {}.", shelfIds);
        } else {
            logger.trace("One or more values in list are not valid. Ids: {}.", shelfIds);
        }
        logger.debug("Shelf ids validation completed.");
        return validCheck;
    }

}
