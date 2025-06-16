package products;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import controller.Constants.CheeseType;
import controller.Constants.ProductType;
import controller.DateTimeUtils;

public class Cheese extends Product {

	private Clock clock;

	public Cheese(CheeseType specification, int initialQuality, Date delivered, Clock clock) {
		super(ProductType.cheese, specification, initialQuality, delivered, specification.getDaysUntilExpiry());
		this.clock = clock;
		minimumQuality = 30;
	}

	/**
	 * Calculates quality, 
	 */
	@Override
	public int getQuality(Date current) {
		long stored = DateTimeUtils.durationBetween(delivered, current);
		int quality =  (initialQuality -  (int)stored);
		quality = Math.max(quality, 0);
		return quality;
	}

	/**
	 * Der Tagespreis wird durch Qualität bestimmt: Grundpreis + 0,10€ * Qualität
	 */
	@Override
	public double getPrice() {
		int quality = getQuality(clock.getCurrentDate());
		BigDecimal byQuality = new BigDecimal(quality).divide(new BigDecimal("10"), 2, RoundingMode.HALF_UP);
		BigDecimal base = BigDecimal.valueOf(specification.getBasePrice());
		BigDecimal sum = base.add(byQuality);
		//System.out.println("(   basePrice "+ specification.getBasePrice() + ", quality " + quality +", byQuality " + byQuality + ", Price " + sum + "  )");
		return sum.doubleValue();
	}
}
