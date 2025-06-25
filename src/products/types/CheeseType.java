package products.types;

import products.Specification;

/**
 * Spezifikation, Typ des Produkts "Käse". 
 * Gekennzeichnet durch Namen, Grundpreis und außerdem durch die Anzahl von Tagen bis zum Verfall.
 */
public enum CheeseType implements Specification {
	gouda("Gouda", 90, 3.99), gorgonzola("Gorgonzola", 80, 4.99), parmesan("Parmesan", 80, 3.98), limburger("Limburger", 90, 3.89), quark("Quark", 50, 1.50);

	private String name;

	/**
	 * Tage bis zum Verfall.
	 */
	private int daysUntilExpiry;

	/**
	 * Grundpreis, Sockelbetrag des Verkaufspreises
	 */
	private double basePrice;

	private CheeseType(String name, int daysUntilExpiry, double basePrice) {
		this.name = name;
		this.daysUntilExpiry = daysUntilExpiry;
		this.basePrice = basePrice;
	}

	@Override
	public String getName() {
		return name;
	}

	public int getDaysUntilExpiry() {
		return daysUntilExpiry;
	}

	@Override
	public double getBasePrice() {
		return basePrice;
	}
}