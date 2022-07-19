package util;

import com.opencsv.bean.CsvBindByPosition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlinePlatform {
	
	@CsvBindByPosition(position=0)
	private String onlinePlatformURL;

	public String getOnlinePlatformURL() {
		return onlinePlatformURL;
	}

	public void setOnlinePlatformURL(String onlinePlatformURL) {
		this.onlinePlatformURL = onlinePlatformURL;
	}
	
	


}
