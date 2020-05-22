package me.gelloe.TreeClick.Utils;

import java.io.File;
import java.util.HashSet;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.gelloe.TreeClick.Main;

public class ConH {

	private static FileConfiguration config;
	private static ConfigurationSection trees;
	

	public static HashSet<String> worlds = new HashSet<String>();
	public static boolean auto_update;
	public static boolean creative;
	public static boolean give_i_d;
	public static int log_speed;
	public static int leaf_speed;
	public static int leaf_r;
	public static boolean auto;
	public static boolean axe;

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

	public static void setUp() {

		File dataFolder = Main.getPlugin(Main.class).getDataFolder();
		if (!dataFolder.exists()) {
			dataFolder.mkdir();
			Util.print("Creating plugin folder");
		}

		File conYAML = new File(dataFolder, "/config.yml");
		if (!conYAML.exists()) {
			Main.getPlugin(Main.class).saveDefaultConfig();
			Util.print("Generating configuration");
		}
		config = YamlConfiguration.loadConfiguration(conYAML);

		auto_update = config.getBoolean("autoupdate-enabled");
		config.getStringList("enabled-worlds").forEach(worlds::add);
		creative = config.getBoolean("creative");
		give_i_d = config.getBoolean("give-item-drops-directly");
		auto = config.getBoolean("auto-replant-saplings");
		leaf_speed = config.getInt("leaves-breaking.speed");
		leaf_r = config.getInt("leaves-breaking.max-range");
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
		trees = config.getConfigurationSection("species");
		Util.print("Registering " + config.getStringList("forbidden-blocks").size() + " safety blocks...");
		for (String s : config.getStringList("forbidden-blocks"))
			Util.FORBIDDEN_BLOCKS.add(Material.getMaterial(s.toUpperCase()));
		Util.print("Registering " + trees.getKeys(false).size() + " tree species...");
		trees.getKeys(false).forEach(s -> {
			Util.log_leaf.put(Material.getMaterial(s), Material.getMaterial(trees.get(s + ".leaves").toString()));
			Util.log_sapling.put(Material.getMaterial(s), Material.getMaterial(trees.get(s + ".sapling").toString()));
		});
		Util.print("Registering " + config.getStringList("tools").size() + " different tools...");
		for (String s : config.getStringList("tools"))
			Util.AXES.add(Material.getMaterial(s.toUpperCase()));
	}
}
