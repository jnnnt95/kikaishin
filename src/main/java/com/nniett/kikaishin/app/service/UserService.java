package com.nniett.kikaishin.app.service;

import com.nniett.kikaishin.app.persistence.entity.UserEntity;
import com.nniett.kikaishin.app.persistence.repository.UserRepository;
import com.nniett.kikaishin.app.persistence.repository.virtual.UserInfoVirtualRepository;
import com.nniett.kikaishin.app.service.construction.Service;
import com.nniett.kikaishin.app.service.crud.user.UserCreateService;
import com.nniett.kikaishin.app.service.crud.user.UserDeleteService;
import com.nniett.kikaishin.app.service.crud.user.UserReadService;
import com.nniett.kikaishin.app.service.crud.user.UserUpdateService;
import com.nniett.kikaishin.app.service.dto.UserDto;
import com.nniett.kikaishin.app.service.dto.UserInfoDto;
import com.nniett.kikaishin.app.service.dto.write.user.PasswordUpdateDto;
import com.nniett.kikaishin.app.service.mapper.UserInfoMapper;
import com.nniett.kikaishin.app.service.dto.write.user.UserCreationDto;
import com.nniett.kikaishin.app.service.dto.write.user.UserUpdateDto;
import com.nniett.kikaishin.app.service.mapper.UserMapper;
import com.nniett.kikaishin.app.web.controller.construction.UsesHttpServletRequest;
import com.nniett.kikaishin.app.web.controller.exception.UsernameNotMatchedException;
import com.nniett.kikaishin.app.web.security.CanRetrieveUsernameFromJWT;
import com.nniett.kikaishin.app.web.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UserService
        extends Service
        <
                        UserCreationDto,
                        UserUpdateDto,
                UserDto,
                        UserEntity,
                        String
                        >
    implements UsesHttpServletRequest, CanRetrieveUsernameFromJWT
{

    private final UserMapper userMapper;
    private final UserInfoVirtualRepository userInfoRepository;
    private final UserInfoMapper userInfoMapper;
    private final JwtUtils jwtUtils;

    @Autowired
    public UserService(
            UserRepository repository,
            UserCreateService createService,
            UserReadService readService,
            UserUpdateService updateService,
            UserDeleteService deleteService,
            UserMapper userMapper,
            UserInfoVirtualRepository userInfoRepository,
            UserInfoMapper userInfoMapper,
            JwtUtils jwtUtils
    ) {
        super(repository, createService, readService, updateService, deleteService);
        this.userMapper = userMapper;
        this.userInfoRepository = userInfoRepository;
        this.userInfoMapper = userInfoMapper;
        this.jwtUtils = jwtUtils;
    }

    public boolean updatePassword(PasswordUpdateDto passwordUpdateDto) {
        String username = getUsernameFromJWT(getHttpServletRequest(), this.jwtUtils);
        UserEntity userEntity = getRepository().findById(username).orElseThrow();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(passwordEncoder.matches(passwordUpdateDto.getOldPassword(), userEntity.getPassword())) {
            String newPasswordFromDto = passwordEncoder.encode(passwordUpdateDto.getNewPassword());
            userEntity.setPassword(newPasswordFromDto);
            try {
                getRepository().save(userEntity);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void populateAsDefaultForCreation(UserEntity entity) {

    }

    @Override
    public void populateEntityForUpdate(UserEntity entity, UserDto pojo) {
        getUpdateService().populateEntityForUpdate(entity, pojo);
    }

    @Override
    public UserEntity findEntityByDto(UserUpdateDto dto) {
        return null;
    }

    public UserDto updateDisplayName(UserUpdateDto updateDto) {
        String username = getUsernameFromJWT(getHttpServletRequest(), this.jwtUtils);
        UserEntity userEntity = getRepository().findById(username).orElseThrow();
        if(username.equals(updateDto.getUsername())) {
            userEntity.setDisplayName(updateDto.getDisplayName());
            try {
                getRepository().save(userEntity);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            userEntity.setShelves(null);
            return this.userMapper.toPojo(userEntity);
        } else {
            throw new UsernameNotMatchedException("Provided username does not match logged user's.");
        }
    }

    public UserDto getLoggedUser() {
        String username = getUsernameFromJWT(getHttpServletRequest(), this.jwtUtils);
        return userMapper.toPojo(getRepository().findById(username).orElseThrow());
    }

    public UserInfoDto getUserInfo() {
        String username = getUsernameFromJWT(getHttpServletRequest(), this.jwtUtils);
        return userInfoMapper.
                toUserInfo(
                        userInfoRepository.
                                getUserInfoById(username)
                );
    }

    public void createRole(UserDto user) {
        ((UserCreateService) getCreateService()).createUserRole(userMapper.toEntity(user));
    }

    public void updateUserIsDisabled(String username, boolean flag) {
        UserEntity user = getRepository().findById(username).orElseThrow();
        user.setDisabled(flag);
        try {
            getRepository().save(user);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean mailExists(String email) {
        return ((UserRepository) getRepository()).countByEmail(email).orElseThrow() > 0;
    }

    public void updateUserLock(String username, boolean flag) {
        UserEntity user = getRepository().findById(username).orElseThrow();
        user.setLocked(flag);
        try {
            getRepository().save(user);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void reportFailedAuthentication(String username) {
        UserEntity user = getRepository().findById(username).orElseThrow();
        user.setFailedAuthentications(user.getFailedAuthentications() + 1);
        if(user.getFailedAuthentications() > 5) {
            user.setLocked(true);
        }
        getRepository().save(user);
    }

    public void reportSuccessfulAuthentication(String username) {
        UserEntity user = getRepository().findById(username).orElseThrow();
        user.setFailedAuthentications(0);
        getRepository().save(user);
    }

    public boolean checkIsLocked(String username) {
        return getRepository().findById(username).orElseThrow().getLocked();
    }

    public boolean checkIsDisabled(String username) {
        return getRepository().findById(username).orElseThrow().getDisabled();
    }

    public boolean checkIsBlocked(String username) {
        UserEntity user = getRepository().findById(username).orElseThrow();
        return user.getDisabled() || user.getLocked();
    }

}