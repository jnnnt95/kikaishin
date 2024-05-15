package com.nniett.kikaishin.common;

import static com.nniett.kikaishin.common.Constants.PREPARATION_LIST_PLACEHOLDER;

public final class SqlConstants {

    private SqlConstants() {}

    public static final String GET___ALL___REVIEWABLE_QUESTIONS_QUERY =
            " select question.id question_id, question.body question_text, question.create_date question_date, case when revCount.val is not null then revCount.val else 0 end question_review_count, revRecent.review_fk most_recent_review_id, revRecent.review_most_recent_date most_recent_review_date, topic.lookup_key lookup_key, review_model.* from shelf inner join book on shelf.user_fk = ? and shelf.active = true and shelf.id = book.shelf_fk and book.active = true inner join topic on book.id = topic.book_fk and topic.active = true inner join question on topic.id = question.topic_fk and topic.active = true and question.active = true inner join review_model on question.id = review_model.question_fk left join ( select aQuestion.question_fk questionId2, count(*) val from question_review_grade aQuestion inner join Review review on aQuestion.review_fk = review.id group by questionId2 ) revCount on question.id = revCount.questionId2 left join ( select question_review_grade.*, innr.max_date review_most_recent_date from question_review_grade inner join (select max(create_date) max_date from question_review_grade group by question_review_grade.question_fk) innr on question_review_grade.create_date = innr.max_date ) revRecent on question.id = revRecent.question_fk";
    // params: 1: username, 2: shelfId
    public static final String GET___SHELF___REVIEWABLE_QUESTIONS_QUERY = GET___ALL___REVIEWABLE_QUESTIONS_QUERY +
                    " where book.shelf_fk = ?";
    // params: 1: username, 2: bookId
    public static final String GET___BOOK___REVIEWABLE_QUESTIONS_QUERY = GET___ALL___REVIEWABLE_QUESTIONS_QUERY +
            " where topic.book_fk = ?";
    // params: 1: username, 2: topicId
    public static final String GET___TOPIC___REVIEWABLE_QUESTIONS_QUERY = GET___ALL___REVIEWABLE_QUESTIONS_QUERY +
            " where question.topic_fk = ?";
    // params: 1: username, 2: question ids list
    public static final String GET___QUESTION_COUNT___QUERY =
        "select count(*) count from user inner join shelf on user.username = :username and user.username = shelf.user_fk inner join book on shelf.id = book.shelf_fk inner join topic on book.id = topic.book_fk inner join question on topic.id = question.topic_fk where question.id in :ids";

    public static final String GET___TOPIC_COUNT___QUERY =
            "select count(*) count from user inner join shelf on user.username = :username and user.username = shelf.user_fk inner join book on shelf.id = book.shelf_fk inner join topic on topic.id in :ids and book.id = topic.book_fk";

    public static final String GET___QUESTIONS_INFO___QUERY =
            " select question.id questionId, question.active active, (select count(*) from question_review_grade where question_review_grade.question_fk = question.id) totalReviews, (select avg(grade_value) from question_review_grade where question_review_grade.question_fk = question.id) gradeAverage, question.body body, (select count(*) from answer where answer.question_fk = question.id) totalAnswers, (select count(*) from clue where clue.question_fk = question.id) totalClues, question.create_date createDate, revRecent.review_fk lastReviewId, revRecent.review_most_recent_date lastReviewDate, revRecent.grade_value lastReviewGrade, review_model.x0 x0, review_model.x1 x1, review_model.x2 x2, review_model.expected_reviews expectedReviews from user inner join shelf on user.username = ? and user.username = shelf.user_fk inner join book on shelf.id = book.shelf_fk inner join topic on book.id = topic.book_fk inner join question on question.id in " +
                    PREPARATION_LIST_PLACEHOLDER + "1" +
                    " and topic.id = question.topic_fk inner join review_model on question.id = review_model.question_fk left join ( select question_review_grade.*, innr.max_date review_most_recent_date from question_review_grade inner join (select max(create_date) max_date from question_review_grade group by question_review_grade.question_fk) innr on question_review_grade.create_date = innr.max_date ) revRecent on question.id = revRecent.question_fk ";

    // params: 1: topicId
    public static final String GET___TOPIC_QUESTIONS_INFO___QUERY =
            " select question.id questionId, question.active active, (select count(*) from question_review_grade where question_review_grade.question_fk = question.id) totalReviews, (select avg(grade_value) from question_review_grade where question_review_grade.question_fk = question.id) gradeAverage, question.body body, (select count(*) from answer where answer.question_fk = question.id) totalAnswers, (select count(*) from clue where clue.question_fk = question.id) totalClues, question.create_date createDate, revRecent.review_fk lastReviewId, revRecent.review_most_recent_date lastReviewDate, revRecent.grade_value lastReviewGrade, review_model.x0 x0, review_model.x1 x1, review_model.x2 x2, review_model.expected_reviews expectedReviews from topic inner join question on topic.id = ? and topic.id = question.topic_fk inner join review_model on question.id = review_model.question_fk left join ( select question_review_grade.*, innr.max_date review_most_recent_date from question_review_grade inner join (select max(create_date) max_date from question_review_grade group by question_review_grade.question_fk) innr on question_review_grade.create_date = innr.max_date ) revRecent on question.id = revRecent.question_fk ";


    // params: 1: username, 2: topicId
    public static final String GET___TOPICS_INFO___QUERY =
            " select topic.id topicId, topic.active active, topic.create_date createDate from user inner join shelf on user.username = ? and user.username = shelf.user_fk inner join book on shelf.id = book.shelf_fk inner join topic on topic.id in " +
                    PREPARATION_LIST_PLACEHOLDER + "1" +
                    " and book.id = topic.book_fk ";


    public static final String GET___BOOKS_INFO___QUERY =
            " select book.id bookId, book.active active, book.create_date createDate from user inner join shelf on user.username = ? and user.username = shelf.user_fk inner join book on book.id in  " +
                    PREPARATION_LIST_PLACEHOLDER + "1" +
                    " and shelf.id = book.shelf_fk ";

    // params: 1: bookId
    public static final String GET___BOOK_TOPICS_INFO___QUERY =
            " select topic.id topicId, topic.active active, topic.create_date createDate from book inner join topic on book.id = ? and book.id = topic.book_fk ";


    public static final String GET___BOOK_COUNT___QUERY =
            "select count(*) count from user inner join shelf on user.username = :username and user.username = shelf.user_fk inner join book on book.id in :ids and shelf.id = book.shelf_fk";



    public static final String GET___SHELVES_INFO___QUERY =
            "select shelf.id shelfId, shelf.active active, shelf.create_date createDate from user inner join shelf on user.username = ? and shelf.id in " +
                    PREPARATION_LIST_PLACEHOLDER + "1" +
                    " and user.username = shelf.user_fk ";


    // params: 1: shelfId
    public static final String GET___SHELF_BOOKS_INFO___QUERY =
            " select book.id bookId, book.active active, book.create_date createDate from shelf inner join book on shelf.id = ? and shelf.id = book.shelf_fk ";

    public static final String GET___SHELF_COUNT___QUERY =
            "select count(*) count from user inner join shelf on shelf.id in :ids and user.username = :username and user.username = shelf.user_fk";

    // params: 1: username
    public static final String GET___USER_INFO___QUERY =
            "select user.username username, user.display_name displayName, user.email email, user.create_date createDate from user where user.username = ?";



    // params: 1: username
    public static final String GET___USER_SHELVES_INFO___QUERY =
            "select shelf.id shelfId, shelf.active active, shelf.create_date createDate from user inner join shelf on user.username = ? and user.username = shelf.user_fk";

    public static final String GET___USER_QUESTIONS___QUERY =
            "select question.* from user inner join shelf on user.username = :username and user.username = shelf.user_fk inner join book on shelf.id = book.shelf_fk inner join topic on book.id = topic.book_fk inner join question on topic.id = question.topic_fk";

    public static final String GET___QUESTIONS_IN_LIST___QUERY =
            " select question.id question_id, question.body question_text, question.create_date question_date, coalesce(revCount.val, 0) question_review_count, revRecent.review_fk most_recent_review_id, revRecent.review_most_recent_date most_recent_review_date, topic.lookup_key lookup_key, review_model.* from topic inner join question on question.id in "
                    + PREPARATION_LIST_PLACEHOLDER + "1" +
                    " and question.active = true and topic.active = true and topic.id = question.topic_fk inner join review_model on question.id = review_model.question_fk left join ( select aQuestion.question_fk questionId2, count(*) val from question_review_grade aQuestion inner join Review review on aQuestion.review_fk = review.id group by questionId2 ) revCount on question.id = revCount.questionId2 left join ( select question_review_grade.*, innr.max_date review_most_recent_date from question_review_grade inner join ( select max(create_date) max_date from question_review_grade group by question_review_grade.question_fk ) innr on question_review_grade.create_date = innr.max_date ) revRecent on question.id = revRecent.question_fk ";


    // params: 1: username, 2: question ids list
    public static final String GET___REVIEW_COUNT___QUERY =
            "select count(*) count from user inner join review on user.username = :username and user.username = review.user_fk and review.id in :ids";


    // params: 1: username, 2: question ids list
    public static final String GET___ANSWER_COUNT___QUERY =
            "select count(*) count from user inner join shelf on user.username = :username and user.username = shelf.user_fk inner join book on shelf.id = book.shelf_fk inner join topic on book.id = topic.book_fk inner join question on topic.id = question.topic_fk inner join answer on question.id = answer.question_fk and answer.id in :ids";

    // params: 1: username, 2: question ids list
    public static final String GET___CLUE_COUNT___QUERY =
            "select count(*) count from user inner join shelf on user.username = :username and user.username = shelf.user_fk inner join book on shelf.id = book.shelf_fk inner join topic on book.id = topic.book_fk inner join question on topic.id = question.topic_fk inner join clue on question.id = clue.question_fk and clue.id in :ids";


}
