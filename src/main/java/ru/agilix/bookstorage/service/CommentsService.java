package ru.agilix.bookstorage.service;

public interface CommentsService {
    String addCommentByBookId(long bookId);

    String deleteComment(long id);

    String updateComment(long id);

}
