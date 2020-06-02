package io.github.pirgosth.skyshop;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class EventListener implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getInventory().getHolder() != SkyShop.getShop()) {
			return;
		}
		event.setCancelled(true);
		if (event.getClickedInventory() == null || event.getClickedInventory().getHolder() != SkyShop.getShop()) {
			return;
		}
		SkyShop.getShop().onClick(event);
//		if(event.getSlot() == 3) {
//			Player player = (Player) event.getWhoClicked();
//			if(SkyShop.economy.withdrawPlayer(player, 100).type == EconomyResponse.ResponseType.SUCCESS) {
//				player.sendMessage("You purchase x1 Quartz for 100€");
//				player.getInventory().addItem(new ItemStack(Material.QUARTZ));
//			}
//			else {
//				player.sendMessage("You don't have enough money for this !");
//			}
//		}
	}
}
