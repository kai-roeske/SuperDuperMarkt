package controller;
import java.text.ParseException;
import java.util.List;

import controller.factory.CsvProductFactory;
import controller.factory.DefaultProductFactory;
import controller.factory.ProductFactory;
import controller.factory.SqlProductFactory;
import controller.print.ConsoleWriter;
import controller.print.OutputWriter;
import controller.print.ProductPrinter;
import controller.procuctbuild.ProductBuilderRegistry;
import products.Product;

public class Application {

	private final int SIMULATION_DAYS = 400;

	public static void main(String[] args) {
		try {
			new Application().runSimulation();
		} catch (ParseException e) {
			System.err.println("Simulation failed:"); 
			e.printStackTrace();
		}
	}

	private void runSimulation() throws ParseException {
		DiaryClock clock = new DiaryClock();
		ProductBuilderRegistry registry = ProductBuilderRegistry.loadFromService();
		@SuppressWarnings("unused")
		ProductFactory factoryDefault = new DefaultProductFactory(clock);
		@SuppressWarnings("unused")
		ProductFactory factoryCsv = new CsvProductFactory(clock, "resources/produkte.csv", registry); 
		String jdbcUrl = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:import.sql'";
		ProductFactory factorySql = new SqlProductFactory(clock, registry, jdbcUrl, "sa", "");
		ProductFactory productFactory = factorySql;
		List<Product> products =  productFactory.initProducts();   
		OutputWriter writer = new ConsoleWriter();
		ProductPrinter printer = new ProductPrinter(clock, writer);
		new ProductSimulationService().simulateProductLifecycle(products, SIMULATION_DAYS, printer, clock);
	}
}
