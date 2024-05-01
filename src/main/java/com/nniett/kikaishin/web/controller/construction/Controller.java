package com.nniett.kikaishin.web.controller.construction;

import com.nniett.kikaishin.web.persistence.entity.construction.MutableEntity;
import com.nniett.kikaishin.web.service.construction.Service;
import com.nniett.kikaishin.web.service.pojo.Pojo;
import com.nniett.kikaishin.web.service.pojo.dto.UpdateDto;
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.function.Supplier;

@Getter
public abstract class Controller
        <
                PK,
                ENTITY extends MutableEntity,
                POJO extends Pojo<PK>,
                CREATE_DTO,
                UPDATE_DTO extends UpdateDto<PK>,
                SERVICE extends Service <CREATE_DTO, UPDATE_DTO, POJO, ENTITY, PK>
                >
{

    public Controller(SERVICE service) {
        this.service = service;
    }

    private final SERVICE service;

    public abstract ResponseEntity<POJO> persistNewEntity(CREATE_DTO creationDto);
    public abstract ResponseEntity<POJO> getEntityById(PK id);
    public abstract ResponseEntity<POJO> updateEntity(UPDATE_DTO creationDto);
    public abstract ResponseEntity<Void> deleteEntityById(PK id);

    public boolean exists(PK id) {
        return this.service.exists(id);
    }

    @Transactional(Transactional.TxType.MANDATORY)
    public ResponseEntity<POJO> create(CREATE_DTO dto) {
        return catchedOperate(() -> {
            POJO pojo = this.service.createFromDto(dto);
            if(pojo != null) {
                return new ResponseEntity<>(pojo, HttpStatus.CREATED);
            } else {
                return ResponseEntity.badRequest().build();
            }
        });
    }

    public ResponseEntity<POJO> readById(PK id) {
        return catchedOperate(() -> {
            if(exists(id)) {
                POJO pojo = this.service.findById(id);
                return new ResponseEntity<>(pojo, HttpStatus.OK);
            } else {
                return ResponseEntity.notFound().build();
            }
        });
    }

    @Transactional(Transactional.TxType.MANDATORY)
    public ResponseEntity<POJO> update(UPDATE_DTO dto) {
        return catchedOperate(() -> {
            if(exists(dto.getPK())) {
                POJO pojo = this.service.updateFromDto(dto);
                return new ResponseEntity<>(pojo, HttpStatus.OK);
            } else {
                return ResponseEntity.notFound().build();
            }
        });
    }

    @Transactional(Transactional.TxType.MANDATORY)
    public ResponseEntity<Void> delete(PK id) {
        return catchedOperate(() -> {
            if(getService().exists(id)) {
                return getService().delete(id)
                        ? new ResponseEntity<>(HttpStatus.OK)
                        : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        });
    }

    public static <T> ResponseEntity<T> catchedOperate(Supplier<ResponseEntity<T>> operation) {
        try {
            return operation.get();
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
