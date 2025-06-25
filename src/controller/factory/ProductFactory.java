package controller.factory;

import java.text.ParseException;
import java.util.List;

import products.Product;

/**
 * Factory, gekapselte Modell-Erzeugung, getrennt von der Geschäftslogik. Gemeinsames Interface aller "ProductFactories".
 */
public interface ProductFactory {
	List<Product> initProducts() throws ParseException;
}
