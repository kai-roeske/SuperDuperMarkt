package controller.procuctbuild;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * Erzeugt alle {@link ProductBuilder} und speichert sie in der Map und hält sie zugreifbar über
 * {@link #getBuilder(String)}
 */
public class ProductBuilderRegistry {

    private final Map<String, ProductBuilder> builders = new HashMap<>();

    public ProductBuilderRegistry(Iterable<ProductBuilder> builderList) {
        for (ProductBuilder builder : builderList) {
            builders.put(builder.getSupportedType().toLowerCase(), builder);
        }
    }

    /**
     * Lädt alle {@link ProductBuilder} mithilfe von {@link ServiceLoader.load(Class) }
     * ServiceLoader: 'A facility to load implementations of a service.'
     * Ladbare Services werden über META-INF... konfiguriert.
     *
     * @return
     */
    public static ProductBuilderRegistry loadFromService() {
        ServiceLoader<ProductBuilder> loader = ServiceLoader.load(ProductBuilder.class);
        return new ProductBuilderRegistry(loader);
    }

    /**
     * Bietet Zugriff auf die {@link ProductBuilder} 
     * @param type muss builder.getSupportedType() entsprechen - 'Case' ist irrelevant
     * @return ProductBuilder
     */
    public ProductBuilder getBuilder(String type) {
        ProductBuilder builder = builders.get(type.toLowerCase());
        if (builder == null) {
            throw new IllegalArgumentException("Kein Builder für Produkttyp: " + type);
        }
        return builder;
    }
}