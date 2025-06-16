package controller.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controller.DateTimeUtils;
import controller.procuctbuild.ProductBuilderRegistry;
import products.Clock;
import products.Product;

public class SqlProductFactory implements ProductFactory {

	private final Clock clock;
	private final ProductBuilderRegistry registry;
	private final String jdbcUrl;
	private final String user;
	private final String password;

	public SqlProductFactory(Clock clock, ProductBuilderRegistry registry, String jdbcUrl, String user,
			String password) {
		this.clock = clock;
		this.registry = registry;
		this.jdbcUrl = jdbcUrl;
		this.user = user;
		this.password = password;
	}

	@Override
	public List<Product> initProducts() {
		List<Product> products = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection(jdbcUrl, user, password)) {
			String sql = "SELECT type, name, quality, delivered FROM products";
			try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

				while (rs.next()) {
					String type = rs.getString("type").trim().toLowerCase();
					String name = rs.getString("name").trim();
					int quality = rs.getInt("quality");
					Date delivered = DateTimeUtils.fromSqlDate(rs.getDate("delivered"));

					Product product = registry.getBuilder(type).build(name, quality, delivered, clock);
					products.add(product);
				}
				rs.close();
				stmt.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return products;
	}

}


