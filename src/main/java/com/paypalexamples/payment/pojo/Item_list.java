package com.paypalexamples.payment.pojo;

import java.util.List;

public class Item_list {
	private Shipping_Address shipping_address;
	private List<Items> items;
	public Shipping_Address getShipping_address() {
		return shipping_address;
	}
	public void setShipping_address(Shipping_Address shipping_address) {
		this.shipping_address = shipping_address;
	}
	public List<Items> getItems() {
		return items;
	}
	public void setItems(List<Items> items) {
		this.items = items;
	}
	
	
}
