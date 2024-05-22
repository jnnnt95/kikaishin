package com.nniett.kikaishin.app.web.controller;

import com.nniett.kikaishin.app.persistence.entity.ReviewEntity;
import com.nniett.kikaishin.app.service.ReviewService;
import com.nniett.kikaishin.app.service.dto.ReviewDto;
import com.nniett.kikaishin.app.service.dto.write.review.ReviewCreationDto;
import com.nniett.kikaishin.app.web.controller.construction.CanCheckOwnership;
import com.nniett.kikaishin.app.web.controller.construction.CanVerifyId;
import com.nniett.kikaishin.app.web.controller.construction.ImmutableController;
import com.nniett.kikaishin.app.web.controller.construction.VerifiesIntegerId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

import static com.nniett.kikaishin.common.Constants.*;

@RestController
@RequestMapping(REVIEW_PATH)
public class ReviewController extends ImmutableController
        <
                ReviewEntity,
                Integer,
                ReviewDto,
                ReviewCreationDto,
                ReviewService
                >
        implements CanCheckOwnership<Integer>, CanVerifyId<Integer>, VerifiesIntegerId
{
    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    public ReviewController(ReviewService service) {
        super(service);
        logger.info("ReviewController initialized.");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<ReviewDto> persistNewEntity(ReviewCreationDto dto) {
        return create(dto);
    }

    @Override
    public ResponseEntity<ReviewDto> getEntityById(Integer id) {
        return readById(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Void> deleteEntityById(Integer id) {
        return delete(id);
    }

    @Override
    public boolean own(List<Integer> ids) {
        logger.debug("Review ids validation for ownership.");
        logger.trace("Checking if ids are own. Ids: {}.", ids.toString());
        boolean ownCheck = getService().countExistingIds(ids) == ids.size();
        if(ownCheck) {
            logger.trace("Ids are own. Ids: {}.", ids);
        } else {
            logger.trace("One or more values in list are not own. Ids: {}.", ids);
        }
        logger.debug("Review ids validation for ownership completed.");
        return ownCheck;
    }

    @Override
    public boolean valid(Collection<Integer> ids) {
        logger.debug("Review ids validation.");
        logger.trace("Checking if ids are valid. Ids: {}", ids);
        boolean validCheck = validIntegers(ids);
        if(validCheck) {
            logger.trace("Ids are valid. Ids: {}.", ids);
        } else {
            logger.trace("One or more values in list are not valid. Ids: {}.", ids);
        }
        logger.debug("Review ids validation completed.");
        return validCheck;
    }
}
