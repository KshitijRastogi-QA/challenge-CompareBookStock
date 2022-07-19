package Wrapper;

import java.util.List;

public class Details {

	public List<BookDetails> isbn ;

	public List<BookDetails> getIsbn() {
		return isbn;
	}

	public void setIsbn(List<BookDetails> isbn) {
		this.isbn = isbn;
	}

	public Details(List<BookDetails> isbn) {
		super();
		this.isbn = isbn;
	}

	 
	

}
