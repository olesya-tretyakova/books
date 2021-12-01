package guru.qa.books.controller;

import guru.qa.books.domain.Author;
import guru.qa.books.domain.Book;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class BooksController {
    Author author_1 = new Author("Erich Maria", "Remarque");
    Author author_2 = new Author("Alexander", "Pushkin");
    Author author_3 = new Author("Mikhail", "Lermontov");
    Author author_4 = new Author("William", "Shakespeare");
    List<Author> authorList = Stream.of(author_1, author_2, author_3, author_4)
            .collect(Collectors.toList());

    Map<Integer, Book> books = new HashMap<>() {
        {
            put(1, new Book("Three comrades", author_1, 1985));
            put(2, new Book("Eugene Onegin", author_2, 2012));
            put(3, new Book("Borodino", author_3, 1990));
            put(4, new Book("Romeo and Juliet", author_4, 2000));
        }
    };


    @PostMapping("/addAuthor")
    @ApiOperation("Добавить автора")
    public String addAuthor(@RequestBody Author author) {
        authorList.add(author);
        return "Add author with id = " + authorList.size();
    }

    @PostMapping("/addBook")
    @ApiOperation("Добавить книгу")
    public String addBook(@RequestBody Book book) {
        Integer bookKey = books.size() + 1;
        books.put(bookKey, book);
        return "Book was added";
    }

    @GetMapping("/getBooksByAuthor")
    @ApiOperation("Получить список книг по автору")
    public List<Book> getBooksByAuthor(@RequestParam String name, @RequestParam String surname) {
        List<Book> result = new ArrayList<>();
        books.forEach((integer, book) -> {
            if (book.getAuthor().getName().equals((name)) &&
                    book.getAuthor().getSurname().equals(surname))
                result.add(book);
        });
        return result;
    }

    @GetMapping("/getAllAuthors")
    @ApiOperation("Получить список авторов")
    public List<Author> getAuthorList() {
        return authorList;
    }

    @GetMapping("/getAllBooks")
    @ApiOperation("Получить список книг")
    public Map<Integer, Book> getAllBooks() {
        return books;
    }

    @SneakyThrows
    @GetMapping("/getBooksByTitle")
    @ApiOperation("Найти книгу по названию")
    public List<Book> getBooksByTitle(@RequestParam String title) {
        List<Book> result = new ArrayList<>();
        books.forEach((integer, book) -> {
            if (book.getTitle().equals((title)))
                result.add(book);
        });
        return result;
    }
}

