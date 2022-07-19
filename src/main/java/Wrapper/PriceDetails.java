package Wrapper;

public class PriceDetails {
	
	public String onlinePlatform;
	public String price;
	public boolean isAvailable;

	public String getPrice() {
		return price;
	}

	public PriceDetails(String onlinePlatform, String price, boolean isAvailable) {
		this.onlinePlatform = onlinePlatform;
		this.price = price;
		this.isAvailable = isAvailable;
	}

	public String getOnlinePlatform() {
		return onlinePlatform;
	}

	public void setOnlinePlatform(String onlinePlatform) {
		this.onlinePlatform = onlinePlatform;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

}
