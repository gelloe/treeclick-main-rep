package me.gelloe.TreeClick;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import me.gelloe.TreeClick.Utils.ConH;
import me.gelloe.TreeClick.Utils.Util;

public class EventListener implements Listener {

	@EventHandler
	public void blockBreakEvent(BlockBreakEvent e) {
		Player p = e.getPlayer();
		
		if (!Util.holdingAxe(p))
			return;
		
		Block b = e.getBlock();
		
		if (!Util.isLog(b))
			return;
		
		if (!ConH.worlds.contains(b.getWorld().getName()))
			return;
		
		Tree tree = new Tree(b, p);
		tree.timber();
		
		if (ConH.give_i_d && tree.isCuttable()){
			b.getDrops().forEach(i -> p.getInventory().addItem(i));
			e.setDropItems(false);
		}
	}

	@EventHandler
	public void playerJoinEvent(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (p.hasPermission("treeclick") && Main.update_available) {
			p.sendMessage(Util.tag + "An update for TreeClick is available!");
			p.sendMessage(Main.newestVersion + " is available for download at "
					+ Main.latestFileLink);
			p.sendMessage(Util.tag + "or type in '/tc update' to update");
		}
	}

}
