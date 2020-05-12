package me.gelloe.TreeClick.Utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.gelloe.TreeClick.Main;

public class ComMan implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length < 1)
			return false;
		if (args[0].equalsIgnoreCase("update")) {
			new Main().performUpdate();
		}
		return false;
	}
	

}
