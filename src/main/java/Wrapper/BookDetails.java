package Wrapper;

import java.util.ArrayList;
import java.util.List;

public class BookDetails {

	public String isbn;
	public String bookName;
	public String author;
	public String publisher;
	public int qty;
	public List<PriceDetails> book = new ArrayList<PriceDetails>();
	//public PriceDetails book;

	public BookDetails(String isbn, String bookName, String author, String publisher, int qty, List<PriceDetails> book) {
		super();
		this.isbn = isbn;
		this.bookName = bookName;
		this.author = author;
		this.publisher = publisher;
		this.qty = qty;
		this.book = book;
	}



	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public List<PriceDetails> getBook() {
		return book;
	}

	public void setBook(List<PriceDetails> book) {
		this.book = book;
	}

}
