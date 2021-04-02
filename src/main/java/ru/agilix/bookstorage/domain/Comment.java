package ru.agilix.bookstorage.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "text")
    private String text;

    @Column(name = "author")
    private String author;

    @Column(name = "date")
    private Timestamp date;

    @ManyToOne(cascade=CascadeType.MERGE, targetEntity = Book.class)
    private Book book;

    public Comment(long id, String text, String author, Timestamp date) {
        this.id = id;
        this.text = text;
        this.author = author;
        this.date = date;
    }

    public Comment(String text, String author) {
        this.text = text;
        this.author = author;
    }

    @Override
    public String toString() {
        return String.format("Comment(%d,'%s','%s','%s', %s)", id, text, author,date, book);
    }
}
