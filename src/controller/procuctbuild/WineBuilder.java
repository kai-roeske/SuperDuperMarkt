package controller.procuctbuild;

import java.util.Date;

import products.Clock;
import products.Product;
import products.ProductType;
import products.Wine;
import products.types.WineType;

/**
 * See {@link ProductBuilder}
 */
public class WineBuilder implements ProductBuilder {

    @Override
    public String getSupportedType() {
    	return ProductType.wine.name();
    }

    @Override
    public Product build(String name, int quality, Date delivered, Clock clock) {
        WineType type = WineType.valueOf(name.toLowerCase());
        return new Wine(type, quality, delivered);
    }
}