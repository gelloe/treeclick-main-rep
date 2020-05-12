package me.gelloe.TreeClick.Utils;

import java.io.File;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.gelloe.TreeClick.Main;
import net.md_5.bungee.api.ChatColor;

public class ConH {

	static FileConfiguration config;
	public static HashSet<String> worlds;
	public static boolean auto_update;
	public static boolean creative;
	public static boolean give_i_d;
	public static boolean drop_i;
	public static int log_speed;
	public static int leaf_speed;
	public static boolean auto;
	public static List<String> block_l;

	public static String leaf_e;
	public static String leaf_p;
	public static int leaf_p_c;
	public static double leaf_p_o;
	public static String leaf_s;
	public static float leaf_s_v;
	public static float leaf_s_p;

	public static String log_e;
	public static String log_p;
	public static int log_p_c;
	public static double log_p_o;
	public static String log_s;
	public static float log_s_v;
	public static float log_s_p;

	public static String tree_s;
	public static float tree_s_v;
	public static float tree_s_p;

	public static boolean axe;

	public static void setUp() {

		File dataFolder = Main.getPlugin(Main.class).getDataFolder();
		if (!dataFolder.exists()) {
			dataFolder.mkdir();
			Util.print(ChatColor.YELLOW + "Creating plugin folder");
		}
		File autYAML = new File(dataFolder, "/config.yml");
		if (!autYAML.exists()) {
			Main.getPlugin(Main.class).saveDefaultConfig();
			Util.print(ChatColor.YELLOW + "Generating configuration");
		}

		config = YamlConfiguration.loadConfiguration(autYAML);

		auto_update = config.getBoolean("autoupdate-enabled");
		config.getStringList("enabled-worlds").forEach((s) -> worlds.add(s));
		creative = config.getBoolean("creative");
		give_i_d = config.getBoolean("give-item-drops-directly");
		drop_i = config.getBoolean("drop-items");
		auto = config.getBoolean("auto-replant-saplings");
		block_l = config.getStringList("forbidden-blocks");

		leaf_speed = config.getInt("leaves-breaking.speed");
		leaf_e = config.getString("leaves-breaking.effect").toUpperCase();
		leaf_p = config.getString("leaves-breaking.particle.type").toUpperCase();
		leaf_p_c = config.getInt("leaves-breaking.particle.count");
		leaf_p_o = config.getDouble("leaves-breaking.particle.offset");
		leaf_s = config.getString("leaves-breaking.sound.type").toUpperCase();
		leaf_s_v = config.getLong("leaves-breaking.sound.volume");
		leaf_s_p = config.getLong("leaves-breaking.sound.pitch");

		log_speed = config.getInt("logs-breaking.speed");
		log_e = config.getString("logs-breaking.effect").toUpperCase();
		log_p = config.getString("logs-breaking.particle.type").toUpperCase();
		log_p_c = config.getInt("logs-breaking.particle.count");
		log_p_o = config.getDouble("logs-breaking.particle.offset");
		log_s = config.getString("logs-breaking.sound.type").toUpperCase();
		log_s_v = config.getLong("logs-breaking.sound.volume");
		log_s_p = config.getLong("logs-breaking.sound.pitch");

		tree_s = config.getString("tree-breaking.sound.type").toUpperCase();
		tree_s_v = config.getLong("tree-breaking.sound.volume");
		tree_s_p = config.getLong("tree-breaking.sound.pitch");
		axe = config.getBoolean("damage-axe-accordingly");
		Util.print("Adding " + block_l.size() + " blocks to the registry...");
		for (String s : block_l)
			Util.FORBIDDEN_BLOCKS.add(Material.getMaterial(s.toUpperCase()));

	}

}
