package com.nniett.kikaishin.app.service.crud.user;

import com.nniett.kikaishin.app.persistence.entity.UserEntity;
import com.nniett.kikaishin.app.persistence.entity.UserRoleEntity;
import com.nniett.kikaishin.app.persistence.repository.UserRepository;
import com.nniett.kikaishin.app.persistence.repository.UserRoleRepository;
import com.nniett.kikaishin.app.service.construction.CreateService;
import com.nniett.kikaishin.app.service.mapper.UserMapper;
import com.nniett.kikaishin.app.service.mapper.dto.user.UserCreationMapper;
import com.nniett.kikaishin.app.service.dto.UserDto;
import com.nniett.kikaishin.app.service.dto.write.user.UserCreationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.nniett.kikaishin.common.Constants.USER;

@Repository
public class UserCreateService
        extends CreateService
        <
                UserEntity,
                String,
                UserDto,
                UserCreationDto
                >
{
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserCreateService(
            UserRepository repository,
            UserMapper entityPojoMapper,
            @Qualifier("userCreationMapperImpl")
            UserCreationMapper createMapper,
            UserRoleRepository userRoleRepository
    ) {
        super(repository, entityPojoMapper, createMapper);
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void populateAsDefaultForCreation(UserEntity userEntity) {
        userEntity.setDisabled(false);
        userEntity.setLocked(false);
        userEntity.setFailedAuthentications(0);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void createUserRole(UserEntity createdUser) {
        UserRoleEntity userRoleEntity = new UserRoleEntity();

        userRoleEntity.setUsername(createdUser.getUsername());
        userRoleEntity.setRole(USER);
        userRoleEntity.setGrantedDate(LocalDateTime.now());

        userRoleRepository.save(userRoleEntity);
    }
}
