package com.nniett.kikaishin.app.service.construction;

public interface CanCreate
        <POJO, CREATE_DTO, ENTITY> {

    POJO create(POJO pojo);
    POJO createFromDto(CREATE_DTO dto);

    //For the purpose of populating the fields that need default value
    //but that were not provided by client (or that are not to be provided by client).
    void populateAsDefaultForCreation(ENTITY entity);

}
