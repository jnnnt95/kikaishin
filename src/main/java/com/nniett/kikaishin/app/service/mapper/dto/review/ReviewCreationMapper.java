package com.nniett.kikaishin.app.service.mapper.dto.review;


import com.nniett.kikaishin.app.service.mapper.dto.DtoPojoMapper;
import com.nniett.kikaishin.app.service.mapper.dto.answer.AnswerCreationMapper;
import com.nniett.kikaishin.app.service.mapper.dto.clue.ClueCreationMapper;
import com.nniett.kikaishin.app.service.mapper.dto.questionreviewgrade.QuestionReviewGradeCreationMapper;
import com.nniett.kikaishin.app.service.mapper.dto.reviewmodel.ReviewModelCreationMapper;
import com.nniett.kikaishin.app.service.pojo.Question;
import com.nniett.kikaishin.app.service.pojo.Review;
import com.nniett.kikaishin.app.service.pojo.dto.question.QuestionCreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.review.ReviewCreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.topic.TopicCreationDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {QuestionReviewGradeCreationMapper.class})
public interface ReviewCreationMapper extends DtoPojoMapper<ReviewCreationDto, Review> {
    @Override
    @Mappings({
            @Mapping(source = "questionGrades", target = "questionGrades"),
            @Mapping(source = "username", target = "username"),

            @Mapping(target = "user", ignore = true),
            @Mapping(target = "reviewId", ignore = true),
            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "questions", ignore = true)
    })
    public Review toPojo(ReviewCreationDto dto);

    @Override
    @InheritInverseConfiguration
    public ReviewCreationDto toDto(Review pojo);
}
