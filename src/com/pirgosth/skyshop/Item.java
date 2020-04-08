package com.pirgosth.skyshop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

public class Item {
	private Material material;
	private int index;
	private String name;
	private ArrayList<String> description;
	
	public Item(String name, int x, int y, Material material, List<String> description) throws Exception{
		this.name = name;
		try {
			this.index = Utility.getIndex(x, y);
			this.material = material;
		}
		catch(Exception e) {
			throw e;
		}
		this.description = new ArrayList<String>(description);
	}
	
	public Item(String name, int x, int y, String material, List<String> description) throws Exception{
		this.name = name;
		try {
			this.index = Utility.getIndex(x, y);
			this.material = Utility.getMaterial(material);
		}
		catch(Exception e) {
			throw e;
		}
		this.description = new ArrayList<String>(description);
	}
	
	public Material material() {
		return material;
	}
	
	public String name() {
		return name;
	}
	
	public ArrayList<String> description(){
		return description;
	}
	
	public int index() {
		return index;
	}
	
	public void onClick() {
		
	}
}
