package io.github.pirgosth.skyshop.commands;

import io.github.pirgosth.liberty.core.api.commands.ICommandArgument;
import io.github.pirgosth.liberty.core.api.commands.ICommandListener;
import io.github.pirgosth.liberty.core.api.commands.annotations.LibertyCommand;
import io.github.pirgosth.liberty.core.api.commands.annotations.LibertyCommandArgument;
import io.github.pirgosth.liberty.core.commands.CommandParameters;
import io.github.pirgosth.skyshop.SkyShop;
import io.github.pirgosth.skyshop.Utility;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

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

}
