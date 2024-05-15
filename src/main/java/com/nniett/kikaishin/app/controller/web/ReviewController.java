package com.nniett.kikaishin.app.controller.web;

import com.nniett.kikaishin.app.controller.construction.*;
import com.nniett.kikaishin.app.persistence.entity.ReviewEntity;
import com.nniett.kikaishin.app.service.ReviewService;
import com.nniett.kikaishin.app.service.pojo.Review;
import com.nniett.kikaishin.app.service.pojo.ReviewableQuestion;
import com.nniett.kikaishin.app.service.pojo.dto.review.ReviewCreationDto;
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
                Review,
                ReviewCreationDto,
                ReviewService
                >
        implements CanCheckOwnership<Integer>, CanVerifyId<Integer>, VerifiesIntegerId
{


    public ReviewController(ReviewService service) {
        super(service);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Review> persistNewEntity(ReviewCreationDto dto) {
        return create(dto);
    }

    @Override
    public ResponseEntity<Review> getEntityById(Integer id) {
        return readById(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Void> deleteEntityById(Integer id) {
        return delete(id);
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
