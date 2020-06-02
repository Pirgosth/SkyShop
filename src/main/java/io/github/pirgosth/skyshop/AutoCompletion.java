package io.github.pirgosth.skyshop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

public class AutoCompletion implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		ArrayList<String> completions = new ArrayList<String>();
		if (args.length == 1) {
			StringUtil.copyPartialMatches(args[0], Arrays.asList("reload"), completions);
		}
		return completions;
	}

}
