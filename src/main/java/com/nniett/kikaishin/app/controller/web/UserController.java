package com.nniett.kikaishin.app.controller.web;

import com.nniett.kikaishin.app.controller.construction.Controller;
import com.nniett.kikaishin.app.persistence.entity.UserEntity;
import com.nniett.kikaishin.app.service.UserService;
import com.nniett.kikaishin.app.service.pojo.User;
import com.nniett.kikaishin.app.service.pojo.UserInfo;
import com.nniett.kikaishin.app.service.pojo.dto.user.UserCreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.user.UserUpdateDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import static com.nniett.kikaishin.common.Constants.*;

@RestController
@RequestMapping("/api/user")
public class UserController extends Controller
        <
                UserEntity,
                String,
                User,
                UserCreationDto,
                UserUpdateDto,
                UserService>
{

    public UserController(UserService service) {
        super(service);
    }

    //TODO: service impl

    @PostMapping("/password/update")
    public ResponseEntity<Void> changePassword() {
        //TODO implementation of changing password for authenticated user.
        return ResponseEntity.ok().build();
    }

    @PostMapping("/display_name/update")
    public ResponseEntity<Void> changeDisplayName() {
        //TODO implementation of changing display name for authenticated user.
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<User> updateEntity(UserUpdateDto creationDto) {
        return null;
    }

    @Override
    public ResponseEntity<User> persistNewEntity(UserCreationDto creationDto) {
        return null;
    }

    @Override
    public ResponseEntity<User> getEntityById(String id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteEntityById(String id) {
        return null;
    }


    ///////////// User Info Control


    @GetMapping(INFO_ENDPOINT)
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<UserInfo> requestShelfInfo() {
        return new ResponseEntity<>(getService().getUserInfo(), HttpStatus.OK);
    }
}
