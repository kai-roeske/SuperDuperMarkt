package controller.factory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controller.DateTimeUtils;
import controller.procuctbuild.ProductBuilder;
import controller.procuctbuild.ProductBuilderRegistry;
import products.Clock;
import products.Product;

public class CsvProductFactory implements ProductFactory {

    private final Clock clock;
    private final String csvFile;
    private final ProductBuilderRegistry registry;

    public CsvProductFactory(Clock clock, String csvFile, ProductBuilderRegistry registry) {
        this.clock = clock;
        this.csvFile = csvFile;
        this.registry = registry;
    }

    @Override
    public List<Product> initProducts() {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith("type")) {
					continue;
				} // skip header
                String[] parts = line.split(",");
                String type = parts[0].trim();
                String name = parts[1].trim();
                int quality = Integer.parseInt(parts[2].trim());
                Date delivered = DateTimeUtils.toDate(parts[3].trim());

                ProductBuilder builder = registry.getBuilder(type);
                Product product = builder.build(name, quality, delivered, clock);
                products.add(product);
            }
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim CSV-Import", e);
        }
        return products;
    }
}

