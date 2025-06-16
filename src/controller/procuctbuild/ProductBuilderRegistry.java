package controller.procuctbuild;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public class ProductBuilderRegistry {

    private final Map<String, ProductBuilder> builders = new HashMap<>();

    public ProductBuilderRegistry(Iterable<ProductBuilder> builderList) {
        for (ProductBuilder builder : builderList) {
            builders.put(builder.getSupportedType().toLowerCase(), builder);
        }
    }

    public static ProductBuilderRegistry loadFromService() {
        ServiceLoader<ProductBuilder> loader = ServiceLoader.load(ProductBuilder.class);
        return new ProductBuilderRegistry(loader);
    }

    public ProductBuilder getBuilder(String type) {
        ProductBuilder builder = builders.get(type.toLowerCase());
        if (builder == null) {
            throw new IllegalArgumentException("Kein Builder für Produkttyp: " + type);
        }
        return builder;
    }
}