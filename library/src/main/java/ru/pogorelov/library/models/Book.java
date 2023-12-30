package ru.pogorelov.library.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;
    @Column(name = "year")
    private int year;
    @Column(name = "author_id")
    private int author_id;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Person owner;

    public Book() {
    }

    public Book(String title, int year, String author) {
        this.title = title;
        this.year = year;
        this.author_id = author_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getAuthor() {
        return author_id;
    }

    public void setAuthor(String author) {
        this.author_id = author_id;
    }
}
