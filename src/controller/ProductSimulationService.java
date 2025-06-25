package controller;

import java.util.Date;
import java.util.List;

import controller.print.ProductPrinter;
import products.Clock;
import products.Product;

/**
 * Service zum Simulieren des Ablaufs eines gegebenen Zeitraums, in der Status von Produkten sich ändert.
 * see {@link #simulateProductLifecycle(List, int, ProductPrinter, Clock)}
 */
public class ProductSimulationService {

	/**
	 * Simuliert einen Ablauf eines gegebenen Zeitraums und veranlasst dabei eine Status-Übersicht mit täglichen Statusmeldungen.
	 * Benötigt dazu die Liste der anzuzeigenden Produkte, die Anzahl der zu simulierenden Tage, einen Printer, den er anweist, 
	 * täglich Meldungen auszugeben und einen Kalender.
	 */
	public void simulateProductLifecycle(List<Product> products, int simulationDays, ProductPrinter printer, Clock clock) {
		printer.printInition();
		printer.printBlankLine();
		if (products != null) {
			for (int i = 0; i < simulationDays; i++) {
				Date currentDate = clock.getCurrentDate();
				printer.printDayHeader(currentDate);
				for (Product product : products) {
					printer.printProductStatus(product, currentDate);
				}
				clock.passDay();
				printer.printBlankLine();
			}
		}
	}
}
