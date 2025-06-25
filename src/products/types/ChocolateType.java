package products.types;

import products.Specification;

/**
 * Spezifikation, Typ des Produkts "Wein". Gekennzeichnet durch Namen und Grundpreis.
 */
public enum ChocolateType implements Specification {
	milka_marzipan("Milka Marzipan", 2.99);

	private String name;

	/**
	 * Grundpreis, Sockelbetrag des Verkaufspreises
	 */
	private double basePrice;

	private ChocolateType(String name, double basePrice) {
		this.name = name;
		this.basePrice = basePrice;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public double getBasePrice() {
		return basePrice;
	}
}