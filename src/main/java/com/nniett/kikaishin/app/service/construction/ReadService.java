package com.nniett.kikaishin.app.service.construction;

import com.nniett.kikaishin.app.service.mapper.EntityPojoMapper;
import lombok.Getter;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

@Getter
public abstract class ReadService
        <
                ENTITY,
                PK,
                POJO
                >
        implements CanRead
        <
                ENTITY,
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
    public POJO readPojo(PK id) {
        ENTITY entity  = repository.findById(id).orElse(null);
        return entityPojoMapper.toPojo(entity);
    }

    @Override
    public ENTITY readEntity(PK id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<POJO> readAll() {
        return entityPojoMapper.toPojos(repository.findAll());
    }

}
