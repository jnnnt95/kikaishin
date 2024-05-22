package com.nniett.kikaishin.app.web.controller;

import com.nniett.kikaishin.app.service.dto.UserDto;
import com.nniett.kikaishin.app.service.dto.write.user.PasswordUpdateDto;
import com.nniett.kikaishin.app.web.controller.construction.CanVerifyId;
import com.nniett.kikaishin.app.web.controller.construction.Controller;
import com.nniett.kikaishin.app.persistence.entity.UserEntity;
import com.nniett.kikaishin.app.service.UserService;
import com.nniett.kikaishin.app.service.dto.UserInfoDto;
import com.nniett.kikaishin.app.service.dto.write.user.UserCreationDto;
import com.nniett.kikaishin.app.service.dto.write.user.UserUpdateDto;
import com.nniett.kikaishin.app.web.controller.exception.UsernameNotMatchedException;
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
import org.springframework.http.*;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

import static com.nniett.kikaishin.common.Constants.*;

@RestController
@RequestMapping(USER_PATH)
public class UserController extends Controller
        <
                UserEntity,
                String,
                UserDto,
                UserCreationDto,
                UserUpdateDto,
                UserService>
    implements CanVerifyId<String>
{
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService service) {
        super(service);
        logger.info("UserController initialized.");
    }

    @PutMapping(PASSWORD_ENDPOINT)
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Updates user's password.")
    @ApiResponses({
            @ApiResponse(description = "Password updated successfully.", responseCode = "200"),
            @ApiResponse(description = "Dto validation failed.", responseCode = "400", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = "PasswordUpdateDto",
                    description = """
                            Old password must be provided. New password must be provided and comply with password policy \
                                     1. Password must contain one digit from 1 to 9.
                                     2. Password must contain one lowercase letter.
                                     3. Password must contain one uppercase letter.
                                     4. Password must contain one special character.
                                     5. Password must not contain space characters.
                                     6. Password must be 12-50 characters long.""",
                    schema = @Schema(implementation = PasswordUpdateDto.class))
    })
    public ResponseEntity<Void> changePassword(
           @Valid @RequestBody PasswordUpdateDto passwordUpdateDto
    ) {
        logger.debug("Password update requested using method: {}.", "changePassword(PasswordUpdateDto)");
        logger.trace("Password update request body: {}.", passwordUpdateDto.toString());
        if(getService().updatePassword(passwordUpdateDto)) {
            logger.debug("Password update success.");
            return ResponseEntity.ok().build();
        } else {
            logger.debug("Password update failed.");
            throw new RuntimeException("Password update failed");
        }
    }

    @Override
    @PutMapping(DISPLAY_NAME_ENDPOINT)
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Updates user's display name.")
    @ApiResponses({
            @ApiResponse(description = "User's display name updated successfully.", responseCode = "200"),
            @ApiResponse(description = "Dto validation failed.", responseCode = "400", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = "UserUpdateDto",
                    description = "Username and email cannot be updated.",
                    schema = @Schema(implementation = UserUpdateDto.class))
    })
    public ResponseEntity<UserDto> updateEntity(
            @Valid @RequestBody UserUpdateDto userUpdateDto
    ) {
        userUpdateDto.setUsername(userUpdateDto.getUsername().trim());
        userUpdateDto.setDisplayName(userUpdateDto.getDisplayName().trim());
        logger.debug("User (user display name) update requested using method: {}.", "updateEntity(UserUpdateDto)");
        logger.trace("User (user display name) update request body: {}.", userUpdateDto);
        try {
            UserDto user = getService().updateDisplayName(userUpdateDto);
            logger.debug("Returning expected positive response.");
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch(UsernameNotMatchedException e) {
            logger.debug("User update failed. Reason: provided username does not match logged user's.");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @Override
    @PostMapping()
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Creates a new user.")
    @ApiResponses({
            @ApiResponse(description = "User created successfully.", responseCode = "200"),
            @ApiResponse(description = "Dto validation failed.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided username or E-Mail already in use.", responseCode = "409", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = "UserCreationDto", description = "Username, password, display name and email must be provided. " +

                    "Provided username must comply with Username policy, Username is a " + USERNAME_MIN_SIZE + "-to-" + USERNAME_MAX_SIZE + "-long string comprised of: " +
                    "1. Lower and upper case characters. " +
                    "2. Numbers. " +
                    "3. Character \"_\", but no other special character. "+

                    "Password must comply with password policy: " +
                    "1. Password must contain one digit from 1 to 9. " +
                    "2. Password must contain one lowercase letter. " +
                    "3. Password must contain one uppercase letter. " +
                    "4. Password must contain one special character. " +
                    "5. Password must not contain space characters. " +
                    "6. Password must be 12-50 characters long. " +

                    "Display name should be a string with max length of " + DISPLAY_NAME_SIZE + " characters. " +

                    "E-Mail should be a a valid email string with max length of " + EMAIL_SIZE + " characters. ",

                    schema = @Schema(implementation = UserCreationDto.class))
    })
    public ResponseEntity<UserDto> persistNewEntity(
            @Valid @RequestBody UserCreationDto creationDto
    ) {
        creationDto.setUsername(creationDto.getUsername().trim());
        creationDto.setDisplayName(creationDto.getDisplayName().trim());
        creationDto.setEmail(creationDto.getEmail().trim());
        logger.debug("User requested to be created using method: {}.", "persistNewEntity(UserCreationDto)");
        logger.trace("User creation request body: {}.", creationDto);
        if(!exists(creationDto.getUsername()) && !mailExists(creationDto.getEmail())) {
            ResponseEntity<UserDto> userResponse = create(creationDto);
            getService().createRole(userResponse.getBody());
            logger.debug("Returning not found response.");
            return userResponse;
        } else {
            logger.debug("Either Username or E-Mail already in use. Returning conflict response.");
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
    private boolean mailExists(String email) {
        logger.trace("Checking E-Mail for existence: {}.", email);
        return getService().mailExists(email);
    }

    @Override
    @GetMapping(USERNAME_PARAM_PATH)
    //expected use: administrative.
    public ResponseEntity<UserDto> getEntityById(
            @PathVariable(USERNAME)
            String providedUsername
    ) {
        logger.debug("UserDto requested using method: {}.", "getEntityById(String)");
        logger.trace("Expected UserDto username {}.", providedUsername);
        if(exists(providedUsername)) {
            logger.debug("Returning expected positive response.");
            return readById(providedUsername);
        } else {
            logger.debug("Returning not found response.");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    @Operation(description = "Retrieves logged user and children objects.")
    @ApiResponses({
            @ApiResponse(description = "User retrieved successfully.", responseCode = "200")
    })
    public ResponseEntity<UserDto> getEntity() {
        logger.debug("UserDto requested using method: {}.", "getEntity()");
        return new ResponseEntity<>(getService().getLoggedUser(), HttpStatus.OK);
    }

    //Expected use: administrative.
    @Override
    @DeleteMapping(USERNAME_PARAM_PATH)
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Deletes user object and all related children such as shelves, books, topics, questions, answers, clues, review model, reviews and grades.")
    @ApiResponses({
            @ApiResponse(description = "Deletion performed successfully.", responseCode = "200"),
            @ApiResponse(description = "Provided username not valid.", responseCode = "400", content = @Content(schema = @Schema)),
            @ApiResponse(description = "Provided username does not exist.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = USERNAME, description = "Username")
    })
    public ResponseEntity<Void> deleteEntityById(
            @PathVariable(USERNAME)
            String providedUsername
    ) {
        logger.debug("User deletion requested using method: {}.", "deleteEntityById(String)");
        logger.trace("Expected deletion happening on username {}.", providedUsername);
        if(valid(providedUsername)) {
            if(exists(providedUsername)) {
                logger.debug("Returning expected positive response.");
                return delete(providedUsername);
            } else {
                logger.debug("Returning not found response.");
                return ResponseEntity.notFound().build();
            }
        } else {
            logger.debug("Returning bad request response.");
            return ResponseEntity.badRequest().build();
        }
    }

    //Expected use: administrative
    @Transactional(propagation = Propagation.REQUIRED)
    @PutMapping(DISABLE_PATH + USERNAME_PARAM_PATH)
    @Operation(description = "Set user's disabled status to true.")
    @ApiResponses({
            @ApiResponse(description = "User was disabled successfully.", responseCode = "200"),
            @ApiResponse(description = "Provided username does not exist.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = USERNAME, description = "Username")
    })
    public ResponseEntity<Void> disableUser(
            @PathVariable(USERNAME) String providedUsername
    ) {
        logger.debug("Disable user requested using method: {}.", "disableUser(String)");
        logger.trace("Expected disable user happening on username {}.", providedUsername);
        if(exists(providedUsername)) {
            logger.debug("Returning expected positive response.");
            getService().updateUserIsDisabled(providedUsername, true);
            return ResponseEntity.ok().build();
        } else {
            logger.debug("Returning not found response.");
            return ResponseEntity.notFound().build();
        }
    }

    //Expected use: administrative
    @Transactional(propagation = Propagation.REQUIRED)
    @PutMapping(ENABLE_PATH + USERNAME_PARAM_PATH)
    @Operation(description = "Set user's disabled status to false.")
    @ApiResponses({
            @ApiResponse(description = "User was enabled successfully.", responseCode = "200"),
            @ApiResponse(description = "Provided username does not exist.", responseCode = "404", content = @Content(schema = @Schema))
    })
    @Parameters({
            @Parameter(name = USERNAME, description = "Username")
    })
    public ResponseEntity<Void> enableUser(
            @PathVariable(USERNAME) String providedUsername
    ) {
        logger.debug("Enable user requested using method: {}.", "enableUser(String)");
        logger.trace("Expected enable user happening on username {}.", providedUsername);
        if(exists(providedUsername)) {
            logger.debug("Returning expected positive response.");
            getService().updateUserIsDisabled(providedUsername, false);
            return ResponseEntity.ok().build();
        } else {
            logger.debug("Returning not found response.");
            return ResponseEntity.notFound().build();
        }
    }

    public void reportFailedAuthentication(String username) {
        getService().reportFailedAuthentication(username);
    }

    public void reportSuccessfulAuthentication(String username) {
        getService().reportSuccessfulAuthentication(username);
    }

    public boolean isLocked(String username) {
        return getService().checkIsLocked(username);
    }

    public boolean isDisabled(String username) {
        return getService().checkIsDisabled(username);
    }

    public boolean isBlocked(String username) {
        return getService().checkIsBlocked(username);
    }

    ///////////// User Info Control


    @GetMapping(INFO_ENDPOINT)
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(description = "Retrieves summary information regarding logged user and children objects.")
    @ApiResponses({
            @ApiResponse(description = "Information retrieved successfully.", responseCode = "200")
    })
    public ResponseEntity<UserInfoDto> requestUserInfo() {
        logger.debug("UserInfoDto requested using method: {}.", "requestUserInfo()");
        return new ResponseEntity<>(getService().getUserInfo(), HttpStatus.OK);
    }

    ///////////// Validation

    @Override
    public boolean valid(Collection<String> usernames) {
        logger.debug("User usernames validation.");
        logger.trace("Checking if usernames are valid. Usernames: {}", usernames);
        if(usernames != null && !usernames.isEmpty()) {
            for(String id : usernames) {
                if(id == null || id.isEmpty()) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
