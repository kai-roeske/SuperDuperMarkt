package products;

import java.util.Date;

public interface Clock {
	
	public void passDay();
	
	public Date getCurrentDate();

	public Date getInitialDate();
	
}
