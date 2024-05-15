package com.nniett.kikaishin.app.service;

import com.nniett.kikaishin.app.persistence.entity.UserEntity;
import com.nniett.kikaishin.app.persistence.repository.UserRepository;
import com.nniett.kikaishin.app.persistence.repository.virtual.UserInfoVirtualRepository;
import com.nniett.kikaishin.app.service.construction.Service;
import com.nniett.kikaishin.app.service.crud.user.UserCreateService;
import com.nniett.kikaishin.app.service.crud.user.UserDeleteService;
import com.nniett.kikaishin.app.service.crud.user.UserReadService;
import com.nniett.kikaishin.app.service.crud.user.UserUpdateService;
import com.nniett.kikaishin.app.service.mapper.UserInfoMapper;
import com.nniett.kikaishin.app.service.pojo.User;
import com.nniett.kikaishin.app.service.pojo.UserInfo;
import com.nniett.kikaishin.app.service.pojo.dto.user.UserCreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.user.UserUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserService
        extends Service
        <
                        UserCreationDto,
                        UserUpdateDto,
                        User,
                        UserEntity,
                        String
                        >
{

    private final UserInfoVirtualRepository userInfoRepository;
    private final UserInfoMapper userInfoMapper;

    @Autowired
    public UserService(
            UserRepository repository,
            UserCreateService createService,
            UserReadService readService,
            UserUpdateService updateService,
            UserDeleteService deleteService,
            UserInfoVirtualRepository userInfoRepository,
            UserInfoMapper userInfoMapper
    ) {
        super(repository, createService, readService, updateService, deleteService);
        this.userInfoRepository = userInfoRepository;
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public void populateAsDefaultForCreation(UserEntity entity) {

    }

    @Override
    public void populateEntityForUpdate(UserEntity entity, User pojo) {
        getUpdateService().populateEntityForUpdate(entity, pojo);
    }

    @Override
    public UserEntity findEntityByDto(UserUpdateDto dto) {
        return getRepository().findById(dto.getPK()).orElseThrow();
    }

    public User getShelfById(String username) {
        return readPojo(username);
    }


    public UserInfo getUserInfo() {
        //TODO: username should be retrieved appropriately from JWT.
        String username = "1";
        return userInfoMapper.
                toUserInfo(
                        userInfoRepository.
                                getUserInfoById(username)
                );
    }

}