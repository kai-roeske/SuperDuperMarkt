package products;

/**
 * Art des Produkts. Käse, Wein oder Schokolade
 */
public enum ProductType {

	cheese("Käse"), wine("Wein"), chocolate("Schokolade");

	private String name;

	private ProductType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
