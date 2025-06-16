package controller.procuctbuild;

import java.util.Date;

import controller.Constants.ChocolateType;
import products.Chocolate;
import products.Clock;
import products.Product;

public class ChocolateBuilder implements ProductBuilder {

    @Override
    public String getSupportedType() {
        return "chocolate";
    }

    @Override
    public Product build(String name, int quality, Date delivered, Clock clock) {
        ChocolateType type = ChocolateType.valueOf(name.toLowerCase());
        return new Chocolate(type, quality, delivered, clock);
    }
}