CREATE TABLE books (
    book_id VARCHAR(255) NOT NULL,        -- String bookId, using VARCHAR for strings, and PRIMARY KEY to make it unique
    quantity INT,                            -- int quantity
    title VARCHAR(255),                      -- String title
    series VARCHAR(255),                     -- String series
    author VARCHAR(255),                     -- String author
    rating DOUBLE PRECISION,                 -- double rating
    description TEXT,                        -- String description, TEXT allows larger strings
    language VARCHAR(100),                   -- String language
    isbn VARCHAR(20),                        -- String isbn, sized for typical ISBN length
    genres TEXT[],                           -- String[] genres, PostgreSQL supports arrays with []
    characters TEXT[],                       -- String[] characters, PostgreSQL array for characters
    book_form VARCHAR(50),                   -- String bookForm
    edition VARCHAR(50),                     -- String edition
    pages INT,                               -- int pages
    publisher VARCHAR(255),                  -- String publisher
    publishing_date DATE,                    -- LocalDate publishingDate, stored as DATE
    first_publish_date DATE,                 -- LocalDate firstPublishDate, stored as DATE
    awards TEXT[],                           -- String[] awards, storing as an array of TEXT
    num_rating BIGINT,                       -- long numRating, using BIGINT for large integers
    rating_by_stars BIGINT[],                -- long[] ratingByStars, array of BIGINT for ratings per star
    liked_percent REAL,                      -- float likedPercent, REAL type for single-precision floating-point
    setting TEXT[],                          -- String[] setting, using array of TEXT
    cover_img VARCHAR(255),                  -- String coverImg, corrected name to cover_img
    bbe_score BIGINT,                        -- long bbeScore, using BIGINT for large integers
    bbe_votes BIGINT,                        -- long bbeVotes, using BIGINT for large integers
    price DOUBLE PRECISION,              -- double price, using DOUBLE PRECISION for prices
     PRIMARY KEY(book_id);
);
