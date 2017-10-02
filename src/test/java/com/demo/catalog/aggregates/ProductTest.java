package com.demo.catalog.aggregates;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import com.demo.catalog.aggregates.Product;
import com.demo.catalog.commands.AddProductCommand;
import com.demo.catalog.events.ProductAddedEvent;

import java.util.UUID;

public class ProductTest {

    private FixtureConfiguration fixture;
    private String id;
    private String name;
    private double price;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture(Product.class);
        id = UUID.randomUUID().toString();
        name = "test-"+id;
    }


    @Test
    public void testCommandAddProductToCatalogPasses() {
        fixture.given()
                .when(new AddProductCommand(id, name, price))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new ProductAddedEvent(id, name, price));
    }

    @Test
    public void testCommandAddProductToCatalogFailsWhenIdNull() {
        fixture.given()
                .when(new AddProductCommand(null, name, price))
                .expectException(IllegalArgumentException.class);

        fixture.given()
                .when(new AddProductCommand("", name, price))
                .expectException(IllegalArgumentException.class);
    }

    @Test
    public void testCommandAddProductToCatalogFailsWhenNameNull() {
        fixture.given()
                .when(new AddProductCommand(id, null, price))
                .expectException(IllegalArgumentException.class);

        fixture.given()
                .when(new AddProductCommand(id, "", price))
                .expectException(IllegalArgumentException.class);
    }


}
