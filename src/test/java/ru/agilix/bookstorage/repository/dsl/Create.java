package ru.agilix.bookstorage.repository.dsl;

public class Create {
    public static BookBuilder Book(long id) {
        return new BookBuilder(id);
    }

    public static CommentBuilder Comment() {
        return new CommentBuilder(-1);
    }

    public static BookBuilder Book() {
        return new BookBuilder(-1L);
    }

    public static CommentBuilder Comment(long id) {
        return new CommentBuilder(id);
    }
}
