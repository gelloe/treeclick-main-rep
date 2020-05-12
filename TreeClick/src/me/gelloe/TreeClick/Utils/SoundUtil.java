package me.gelloe.TreeClick.Utils;

import org.bukkit.Effect;
import org.bukkit.Particle;
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
	
	public static void spawnParticle(Block b, String particle, int count, double offset) {
		try {
			b.getWorld().spawnParticle(Particle.valueOf(particle), b.getLocation().add(0.5,0.5,0.5), count, offset, offset, offset);
		} catch (IllegalArgumentException e) {
			return;
		}
	}
	
	
	public static void breakLeaves(Block b) {
		playSound(b, ConH.leaf_s, ConH.leaf_s_v, ConH.leaf_s_p);
		playEffect(b, ConH.leaf_e, 1);
		spawnParticle(b, ConH.leaf_p, ConH.leaf_p_c, ConH.leaf_p_o);
	}
	
	public static void breakLog(Block b) {
		playSound(b, ConH.log_s, ConH.log_s_v, ConH.log_s_p);
		playEffect(b, ConH.log_e, 1);
		spawnParticle(b, ConH.log_p, ConH.log_p_c, ConH.log_p_o);
	}
	
	public static void breakTree(Block b) {
		playSound(b, ConH.tree_s, ConH.tree_s_v, ConH.tree_s_p);
	}

}
