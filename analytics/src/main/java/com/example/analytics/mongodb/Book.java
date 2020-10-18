package com.example.analytics.mongodb;

import java.time.LocalDate;
import java.util.List;

public class Book {

    private String id;
    private String title;
    private String author;
    private LocalDate published;
    private Double goodreadsRating;
    private List<String> genre;

    public Book(String title, String author, LocalDate published) {
        this.title = title;
        this.author = author;
        this.published = published;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDate getPublished() {
        return published;
    }

    public Double getGoodreadsRating() {
        return goodreadsRating;
    }

    public void setGoodreadsRating(Double goodreadsRating) {
        this.goodreadsRating = goodreadsRating;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book{" +
               "id='" + id + '\'' +
               ", title='" + title + '\'' +
               ", author='" + author + '\'' +
               '}';
    }
}