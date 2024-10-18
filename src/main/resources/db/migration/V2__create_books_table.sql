CREATE TABLE book_awards
(
    book_id VARCHAR(255) NOT NULL,
    awards  VARCHAR(255)
);

CREATE TABLE book_characters
(
    book_id    VARCHAR(255) NOT NULL,
    characters VARCHAR(255)
);

CREATE TABLE book_genres
(
    book_id VARCHAR(255) NOT NULL,
    genre   VARCHAR(255)
);

CREATE TABLE book_ratings_by_stars
(
    book_id          VARCHAR(255) NOT NULL,
    ratings_by_stars VARCHAR(255)
);

CREATE TABLE books
(
    book_id            VARCHAR(255) NOT NULL,
    quantity           VARCHAR(255),
    title              VARCHAR(255),
    series             VARCHAR(255),
    author             VARCHAR(255),
    rating             VARCHAR(255),
    description        TEXT,
    language           VARCHAR(255),
    isbn               VARCHAR(255),
    book_form          VARCHAR(255),
    edition            VARCHAR(255),
    pages              VARCHAR(255),
    publisher          VARCHAR(255),
    publishing_date    date,
    first_publish_date date,
    num_rating         VARCHAR(255),
    liked_percent      VARCHAR(255),
    cover_img          TEXT,
    bbe_score          VARCHAR(255),
    bbe_votes          VARCHAR(255),
    price              VARCHAR(255),
    CONSTRAINT pk_books PRIMARY KEY (book_id)
);

ALTER TABLE book_awards
    ADD CONSTRAINT fk_book_awards_on_book FOREIGN KEY (book_id) REFERENCES books (book_id);

ALTER TABLE book_characters
    ADD CONSTRAINT fk_book_characters_on_book FOREIGN KEY (book_id) REFERENCES books (book_id);

ALTER TABLE book_genres
    ADD CONSTRAINT fk_book_genres_on_book FOREIGN KEY (book_id) REFERENCES books (book_id);

ALTER TABLE book_ratings_by_stars
    ADD CONSTRAINT fk_book_ratings_by_stars_on_book FOREIGN KEY (book_id) REFERENCES books (book_id);