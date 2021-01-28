package ru.agilix.bookstorage.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.agilix.bookstorage.domain.Book;
import ru.agilix.bookstorage.domain.Comment;
import ru.agilix.bookstorage.repository.BooksRepository;
import ru.agilix.bookstorage.repository.CommentRepository;
import ru.agilix.bookstorage.repository.dsl.Create;
import ru.agilix.bookstorage.ui.output.BookOutputService;
import ru.agilix.bookstorage.ui.output.CommentOutputService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CLICommentsServiceTests {
    @Mock
    private CommentRepository repository;

    private CommentsService service;

    @Mock
    private CommentOutputService output;

    @Mock
    private InputService input;

    @Mock
    private DateService date;

    @Mock
    private BookOutputService bookUi;

    @BeforeEach
    void setUp() {
        this.service = new CLICommentsService(repository, output, input, date, bookUi);
    }

    @Test
    void shouldGetNewCommentAndSaveIt() {
        Comment comment = Create.Comment(0)
                .Text("text")
                .Author("somebody")
                .build();
        Book book = Create.Book(1L).build();
        given(repository.findCommentByBook_Id(1L)).willReturn(Optional.of(book));
        given(input.getNewComment(1L)).willReturn(comment);

        service.addCommentByBookId(1L);

        verify(input, times(1)).getNewComment(1L);
        verify(repository, times(1)).save(comment);
    }



    @Test
    void shouldCallDeleteOnDaoAndSuccessMessageWhenCommentExists() {
        final var existingId = 1L;
        final var comment = Create.Comment().Id(existingId).build();
        given(repository.findById(existingId)).willReturn(Optional.of(comment));

        service.deleteComment(1);

        verify(repository, times(1)).delete(comment);
        verify(output, times(1)).showCommentDeletedMessage(existingId);
    }

    @Test
    void shouldNotCallDeleteShowErrorMessageWhenCommentNotExists() {
        long nonExistingId = -1L;
        given(repository.findById(nonExistingId)).willReturn(Optional.empty());

        service.deleteComment(nonExistingId);

        verify(repository, times(0)).delete(any(Comment.class));
        verify(output, times(1)).showCommentNotFound(nonExistingId);
    }

    @Test
    void shouldCallGetUpdatedCommentDaoSaveAndShowResultMessage() {
        final var existingCommentId = 1L;
        Comment comment = Create.Comment().Id(existingCommentId).build();
        given(input.getUpdatedComment(comment)).willReturn(comment);
        given(repository.findById(existingCommentId)).willReturn(Optional.of(comment));

        service.updateComment(existingCommentId);

        verify(repository, times(1)).findById(existingCommentId);
        verify(input, times(1)).getUpdatedComment(comment);
        verify(repository, times(1)).save(any(Comment.class));
        verify(output, times(1)).showCommentUpdated(any());
    }

    @Test
    void shouldShowCommentIsNotFoundForNonExistingCommentId() {
        final var nonExistingCommentId = -1L;
        Comment comment = Create.Comment().Id(nonExistingCommentId).build();
        given(repository.findById(nonExistingCommentId)).willReturn(Optional.empty());

        service.updateComment(nonExistingCommentId);

        verify(repository, times(0)).save(comment);
        verify(output, times(1)).showCommentNotFound(nonExistingCommentId);
    }

}
