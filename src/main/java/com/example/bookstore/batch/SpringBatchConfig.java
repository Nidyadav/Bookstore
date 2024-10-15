package com.example.bookstore.batch;

import com.example.bookstore.model.Book;
import com.example.bookstore.utils.DateUtils;
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
                .resource(new ClassPathResource("books_1.Best_Books_Ever.csv"))
                .delimited()
                .delimiter(",")
                .names("bookId", "quantity", "title", "series", "author", "rating", "description", "language", "isbn",
                        "genres", "characters", "bookForm", "edition", "pages", "publisher", "publishingDate", "firstPublishDate",
                        "awards", "numRating", "ratingsByStars", "LikedPercent", "setting", "coverImg", "bbeScores", "bbeVotes", "price")
                .fieldSetMapper(fieldSet -> Book.builder()
                        .bookId(fieldSet.readString("bookId"))
                        .quantity(fieldSet.readInt("quantity"))
                        .title(fieldSet.readString("title"))
                        .series(fieldSet.readString("series"))
                        .author(fieldSet.readString("author"))
                        .rating(fieldSet.readDouble("rating"))
                        .description(fieldSet.readString("description"))
                        .language(fieldSet.readString("language"))
                        .isbn(fieldSet.readString("isbn"))
                        .genres(fieldSet.readString("genres").split("\\|"))
                        .characters(fieldSet.readString("characters").split("\\|"))
                        .bookForm(fieldSet.readString("bookForm"))
                        .edition(fieldSet.readString("edition"))
                        .pages(fieldSet.readInt("pages"))  // Assuming this is an integer
                        .publisher(fieldSet.readString("publisher"))
                        .publishingDate(DateUtils.parseDate(fieldSet.readString("publishingDate")))
                        .firstPublishDate(DateUtils.parseDate(fieldSet.readString("firstPublishDate")))  // Same for dates
                        .awards(fieldSet.readString("awards").split("\\|"))
                        .numRating(fieldSet.readInt("numRating"))
                        .ratingsByStars(fieldSet.readString("ratingsByStars").split("\\|"))  // Assuming it's stored as a string
                        .likedPercent(fieldSet.readFloat("likedPercent"))
                        .setting(fieldSet.readString("setting").split("\\|"))
                        .coverImg(fieldSet.readString("coverImg"))
                        .bbeScore(fieldSet.readLong("bbeScore"))
                        .bbeVotes(fieldSet.readLong("bbeVotes"))
                        .price(fieldSet.readDouble("price"))

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
    public Job csvImporterJob(Step customerStep, JobRepository jobRepository,
                              ImportJobListener importJobListener) {
        return new JobBuilder("csvImporterJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .validator(new JobParametersValidator() {
                    @Override
                    public void validate(JobParameters parameters) throws JobParametersInvalidException {
                        String ignoreCountry = parameters.getString("ignoreCountry");
                        if ("Costa Rica".equals(ignoreCountry)) {
                            throw new JobParametersInvalidException("Country ignored");
                        }
                    }
                })
                .listener(importJobListener)
                .flow(customerStep)
                .end()
                .build();
    }

    @Bean
    public Step csvImporterStep(
            ItemReader<Book> csvReader, ItemWriter<Book> csvWriter,
            JobRepository jobRepository, PlatformTransactionManager tx) {

        return new StepBuilder("csvImporterStep", jobRepository)
                .<Book, Book>chunk(50, tx)
                .reader(csvReader)
                .writer(csvWriter)
              //  .processor(processor)
                .allowStartIfComplete(true)
                .build();
    }
}
