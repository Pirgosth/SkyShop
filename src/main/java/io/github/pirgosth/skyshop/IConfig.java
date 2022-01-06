package io.github.pirgosth.skyshop;

import io.github.pirgosth.skyshop.GUIInventory.ChangeInvButton;
import io.github.pirgosth.skyshop.GUIInventory.Menu;
import io.github.pirgosth.skyshop.models.CategoryConfig;
import io.github.pirgosth.skyshop.models.ItemConfig;
import org.bukkit.Material;

import java.util.ArrayList;

public interface IConfig {

    void load();
    void reload();
    void save();
    void cleanConfig();

    ArrayList<String> getActiveWorlds();
    boolean addActiveWorld(String world);
    boolean delActiveWorld(String world);

    void addCategory(String name, String description, Material material, int x, int y) throws Exception;
    ChangeInvButton getCategory(CategoryConfig cat, Menu parent) throws Exception;
    void removeCategory(int x, int y) throws Exception;
    void editCategoryName(String name, int x, int y) throws Exception;
    void editCategoryDescription(String desc, int x, int y) throws Exception;
    void editCategoryMaterial(Material material, int x, int y) throws Exception;
    void moveCategory(int fromX, int fromY, int toX, int toY) throws Exception;

    void addItem(int catX, int catY, String name, Material material, int x, int y, float buy, float sell) throws Exception;
    void getItem(ItemConfig item, Menu parent) throws Exception;
    void removeItem(int catX, int catY, int x, int y) throws Exception;
    void editItemName(int catX, int catY, String name, int x, int y) throws Exception;
    void editItemBuyPrice(int catX, int catY, float buy, int x, int y) throws Exception;
    void editItemSellPrice(int catX, int catY, float sell, int x, int y) throws Exception;
    void editItemMaterial(int catX, int catY, Material material, int x, int y) throws Exception;
    void moveItem(int catX, int catY, int fromX, int fromY, int toX, int toY) throws Exception;

    Menu loadShop() throws Exception;

}
