package com.nniett.kikaishin.app.web.controller;

import com.nniett.kikaishin.app.service.dto.BookDto;
import com.nniett.kikaishin.app.service.dto.BookInfoDto;
import com.nniett.kikaishin.app.web.controller.construction.ActivateableController;
import com.nniett.kikaishin.app.web.controller.construction.CanCheckOwnership;
import com.nniett.kikaishin.app.web.controller.construction.CanVerifyId;
import com.nniett.kikaishin.app.web.controller.construction.VerifiesIntegerId;
import com.nniett.kikaishin.app.persistence.entity.BookEntity;
import com.nniett.kikaishin.app.service.BookService;
import com.nniett.kikaishin.app.service.dto.write.book.BookCreationDto;
import com.nniett.kikaishin.app.service.dto.write.book.BookUpdateDto;
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
@RequestMapping(BOOK_PATH)
public class BookController
        extends ActivateableController
        <
                BookEntity,
                Integer,
                BookDto,
                BookCreationDto,
                BookUpdateDto,
                BookService
                >
        implements CanCheckOwnership<Integer>, CanVerifyId<Integer>, VerifiesIntegerId
{
    private final ShelfController parentController;

    @Autowired
    public BookController(BookService service, ShelfController shelfController) {
        super(service);
        this.parentController = shelfController;
    }

    @Override
    @PostMapping()
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Creates a new book object for currently logged in user appended to provided shelf id.")
    @ApiResponses({
            @ApiResponse(description = "Book created successfully.", responseCode = "200"),
            @ApiResponse(description = "Dto validation failed.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided shelf id is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = "BookCreationDto", description = "Name must be provided. " +
                    "Topic list (topics) can be omitted (null). For every subsequent child in hierarchy, parent Id must be omitted. " +
                    "For example, if a topic is appended to book creation, book id will be omitted and appended to newly created book object. ",
                    schema = @Schema(implementation = BookCreationDto.class))
    })
    public ResponseEntity<BookDto> persistNewEntity(@Valid @RequestBody BookCreationDto dto) {
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
    @Operation(description = "Retrieves a book object if owned by logged user.")
    @ApiResponses({
            @ApiResponse(description = "Book retrieved successfully.", responseCode = "200"),
            @ApiResponse(description = "Provided book id not valid.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided book id is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = ID, description = "Book Id")
    })
    public ResponseEntity<BookDto> getEntityById(@PathVariable(ID) Integer id) {
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
    @Operation(description = "Updates and retrieves a book object if owned by logged user.")
    @ApiResponses({
            @ApiResponse(description = "Book updated successfully.", responseCode = "200"),
            @ApiResponse(description = "Dto validation failed.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided book id is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = "BookUpdateDto", description = "Child update must not be provided. " +
                    "If child information is provided, it will not be updated. " +
                    "For example, if book update dto appends any information in its topic list, topic information will be omitted.",
                    schema = @Schema(implementation = BookUpdateDto.class))
    })
    public ResponseEntity<BookDto> updateEntity(@Valid @RequestBody BookUpdateDto dto) {
        if(own(dto.getPK())) {
            return update(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @DeleteMapping(ID_PARAM_PATH)
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Deletes book object if owned by logged user and all related children such as topics, questions, answers, review model, clues, reviews and grades.")
    @ApiResponses({
            @ApiResponse(description = "Deletion performed successfully.", responseCode = "200"),
            @ApiResponse(description = "Provided book id not valid.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided book id is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = ID, description = "Book Id")
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
    @PutMapping(TOGGLE_STATUS_PATH)
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Change active status of book entity.")
    @ApiResponses({
            @ApiResponse(description = "Status change performed successfully.", responseCode = "200"),
            @ApiResponse(description = "Dto validation failed.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided book id is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = "BookUpdateDto", schema = @Schema(implementation = BookUpdateDto.class))
    })
    public ResponseEntity<Void> toggleActive(@Valid @RequestBody BookUpdateDto dto) {
        if(own(dto.getPK())) {
            return toggleStatus(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    ///////////// Book Info Control


    @GetMapping(INFO_ENDPOINT + ID_PARAM_PATH)
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Retrieves summary information regarding book and children objects.")
    @ApiResponses({
            @ApiResponse(description = "Information retrieved successfully.", responseCode = "200"),
            @ApiResponse(description = "Provided book id not valid.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided book id is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = ID, description = "Book Id")
    })
    public ResponseEntity<BookInfoDto> requestBookInfo(
            @PathVariable(name = ID) Integer bookId
    ) {
        if(valid(bookId)) {
            if(own(bookId)) {
                return new ResponseEntity<>(getService().getBookInfo(bookId), HttpStatus.OK);
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
    @Operation(description = "Retrieves summary information regarding books and children objects.")
    @ApiResponses({
            @ApiResponse(description = "Information retrieved successfully.", responseCode = "200"),
            @ApiResponse(description = "At least one of provided book ids not valid.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "At least one of provided book ids is not existent or not owned by logged user.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = IDS, description = "List of Book Ids")
    })
    public ResponseEntity<List<BookInfoDto>> requestBooksInfo(
            @PathVariable(name = IDS) Set<Integer> bookIds
    ) {
        if(valid(bookIds)) {
            List<Integer> ids = new ArrayList<>(bookIds);
            // all books are owned or request is rejected.
            if(own(ids)) {
                return new ResponseEntity<>(getService().getBooksInfo(ids), HttpStatus.OK);
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
