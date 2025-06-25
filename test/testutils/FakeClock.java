package testutils;

import java.text.ParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import controller.DateTimeUtils;
import products.Clock;

/**
 * FakeClock simuliert einen steuerbaren Zeitverlauf für Tests.
 */
public class FakeClock implements Clock {

    private Date initial;
    
    private Date current;

    public FakeClock(String initialDate) {
        try {
			this.initial = DateTimeUtils.toDate(initialDate);
	        this.current = this.initial;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
    }

    @Override
    public Date getCurrentDate() {
        return current;
    }

    @Override
    public Date getInitialDate() {
        return initial;
    }

    @Override
    public void passDay() {
        advanceDays(1);
    }

    public void advanceDays(int days) {
        current = Date.from(current.toInstant().plus(days, ChronoUnit.DAYS));
    }
    
	@Override
	public String toString() {
		return DateTimeUtils.formatDate(current);
	}
}



