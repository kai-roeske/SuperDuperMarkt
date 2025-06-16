package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import controller.factory.CsvProductFactory;
import controller.procuctbuild.CheeseBuilder;
import controller.procuctbuild.ChocolateBuilder;
import controller.procuctbuild.ProductBuilder;
import controller.procuctbuild.ProductBuilderRegistry;
import controller.procuctbuild.WineBuilder;
import products.Cheese;
import products.Chocolate;
import products.Product;
import products.Wine;
import testutils.FakeClock;

public class CsvProductFactoryTest {

    private Path tempCsvFile;

    @BeforeEach
    void setupCsvFile() throws Exception {
        tempCsvFile = Files.createTempFile("produkte", ".csv");
        try (PrintWriter writer = new PrintWriter(new FileWriter(tempCsvFile.toFile()))) {
            writer.println("type,name,quality,delivered");
            writer.println("cheese,gouda,90,10.06.2025");
            writer.println("wine,beaujolais,50,01.06.2025");
            writer.println("chocolate,milka_marzipan,50,01.06.2025");
        }
    }

    @Test
    @DisplayName("Csv-Daten werden korrekt eingelesen.")
    void testCsvProductFactoryLoadsProducts() throws Exception {
        FakeClock clock = new FakeClock("10.06.2025");
		List<ProductBuilder> builders = List.of(new CheeseBuilder(), new WineBuilder(), new ChocolateBuilder());
		ProductBuilderRegistry registry = new ProductBuilderRegistry(builders);
        CsvProductFactory factory = new CsvProductFactory(clock, tempCsvFile.toString(), registry);

        List<Product> products = factory.initProducts();

        assertEquals(3, products.size());

        assertTrue(products.get(0) instanceof Cheese);
        assertTrue(products.get(1) instanceof Wine);
        assertTrue(products.get(2) instanceof Chocolate);

        assertEquals("Gouda", products.get(0).getName());
        assertEquals(90, products.get(0).getQuality(clock.getCurrentDate()));
        assertEquals(50, products.get(1).getQuality(clock.getCurrentDate()));
        assertEquals(50, products.get(2).getQuality(clock.getCurrentDate()));
    }
}
