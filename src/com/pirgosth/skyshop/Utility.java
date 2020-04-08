package com.pirgosth.skyshop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
		if(texts == null) {
			return;
		}
		for(int i = 0; i<texts.size(); i++) {
			texts.set(i, colorTranslate(texts.get(i)));
		}
	}
	
	public static boolean canFitInInventory(ItemStack stack, Inventory inv) {
		int emptyCount = 0;
		int requiredSlots = stack.getAmount()/64 + (stack.getAmount()%64 == 0 ? 0 : 1);
		int emptyStacks = 0;
		for(ItemStack slot: inv.getStorageContents()) {
			if(slot == null || slot.getType() == Material.AIR) {
				emptyCount ++;
				emptyStacks += 64;
			}
			if(slot != null && slot.getType() == stack.getType()) {
				emptyStacks += (64-slot.getAmount());
			}
			if(emptyCount >= requiredSlots || emptyStacks >= stack.getAmount()) return true;
		}
		return false;
	}
	
	public static int countInInventory(ItemStack stack, Inventory inv) {
		int amount = 0;
		for(ItemStack slot: inv.getStorageContents()) {
			if(slot == null || slot.getType() == Material.AIR) {
				continue;
			}
			if(slot.getType() == stack.getType()) {
				amount += slot.getAmount();
			}
		}
		return amount;
	}
}
