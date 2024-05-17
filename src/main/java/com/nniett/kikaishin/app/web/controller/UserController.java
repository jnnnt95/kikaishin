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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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

    public UserController(
            UserService service
    ) {
        super(service);
    }

    @PutMapping(PASSWORD_ENDPOINT)
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Void> changePassword(
           @Valid @RequestBody PasswordUpdateDto passwordUpdateDto
    ) {
        if(getService().updatePassword(passwordUpdateDto)) {
            return ResponseEntity.ok().build();
        } else {
            throw new RuntimeException("Password update failed");
        }
    }

    @Override
    @PutMapping(DISPLAY_NAME_ENDPOINT)
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<UserDto> updateEntity(
            @Valid @RequestBody UserUpdateDto userUpdateDto
    ) {
        userUpdateDto.setUsername(userUpdateDto.getUsername().trim());
        userUpdateDto.setDisplayName(userUpdateDto.getDisplayName().trim());
        try {
            UserDto user = getService().updateDisplayName(userUpdateDto);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch(UsernameNotMatchedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @Override
    @PostMapping()
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<UserDto> persistNewEntity(
            @Valid @RequestBody UserCreationDto creationDto
    ) {
        creationDto.setUsername(creationDto.getUsername().trim());
        creationDto.setDisplayName(creationDto.getDisplayName().trim());
        creationDto.setEmail(creationDto.getEmail().trim());
        if(!exists(creationDto.getUsername())) {
            if(!mailExists(creationDto.getEmail())) {
                ResponseEntity<UserDto> userResponse = create(creationDto);
                getService().createRole(userResponse.getBody());
                return userResponse;
            } else {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Provided E-Mail already in use.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Provided Username already in use.");
        }
    }
    private boolean mailExists(String email) {
        return getService().mailExists(email);
    }

    @Override
    @GetMapping(ID_PARAM_PATH)
    public ResponseEntity<UserDto> getEntityById(
            @PathVariable(ID)
            String providedUsername
    ) {
        if(exists(providedUsername)) {
            return readById(providedUsername);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    public ResponseEntity<UserDto> getEntity() {
        return new ResponseEntity<>(getService().getLoggedUser(), HttpStatus.OK);
    }

    @Override
    @DeleteMapping(ID_PARAM_PATH)
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Void> deleteEntityById(
            @PathVariable(ID)
            String providedUsername
    ) {
        if(exists(providedUsername)) {
            return delete(providedUsername);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @PutMapping(DISABLE_PATH + ID_PARAM_PATH)
    public ResponseEntity<Void> disableUser(
            @PathVariable(ID) String providedUsername
    ) {
        if(exists(providedUsername)) {
            getService().updateUserIsDisabled(providedUsername, true);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @PutMapping(ENABLE_PATH + ID_PARAM_PATH)
    public ResponseEntity<Void> enableUser(
            @PathVariable(ID) String providedUsername
    ) {
        if(exists(providedUsername)) {
            getService().updateUserIsDisabled(providedUsername, false);
            return ResponseEntity.ok().build();
        } else {
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
    public ResponseEntity<UserInfoDto> requestShelfInfo() {
        return new ResponseEntity<>(getService().getUserInfo(), HttpStatus.OK);
    }

    ///////////// Validation

    @Override
    public boolean valid(Collection<String> ids) {
        if(ids != null && !ids.isEmpty()) {
            for(String id : ids) {
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
