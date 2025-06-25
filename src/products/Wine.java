package products;

import java.util.Date;

import controller.DateTimeUtils;
import products.types.WineType;

/**
 * Merkmale: Wein verfällt nicht, und auch der Preis bleibt immer konstant.
 * Wein gewinnt ab dem Stichtag alle 10 Tage +1 Qualität hinzu, bis die Qualität 50 erreicht ist.
 */
public class Wine extends Product {
	
	/**
	 * @param specification der Typ des Weins, bestimmt Namen und Preis
	 * @param initialQuality die Qualitätslevel zum Zeitpunkt der Lieferung
	 * @param delivered das Lieferdatum
	 */
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

	/**
	 * Konstanter Preis.
	 */
	@Override
	public double getPrice() {
		return specification.getBasePrice();
	}
}
