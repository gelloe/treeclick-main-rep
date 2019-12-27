package me.gelloe.TreeClick;

import java.util.ArrayList;

import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public static ArrayList<Block> blocks = new ArrayList<Block>();
	
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new TreeEventListener(), this);
		loadConfig();
	}

	public void onDisable() {
	}

	public void loadConfig() {
		this.getConfig().options().copyDefaults(true);
		this.saveDefaultConfig();
	}
}
