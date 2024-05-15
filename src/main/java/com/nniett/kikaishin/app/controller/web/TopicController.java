package com.nniett.kikaishin.app.controller.web;

import com.nniett.kikaishin.app.controller.construction.ActivateableController;
import com.nniett.kikaishin.app.controller.construction.CanCheckOwnership;
import com.nniett.kikaishin.app.controller.construction.CanVerifyId;
import com.nniett.kikaishin.app.controller.construction.VerifiesIntegerId;
import com.nniett.kikaishin.app.persistence.entity.TopicEntity;
import com.nniett.kikaishin.app.service.TopicService;
import com.nniett.kikaishin.app.service.pojo.Topic;
import com.nniett.kikaishin.app.service.pojo.TopicInfo;
import com.nniett.kikaishin.app.service.pojo.dto.topic.TopicCreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.topic.TopicUpdateDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.nniett.kikaishin.common.Constants.*;

@RestController
@RequestMapping(TOPIC_PATH)
public class TopicController
        extends ActivateableController
        <
                TopicEntity,
                Integer,
                Topic,
                TopicCreationDto,
                TopicUpdateDto,
                TopicService
                >
        implements CanCheckOwnership<Integer>, CanVerifyId<Integer>, VerifiesIntegerId
{
    private final BookController parentController;

    @Autowired
    public TopicController(TopicService service, BookController bookController) {
        super(service);
        this.parentController = bookController;
    }

    @Override
    @PostMapping()
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Topic> persistNewEntity(@Valid @RequestBody TopicCreationDto dto) {
        if(parentController.valid(dto.getParentPK())) {
            if(parentController.own(dto.getParentPK())) {
                return create(dto);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @GetMapping(ID_PARAM_PATH)
    public ResponseEntity<Topic> getEntityById(@PathVariable(ID) Integer id) {
        if(valid(id)) {
            if(own(id)) {
                return readById(id);
            }
            else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @PutMapping()
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Topic> updateEntity(@Valid @RequestBody TopicUpdateDto dto) {
        if(own(dto.getPK())) {
            return update(dto);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(ID_PARAM_PATH)
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Void> deleteEntityById(@PathVariable(ID) Integer id) {
        if(valid(id)) {
            if(own(id)) {
                return delete(id);
            }
            else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @PutMapping(TOGGLE_STATUS_PATH)
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Void> toggleActive(@Valid @RequestBody TopicUpdateDto dto) {
        if(own(dto.getPK())) {
            return toggleStatus(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    ///////////// Topic Info Control


    @GetMapping(INFO_ENDPOINT + ID_PARAM_PATH)
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<TopicInfo> requestTopicInfo(
            @PathVariable(name = ID) Integer topicId
    ) {
        if(valid(topicId)) {
            if(own(topicId)) {
                return new ResponseEntity<>(getService().getTopicInfo(topicId), HttpStatus.OK);
            }
            else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(MULTI_INFO_ENDPOINT + IDS_PARAM_PATH)
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<List<TopicInfo>> requestTopicsInfo(
            @PathVariable(name = IDS) Set<Integer> topicIds
    ) {
        if(valid(topicIds)) {
            List<Integer> ids = new ArrayList<>(topicIds);
            // all topics exist or request is rejected.
            if(own(ids)) {
                return new ResponseEntity<>(getService().getTopicsInfo(ids), HttpStatus.OK);
            }
            else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public boolean own(List<Integer> ids) {
        return getService().countExistingIds(ids) == ids.size();
    }

    @Override
    public boolean valid(Collection<Integer> ids) {
        return validIntegers(ids);
    }
}
