package io.github.pirgosth.skyshop;

import io.github.pirgosth.skyshop.GUIInventory.Button;
import io.github.pirgosth.skyshop.GUIInventory.ChangeInvButton;
import io.github.pirgosth.skyshop.GUIInventory.Menu;
import io.github.pirgosth.skyshop.GUIInventory.TradeButton;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.logging.Level;

public class Shop implements InventoryHolder {

	private Menu shop;
	private HashMap<Player, Menu> players;

	public Shop() {
		players = new HashMap<>();
	}

	public void load() throws Exception {
		try {
			shop = SkyShop.getMainConfig().loadShop();
		} catch (Exception e) {
			Bukkit.getLogger().log(Level.WARNING, "Error in config, loading SkyShop default config ...");
			e.printStackTrace();
			this.loadDefaultShop();
			throw e;
		}
	}

	public void reload() throws Exception {
		players = new HashMap<>();
		load();
	}

	private void loadDefaultShop() {
		shop = new Menu("Default", 3, this);
	}

	@NotNull
	@Override
	public Inventory getInventory() {
		return shop.inventory();
	}

	public void openInventory(Player player) {
		players.put(player, shop);
		shop.open(player);
	}

	public void onClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		Menu menu = players.get(player);
		if (menu != null) {
			Button button = menu.getButton(event.getSlot());
			if (button != null) {
				button.onClick(event);
				if (button instanceof ChangeInvButton && ((ChangeInvButton) button).getMenu() != null) {
					players.put(player, ((ChangeInvButton) button).getMenu());
				} else if (button instanceof TradeButton && ((TradeButton) button).getMenu() != null) {
					players.put(player, ((TradeButton) button).getMenu());
				}
			}
		}
	}

	public void buy(Player player, int slot) {

	}
}
