package io.github.pirgosth.skyshop;

import io.github.pirgosth.skyshop.models.SkyConfig;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

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
    }

    @EventHandler
    public void onVillagerInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Villager villager && SkyConfig.getConfiguration().areNPCTradesDisabled(villager.getWorld().getName()))
            event.setCancelled(true);
    }
}
