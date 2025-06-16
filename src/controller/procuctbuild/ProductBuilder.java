package controller.procuctbuild;

import java.util.Date;

import products.Clock;
import products.Product;

public interface ProductBuilder {
    String getSupportedType(); 
    Product build(String name, int quality, Date delivered, Clock clock);
}