package products;

import java.math.BigDecimal;
import java.util.Date;

import controller.Constants.ProductType;
import controller.Constants.Specification;
import controller.DateTimeUtils;

/**
 * Schokolade hat eine Haltbarkeit von einem 365 Tagen. Ihre Qualität halbiert sich nach 330 Tagen. Ihr Preis reduziert sich nach 300 Tagen um 1 Euro. 
 */
public class Chocolate extends Product {
	
	private Clock clock;

	public Chocolate(Specification specification, int initialQuality, Date delivered, Clock clock) {
		super(ProductType.chocolate , specification, initialQuality, delivered, 365);
		this.clock = clock;
	}

	@Override
	public int getQuality(Date current) {
		long stored = DateTimeUtils.durationBetween(delivered, clock.getCurrentDate());
		int quality = stored < 330 ? initialQuality : initialQuality / 2;
		return quality;
	}

	@Override
	public double getPrice() {
		long stored = DateTimeUtils.durationBetween(delivered, clock.getCurrentDate());
		double price = stored < 300 ? specification.getBasePrice() : new BigDecimal( String.valueOf( specification.getBasePrice()) ).doubleValue() - 1;
		return price;
	}

}
