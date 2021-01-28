package ru.agilix.bookstorage.service;

import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.agilix.bookstorage.domain.Book;
import ru.agilix.bookstorage.repository.AuthorRepository;
import ru.agilix.bookstorage.repository.BooksRepository;
import ru.agilix.bookstorage.repository.GenreRepository;
import ru.agilix.bookstorage.ui.output.BookOutputService;

import java.util.List;
import java.util.Optional;

@Service
public class CLIBooksService implements BooksService {
    private final BooksRepository booksRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final BookOutputService ui;
    private final InputService input;

    public CLIBooksService(BooksRepository booksRepository, GenreRepository genreRepository, AuthorRepository authorRepository, BookOutputService ui, InputService input) {
        this.booksRepository = booksRepository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
        this.ui = ui;
        this.input = input;
    }

    @Override
    @Transactional
    public String updateBook(long id) {
        val existingBook = booksRepository.findById(id);
        if (existingBook.isPresent()) {

            Book updatedBook = input.getUpdatedBookInfo(existingBook.get(), authorRepository.findAll(), genreRepository.findAll());
            booksRepository.save(updatedBook);
            return ui.showBookInfo(updatedBook);
        } else {
            return ui.showBookNotFound(id);
        }
    }

    @Override
    public String retrieveAllBooks() {
        List<Book> bookList = booksRepository.findAll();
        return ui.showBooksList(bookList);
    }

    @Override
    @Transactional
    public String createBook() {
        Book book = input.getNewBook(authorRepository.findAll(), genreRepository.findAll());
        Book inserted = booksRepository.save(book);
        return ui.showBookCreatedMessage(inserted);
    }

    @Override
    @Transactional(readOnly = true)
    public String retrieveBook(long id) {
        Optional<Book> book = booksRepository.findById(id);
        if (book.isPresent()) {
            return ui.showBookInfo(book.get());
        } else {
            return ui.showBookNotFound(id);
        }
    }

    @Override
    @Transactional
    public String deleteBook(long id) {
        final var book = booksRepository.findById(id);
        if (book.isPresent()) {
            booksRepository.delete(book.get());
            return ui.showBookDeletedMessage(id);
        } else {
            return ui.showBookNotFound(id);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public String getCommentsByBookId(long bookId) {

        final var book = booksRepository.findById(bookId);
        if (book.isPresent()) {
            return ui.showListOfComments(book.get().getComments());
        } else {
            return ui.showBookNotFound(bookId);
        }

    }

}
