package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import controller.procuctbuild.WineBuilder;
import products.Cheese;
import products.Wine;
import products.types.CheeseType;
import products.types.WineType;
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
		clock.advanceDays(advanceTwoDays); // Wenn zwei Tage vergehen, sinkt die Qualität um zwei
		assertEquals(initialQuality - advanceTwoDays, cheese.getQuality(clock.getCurrentDate())); 

		int advanceFiftyDays = 50; 
		clock.advanceDays(advanceFiftyDays);
		 // Wenn weitere 50 Tage vergehen, sinkt die Qualität um weitere 50, insgesamt um 52
		assertEquals(initialQuality - (advanceTwoDays + advanceFiftyDays), cheese.getQuality(clock.getCurrentDate())); 
	}
	
	@Test
	@DisplayName("Käse: Qualität nicht negativ.")
	void testCannotGoBelowZero(){
		 FakeClock clock = new FakeClock("01.06.2025");
		  Cheese cheese = new Cheese(CheeseType.quark, 5, clock.getCurrentDate(), clock);
		  clock.advanceDays(10);	
		  assertEquals(0, cheese.getQuality(clock.getCurrentDate())); // Die Qualität würde um 10 gesunken sein, aber bei 0 ist Schluss
	}
	
    @Test
    @DisplayName("Käse: aktuelle Qualität / 10  = Aufschlag auf Basispreis.")
    void testCheesePriceGrowsWithQuality() {
        FakeClock clock = new FakeClock("10.06.2025"); // Käse (Limburger, Grundpreis 3.89, Qualität 30 
        Cheese cheese = new Cheese(CheeseType.limburger, 30, clock.getCurrentDate(), clock);  
        double price = cheese.getPrice(); // Preis = basePrice + 0.1 * 30  = 3.89 + 0.3 = 4.19 <-- Qualität/10 addiert sich zum  Grundpreis
        // Qualität ist unverändert, es ist kein Tag vergangen. 
        // Preis = Basispreis (CheeseType.limburger.getBasePrice()) plus 1/10 * aktuelle Qualität
        assertEquals(CheeseType.limburger.getBasePrice() + (cheese.getQuality(clock.getCurrentDate()) * 0.1 ), price, 0.01);  // Abweichungen wegen "double" mögl.
    }
	
	@ParameterizedTest
	@CsvSource({
	  "10.06.2025,0,100",
	  "10.06.2025,5,95",
	  "10.06.2025,6,94",
	  "10.06.2025,100,0"
	})
	@DisplayName("Käse: Je mehr Tage vergehen, desto schlechter die Qualität. Die Qualität nimmt täglich um 1 ab.")
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
    
    @Test
    @DisplayName("Reale Clock funktioniert")
    void testDiaryClockWorking() throws ParseException {
    	DiaryClock clock = new DiaryClock();
    	Date initialDate = clock.getInitialDate();
    	clock.passDay();
    	Date currentDate = clock.getCurrentDate();
    	long passed = DateTimeUtils.durationBetween(initialDate, currentDate);
    	assertEquals(1, passed);
    	
		Calendar c = Calendar.getInstance();
		c.setTime(initialDate);
		c.add(Calendar.DATE, 1);
		Date initialPlusOneDay = c.getTime();
    	assertEquals(currentDate, initialPlusOneDay);
    }

	@Test
	@DisplayName("Wein - Preis bleibt konstant")
	void testWinePriceNoProgression() throws ParseException {
		FakeClock clock = new FakeClock("26.06.2025");
		Wine wine = (Wine) new WineBuilder().build(WineType.cabernet.name(), 60, DateTimeUtils.toDate("20.06.2025"),
				clock);
		double priceInitial = wine.getPrice();
		clock.advanceDays(240);
		double priceYearLater = wine.getPrice();
		assertEquals(priceInitial, priceYearLater, 0.01); // Abweichungen wegen "double" mögl.

	}
}



