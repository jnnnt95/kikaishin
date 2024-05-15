package com.nniett.kikaishin.app.service.mapper;

import com.nniett.kikaishin.app.persistence.entity.ReviewEntity;
import com.nniett.kikaishin.app.service.pojo.Review;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {QuestionMapper.class, QuestionReviewGradeMapper.class})
public interface ReviewMapper extends EntityPojoMapper<ReviewEntity, Review> {
    @Override
    @Mappings({
            @Mapping(source = "id", target = "reviewId"),
            @Mapping(source = "questionReviewGrades", target = "questionGrades"),
            @Mapping(source = "userId", target = "username"),
            @Mapping(source = "creationDate", target = "createDate"),
            @Mapping(source = "questions", target = "questions"),


            @Mapping(target = "user", ignore = true)
    })
    Review toPojo(ReviewEntity entity);
    @Override
    List<Review> toPojos(List<ReviewEntity> entities);

    @Override
    @InheritInverseConfiguration
    ReviewEntity toEntity(Review pojo);
    @Override
    List<ReviewEntity> toEntities(List<Review> pojos);
}
