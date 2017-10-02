package com.demo.catalog.events;

public class PriceUpdatedEvent {

	private String id;
	private double price;
	
	public PriceUpdatedEvent(){}
	
	public PriceUpdatedEvent(String id, double price) {
		this.id=id;
		this.price=price;
	}

	public double getPrice() {
		return price;
	}

	public String getId() {
		return id;
	}



}
