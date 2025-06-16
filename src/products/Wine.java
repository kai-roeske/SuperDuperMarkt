package products;

import java.util.Date;

import controller.Constants.ProductType;
import controller.Constants.WineType;
import controller.DateTimeUtils;

public class Wine extends Product {
	
	public Wine(WineType specification, int initialQuality, Date delivered) {
		super(ProductType.wine, specification, initialQuality, delivered, null);
	}

	/**
	 * Wein verliert nicht an Qualität, sondern gewinnt ab dem Stichtag alle 10 Tage +1 Qualität hinzu, bis die Qualität 50 erreicht ist.
	 */
	@Override
	public int getQuality(Date current) {
		long stored = DateTimeUtils.durationBetween(delivered, current);
		double qualityGain = stored / 10;
		int qualityGainRound = (int) Math.round(qualityGain); 
		int quality = Math.min(50, initialQuality + qualityGainRound);
		return quality;
	}

	@Override
	public double getPrice() {
		return specification.getBasePrice();
	}
}
