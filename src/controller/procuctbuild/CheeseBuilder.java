package controller.procuctbuild;

import java.util.Date;

import products.Cheese;
import products.Clock;
import products.Product;
import products.ProductType;
import products.types.CheeseType;

/**
 * See {@link ProductBuilder}
 */
public class CheeseBuilder implements ProductBuilder {

    @Override
    public String getSupportedType() {
        return ProductType.cheese.name();
    }

    @Override
    public Product build(String name, int quality, Date delivered, Clock clock) {
        CheeseType type = CheeseType.valueOf(name.toLowerCase());
        return new Cheese(type, quality, delivered, clock);
    }
}