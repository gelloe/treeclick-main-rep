package me.gelloe.TreeClick;

import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.gelloe.TreeClick.Utils.ConH;
import me.gelloe.TreeClick.Utils.Util;

public class TreeEventListener implements Listener {
	

	@EventHandler
	public void blockBreakEvent(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if (!Util.holdingAxe(p))
			return;
		Block b = e.getBlock();
		if (!Util.isLog(b))
			return;
		Tree tree = new Tree(b, b.getType(), p.getInventory().getItemInMainHand());
		if (tree.leaves.isEmpty())
			return;
		if (tree.containsForbiddenBlocks())
			return;
		if (p.getGameMode() == GameMode.CREATIVE)
			if (!ConH.creative)
				return;
		
		for (String w : ConH.worlds) {
			if (p.getWorld().getName().equals(w)) {
				tree.breakSlowly(p);
				break;
			}
		}
		if (ConH.give_i_d) {
			e.setCancelled(true);
			Util.destroyBlock(p, p.getInventory().getItemInMainHand(), true, b, true);
		}
	}

}
