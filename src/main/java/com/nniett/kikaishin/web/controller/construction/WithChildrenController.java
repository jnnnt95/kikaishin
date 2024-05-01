package com.nniett.kikaishin.web.controller.construction;

import com.nniett.kikaishin.common.ChildrenPersistable;
import com.nniett.kikaishin.web.persistence.entity.construction.MutableEntity;
import com.nniett.kikaishin.web.service.construction.WithChildrenService;
import com.nniett.kikaishin.web.service.pojo.Pojo;
import com.nniett.kikaishin.web.service.pojo.common.HasChildren;
import com.nniett.kikaishin.web.service.pojo.dto.CreationDtoWithParent;
import com.nniett.kikaishin.web.service.pojo.dto.CreationDtoWithChildren;
import com.nniett.kikaishin.web.service.pojo.dto.UpdateDto;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class WithChildrenController
        <
                ENTITY extends MutableEntity,
                PK,
                POJO extends Pojo<PK> & HasChildren<CHILD_POJO>,
                CREATE_DTO extends CreationDtoWithChildren<CHILD_CREATE_DTO>,
                UPDATE_DTO extends UpdateDto<PK>,
                CHILD_ENTITY,
                CHILD_PK,
                CHILD_POJO,
                CHILD_CREATE_DTO extends CreationDtoWithParent<PK>,
                SERVICE extends WithChildrenService<
                        ENTITY,
                        PK,
                        POJO,
                        CREATE_DTO,
                        UPDATE_DTO,
                        CHILD_ENTITY,
                        CHILD_PK,
                        CHILD_POJO,
                        CHILD_CREATE_DTO
                        >
                >
        extends Controller<PK, ENTITY, POJO, CREATE_DTO, UPDATE_DTO, SERVICE>
        implements ChildrenPersistable<CHILD_POJO, CHILD_CREATE_DTO>
{

    public WithChildrenController(SERVICE service) {
        super(service);
    }

    @Override
    public CHILD_POJO createChild(CHILD_CREATE_DTO creationDto) {
        return getService().createChild(creationDto);
    }

    @Transactional(Transactional.TxType.MANDATORY)
    public ResponseEntity<POJO> createWithChildren(CREATE_DTO creationDto) {
        if(creationDto.getChildren() == null) {
            creationDto.setChildren(new ArrayList<>());
        }
        ResponseEntity<POJO> pojoResponse = create(creationDto);
        POJO pojo = pojoResponse.getBody();
        if(pojo != null) {
            List<CHILD_CREATE_DTO> childDtos = creationDto.getChildren();
            pojoResponse.getBody().getChildren().clear();
            try {
                for(CHILD_CREATE_DTO childDto: childDtos) {
                    childDto.setParentPK(pojo.getPK());
                    pojo.getChildren().add(getService().createChild(childDto));
                }
            } catch(RuntimeException e) {
                throw new RuntimeException();
            }
        }
        return pojoResponse;
    }

    @Transactional(Transactional.TxType.MANDATORY)
    public ResponseEntity<POJO> updateWithChildren(UPDATE_DTO dto) {
        try {
            ResponseEntity<POJO> pojoResponse = update(dto);
            POJO pojo = pojoResponse.getBody();
            if(pojo != null) {
                pojo.setChildren(null);
            }
            return pojoResponse;
        } catch(Exception e) {
            throw new RuntimeException();
        }
    }

}
