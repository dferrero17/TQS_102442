package pt.ua.deti.tqs.lab5.lab5_2;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookSearchSteps {

	Library library = new Library();
	List<Book> result = new ArrayList<>();

	@ParameterType("([0-9]{4})-([0-9]{2})-([0-9]{2})")
	public Date iso8601Date(String year, String month, String day){
		return Date.from(LocalDateTime.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day),0, 0).atZone(ZoneId.systemDefault()).toInstant());
	}

	@Given("a book with the title {string}, written by {string}, published in {iso8601Date}")
	public void addNewBook(final String title, final String author, final Date published) {
		Book book = new Book(title, author, published);
		library.addBook(book);
	}

	@Given("another book with the title {string}, written by {string}, published in {iso8601Date}")
	public void addNewBook2(final String title, final String author, final Date published) {
		Book book = new Book(title, author, published);
		library.addBook(book);
	}
 
	@When("the customer searches for books published between {iso8601Date} and {iso8601Date}")
	public void setSearchParameters(final Date from, final Date to) {
		result = library.findBooks(from, to);
	}
 
	@Then("{int} books should have been found")
	public void verifyAmountOfBooksFound(final int booksFound) {
		Assertions.assertEquals(result.size(), booksFound);
	}
 
	@Then("Book {int} should have the title {string}")
	public void verifyBookAtPosition(final int position, final String title) {
		Assertions.assertEquals(result.get(position - 1).getTitle(), title);
	}

}