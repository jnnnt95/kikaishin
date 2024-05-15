package com.nniett.kikaishin.app.controller.construction;

import com.nniett.kikaishin.app.persistence.entity.construction.ImmutableEntity;
import com.nniett.kikaishin.app.persistence.entity.construction.MutableEntity;
import com.nniett.kikaishin.app.service.construction.ImmutableService;
import com.nniett.kikaishin.app.service.pojo.Pojo;
import com.nniett.kikaishin.app.service.pojo.dto.CreationDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.function.Supplier;

@Getter
public abstract class ImmutableController
        <
                ENTITY extends ImmutableEntity,
                PK,
                POJO extends Pojo<PK>,
                CREATE_DTO extends CreationDto<PK>,
                SERVICE extends ImmutableService<CREATE_DTO, POJO, ENTITY, PK>
                >
{

    public ImmutableController(SERVICE service) {
        this.service = service;
    }

    private final SERVICE service;

    public abstract ResponseEntity<POJO> persistNewEntity(CREATE_DTO creationDto);
    public abstract ResponseEntity<POJO> getEntityById(PK id);
    public abstract ResponseEntity<Void> deleteEntityById(PK id);

    public boolean exists(PK id) {
        return this.service.exists(id);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public ResponseEntity<POJO> create(CREATE_DTO dto) {
        return caughtOperate(() -> {
            POJO pojo = this.service.createFromDto(dto);
            if(pojo != null) {
                return new ResponseEntity<>(pojo, HttpStatus.CREATED);
            } else {
                return ResponseEntity.badRequest().build();
            }
        });
    }

    public ResponseEntity<POJO> readById(PK id) {
        return caughtOperate(() -> {
            if(exists(id)) {
                POJO pojo = this.service.readPojo(id);
                return new ResponseEntity<>(pojo, HttpStatus.OK);
            } else {
                return ResponseEntity.notFound().build();
            }
        });
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public ResponseEntity<Void> delete(PK id) {
        return caughtOperate(() -> {
            if(getService().exists(id)) {
                return getService().delete(id)
                        ? new ResponseEntity<>(HttpStatus.OK)
                        : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        });
    }

    public static <T> ResponseEntity<T> caughtOperate(Supplier<ResponseEntity<T>> operation) {
        try {
            return operation.get();
        } catch(Exception e) {
            //e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
