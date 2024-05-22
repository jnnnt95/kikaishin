package com.nniett.kikaishin.app.web.controller;

import com.nniett.kikaishin.app.web.controller.construction.CanCheckOwnership;
import com.nniett.kikaishin.app.web.controller.construction.CanVerifyId;
import com.nniett.kikaishin.app.web.controller.construction.Controller;
import com.nniett.kikaishin.app.web.controller.construction.VerifiesIntegerId;
import com.nniett.kikaishin.app.persistence.entity.ClueEntity;
import com.nniett.kikaishin.app.service.ClueService;
import com.nniett.kikaishin.app.service.dto.ClueDto;
import com.nniett.kikaishin.app.service.dto.write.clue.ClueCreationDto;
import com.nniett.kikaishin.app.service.dto.write.clue.ClueUpdateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
//@RequestMapping(REVIEW_PATH)
public class ClueController extends Controller
        <
                ClueEntity,
                Integer,
                ClueDto,
                ClueCreationDto,
                ClueUpdateDto,
                ClueService
                >
        implements CanCheckOwnership<Integer>, CanVerifyId<Integer>, VerifiesIntegerId
{

    private static final Logger logger = LoggerFactory.getLogger(ClueController.class);

    @Autowired
    public ClueController(ClueService service) {
        super(service);
        logger.info("ClueController initialized.");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<ClueDto> updateEntity(ClueUpdateDto creationDto) {
        return update(creationDto);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<ClueDto> persistNewEntity(ClueCreationDto dto) {
        return create(dto);
    }

    @Override
    public ResponseEntity<ClueDto> getEntityById(Integer id) {
        return readById(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Void> deleteEntityById(Integer id) {
        return delete(id);
    }

    @Override
    public boolean own(List<Integer> ids) {
        logger.debug("Clue ids validation for ownership.");
        logger.trace("Checking if ids are own. Ids: {}.", ids.toString());
        boolean ownCheck = getService().countExistingIds(ids) == ids.size();
        if(ownCheck) {
            logger.trace("Ids are own. Ids: {}.", ids);
        } else {
            logger.trace("One or more values in list are not own. Ids: {}.", ids);
        }
        logger.debug("Clue ids validation for ownership completed.");
        return ownCheck;
    }

    @Override
    public boolean valid(Collection<Integer> ids) {
        logger.debug("Clue ids validation.");
        logger.trace("Checking if ids are valid. Ids: {}", ids);
        boolean validCheck = validIntegers(ids);
        if(validCheck) {
            logger.trace("Ids are valid. Ids: {}.", ids);
        } else {
            logger.trace("One or more values in list are not valid. Ids: {}.", ids);
        }
        logger.debug("Clue ids validation completed.");
        return validCheck;
    }
}
