package me.gelloe.TreeClick;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import me.gelloe.TreeClick.Utils.ComMan;
import me.gelloe.TreeClick.Utils.ConH;
import me.gelloe.TreeClick.Utils.Util;
import net.gravitydevelopment.updater.Updater;
import net.gravitydevelopment.updater.Updater.UpdateResult;
import net.gravitydevelopment.updater.Updater.UpdateType;

public class Main extends JavaPlugin {
	
	
	public static boolean update_available = false;
	public static String newestVersion;
	public static String latestFileLink;
	public static File f;
	
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new EventListener(), this);
		getCommand("treeclick").setExecutor(new ComMan());
		ConH.setUp();
		if (ConH.auto_update)
			new Updater(this, 348361, this.getFile(), UpdateType.DEFAULT, true);
		else {
			Updater update = new Updater(this, 348361, this.getFile(), UpdateType.NO_DOWNLOAD, false);
			update_available = update.getResult() == UpdateResult.UPDATE_AVAILABLE;
			if (update_available) {
				newestVersion = update.getLatestName();
				latestFileLink = update.getLatestFileLink();
				Util.print("An update for TreeClick is available!");
				Util.print(newestVersion + " is available for download at " + latestFileLink);
				Util.print("or type in '/tc update' to update");
			}
		}
	}
	
	public void performUpdate() {
		new Updater(this, 348361, this.getFile(), UpdateType.NO_VERSION_CHECK, true);
	}

	public void onDisable() {
		
	}
}
