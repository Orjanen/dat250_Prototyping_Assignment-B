package com.example.analytics.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.result.DeleteResult;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Component
public class AppRunner implements CommandLineRunner {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("collectionExists: " + mongoTemplate.collectionExists("book"));

        /*
         * Perform action based upon the provided program argument.
         * Valid action arguments are: query, update, insert and delete.
         * No argument defaults to query.
         */
        if (args.length == 0 || args[0].equalsIgnoreCase("query")) {
            // When no program argument is supplied or argument is "query",
            // query the collection.
            doQuery();
        }
        else if (args[0].equalsIgnoreCase("insert")) {
            doInsert();
        }
        else if (args[0].equalsIgnoreCase("update")) {
            doUpdate();
        }
        else if (args[0].equalsIgnoreCase("delete")) {
            doDelete();
        }
        else {
            throw new IllegalArgumentException("### Invalid program argument ###: " + args[0]);
        }
    }

    private void doInsert() {

        // Insert one book
        Book animalFarm = new Book("Animal Farm", "George Orwell", LocalDate.of(1945, 8, 17));
        mongoTemplate.insert(animalFarm);

        // Insert multiple books
        Book brothersKaramazov = new Book("Brothers Karamazov", "Fyodor Dostoyevsky", LocalDate.of(1879, 1, 1));
        Book crimeAndPunishmant = new Book("Crime and Punishment", "Fyodor Dostoyevsky", LocalDate.of(1866, 1, 1));
        mongoTemplate.insert(Arrays.asList(brothersKaramazov, crimeAndPunishmant), Book.class);
    }

    private void doQuery() {

        // Query one book by title
        Criteria criteria = Criteria.where("title").is("Animal Farm");
        Query query = Query.query(criteria);
        Book book = mongoTemplate.findOne(query, Book.class);
        System.out.println(book);

        // Query all books
        List<Book> books = mongoTemplate.findAll(Book.class);
        books.forEach(System.out::println);

        // Query multiple books by author
        BasicQuery queryByAuthor = new BasicQuery("{ author: 'Fyodor Dostoyevsky' }");
        List<Book> booksByAuthor = mongoTemplate.find(queryByAuthor, Book.class);
        booksByAuthor.forEach(System.out::println);
    }

    private void doUpdate() {

        // Update one book
        Query query = new Query(Criteria.where("title").is("Animal Farm"));
        Update update = new Update().set("goodreadsRating", 3.9)
                .set("genre", Arrays.asList("Novel", "Allegory", "Satire" ));
        UpdateResult result = mongoTemplate.updateFirst(query, update, Book.class);
        System.out.println("Modified documents: " + result.getModifiedCount());

        // Update multiple books queried by author
        Query queryAuthor = query(where("author").is("Fyodor Dostoyevsky"));
        Update updateAuthor = new Update().set("author", "Fyodor Mikhailovich Dostoyevsky");
        UpdateResult resultAuthor = mongoTemplate.updateMulti(queryAuthor, updateAuthor, Book.class);
        System.out.println("Modified documents: " + resultAuthor.getModifiedCount());
    }

    private void doDelete() {

        // Delete a book by title
        Query query = query(where("title").is("Animal Farm"));
        DeleteResult result = mongoTemplate.remove(query, Book.class);
        System.out.println("Deleted documents: " + result.getDeletedCount());
    }
}