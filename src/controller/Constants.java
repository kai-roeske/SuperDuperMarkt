package controller;

public final class Constants {
	public static String productDisplayStartMsg = "Das Angebot des SuperDuperMarkts: ";
	public static String quality = "Qualität";
	public static String price = "Preis";
	public static String expiryDate = "Verfallsdatum";
	public static String disposeMsg = "Muss ausgeräumt werden:";
	public static String qualityNotAcceptable = "Qualität abgelaufen";
	public static String expired = "Ablaufdatum überschritten";
	public static String deliveredOn = "geliefert am";

	public static enum ProductType {

		cheese("Käse"), wine("Wein"), chocolate("Schokolade");

		private String name;

		private ProductType(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	/**
	 * Spezifikation, Typ des Produkts "Käse". Gekennzeichnet durch Namen, Grundpreis und Anzahl der Tage des Verfalls.
	 */
	public static enum CheeseType implements Specification {
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

	/**
	 * Spezifikation, Typ des Produkts "Wein". Gekennzeichnet durch Namen und Grundpreis.
	 */
	public static enum WineType implements Specification {
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
	
	/**
	 * Spezifikation, Typ des Produkts "Wein". Gekennzeichnet durch Namen und Grundpreis.
	 */
	public static enum ChocolateType implements Specification {
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

	/**
	 * Spezifikation des Produkts, genauere Bestimmung. Gekennzeichnet durch Namen und Grundpreis 
	 */
	public static interface Specification {
		public String getName();
		public double getBasePrice();
	}
}
