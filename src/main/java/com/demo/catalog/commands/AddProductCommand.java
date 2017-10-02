package com.demo.catalog.commands;

public class AddProductCommand {

    private final String id;
    private final String name;
    private final double price;

    public AddProductCommand(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price=price;
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
	
    @Override
    public String toString() {
        return "AddProductToCatalogCommand{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }


}
