package controller.procuctbuild;

import java.util.Date;

import products.Chocolate;
import products.Clock;
import products.Product;
import products.ProductType;
import products.types.ChocolateType;

/**
 * See {@link ProductBuilder}
 */
public class ChocolateBuilder implements ProductBuilder {

    @Override
    public String getSupportedType() {
        return ProductType.chocolate.name();
    }

    @Override
    public Product build(String name, int quality, Date delivered, Clock clock) {
        ChocolateType type = ChocolateType.valueOf(name.toLowerCase());
        return new Chocolate(type, quality, delivered, clock);
    }
}