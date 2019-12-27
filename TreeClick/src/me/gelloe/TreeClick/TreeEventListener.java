package me.gelloe.TreeClick;

import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;

import me.gelloe.TreeClick.Utils.Util;

public class TreeEventListener implements Listener {
	
	private static Plugin plugin = Main.getPlugin(Main.class);
	private static FileConfiguration config = plugin.getConfig();
	private static List<String> worlds = config.getStringList("enabled-worlds");
	private static boolean creative = config.getBoolean("creative");
	private static boolean give_items_directly = config.getBoolean("give-item-drops-directly");
	@EventHandler
	public void blockBreakEvent(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if (!Util.holdingAxe(p))
			return;
		Block b = e.getBlock();
		if (!Util.isLog(b))
			return;
		Tree tree = new Tree(b, b.getType());
		if (!tree.hasLeaves())
			return;
		if (tree.containsForbiddenBlocks())
			return;
		if (p.getGameMode() == GameMode.CREATIVE)
			if (!creative)
				return;
		
		for (String w : worlds) {
			if (p.getWorld().getName().equals(w)) {
				tree.breakSlowly(p);
				break;
			}
		}
		if (give_items_directly) {
			e.setCancelled(true);
			Util.destroyBlock(p, true, b, true);
		}
	}

}
