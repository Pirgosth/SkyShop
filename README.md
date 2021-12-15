# SkyShop Documentation

## 1. General commands

- `/shop`: Open shop inventory menu.
- `/shop reload`: Reload shop configuration from file.

## 2. Admins commands

These commands allow you to *add/edit/remove/move* `categories` and `items` from shop configuration.

### 2.1 Category management

- `/shopadmin category add [name] [desc] [material] [x] [y]`: Add a new category named `name`,
 with description `desc`, material `material`, at inventory position `x` and `y`.

- `/shopadmin category remove [x] [y]`: Remove category at position `x` and `y`.

- `/shopadmin category edit name [x] [y] [name]`: Change category name with `name` at position `x` and `y`.

- `/shopadmin category edit desc [x] [y] [desc]`: Change category description with `desc` at position `x` and `y`.

- `/shopadmin category move [from_x] [from_y] [to_x] [to_y]`: Move category from `from_x` and `from_y` to `to_x` and `to_y`. Send an error if target position is already taken.

**Note: Be careful to respect position borders, whether between `0` and `8` for `x` and between `0` and `2` for `y`.**

### 2.2 Item management

- `/shopadmin item add [cat_x] [cat_y] [name] [material] [x] [y] [buy] [sell]`: Add a new item in category (at `cat_x`, `cat_y`) named `name`, with material `material`, at inventory position `x` and `y`, and with buying price `buy` and selling price `sell`.
 with description `desc`, material `material`, at inventory position `x` and `y`.

- `/shopadmin item remove [cat_x] [cat_y] [x] [y]`: Remove item in category (at `cat_x`, `cat_y`) at position `x` and `y`.

- `/shopadmin item edit name [cat_x] [cat_y] [x] [y] [name]`: Change item name in category (at `cat_x`, `cat_y`) with `name` at position `x` and `y`.

- `/shopadmin item edit buy [cat_x] [cat_y] [x] [y] [buy]`: Change item buying price in category (at `cat_x`, `cat_y`) with `buy` at position `x` and `y`.

- `/shopadmin item edit buy [cat_x] [cat_y] [x] [y] [sell]`: Change item selling price in category (at `cat_x`, `cat_y`) with `sell` at position `x` and `y`.

- `/shopadmin item move [cat_x] [cat_y] [from_x] [from_y] [to_x] [to_y]`: Move item in category (at `cat_x`, `cat_y`) from `from_x` and `from_y` to `to_x` and `to_y`. Send an error if target position is already taken.

**Note: Be careful to respect position borders, whether between `0` and `8` for `x` and between `0` and `2` for `y`.**

## 3 Resources

- To get a full list of available `MATERIALS`: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html