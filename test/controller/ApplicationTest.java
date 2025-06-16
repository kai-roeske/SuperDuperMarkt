package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.text.ParseException;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import controller.Constants.CheeseType;
import controller.Constants.WineType;
import products.Cheese;
import products.Wine;
import testutils.FakeClock;

public class ApplicationTest {
	
	@Test
	@DisplayName("Käse: Qualität sinkt täglich um 1.")
	void testQualityReducesOnePerDay(){
		FakeClock clock = new FakeClock("08.06.2025");
		Date currentDate = clock.getCurrentDate();
		int initialQuality = 100;
		
		Cheese cheese = new Cheese(CheeseType.gouda, initialQuality, currentDate, clock);
		assertEquals(initialQuality, cheese.getQuality(clock.getCurrentDate()));
		
		int advanceTwoDays = 2;
		clock.advanceDays(advanceTwoDays);
		assertEquals(initialQuality - advanceTwoDays, cheese.getQuality(clock.getCurrentDate())); 

		int advanceFiftyDays = 50;
		clock.advanceDays(advanceFiftyDays);
		
		assertEquals(initialQuality - (advanceTwoDays + advanceFiftyDays), cheese.getQuality(clock.getCurrentDate())); 
	}
	
	@Test
	@DisplayName("Käse: Qualität nicht negativ.")
	void testCannotGoBelowZero(){
		 FakeClock clock = new FakeClock("01.06.2025");
		  Cheese cheese = new Cheese(CheeseType.quark, 5, clock.getCurrentDate(), clock);
		  clock.advanceDays(10);
		  assertEquals(0, cheese.getQuality(clock.getCurrentDate()));
	}
	
    @Test
    @DisplayName("Käse: aktuelle Qualität / 10  erhöht Basispreis.")
    void testCheesePriceGrowsWithQuality() {
        FakeClock clock = new FakeClock("10.06.2025"); // Käse (Limburger - Grundpreis 3.89) mit Qualität 30 
        Cheese cheese = new Cheese(CheeseType.limburger, 30, clock.getCurrentDate(), clock);  
        double price = cheese.getPrice(); // Preis = basePrice + 0.10 * 30  = 3.89 + 0.3 = 4.19 // Qualität/10 addiert sich zum  Grundpreis
  
        // Qualität ist unverändert, es ist kein Tag vergangen. 
        // Preis erreihnet sich aus Basispreis (CheeseType.limburger.getBasePrice()) plus 1/10 * aktuelle Qualität
        assertEquals(CheeseType.limburger.getBasePrice() + (cheese.getQuality(clock.getCurrentDate()) * 0.1 ), price, 0.01);  // Abweichungen wegen "double" mögl.
    }
	
	@ParameterizedTest
	@CsvSource({
	  "10.06.2025,0,100",
	  "10.06.2025,5,95",
	  "10.06.2025,6,94",
	  "10.06.2025,100,0"
	})
	@DisplayName("Käse: Je mehr Tage vergehen, desto schlechter die Qualität. Dia Qualität nimmt täglich um 1 ab.")
	void testCheeseQualityDegression(String dateStr, int days, int expectedQuality) {
	    FakeClock clock = new FakeClock(dateStr);
	    Cheese cheese = new Cheese(CheeseType.quark, 100, clock.getCurrentDate(), clock);
	    clock.advanceDays(days);
	    assertEquals(expectedQuality, cheese.getQuality(clock.getCurrentDate()));
	}
	
	@Test
	 @DisplayName("Wein erhöht seine Qualität alle 10 Tage um 1. Maximale Qualität ist 50.")
	void testWineGainsOneQualityPerTenDaysUntilMaxFifty() throws ParseException {
		FakeClock clock = new FakeClock("09.06.2025");
		Wine wine = new Wine(WineType.beaujolais, 45, clock.getCurrentDate());
		assertEquals(45, wine.getQuality(clock.getCurrentDate())); // Am Liefertag initiale Qualität
		clock.advanceDays(10);
		assertEquals(46, wine.getQuality(clock.getCurrentDate())); // 10 Tage sind vergangen, die Qualität ist um 1 gestiegen
		clock.advanceDays(10);
		assertEquals(47, wine.getQuality(clock.getCurrentDate())); // weitere 10 Tage sind vergangen, die Qualität ist um 1 gestiegen
		clock.advanceDays(50);
        assertEquals(50, wine.getQuality(clock.getCurrentDate()));  // weitere 50 Tage sind vergangen, die Qualität hat ihren Maximalwert von 50 erreicht
		
	}
    
    @Test
    @DisplayName("Wein hat kein Ablaufdatum – getExpiryDate() ist immer null.")
    void testWineHasNoExpiryDate() throws ParseException {
        Wine wine = new Wine(WineType.beaujolais, 50, DateTimeUtils.toDate("01.06.2025"));
        assertNull(wine.getExpiryDate(), "Wein sollte kein Verfallsdatum haben");
    }
    
    @Test
    @DisplayName("Clock der Testumgebung funktioniert")
    void testFakeClockWorking() throws ParseException {
    	FakeClock clock = new FakeClock("09.06.2025");
    	clock.advanceDays(10);
    	Date initialDate = clock.getInitialDate();
    	Date currentDate = clock.getCurrentDate();
    	long passed = DateTimeUtils.durationBetween(initialDate, currentDate);
    	assertEquals(10, passed);
    	assertEquals(currentDate, DateTimeUtils.toDate("19.06.2025"));
    }
}





