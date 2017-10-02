package com.demo.catalog.commands;

public class UpdatePriceCommand {

	private String id;
    private final double price;

    public UpdatePriceCommand(String id, double price) {
    	this.id = id;
        this.price=price;
    }

 
	public double getPrice() {
		return price;
	}



	public String getId() {
		return id;
	}


	@Override
	public String toString() {
		return "UpdatePriceCommand [id=" + id + ", price=" + price + "]";
	}


	
 
	
	

}
