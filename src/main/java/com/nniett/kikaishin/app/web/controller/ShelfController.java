package com.nniett.kikaishin.app.web.controller;

import com.nniett.kikaishin.app.service.dto.ShelfDto;
import com.nniett.kikaishin.app.service.dto.ShelfInfoDto;
import com.nniett.kikaishin.app.service.dto.write.question.QuestionCreationDto;
import com.nniett.kikaishin.app.service.dto.write.question.QuestionUpdateDto;
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

    @Autowired
    public ShelfController(ShelfService service) {
        super(service);
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
        if(own(dto.getPK())) {
            return update(dto);
        } else {
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
        if(own(dto.getPK())) {
            return toggleStatus(dto);
        } else {
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
            @PathVariable(name = ID) Integer shelfId
    ) {
        if(valid(shelfId)) {
            if(own(shelfId)) {
                return new ResponseEntity<>(getService().getShelfInfo(shelfId), HttpStatus.OK);
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
        if(valid(shelfIds)) {
            List<Integer> ids = new ArrayList<>(shelfIds);
            // all ids exist and are owned or request is rejected.
            if(own(ids)) {
                return new ResponseEntity<>(getService().getShelvesInfo(ids), HttpStatus.OK);
            }
            else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public boolean own(List<Integer> shelfIds) {
        return getService().countExistingIds(shelfIds) == shelfIds.size();
    }

    @Override
    public boolean valid(Collection<Integer> shelfIds) {
        return validIntegers(shelfIds);
    }
}
