package com.pirgosth.skyshop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;

public class Utility {
	public static ArrayList<String> getWorldNames(){
		ArrayList<String> worlds = new ArrayList<String>();
		for(World world: Bukkit.getWorlds()) {
			worlds.add(world.getName());
		}
		return worlds;
	}
	
	public static Material getMaterial(String material) throws Exception{
		Material result = Material.getMaterial(material);
		if(result == null) {
			throw new Exception("Invalid material: " + material);
		}
		return result;
	}
	
	public static int getIndex(int x, int y) throws Exception{
		if(x >= 9 || y >= 6) {
			throw new Exception("Invalid item position: (x = " + x + ", y = " + y + ")");
		}
		return 9 * y + x;
	}
	
	public static String colorTranslate(String text) {
		return(ChatColor.translateAlternateColorCodes('&', text));
	}
	
	public static void colorTranslate(List<String> texts) {
		for(int i = 0; i<texts.size(); i++) {
			texts.set(i, colorTranslate(texts.get(i)));
		}
	}
}
