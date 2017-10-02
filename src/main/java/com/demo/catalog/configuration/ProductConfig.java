package com.demo.catalog.configuration;

import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.spring.eventsourcing.SpringPrototypeAggregateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.demo.catalog.aggregates.Product;

@Configuration
public class ProductConfig {

	
	@Autowired
	private EventStore eventStore;
	
	@Bean
	@Scope("prototype")
	public Product product(){
		return new Product();
	}
	
	
	@Bean
	public AggregateFactory<Product> productAggregateFactory(){
		
		SpringPrototypeAggregateFactory<Product> aggregateFactory = new SpringPrototypeAggregateFactory<>();
		aggregateFactory.setPrototypeBeanName("product");
		
		return aggregateFactory;
	}
	
	
	@Bean
	public Repository<Product> productRepository(){
		
		EventSourcingRepository<Product> repository = new EventSourcingRepository<>(
				productAggregateFactory(),
				eventStore
				);
		
		return repository;
	}
	
}
