package com.nniett.kikaishin.web.service.crud.shelf;

import com.nniett.kikaishin.web.persistence.entity.ShelfEntity;
import com.nniett.kikaishin.web.service.construction.DeleteService;
import com.nniett.kikaishin.web.service.construction.ReadService;
import com.nniett.kikaishin.web.service.mapper.EntityPojoMapper;
import com.nniett.kikaishin.web.service.pojo.Shelf;
import com.nniett.kikaishin.web.service.pojo.dto.shelf.ShelfUpdateDto;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ShelfDeleteService
        extends DeleteService<ShelfEntity, Integer>
{

    public ShelfDeleteService(ListCrudRepository<ShelfEntity, Integer> repository) {
        super(repository);
    }

}
