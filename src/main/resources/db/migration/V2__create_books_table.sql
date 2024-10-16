CREATE TABLE books (
    book_id TEXT NOT NULL,
    quantity TEXT,
    title TEXT,
    series TEXT,
    author TEXT,
    rating TEXT,
    description TEXT,
    language TEXT,
    isbn TEXT,
    genres TEXT[],
    characters TEXT[],
    book_form TEXT,
    edition TEXT,                     -- String edition
    pages TEXT,                               -- int pages
    publisher TEXT,
    publishing_date DATE,                    -- LocalDate publishingDate, stored as DATE
    first_publish_date DATE,                 -- LocalDate firstPublishDate, stored as DATE
    awards TEXT[],                           -- String[] awards, storing as an array of TEXT
    num_rating TEXT,                       -- long numRating, using BIGINT for large integers
    rating_by_stars TEXT[],                -- long[] ratingByStars, array of BIGINT for ratings per star
    liked_percent TEXT,                      -- float likedPercent, REAL type for single-precision floating-point
    setting TEXT[],
    cover_img TEXT,
    bbe_score BIGINT,
    bbe_votes BIGINT,
    price TEXT,
    PRIMARY KEY(book_id)
);
