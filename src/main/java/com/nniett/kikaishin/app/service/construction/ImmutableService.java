package com.nniett.kikaishin.app.service.construction;

import com.nniett.kikaishin.app.service.dto.common.Pojo;
import com.nniett.kikaishin.app.service.dto.write.CreationDto;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Getter
public abstract class ImmutableService
        <
                CREATE_DTO extends CreationDto<PK>,
                POJO extends Pojo<PK>,
                ENTITY,
                PK>
    implements
        CanCreate<POJO, CREATE_DTO, ENTITY>,
        CanRead<ENTITY, PK, POJO>,
        CanDelete<PK>

{
    private static final Logger logger = LoggerFactory.getLogger(ImmutableService.class);

    private final ListCrudRepository<ENTITY, PK> repository;
    private final CreateService<ENTITY, PK, POJO, CREATE_DTO> createService;
    private final ReadService<ENTITY, PK, POJO> readService;
    private final DeleteService<ENTITY, PK> deleteService;

    public ImmutableService(
            ListCrudRepository<ENTITY, PK> repository,
            CreateService<ENTITY, PK, POJO, CREATE_DTO> createService,
            ReadService<ENTITY, PK, POJO> readService,
            DeleteService<ENTITY, PK> deleteService
    ) {
        this.repository = repository;
        this.createService = createService;
        this.readService = readService;
        this.deleteService = deleteService;
    }

    @Override
    public POJO create(POJO pojo) {
        ENTITY entity = getCreateService().getEntityPojoMapper().toEntity(pojo);
        return getCreateService().getEntityPojoMapper().toPojo(this.repository.save(entity));
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public POJO createFromDto(CREATE_DTO createDto) {
        return getCreateService().createFromDto(createDto);
    }

    @Override
    public abstract void populateAsDefaultForCreation(ENTITY entity);

    @Override
    public boolean delete(PK id) {
        return deleteService.delete(id);
    }

    @Override
    public boolean exists(PK id) {
        return readService.exists(id);
    }

    @Override
    public POJO readPojo(PK id) {
        return readService.readPojo(id);
    }

    @Override
    public ENTITY readEntity(PK id) {
        return readService.readEntity(id);
    }

    @Override
    public List<POJO> readAll() {
        return readService.readAll();
    }

}
