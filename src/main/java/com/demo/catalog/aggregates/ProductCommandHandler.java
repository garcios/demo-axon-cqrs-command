package com.demo.catalog.aggregates;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Aggregate;
import org.axonframework.commandhandling.model.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.catalog.commands.AddProductCommand;
import com.demo.catalog.commands.UpdatePriceCommand;

@Component
public class ProductCommandHandler {

	@Autowired
	private Repository<Product> productRepository;
	
	@CommandHandler
	public void handleCreateProduct(AddProductCommand cmd) throws Exception{
		
		productRepository.newInstance(()-> new Product( cmd.getId(), cmd.getName(), cmd.getPrice() ) );
		
		
	}
	
	
	@CommandHandler
	public void handleUpdatePrice(UpdatePriceCommand cmd) throws Exception{
		
		Aggregate<Product> product = productRepository.load(cmd.getId());
		
		product.execute(aggregateRoot ->{
			aggregateRoot.update(cmd.getPrice());
		});
		
	}
	
	
}
