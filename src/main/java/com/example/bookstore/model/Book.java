package com.example.bookstore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@Entity
@Table(name = "books")
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    private String bookId;
    private String quantity;
    private String title;
    private String series;
    private String author;
    private String rating;
    private String description;
    private String language;
    private String isbn;
    private String[] genres;
    private String[] characters;
    private String bookForm;
    private String edition;
    private String pages;
    private String publisher;
    private LocalDate publishingDate;
    private LocalDate firstPublishDate;
    private String[] awards;
    private String numRating;
    private String[] ratingsByStars;
    private String likedPercent;
    private String[] setting;
    private String coverImg;
    private String bbeScore;
    private String bbeVotes;
    private String price;


}
