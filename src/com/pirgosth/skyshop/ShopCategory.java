package com.pirgosth.skyshop;

import java.util.ArrayList;

import org.bukkit.Material;

public class ShopCategory {
	private String name;
	private Material material;
	private int index;
	private ArrayList<String> description;
	private ArrayList<ShopItem> items;
	
	public ShopCategory(String name, int x, int y, Material material, ArrayList<String> description) throws Exception{
		this.name = name;
		this.material = material;
		try {
			this.index = Utility.getIndex(x, y);
		}
		catch(Exception e) {
			throw e;
		}
		this.description = description != null ? description : new ArrayList<String>();
		this.items = new ArrayList<ShopItem>();
	}
	
	public ShopCategory(String name, int x, int y, String material, ArrayList<String> description) throws Exception{
		this.name = name;
		try {
			this.material = Utility.getMaterial(material);
			this.index = Utility.getIndex(x, y);
		}
		catch(Exception e) {
			throw e;
		}
		this.description = description != null ? description : new ArrayList<String>();
		this.items = new ArrayList<ShopItem>();
	}
	
	public void addItem(ShopItem item) {
		items.add(item);
	}
	
	public String name() {
		return name;
	}
	
	public Material material() {
		return material;
	}
	
	public int index() {
		return index;
	}
	
	public ArrayList<String> description(){
		return description;
	}
	
	public ArrayList<ShopItem> items(){
		return items;
	}
}
