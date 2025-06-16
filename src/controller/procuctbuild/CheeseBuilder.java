package controller.procuctbuild;

import java.util.Date;

import controller.Constants.CheeseType;
import products.Cheese;
import products.Clock;
import products.Product;

public class CheeseBuilder implements ProductBuilder {

    @Override
    public String getSupportedType() {
        return "cheese";
    }

    @Override
    public Product build(String name, int quality, Date delivered, Clock clock) {
        CheeseType type = CheeseType.valueOf(name.toLowerCase());
        return new Cheese(type, quality, delivered, clock);
    }
}