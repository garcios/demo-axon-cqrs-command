package com.demo.catalog.aggregates;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.demo.catalog.events.ProductAddedEvent;
import com.demo.catalog.events.PriceUpdatedEvent;

@Aggregate
public class Product {

    private static final Logger LOG = LoggerFactory.getLogger(Product.class);

    @AggregateIdentifier
    private String id;
    private String name;
    private double price;

    public Product() {
    }


    /**
     * 
     * @param id
     * @param name
     * @param price
     */
    public Product(String id, String name, double price) {
        Assert.hasLength(id, "ID should NOT be empty or null.");
        Assert.hasLength(name, "Name should NOT be empty or null.");
        apply(new ProductAddedEvent(id, name, price));
        LOG.debug("Done executing {} constructor: {}, {}, {}", "Product()", id, name, price);

	}

    /**
     * 
     * @param price
     */
    public void update(double price){
    	LOG.debug("=====update: Price before apply: {}",  this.price);
    	
    	apply(new PriceUpdatedEvent(this.id, price));
    	
    	 LOG.debug("=====Done executing update: {}, {}", this.id, price);
    	
    }
    
    
	@EventSourcingHandler
    public void on(ProductAddedEvent evnt) {
        LOG.debug("****EventSourcingHandler: Handling {} event: {}, {}, {}", evnt.getClass().getSimpleName(), evnt.getId(), evnt.getName(), evnt.getPrice());
        
        this.id = evnt.getId();
        this.name = evnt.getName();
        this.price = evnt.getPrice();
    
    }

	@EventSourcingHandler
    public void on(PriceUpdatedEvent evnt) {
        LOG.debug("****EventSourcingHandler: Handling {} event: {}, {}, {}", evnt.getClass().getSimpleName(), evnt.getId(),  evnt.getPrice());

        this.price = evnt.getPrice();
      
    }


	
	
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

	public double getPrice() {
		return price;
	}

   
    
}
