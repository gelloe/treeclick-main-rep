package me.gelloe.TreeClick;

import org.bukkit.plugin.java.JavaPlugin;

import me.gelloe.TreeClick.Utils.ConH;

public class Main extends JavaPlugin {
	
	
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new TreeEventListener(), this);
		ConH.setUp();
	}

	public void onDisable() {
	}
}
