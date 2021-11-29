package guru.qa.books.controller;

import guru.qa.books.domain.Author;
import guru.qa.books.domain.Book;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class BooksController {
    Author author_1 = new Author("Erich Maria", "Remarque");
    Author author_2 = new Author("Alexander", "Pushkin");
    Author author_3 = new Author("Mikhail", "Lermontov");
    Author author_4 = new Author("William", "Shakespeare");
    private Map<Integer, Book> books = Map.of(
            1, Book.builder().id(1).author(author_1).name("Three comrades").build(),
            2, Book.builder().id(2).author(author_2).name("Eugene Onegin").build(),
            3, Book.builder().id(3).author(author_3).name("Borodino").build(),
            4, Book.builder().id(4).author(author_4).name("Romeo and Juliet").build()
    );

    List<Author> authors = new ArrayList<>();
    

    @PostMapping("/addAuthor")
    @ApiOperation("Добавить автора")
    public Integer addAuthor(Author author) {

        return 2;
    }
}
