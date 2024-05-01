package com.nniett.kikaishin.web.service.construction;

import com.nniett.kikaishin.web.service.mapper.EntityPojoMapper;
import com.nniett.kikaishin.web.service.mapper.dto.DtoPojoMapper;
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.springframework.data.repository.ListCrudRepository;

@Getter
public abstract class CreateService <ENTITY, PK, POJO, CREATE_DTO>
        implements CanCreate<POJO, CREATE_DTO> {

    private final EntityPojoMapper<ENTITY, POJO> entityPojoMapper;
    private final ListCrudRepository<ENTITY, PK> repository;
    private final DtoPojoMapper<CREATE_DTO, POJO> createMapper;

    public CreateService(
            ListCrudRepository<ENTITY, PK> repository,
            EntityPojoMapper<ENTITY, POJO> entityPojoMapper,
            DtoPojoMapper<CREATE_DTO, POJO> createMapper
    ) {
        this.entityPojoMapper = entityPojoMapper;
        this.repository = repository;
        this.createMapper = createMapper;
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public POJO create(POJO pojo) {
        populateAsDefaultPojoForCreation(pojo);
        ENTITY entity = entityPojoMapper.toEntity(pojo);
        entity = repository.save(entity);
        pojo = entityPojoMapper.toPojo(entity);
        return pojo;
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public POJO createFromDto(CREATE_DTO dto) {
        return create(createMapper.toPojo(dto));
    }

    @Override
    public abstract void populateAsDefaultPojoForCreation(POJO pojo);
}
