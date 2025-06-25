package controller;

import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Utility class for common date operations such as parsing, formatting,
 * and conversion between {@link Date} and {@link LocalDateTime}.
 */
public class DateTimeUtils {
	
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	private DateTimeUtils() {
		super();
	}
	
	/**
	 * Converts a date string in the format "dd.MM.yyyy" into a {@link Date} object.
	 *
	 * @param dateStr the date string to parse (must match "dd.MM.yyyy")
	 * @return the parsed {@link Date}
	 * @throws ParseException if the string cannot be parsed
	 */
	public static Date toDate(String dateStr) throws ParseException {
		LocalDate date = LocalDate.parse(dateStr, DATE_FORMATTER);
		return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
	
	/**
	 * Formats the given {@link Date} into a string using the pattern "dd.MM.yyyy".
	 *
	 * @param date the {@link Date} to format
	 * @return the formatted date string
	 */
	public static String formatDate(Date date){
        LocalDate localDate = toLocalDateTime(date).toLocalDate();
        return localDate.format(DATE_FORMATTER);
	}
	
	/**
	 * Converts a {@link Date} to a {@link LocalDateTime}.
	 *
	 * @param date the {@link Date} to convert
	 * @return the corresponding {@link LocalDateTime}
	 */
	public static LocalDateTime toLocalDateTime(Date date) {
	    return date.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDateTime();
	}
	
	/**
	 * Converts a {@link LocalDate} to a {@link Date}.
	 *
	 * @param localDate the {@link LocalDate} to convert
	 * @return the corresponding {@link Date}
	 */
	public static Date toDate(LocalDate localDate) {
	    return Date.from(localDate.atStartOfDay()
	      .atZone(ZoneId.systemDefault())
	      .toInstant());
	}

	/**
	 * Calculates the number of full days between two {@link LocalDateTime} values.
	 *
	 * @param from the start datetime
	 * @param to the end datetime
	 * @return the number of days between the two dates
	 */
	public static long durationBetween(LocalDateTime from, LocalDateTime to) {
		 long daysBetween = Duration.between(from, to).toDays();
		 return daysBetween;
	}
	
	/**
	 * Calculates the number of full days between two {@link LocalDateTime} values.
	 *
	 * @param from the start date
	 * @param to the end date
	 * @return the number of days between the two dates
	 */
	public static long durationBetween(Date from, Date to) {
		return durationBetween(toLocalDateTime(from), toLocalDateTime(to));
	}
	
	/**
	 * Konvertiert ein java.sql.Date in ein java.util.Date
	 * @param sqlDate
	 * @return utilDate
	 */
	public static Date fromSqlDate(java.sql.Date sqlDate) {
		return new Date(sqlDate.getTime());
	}
}



