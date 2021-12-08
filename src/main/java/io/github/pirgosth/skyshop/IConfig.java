package io.github.pirgosth.skyshop;

import io.github.pirgosth.skyshop.GUIInventory.ChangeInvButton;
import io.github.pirgosth.skyshop.GUIInventory.Menu;
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
    ChangeInvButton getCategory(String cat, Menu parent) throws Exception;
    void removeCategory(int x, int y) throws Exception;
//    void editCategoryName(String name, int x, int y) throws Exception;
//    void editCategoryDescription(String desc, int x, int y) throws Exception;
//    void moveCategory(int fromX, int fromY, int toX, int toY) throws Exception;

    Menu loadShop() throws Exception;

}
