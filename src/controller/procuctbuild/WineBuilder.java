package controller.procuctbuild;

import java.util.Date;

import controller.Constants.WineType;
import products.Clock;
import products.Product;
import products.Wine;

public class WineBuilder implements ProductBuilder {

    @Override
    public String getSupportedType() {
        return "wine";
    }

    @Override
    public Product build(String name, int quality, Date delivered, Clock clock) {
        WineType type = WineType.valueOf(name.toLowerCase());
        return new Wine(type, quality, delivered);
    }
}