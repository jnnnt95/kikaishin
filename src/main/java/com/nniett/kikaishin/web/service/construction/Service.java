package com.nniett.kikaishin.web.service.construction;

import com.nniett.kikaishin.web.service.pojo.Pojo;
import com.nniett.kikaishin.web.service.pojo.dto.UpdateDto;
import lombok.Getter;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

@Getter
public abstract class Service
        <CREATE_DTO, UPDATE_DTO extends UpdateDto<PK>, POJO extends Pojo<PK>, ENTITY, PK>
    implements
        CanCreate<POJO, CREATE_DTO>,
        CanRead<ENTITY, UPDATE_DTO, PK, POJO>,
        CanUpdate<ENTITY, UPDATE_DTO, POJO>,
        CanDelete<PK>

{

    private final ListCrudRepository<ENTITY, PK> repository;
    private final CreateService<ENTITY, PK, POJO, CREATE_DTO> createService;
    private final ReadService<ENTITY, UPDATE_DTO, PK, POJO> readService;
    private final UpdateService<ENTITY, PK, UPDATE_DTO, POJO> updateService;
    private final DeleteService<ENTITY, PK> deleteService;

    public Service(
            ListCrudRepository<ENTITY, PK> repository,
            CreateService<ENTITY, PK, POJO, CREATE_DTO> createService,
            ReadService<ENTITY, UPDATE_DTO, PK, POJO> readService,
            UpdateService<ENTITY, PK, UPDATE_DTO, POJO> updateService,
            DeleteService<ENTITY, PK> deleteService
    ) {
        this.repository = repository;
        this.createService = createService;
        this.readService = readService;
        this.updateService = updateService;
        this.deleteService = deleteService;
    }

    @Override
    public POJO create(POJO pojo) {
        return null;
    }

    @Override
    public POJO createFromDto(CREATE_DTO createDto) {
        return createService.createFromDto(createDto);
    }

    @Override
    public abstract void populateAsDefaultPojoForCreation(POJO pojo);

    @Override
    public boolean delete(PK id) {
        return deleteService.delete(id);
    }

    @Override
    public boolean exists(PK id) {
        return readService.exists(id);
    }

    @Override
    public POJO read(PK id) {
        return readService.read(id);
    }

    public POJO findById(PK id) {
        return read(id);
    }

    @Override
    public List<POJO> readAll() {
        return readService.readAll();
    }

    @Override
    public ENTITY findEntityByDto(UPDATE_DTO updateDto) {
        return readService.findEntityByDto(updateDto);
    }

    @Override
    public POJO update(POJO pojo) {
        return updateService.update(pojo);
    }

    @Override
    public abstract void populateEntityForUpdate(ENTITY entity, POJO pojo);

    @Override
    public POJO updateFromDto(UPDATE_DTO updateDto) {
        return updateService.updateFromDto(updateDto);
    }

}
