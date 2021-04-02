package ru.agilix.bookstorage.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.agilix.bookstorage.repository.CommentRepository;
import ru.agilix.bookstorage.ui.output.BookOutputService;
import ru.agilix.bookstorage.ui.output.CommentOutputService;

@Service
public class CLICommentsService implements CommentsService {
    private final CommentRepository repository;
    private final CommentOutputService ui;
    private final InputService input;
    private final DateService date;
    private final BookOutputService bookUi;

    public CLICommentsService(CommentRepository repository, CommentOutputService ui, InputService input, DateService date, BookOutputService bookUi) {
        this.repository = repository;
        this.ui = ui;
        this.input = input;
        this.date = date;
        this.bookUi = bookUi;
    }

    @Override
    @Transactional
    public String addCommentByBookId(long bookId) {
        final var book = repository.findCommentByBook_Id(bookId);
        if (book.isPresent()) {
            final var newComment = input.getNewComment(book.get().getId());
            newComment.setBook(book.get());
            newComment.setDate(date.getCurrentDate());
            final var comment = repository.save(newComment);
            return ui.showCommentInfo(comment);
        } else {
            return bookUi.showBookNotFound(bookId);
        }
    }

    @Override
    @Transactional
    public String deleteComment(long id) {
        final var comment = repository.findById(id);
        if (comment.isPresent()){
            repository.delete(comment.get());
            return ui.showCommentDeletedMessage(id);
        } else {
            return ui.showCommentNotFound(id);
        }
    }

    @Override
    @Transactional
    public String updateComment(long id) {
        final var comment = repository.findById(id);
        if(comment.isPresent()) {
            final var updatedComment = input.getUpdatedComment(comment.get());
            final var savedComment = repository.save(updatedComment);
            return ui.showCommentUpdated(savedComment);
        } else {
            return ui.showCommentNotFound(id);
        }

    }
}
