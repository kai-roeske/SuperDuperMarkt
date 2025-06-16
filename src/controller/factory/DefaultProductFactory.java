package controller.factory;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import controller.Constants.CheeseType;
import controller.Constants.ChocolateType;
import controller.Constants.WineType;
import controller.DateTimeUtils;
import products.Cheese;
import products.Chocolate;
import products.Clock;
import products.Product;
import products.Wine;

public class DefaultProductFactory implements ProductFactory {
	
    private final Clock clock;

	public DefaultProductFactory(Clock clock) {
        this.clock = clock;
    }

	@Override
	public List<Product> initProducts() throws ParseException {
		return List.of(new Cheese(CheeseType.gouda, 100, new Date(), clock),
				new Cheese(CheeseType.quark, 100, DateTimeUtils.toDate("20.04.2025"), clock),
				new Cheese(CheeseType.limburger, 70, DateTimeUtils.toDate("09.06.2025"), clock),
				new Cheese(CheeseType.parmesan, 80, DateTimeUtils.toDate("09.06.2025"), clock),
				new Cheese(CheeseType.gorgonzola, 85, DateTimeUtils.toDate("09.06.2025"), clock),
				new Wine(WineType.beaujolais, 80, DateTimeUtils.toDate("20.04.2025")),
				new Wine(WineType.burgunder, 100, DateTimeUtils.toDate("01.05.2025")),
				new Wine(WineType.dornfelder, 60, DateTimeUtils.toDate("30.04.2025")),
				new Wine(WineType.cabernet, 55, DateTimeUtils.toDate("01.05.2025")),
				new Wine(WineType.tuetenwein, 20, DateTimeUtils.toDate("20.04.2025")),
				new Chocolate(ChocolateType.milka_marzipan, 100, DateTimeUtils.toDate("16.08.2024"), clock)
				);
	}
}
