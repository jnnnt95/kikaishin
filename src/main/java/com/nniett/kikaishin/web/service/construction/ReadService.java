package com.nniett.kikaishin.web.service.construction;

import com.nniett.kikaishin.web.service.mapper.EntityPojoMapper;
import com.nniett.kikaishin.web.service.pojo.dto.UpdateDto;
import lombok.Getter;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

@Getter
public abstract class ReadService
        <
                ENTITY,
                UPDATE_DTO extends UpdateDto<PK>,
                PK,
                POJO
                >
        implements CanRead
        <
                ENTITY,
                UPDATE_DTO,
                PK,
                POJO
                > {

    private final ListCrudRepository<ENTITY, PK> repository;
    private final EntityPojoMapper<ENTITY, POJO> entityPojoMapper;

    public ReadService(
            ListCrudRepository<ENTITY, PK> repository,
            EntityPojoMapper<ENTITY, POJO> entityPojoMapper
    ) {
        this.repository = repository;
        this.entityPojoMapper = entityPojoMapper;
    }

    @Override
    public boolean exists(PK id) {
        return this.repository.existsById(id);
    }

    @Override
    public POJO read(PK id) {
        return entityPojoMapper.toPojo(repository.findById(id).orElse(null));
    }

    @Override
    public List<POJO> readAll() {
        return entityPojoMapper.toPojos(repository.findAll());
    }

    @Override
    public ENTITY findEntityByDto(UPDATE_DTO updateDto) {
        return getRepository().findById(updateDto.getPK()).get();
    }

}
