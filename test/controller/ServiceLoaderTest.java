package controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import controller.procuctbuild.ProductBuilder;
import controller.procuctbuild.ProductBuilderRegistry;

public class ServiceLoaderTest {
	@Test
	@DisplayName("ServiceLoader lädt ProductBuilder")
	void serviceLoaderLoadsCheeseBuilder() {
	    ProductBuilderRegistry registry = ProductBuilderRegistry.loadFromService();
	    ProductBuilder cheeseBuilder = registry.getBuilder("cheese");
	    assertNotNull(cheeseBuilder);
	}
}
