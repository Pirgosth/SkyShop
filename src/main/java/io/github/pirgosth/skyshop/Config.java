package io.github.pirgosth.skyshop;

import io.github.pirgosth.skyshop.GUIInventory.ChangeInvButton;
import io.github.pirgosth.skyshop.GUIInventory.Menu;
import io.github.pirgosth.skyshop.GUIInventory.TradeButton;
import io.github.pirgosth.skyshop.models.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Config implements IConfig {
    private FileConfiguration config;

    @Override
    public void load() {
        SkyShop.getInstance().saveDefaultConfig();
        config = SkyShop.getInstance().getConfig();
        cleanConfig();
        save();
    }

    @Override
    public void reload() {
        SkyShop.getInstance().saveDefaultConfig();
        SkyShop.getInstance().reloadConfig();
        config = SkyShop.getInstance().getConfig();
        // cleanConfig();
        save();
    }

    @Override
    public void save() {
        SkyShop.getInstance().saveConfig();
    }

    @Override
    public void cleanConfig() {
        ArrayList<String> worlds = getActiveWorlds();
        ArrayList<String> common = new ArrayList<>(worlds);
        common.retainAll(Utility.getWorldNames());
        config.set("enabled-worlds", common);
        if (common.size() != worlds.size()) {
            worlds.removeAll(common);
            Bukkit.getConsoleSender().sendMessage(
                    ChatColor.GOLD + "Old worlds: " + worlds + " have been removed from configuration.");
        }
    }

    public static boolean doesWorldExist(String world) {
        for (World w : Bukkit.getWorlds()) {
            if (w.getName().equalsIgnoreCase(world)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<String> getActiveWorlds() {
        List<String> worlds = config.getStringList("enabled-worlds");
        return new ArrayList<>(worlds);
    }

    @Override
    public boolean addActiveWorld(String world) {
        ArrayList<String> worlds = getActiveWorlds();
        if (!worlds.contains(world)) {
            worlds.add(world);
            config.set("enabled-worlds", worlds);

            return true;
        }
        return false;
    }

    @Override
    public boolean delActiveWorld(String world) {
        ArrayList<String> worlds = getActiveWorlds();
        if (worlds.contains(world)) {
            worlds.remove(world);
            config.set("enabled-worlds", worlds);
            return true;
        }
        return false;
    }

    public void addCategory(String name, String description, Material material, int x, int y) throws Exception {
        InsertionResult insertionResult = SkyConfig.getConfiguration().shopSection.addCategory(new CategoryConfig(
                name,
                description,
                material.toString(),
                x,
                y,
                new ArrayList<>()
        ));

        if (insertionResult == InsertionResult.DUPLICATE_POSITION) {
            throw new Exception(String.format("Category already exists at (%s;%s).", x, y));
        }

        SkyConfig.getConfiguration().save();
        SkyShop.getShop().reload();
    }

    public ChangeInvButton getCategory(CategoryConfig categoryConfig, Menu parent) throws Exception {
        String name = Utility.colorTranslate(categoryConfig.name);
        String description = Utility.colorTranslate(categoryConfig.description);
        Material material = Utility.getMaterial(categoryConfig.material);

        Menu menu = new Menu(name, 6, parent.holder(), parent);

        for (ItemConfig item : categoryConfig.items) {
            getItem(item, menu);
        }

        menu.addButton(new ChangeInvButton("Back", Material.ACACIA_DOOR, 4, 5, null, menu, menu.parent()));
        return new ChangeInvButton(name, material, categoryConfig.x, categoryConfig.y, Arrays.asList(description), menu.parent(), menu);
    }

    @Override
    public void removeCategory(int x, int y) throws Exception {
        CategoryConfig cat = SkyConfig.getConfiguration().shopSection.getCategoryAt(x, y);
        if (cat == null) throw new Exception(String.format("There's no category at (%s;%s).", x, y));
        SkyConfig.getConfiguration().shopSection.removeCategory(cat);
        SkyConfig.getConfiguration().save();
        SkyShop.getShop().reload();
    }

    @Override
    public void editCategoryName(String name, int x, int y) throws Exception {
        CategoryConfig cat = SkyConfig.getConfiguration().shopSection.getCategoryAt(x, y);
        if (cat == null) throw new Exception(String.format("There's no category at (%s;%s).", x, y));
        cat.name = name;
        SkyConfig.getConfiguration().save();
        SkyShop.getShop().reload();
    }

    @Override
    public void editCategoryDescription(String desc, int x, int y) throws Exception {
        CategoryConfig cat = SkyConfig.getConfiguration().shopSection.getCategoryAt(x, y);
        if (cat == null) throw new Exception(String.format("There's no category at (%s;%s).", x, y));
        cat.description = desc;
        SkyConfig.getConfiguration().save();
        SkyShop.getShop().reload();
    }

    @Override
    public void moveCategory(int fromX, int fromY, int toX, int toY) throws Exception {
        CategoryConfig cat = SkyConfig.getConfiguration().shopSection.getCategoryAt(fromX, fromY);
        if (cat == null) throw new Exception(String.format("There's no category at (%s;%s).", fromX, fromY));
        CategoryConfig toCat = SkyConfig.getConfiguration().shopSection.getCategoryAt(toX, toY);
        if (toCat != null) throw new Exception(String.format("There's already a category at (%s;%s).", toX, toY));
        cat.x = toX;
        cat.y = toY;
        SkyConfig.getConfiguration().save();
        SkyShop.getShop().reload();
    }

    @Override
    public void addItem(int catX, int catY, String name, Material material, int x, int y, float buy, float sell) throws Exception {
        CategoryConfig cat = SkyConfig.getConfiguration().shopSection.getCategoryAt(catX, catY);
        if (cat == null) throw new Exception(String.format("There's no category at (%s;%s).", catX, catY));
        InsertionResult insertionResult = cat.addItem(new ItemConfig(name, material.toString(), x, y, buy, sell));
        if (insertionResult == InsertionResult.DUPLICATE_POSITION) {
            throw new Exception(String.format("Item already exists at (%s;%s).", x, y));
        }
        SkyConfig.getConfiguration().save();
        SkyShop.getShop().reload();
    }

    public void getItem(ItemConfig item, Menu parent) throws Exception {
        Material material = Utility.getMaterial(item.material);
        parent.addButton(new TradeButton(Utility.colorTranslate(item.name), material, item.x, item.y, item.buy, item.sell, parent));
    }

    @Override
    public void removeItem(int catX, int catY, int x, int y) throws Exception {
        CategoryConfig cat = SkyConfig.getConfiguration().shopSection.getCategoryAt(catX, catY);
        if (cat == null) throw new Exception(String.format("There's no category at (%s;%s).", catX, catY));
        ItemConfig item = cat.getItemAt(x, y);
        if (item == null) throw new Exception(String.format("There's no item at (%s;%s).", x, y));
        cat.removeItem(item);
        SkyConfig.getConfiguration().save();
        SkyShop.getShop().reload();
    }

    @Override
    public void editItemName(int catX, int catY, String name, int x, int y) throws Exception {
        CategoryConfig cat = SkyConfig.getConfiguration().shopSection.getCategoryAt(catX, catY);
        if (cat == null) throw new Exception(String.format("There's no category at (%s;%s).", catX, catY));
        ItemConfig item = cat.getItemAt(x, y);
        if (item == null) throw new Exception(String.format("There's no item at (%s;%s).", x, y));
        item.name = name;
        SkyConfig.getConfiguration().save();
        SkyShop.getShop().reload();
    }

    @Override
    public void editItemBuyPrice(int catX, int catY, float buy, int x, int y) throws Exception {
        CategoryConfig cat = SkyConfig.getConfiguration().shopSection.getCategoryAt(catX, catY);
        if (cat == null) throw new Exception(String.format("There's no category at (%s;%s).", catX, catY));
        ItemConfig item = cat.getItemAt(x, y);
        if (item == null) throw new Exception(String.format("There's no item at (%s;%s).", x, y));
        item.buy = buy;
        SkyConfig.getConfiguration().save();
        SkyShop.getShop().reload();
    }

    @Override
    public void editItemSellPrice(int catX, int catY, float sell, int x, int y) throws Exception {
        CategoryConfig cat = SkyConfig.getConfiguration().shopSection.getCategoryAt(catX, catY);
        if (cat == null) throw new Exception(String.format("There's no category at (%s;%s).", catX, catY));
        ItemConfig item = cat.getItemAt(x, y);
        if (item == null) throw new Exception(String.format("There's no item at (%s;%s).", x, y));
        item.sell = sell;
        SkyConfig.getConfiguration().save();
        SkyShop.getShop().reload();
    }

    @Override
    public void moveItem(int catX, int catY, int fromX, int fromY, int toX, int toY) throws Exception {
        CategoryConfig cat = SkyConfig.getConfiguration().shopSection.getCategoryAt(catX, catY);
        if (cat == null) throw new Exception(String.format("There's no category at (%s;%s).", catX, catY));
        ItemConfig item = cat.getItemAt(fromX, fromY);
        if (item == null) throw new Exception(String.format("There's no item at (%s;%s).", fromX, fromY));
        ItemConfig toItem = cat.getItemAt(toX, toY);
        if (toItem != null) throw new Exception(String.format("There's already an item at (%s;%s).", toX, toY));
        item.x = toX;
        item.y = toY;
        SkyConfig.getConfiguration().save();
        SkyShop.getShop().reload();
    }

    public Menu loadShop() throws Exception {
        Menu shop = new Menu("Shop", 3, SkyShop.getShop());
        ShopSection shopSection = SkyConfig.getConfiguration().shopSection;
        if (shopSection == null) throw new Exception("Missing node shop in config.yml !");
        for (CategoryConfig cat : shopSection.categories) {
            ChangeInvButton tmp = getCategory(cat, shop);
            shop.addButton(tmp);
        }
        return shop;
    }
}
