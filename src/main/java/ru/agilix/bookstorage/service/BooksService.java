package ru.agilix.bookstorage.service;

public interface BooksService {
    String createBook();

    String retrieveBook(long id);

    String updateBook(long id);

    String retrieveAllBooks();

    String deleteBook(long id);

    String getCommentsByBookId(long bookId);
}
