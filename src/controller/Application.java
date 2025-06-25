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

	/**
	 * Anzahl der Tage, deren Ablauf protokolliert wird
	 */
	private final int SIMULATION_DAYS = 400;

	public static void main(String[] args) {
		try {
			new Application().runSimulation();
		} catch (ParseException e) {
			System.err.println("Simulation failed:"); 
			e.printStackTrace();
		}
	}
	/**
	 * Erzeugung der Beteiligten Komponenten und deren Verwendung. Erst werden alle Beteiligten Module erzeugt und dann 
	 * in die Verwendung 'injiziert' - es ist also eine Art Dependency Injection (DI):
	 * Erzeugung und Verwendung sind getrennt, Objekte wie Clock, OutputWriter, etc. werden übergeben. So kann auf verschiedene
	 * Szenarien eingegangen werden. Die Aufspaltung erleichtert/ermöglicht so z.B. auch ein Testszenario mit JUnit.
	 * @throws ParseException
	 */
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
