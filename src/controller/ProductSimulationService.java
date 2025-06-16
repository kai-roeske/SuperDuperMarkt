package controller;

import java.util.Date;
import java.util.List;

import controller.print.ProductPrinter;
import products.Clock;
import products.Product;

public class ProductSimulationService {

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
