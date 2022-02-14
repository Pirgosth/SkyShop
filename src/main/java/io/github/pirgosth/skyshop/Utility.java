package io.github.pirgosth.skyshop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Utility {
	public static ArrayList<String> getWorldNames() {
		ArrayList<String> worlds = new ArrayList<>();
		for (World world : Bukkit.getWorlds()) {
			worlds.add(world.getName());
		}
		return worlds;
	}

	public static Material getMaterial(String material) throws Exception {
		Material result = Material.getMaterial(material);
		if (result == null) {
			throw new Exception("Invalid material: " + material);
		}
		return result;
	}

	public static int getIndex(int x, int y) throws Exception {
		if (x >= 9 || y >= 6) {
			throw new Exception("Invalid item position: (x = " + x + ", y = " + y + ")");
		}
		return 9 * y + x;
	}

	public static String colorTranslate(String text) {
		return (ChatColor.translateAlternateColorCodes('&', text));
	}

	public static void colorTranslate(List<String> texts) {
		if (texts == null) {
			return;
		}
		for (int i = 0; i < texts.size(); i++) {
			texts.set(i, colorTranslate(texts.get(i)));
		}
	}

	public static int getRequiredSlots(ItemStack stack) {
		int itemStackSize = stack.getMaxStackSize();
		return stack.getAmount() / itemStackSize + (stack.getAmount() % itemStackSize == 0 ? 0 : 1);
	}

	public static boolean canFitInInventory(ItemStack stack, Inventory inv) {
		int emptyCount = 0;
		int itemStackSize = stack.getMaxStackSize();
		int requiredSlots = getRequiredSlots(stack);
		int emptyAmount = 0;
		for (ItemStack slot : inv.getStorageContents()) {
			if (slot == null || slot.getType() == Material.AIR) {
				emptyCount++;
				emptyAmount += itemStackSize;
			}
			if (slot != null && slot.getType() == stack.getType()) {
				emptyAmount += (itemStackSize - slot.getAmount());
			}
			if (emptyCount >= requiredSlots || emptyAmount >= stack.getAmount())
				return true;
		}
		return false;
	}

	public static List<ItemStack> getLegalItemStack(ItemStack stack) {
		int itemStackSize = stack.getMaxStackSize();
		int requiredSlots = getRequiredSlots(stack);
		int amount = stack.getAmount();
		List<ItemStack> stacks = new ArrayList<>();
		for (int i = 0; i < requiredSlots; i++) {
			stacks.add(new ItemStack(stack.getType(), Math.min(amount, itemStackSize)));
			amount -= itemStackSize;
		}
		return stacks;
	}

	public static int countInInventory(ItemStack stack, Inventory inv) {
		int amount = 0;
		for (ItemStack slot : inv.getStorageContents()) {
			if (slot == null || slot.getType() == Material.AIR) {
				continue;
			}
			if (slot.getType() == stack.getType()) {
				amount += slot.getAmount();
			}
		}
		return amount;
	}
}
