package com.nniett.kikaishin.app.controller.construction;

import com.nniett.kikaishin.app.persistence.entity.construction.MutableEntity;
import com.nniett.kikaishin.app.service.construction.Service;
import com.nniett.kikaishin.app.service.pojo.Pojo;
import com.nniett.kikaishin.app.service.pojo.common.HasChildren;
import com.nniett.kikaishin.app.service.pojo.dto.CreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.UpdateDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.nniett.kikaishin.app.controller.construction.ImmutableController.caughtOperate;

@Getter
public abstract class Controller
        <
                ENTITY extends MutableEntity,
                PK,
                POJO extends Pojo<PK>,
                CREATE_DTO extends CreationDto<PK>,
                UPDATE_DTO extends UpdateDto<PK>,
                SERVICE extends Service <CREATE_DTO, UPDATE_DTO, POJO, ENTITY, PK>
                >
        extends ImmutableController
        <
                ENTITY,
                PK,
                POJO,
                CREATE_DTO,
                SERVICE
                >
{

    public Controller(SERVICE service) {
        super(service);
    }

    public abstract ResponseEntity<POJO> updateEntity(UPDATE_DTO creationDto);

    public boolean exists(PK id) {
        return getService().exists(id);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public ResponseEntity<POJO> update(UPDATE_DTO dto) {
        return caughtOperate(() -> {
            if(dto.getPK() != null) {
                if(exists(dto.getPK())) {
                    POJO pojo = getService().updateFromDto(dto);
                    if(pojo instanceof HasChildren<?>) {
                        ((HasChildren<?>) pojo).setChildren(null);
                    }
                    return new ResponseEntity<>(pojo, HttpStatus.OK);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                return ResponseEntity.badRequest().build();
            }
        });
    }

}
