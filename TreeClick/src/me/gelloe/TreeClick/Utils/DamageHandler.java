package me.gelloe.TreeClick.Utils;

import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import me.gelloe.TreeClick.Main;

public class DamageHandler {
	
	
	private static Plugin plugin = Main.getPlugin(Main.class);
	private static FileConfiguration config = plugin.getConfig();
	private static boolean axe = config.getBoolean("damage-axe-accordingly");

	public static boolean isBroken(ItemStack i) {
		return ((Damageable) i.getItemMeta()).getDamage() >= i.getType().getMaxDurability();
	}

	public static void damage(ItemStack i, Player p, boolean override) {
		if (!axe)
			if (!override)
				return;
		if (p.getGameMode() == GameMode.CREATIVE)
			return;
		if (i.containsEnchantment(Enchantment.DURABILITY)) {
			damageUnbreaking(i, p);
			return;
		}
		Damageable d = (Damageable) i.getItemMeta();
		d.setDamage(d.getDamage() + 1);
		if (d.getDamage() >= i.getType().getMaxDurability())
			breakItem(i, p);
		i.setItemMeta((ItemMeta) d);
	}

	private static void damageUnbreaking(ItemStack i, Player p) {
		double unbreaking = 100 / (1 + i.getEnchantmentLevel(Enchantment.DURABILITY));
		double chance = new Random().nextDouble() * 100;
		Damageable d = (Damageable) i.getItemMeta();
		if (unbreaking > chance)
			d.setDamage(d.getDamage() + 1);
		if (d.getDamage() >= i.getType().getMaxDurability())
			breakItem(i, p);
		i.setItemMeta((ItemMeta) d);
	}

	public static void breakItem(ItemStack i, Player p) {
		i.setAmount(0);
		p.getServer().getPluginManager().callEvent(new PlayerItemBreakEvent(p, i));
	}

}
