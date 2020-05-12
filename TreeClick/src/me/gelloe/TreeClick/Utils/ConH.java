package me.gelloe.TreeClick.Utils;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import me.gelloe.TreeClick.Main;

public class ConH {

	static FileConfiguration config;
	public static List<String> worlds;
	public static boolean creative;
	public static boolean give_i_d;
	public static boolean drop_i;
	public static int log_speed;
	public static int leaf_speed;
	public static boolean auto;
	public static List<String> block_l;

	public static String leaf_e;
	public static String leaf_p;
	public static float leaf_p_v;
	public static float leaf_p_p;

	public static String log_e;
	public static String log_b;
	public static float log_b_v;
	public static float log_b_p;

	public static String tree_b;
	public static float tree_b_v;
	public static float tree_b_p;
	
	public static boolean axe;

	public static void setUp() {
		config = Main.getPlugin(Main.class).getConfig();
		worlds = config.getStringList("enabled-worlds");
		creative = config.getBoolean("creative");
		give_i_d = config.getBoolean("give-item-drops-directly");
		drop_i = config.getBoolean("drop-items");
		auto = config.getBoolean("auto-replant-saplings");
		block_l = config.getStringList("forbidden-blocks");
		leaf_speed = config.getInt("leaves-breaking.speed");
		leaf_e = config.getString("leaves-breaking.particle").toUpperCase();
		leaf_p = config.getString("leaves-breaking.sound").toUpperCase();
		leaf_p_v = config.getLong("leaves-breaking.volume");
		leaf_p_p = config.getLong("leaves-breaking.pitch");
		log_speed = config.getInt("logs-breaking.speed");
		log_e = config.getString("logs-breaking.particle").toUpperCase();
		log_b = config.getString("logs-breaking.sound").toUpperCase();
		log_b_v = config.getLong("logs-breaking.volume");
		log_b_p = config.getLong("logs-breaking.pitch");
		tree_b = config.getString("tree-breaking.sound").toUpperCase();
		tree_b_v = config.getLong("tree-breaking.volume");
		tree_b_p = config.getLong("tree-breaking.pitch");
		axe = config.getBoolean("damage-axe-accordingly");
	}

}
