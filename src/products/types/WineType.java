package products.types;

import products.Specification;

/**
 * Spezifikation, Typ des Produkts "Wein". Gekennzeichnet durch Namen und Grundpreis.
 */
public enum WineType implements Specification {
	burgunder("Burgunder", 9.99), beaujolais("Beaujolais", 8.99), dornfelder("Dornfelder", 6.98), cabernet("Cabernet", 5.98), tuetenwein("Aus der Tüte", 1.99);

	private String name;

	/**
	 * Grundpreis, Sockelbetrag des Verkaufspreises
	 */
	private double basePrice;

	private WineType(String name, double basePrice) {
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
