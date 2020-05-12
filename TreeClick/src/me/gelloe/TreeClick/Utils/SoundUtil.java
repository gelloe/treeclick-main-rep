package me.gelloe.TreeClick.Utils;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.block.Block;

public class SoundUtil {

	public static void playSound(Block b, String sound, float volume, float pitch){
		try {
			b.getWorld().playSound(b.getLocation(), Sound.valueOf(sound), volume, pitch);
		} catch (IllegalArgumentException e) {
			return;
		}
		
	}
	
	public static void playEffect(Block b, String effect, int magnitude) {
		try {
			b.getWorld().playEffect(b.getLocation(), Effect.valueOf(effect), magnitude);
		} catch (IllegalArgumentException e) {
			return;
		}
	}
	
	
	public static void breakLeaves(Block b) {
		playSound(b, ConH.leaf_p, ConH.leaf_p_v, ConH.leaf_p_p);
		playEffect(b, ConH.leaf_e, 1);
	}
	
	public static void breakLog(Block b) {
		playSound(b, ConH.log_b, ConH.log_b_v, ConH.log_b_p);
		playEffect(b, ConH.log_e, 1);
	}
	
	public static void breakTree(Block b) {
		playSound(b, ConH.tree_b, ConH.tree_b_v, ConH.tree_b_p);
	}

}
