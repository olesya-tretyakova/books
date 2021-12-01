package guru.qa.books;

import guru.qa.books.domain.Author;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.emptyArray;

@SpringBootTest
class BooksControllerTest {

    private RequestSpecification spec =
            with()
                    .baseUri("http://localhost:8080")
                    .basePath("/")
                    .contentType(JSON)
                    .log().all();

    @Test
    void getAllAuthorsTest() {
        spec.get("/getAllAuthors")
                .then()
                .statusCode(200)
                .body("results", not(emptyArray()));
    }

    @Test
    void addAuthorTest() {
        Map<String, String> body = Map.of(
                "name", "Ivan",
                "surname", "Bunin");
        spec.body(body)
                .post("/addAuthor")
                .then()
                .statusCode(SC_OK)
                .body(CoreMatchers.containsString("Add author with id"));

    }

    @Test
    void addBookWithCorrectBodyTest() {
        Map<String, Object> body = Map.of(
                "title", "Ruslan and Lyudmila",
                "author", new Author("Alexander", "Pushkin"),
                "issue_year", 2010);
        spec.body(body)
                .post("/addBook")
                .then()
                .statusCode(SC_OK)
                .body(CoreMatchers.containsString("Book was added"));

    }

    @Test
    void addBookWithEmptyBodyTest() {
        spec
                .post("/addBook")
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("error", equalTo("Bad Request"));

    }

    @Test
    void getAllBooksTest() {
        spec.get("/getAllBooks")
                .then()
                .statusCode(SC_OK)
                .body("results", not(emptyArray()));
    }

    @Test
    void getBooksByAuthorWithCorrectBodyTest() {

        String authorName = "Alexander";
        String authorSurname = "Pushkin";
        spec.params("name", authorName,
                        "surname", authorSurname)
                .get("/getBooksByAuthor")
                .then()
                .statusCode(SC_OK)
                .body("author.name[0]", equalTo(authorName))
                .body("author.surname[0]", equalTo(authorSurname))
                .body("title", hasItem("Ruslan and Lyudmila"));
    }

    @Test
    void getBooksByTitleTest() {
        spec.param("title", "Borodino")
                .get("/getBooksByTitle")
                .then()
                .statusCode(SC_OK)
                .body("title", hasItem("Borodino"))
                .body("author.name", hasItem("Mikhail"))
                .body("author.surname", hasItem("Lermontov"))
                .body("issue_year", hasItem(1990));
    }
}