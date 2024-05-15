package com.nniett.kikaishin.app.persistence.repository.virtual;

import com.nniett.kikaishin.app.persistence.entity.ReviewModelEntity;
import com.nniett.kikaishin.app.persistence.entity.virtual.ReviewableQuestionVirtualEntity;
import com.nniett.kikaishin.app.persistence.repository.provider.database.ConnectionProvider;
import com.nniett.kikaishin.app.persistence.repository.provider.database.ReadProvider;
import com.nniett.kikaishin.common.Action;
import com.nniett.kikaishin.common.SqlConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.nniett.kikaishin.app.persistence.entity.virtual.GenericUtils.*;
import static com.nniett.kikaishin.common.SqlConstants.GET___QUESTIONS_IN_LIST___QUERY;

@Repository
public class ReviewableQuestionVirtualRepository extends VirtualRepository<ReviewableQuestionVirtualEntity> {

    @Autowired
    public ReviewableQuestionVirtualRepository(ConnectionProvider connectionProvider) {
        super(connectionProvider);
    }

    public List<ReviewableQuestionVirtualEntity> getAllReviewableQuestions(String username) {
        List<ReviewableQuestionVirtualEntity> entities = new ArrayList<>();
        Map<String, Action> params = new HashMap<>();
        params.put(ReadProvider.PREPARE, getPrepareActionForAll(username));
        params.put(ReadProvider.FINISH, getFinishAction(entities));
        new ReadProvider(SqlConstants.GET___ALL___REVIEWABLE_QUESTIONS_QUERY, params).runSelect(getConnection());
        return entities;
    }

    public List<ReviewableQuestionVirtualEntity> getReviewableQuestionsByIdsIn(List<Integer> questionIds) {
        String query = buildQueryWithPreparationList(GET___QUESTIONS_IN_LIST___QUERY, 1, questionIds.size());
        List<ReviewableQuestionVirtualEntity> entities = new ArrayList<>();
        Map<String, Action> params = new HashMap<>();
        params.put(ReadProvider.PREPARE, getPrepareActionForSelectById(questionIds));
        params.put(ReadProvider.FINISH, getFinishAction(entities));
        new ReadProvider(query, params).runSelect(getConnection());
        return entities;
    }

    public Action getPrepareActionForAll(String username) {
        return s -> {
            PreparedStatement stmt = (PreparedStatement) s;
            try {
                stmt.setString(1, username);
            } catch(SQLException e) {
                throw new RuntimeException(e);
            }
        };
    }

    public Action getPrepareActionForSelectById(List<Integer> questionIds) {
        return s -> {
            PreparedStatement stmt = (PreparedStatement) s;
            try {
                for (int i = 1; i <= questionIds.size(); i++) {
                    stmt.setInt(i, questionIds.get(i - 1));
                }
            } catch(SQLException e) {
                throw new RuntimeException(e);
            }
        };
    }

    public List<ReviewableQuestionVirtualEntity> getShelfReviewableQuestions(String username, Integer shelfId) {
        List<ReviewableQuestionVirtualEntity> entities = new ArrayList<>();
        Map<String, Action> params = new HashMap<>();
        params.put(ReadProvider.PREPARE, getPrepareActionForNotAll(username, shelfId));
        params.put(ReadProvider.FINISH, getFinishAction(entities));
        new ReadProvider(SqlConstants.GET___SHELF___REVIEWABLE_QUESTIONS_QUERY, params).runSelect(getConnection());
        return entities;
    }

    public Action getPrepareActionForNotAll(String username, Integer containerId) {
        return s -> {
            PreparedStatement stmt = (PreparedStatement) s;
            try {
                stmt.setString(1, username);
                stmt.setInt(2, containerId);
            } catch(SQLException e) {
                throw new RuntimeException(e);
            }
        };
    }

    public Action getFinishAction(List<ReviewableQuestionVirtualEntity> entities) {
        return r -> {
            ResultSet result = (ResultSet) r;
            try {
                while (result.next()) {
                    this.questionId = result.getInt("question_id");
                    this.body = result.getString("question_text");
                    this.createDate = result.getTimestamp("question_date");
                    this.reviewCount = result.getInt("question_review_count");
                    this.mostRecentReviewId = result.getInt("most_recent_review_id");
                    this.mostRecentReviewDate = result.getString("most_recent_review_date") != null
                            ? result.getTimestamp("most_recent_review_date")
                            : null;
                    this.lookupKey = result.getString("lookup_key");
                    this.expectedReviews = result.getInt("expected_reviews");
                    this.x0 = result.getFloat("x0");
                    this.x1 = result.getFloat("x1");
                    this.x2 = result.getFloat("x2");
                    entities.add(getInstance());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };
    }

    public List<ReviewableQuestionVirtualEntity> getBookReviewableQuestions(String username, Integer bookId) {
        List<ReviewableQuestionVirtualEntity> entities = new ArrayList<>();
        Map<String, Action> params = new HashMap<>();
        params.put(ReadProvider.PREPARE, getPrepareActionForNotAll(username, bookId));
        params.put(ReadProvider.FINISH, getFinishAction(entities));
        new ReadProvider(SqlConstants.GET___BOOK___REVIEWABLE_QUESTIONS_QUERY, params).runSelect(getConnection());
        return entities;
    }

    public List<ReviewableQuestionVirtualEntity> getTopicReviewableQuestions(String username, Integer topicId) {
        List<ReviewableQuestionVirtualEntity> entities = new ArrayList<>();
        Map<String, Action> params = new HashMap<>();
        params.put(ReadProvider.PREPARE, getPrepareActionForNotAll(username, topicId));
        params.put(ReadProvider.FINISH, getFinishAction(entities));
        new ReadProvider(SqlConstants.GET___TOPIC___REVIEWABLE_QUESTIONS_QUERY, params).runSelect(getConnection());
        return entities;
    }

    private Integer questionId;
    private String body;
    private Date createDate;
    private Integer reviewCount;
    private Integer mostRecentReviewId;
    private Date mostRecentReviewDate;
    private String lookupKey;
    private Integer expectedReviews;
    private Float x0;
    private Float x1;
    private Float x2;

    @Override
    protected ReviewableQuestionVirtualEntity getInstance() {
        ReviewableQuestionVirtualEntity entity = new ReviewableQuestionVirtualEntity();
        entity.setQuestionId(questionId);
        entity.setBody(body);
        entity.setCreationDate(toLocalDateTime(createDate));
        entity.setReviewCount(reviewCount);
        entity.setMostRecentReviewId(mostRecentReviewId);
        entity.setMostRecentReviewDate(mostRecentReviewDate != null ? toLocalDateTime(mostRecentReviewDate) : null);
        entity.setLookupKey(lookupKey);

        ReviewModelEntity reviewModelEntity = new ReviewModelEntity();
        reviewModelEntity.setExpectedReviews(expectedReviews);
        reviewModelEntity.setX0(x0);
        reviewModelEntity.setX1(x1);
        reviewModelEntity.setX2(x2);

        entity.setReviewModel(reviewModelEntity);
        entity.calculateNextReviewDate();
        return entity;
    }
}
