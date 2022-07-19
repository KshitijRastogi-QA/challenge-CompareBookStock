package Wrapper;

public class TotalDetails {

	int quantityPrice;
	String bookName;
	String platForm;

	public TotalDetails(int quantityPrice, String bookName, String platForm) {
		super();
		this.quantityPrice = quantityPrice;
		this.bookName = bookName;
		this.platForm = platForm;
	}

	public int getQuantityPrice() {
		return quantityPrice;
	}

	public void setQuantityPrice(int quantityPrice) {
		this.quantityPrice = quantityPrice;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getPlatForm() {
		return platForm;
	}

	public void setPlatForm(String platForm) {
		this.platForm = platForm;
	}

}
