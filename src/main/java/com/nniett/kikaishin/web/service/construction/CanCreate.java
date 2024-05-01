package com.nniett.kikaishin.web.service.construction;

public interface CanCreate
        <POJO, CREATE_DTO> {

    public POJO create(POJO pojo);
    public POJO createFromDto(CREATE_DTO dto);

    //For the purpose of populating the fields that need default value
    //but that were not provided by client.
    abstract void populateAsDefaultPojoForCreation(POJO pojo);

}
