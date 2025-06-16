package controller.factory;

import java.text.ParseException;
import java.util.List;

import products.Product;

public interface ProductFactory {
	List<Product> initProducts() throws ParseException;
}
