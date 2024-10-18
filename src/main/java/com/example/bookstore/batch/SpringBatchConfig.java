package com.example.bookstore.batch;

import com.example.bookstore.model.Book;
import com.example.bookstore.utils.DateUtils;
import com.example.bookstore.utils.Utils;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
    @Bean
    public FlatFileItemReader<Book> reader() {

        return new FlatFileItemReaderBuilder<Book>()
                .linesToSkip(1)
                .name("csvItemReader")
                .resource(new ClassPathResource("books.csv"))
                .delimited()
                .delimiter(",")
                .names("bookId", "quantity", "title", "series", "author", "rating", "description", "language", "isbn",
                        "genres", "characters", "bookForm", "edition", "pages", "publisher", "publishingDate", "firstPublishDate",
                        "awards", "numRating", "ratingsByStars", "likedPercent", "setting", "coverImg", "bbeScore", "bbeVotes", "price")
                .fieldSetMapper(fieldSet -> Book.builder()
                        .bookId(fieldSet.readString("bookId"))
                        .quantity(fieldSet.readString("quantity"))
                        .title(fieldSet.readString("title"))
                        .series(fieldSet.readString("series"))
                        .author(fieldSet.readString("author"))
                        .rating(fieldSet.readString("rating"))
                        .description(fieldSet.readString("description"))
                        .language(fieldSet.readString("language"))
                        .isbn(fieldSet.readString("isbn"))
                        .genres(Utils.stringToList(fieldSet.readString("genres")))
                        .characters(Utils.stringToList(fieldSet.readString("characters")))
                        .bookForm(fieldSet.readString("bookForm"))
                        .edition(fieldSet.readString("edition"))
                        .pages(fieldSet.readString("pages"))  // Assuming this is an integer
                        .publisher(fieldSet.readString("publisher"))
                        .publishingDate(DateUtils.parseDate(fieldSet.readString("publishingDate")))
                        .firstPublishDate(DateUtils.parseDate(fieldSet.readString("firstPublishDate")))  // Same for dates
                        .awards(Utils.stringToList(fieldSet.readString("awards")))
                        .numRating(fieldSet.readString("numRating"))
                        .ratingsByStars(Utils.stringToList(fieldSet.readString("ratingsByStars")))
                        .likedPercent(fieldSet.readString("likedPercent"))
                        .coverImg(fieldSet.readString("coverImg"))
                        .bbeScore(fieldSet.readString("bbeScore"))
                        .bbeVotes(fieldSet.readString("bbeVotes"))
                        .price(fieldSet.readString("price"))
                        .build()
                ).build();

    }

    @Bean
    public JpaItemWriter<Book> writer(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<Book> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }


    @Bean
    public Job csvImporterJob(Step customerStep, JobRepository jobRepository) {
        return new JobBuilder("csvImporterJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(customerStep)
                .end()
                .build();
    }

    @Bean
    public Step csvImporterStep(
            ItemReader<Book> csvReader, ItemWriter<Book> csvWriter,
            JobRepository jobRepository, PlatformTransactionManager tx) {

        return new StepBuilder("csvImporterStep", jobRepository)
                .<Book, Book>chunk(25, tx)
                .reader(csvReader)
                .writer(csvWriter)
                //.processor(processor)
                .allowStartIfComplete(true)
                .build();
    }
}
