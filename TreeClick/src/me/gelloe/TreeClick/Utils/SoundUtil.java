package me.gelloe.TreeClick.Utils;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import me.gelloe.TreeClick.Main;

public class SoundUtil {
	
	private static Plugin plugin = Main.getPlugin(Main.class);
	private static FileConfiguration config = plugin.getConfig();
	
	private static String leaves_effect = config.getString("leaves-breaking.particle").toUpperCase();
	private static String leaves_popping = config.getString("leaves-breaking.sound").toUpperCase();
	private static float leaves_popping_volume = config.getLong("leaves-breaking.volume");
	private static float leaves_popping_pitch = config.getLong("leaves-breaking.pitch");
	
	private static String logs_effect = config.getString("logs-breaking.particle").toUpperCase();
	private static String logs_breaking = config.getString("logs-breaking.sound").toUpperCase();
	private static float logs_breaking_volume = config.getLong("logs-breaking.volume");
	private static float logs_breaking_pitch = config.getLong("logs-breaking.pitch");
	
	private static String tree_breaking = config.getString("tree-breaking.sound").toUpperCase();
	private static float tree_breaking_volume = config.getLong("tree-breaking.volume");
	private static float tree_breaking_pitch = config.getLong("tree-breaking.pitch");

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
		playSound(b, leaves_popping, leaves_popping_volume, leaves_popping_pitch);
		playEffect(b, leaves_effect, 1);
	}
	
	public static void breakLog(Block b) {
		playSound(b, logs_breaking, logs_breaking_volume, logs_breaking_pitch);
		playEffect(b, logs_effect, 1);
	}
	
	public static void breakTree(Block b) {
		playSound(b, tree_breaking, tree_breaking_volume, tree_breaking_pitch);
	}

}
