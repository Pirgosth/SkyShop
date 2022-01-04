package io.github.pirgosth.skyshop.commands;

import io.github.pirgosth.liberty.core.api.commands.ICommandArgument;
import io.github.pirgosth.liberty.core.api.commands.ICommandListener;
import io.github.pirgosth.liberty.core.api.commands.annotations.LibertyCommand;
import io.github.pirgosth.liberty.core.api.commands.annotations.LibertyCommandArgument;
import io.github.pirgosth.liberty.core.api.commands.annotations.LibertyCommandPermission;
import io.github.pirgosth.liberty.core.commands.CommandParameters;
import io.github.pirgosth.skyshop.SkyShop;
import io.github.pirgosth.skyshop.Utility;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

@LibertyCommandPermission(permission = "skyshop.admin.command.*")
public class AdminCommands implements ICommandListener {

    @LibertyCommand(command = "shopadmin.category.add")
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.String)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.String)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.String)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    public boolean addCategory(CommandParameters params) throws Exception {
        CommandSender sender = params.sender;
        String name = params.args[0];
        String description = params.args[1];
        Material material = Utility.getMaterial(params.args[2]);
        int x = Integer.parseInt(params.args[3]);
        int y = Integer.parseInt(params.args[4]);

        SkyShop.getMainConfig().addCategory(name, description, material, x, y);
        sender.sendMessage(Utility.colorTranslate(String.format("&2[SkyShop] &7Category %s successfully added.", name)));

        return true;
    }

    @LibertyCommand(command = "shopadmin.category.remove")
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    public boolean removeCategory(CommandParameters params) throws Exception {
        CommandSender sender = params.sender;
        int x = Integer.parseInt(params.args[0]);
        int y = Integer.parseInt(params.args[1]);

        SkyShop.getMainConfig().removeCategory(x, y);
        sender.sendMessage(Utility.colorTranslate("&2[SkyShop] &7Category successfully deleted."));

        return true;
    }

    @LibertyCommand(command = "shopadmin.category.edit.name")
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.String)
    public boolean editCategoryName(CommandParameters params) throws Exception {
        CommandSender sender = params.sender;
        int x = Integer.parseInt(params.args[0]);
        int y = Integer.parseInt(params.args[1]);
        String name = params.args[2];

        SkyShop.getMainConfig().editCategoryName(name, x, y);
        sender.sendMessage(Utility.colorTranslate("&2[SkyShop] &7Category name successfully renamed."));

        return true;
    }

    @LibertyCommand(command = "shopadmin.category.edit.desc")
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.String)
    public boolean editCategoryDescription(CommandParameters params) throws Exception {
        CommandSender sender = params.sender;
        int x = Integer.parseInt(params.args[0]);
        int y = Integer.parseInt(params.args[1]);
        String desc = params.args[2];

        SkyShop.getMainConfig().editCategoryDescription(desc, x, y);
        sender.sendMessage(Utility.colorTranslate("&2[SkyShop] &7Category description successfully renamed."));

        return true;
    }

    @LibertyCommand(command = "shopadmin.category.move")
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    public boolean moveCategory(CommandParameters params) throws Exception {
        CommandSender sender = params.sender;
        int fromX = Integer.parseInt(params.args[0]);
        int fromY = Integer.parseInt(params.args[1]);
        int toX = Integer.parseInt(params.args[2]);
        int toY = Integer.parseInt(params.args[3]);

        SkyShop.getMainConfig().moveCategory(fromX, fromY, toX, toY);
        sender.sendMessage(Utility.colorTranslate("&2[SkyShop] &7Category successfully moved."));

        return true;
    }

    @LibertyCommand(command = "shopadmin.item.add")
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.String)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.String)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Float)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Float)
    public boolean addItem(CommandParameters params) throws Exception {
        CommandSender sender = params.sender;
        int catX = Integer.parseInt(params.args[0]);
        int catY = Integer.parseInt(params.args[1]);
        String name = params.args[2];
        Material material = Utility.getMaterial(params.args[3]);
        int x = Integer.parseInt(params.args[4]);
        int y = Integer.parseInt(params.args[5]);
        float buy = Float.parseFloat(params.args[6]);
        float sell = Float.parseFloat(params.args[7]);

        SkyShop.getMainConfig().addItem(catX, catY, name, material, x, y, buy, sell);
        sender.sendMessage(Utility.colorTranslate(String.format("&2[SkyShop] &7Item %s successfully added.", name)));

        return true;
    }

    @LibertyCommand(command = "shopadmin.item.remove")
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    public boolean removeItem(CommandParameters params) throws Exception {
        CommandSender sender = params.sender;
        int catX = Integer.parseInt(params.args[0]);
        int catY = Integer.parseInt(params.args[1]);
        int x = Integer.parseInt(params.args[2]);
        int y = Integer.parseInt(params.args[3]);

        SkyShop.getMainConfig().removeItem(catX, catY, x, y);
        sender.sendMessage(Utility.colorTranslate("&2[SkyShop] &7Item successfully removed."));

        return true;
    }

    @LibertyCommand(command = "shopadmin.item.edit.name")
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.String)
    public boolean editItemName(CommandParameters params) throws Exception {
        CommandSender sender = params.sender;
        int catX = Integer.parseInt(params.args[0]);
        int catY = Integer.parseInt(params.args[1]);
        int x = Integer.parseInt(params.args[2]);
        int y = Integer.parseInt(params.args[3]);
        String name = params.args[4];

        SkyShop.getMainConfig().editItemName(catX, catY, name, x, y);
        sender.sendMessage(Utility.colorTranslate(String.format("&2[SkyShop] &7Item successfully renamed to %s.", name)));

        return true;
    }

    @LibertyCommand(command = "shopadmin.item.edit.buy")
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Float)
    public boolean editItemBuyPrice(CommandParameters params) throws Exception {
        CommandSender sender = params.sender;
        int catX = Integer.parseInt(params.args[0]);
        int catY = Integer.parseInt(params.args[1]);
        int x = Integer.parseInt(params.args[2]);
        int y = Integer.parseInt(params.args[3]);
        float buy = Float.parseFloat(params.args[4]);

        SkyShop.getMainConfig().editItemBuyPrice(catX, catY, buy, x, y);
        sender.sendMessage(Utility.colorTranslate(String.format("&2[SkyShop] &7Item buy price successfully edited to %s.", buy)));

        return true;
    }

    @LibertyCommand(command = "shopadmin.item.edit.sell")
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Float)
    public boolean editItemSellPrice(CommandParameters params) throws Exception {
        CommandSender sender = params.sender;
        int catX = Integer.parseInt(params.args[0]);
        int catY = Integer.parseInt(params.args[1]);
        int x = Integer.parseInt(params.args[2]);
        int y = Integer.parseInt(params.args[3]);
        float sell = Float.parseFloat(params.args[4]);

        SkyShop.getMainConfig().editItemSellPrice(catX, catY, sell, x, y);
        sender.sendMessage(Utility.colorTranslate(String.format("&2[SkyShop] &7Item sell price successfully edited to %s.", sell)));

        return true;
    }

    @LibertyCommand(command = "shopadmin.item.move")
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.Integer)
    public boolean moveItem(CommandParameters params) throws Exception {
        CommandSender sender = params.sender;
        int catX = Integer.parseInt(params.args[0]);
        int catY = Integer.parseInt(params.args[1]);
        int fromX = Integer.parseInt(params.args[2]);
        int fromY = Integer.parseInt(params.args[3]);
        int toX = Integer.parseInt(params.args[4]);
        int toY = Integer.parseInt(params.args[5]);

        SkyShop.getMainConfig().moveItem(catX, catY, fromX, fromY, toX, toY);
        sender.sendMessage(Utility.colorTranslate(String.format("&2[SkyShop] &7Item successfully moved to (%s;%s).", toX, toY)));

        return true;
    }
}