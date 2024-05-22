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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(UserCreateService.class);

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
        logger.info("UserCreateService initialized.");
    }

    @Override
    public void populateAsDefaultForCreation(UserEntity userEntity) {
        logger.debug("Populating default fields for new user.");
        logger.trace("Populating default field disabled as false.");
        userEntity.setDisabled(false);
        logger.trace("Populating default field locked as false.");
        userEntity.setLocked(false);
        logger.trace("Populating default failed authentications as 0.");
        userEntity.setFailedAuthentications(0);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        logger.trace("Encoding and populating password.");
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void createUserRole(UserEntity createdUser) {
        logger.debug("Creating user role.");
        logger.debug("Populating fields for new user role.");
        UserRoleEntity userRoleEntity = new UserRoleEntity();

        logger.trace("Populating field username as {}.", createdUser.getUsername());
        userRoleEntity.setUsername(createdUser.getUsername());
        logger.trace("Populating field role as {}.", USER);
        userRoleEntity.setRole(USER);
        LocalDateTime now = LocalDateTime.now();
        logger.trace("Populating granted date as {}.", now);
        userRoleEntity.setGrantedDate(now);

        userRoleRepository.save(userRoleEntity);
    }
}
