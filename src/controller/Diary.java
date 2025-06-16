package controller;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import products.Clock;

/**
 * Singleton.
 * Simuliert Lauf der Zeit und bietet Werkzeuge zur Ausgabe
 */
public class Diary implements Clock{
	
	/**
	 * Singleton
	 */
	private static Diary instance = null;
	
	/**
	 * stellt das "aktuelle" Datum dar
	 */
	private Date currentDate;

	private Diary() {
		super();
		currentDate =  getInitialDate();
	}
	
	/**
	 * Get/Create the singleton instance of Diary
	 */
	public static synchronized Diary get() {
		if(instance == null) {
			instance = new Diary();
		}
		return instance;
	}
	
	/**
	 * Start-Datum - Hier beginnen die Aufzeichnungen
	 */
	@Override
	public final Date getInitialDate() {
		final LocalDate localDate = LocalDate.of(2025, 06, 07);
		return DateTimeUtils.toDate(localDate);
	}
	
	/**
	 * Simuliert das Verstreichen eines Tags.
	 */
	@Override
	public void passDay() {
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		c.add(Calendar.DATE, 1);
		currentDate = c.getTime();
	}

	@Override
	public Date getCurrentDate() {
		return currentDate;
	}
}
