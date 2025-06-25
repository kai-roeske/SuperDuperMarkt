package controller.procuctbuild;

import java.util.Date;

import products.Clock;
import products.Product;
import products.Specification;

/**
 * Kennt seinen "Supported product type / getSupportedType" (z.B. "Käse") als String und weiß daher, für welches Produkt er 
 * zuständig ist und kann dieses Produkt bauen.
 * Weiß auch, wie er aus einem Namens-String eine {@link Specification} macht, nutzt das in {@link #build(String, int, Date, Clock)}, 
 * um ein Produkt zu erzeugen.
 */
public interface ProductBuilder {
    String getSupportedType(); 
    Product build(String name, int quality, Date delivered, Clock clock);
}