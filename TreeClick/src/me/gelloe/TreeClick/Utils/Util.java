package me.gelloe.TreeClick.Utils;

import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Util {

	public static BlockFace[] allFaces = { BlockFace.NORTH, BlockFace.WEST, BlockFace.SOUTH, BlockFace.EAST,
			BlockFace.UP, BlockFace.DOWN };
	public static BlockFace[] someFaces = { BlockFace.NORTH, BlockFace.WEST, BlockFace.SOUTH, BlockFace.EAST,
			BlockFace.UP };
	public static HashSet<Material> FORBIDDEN_BLOCKS = new HashSet<Material>();
	public static HashSet<Material> AXES = new HashSet<Material>();
	public static HashMap<Material, Material> log_leaf = new HashMap<Material, Material>();
	public static HashMap<Material, Material> log_sapling = new HashMap<Material, Material>();
	public static String tag = ChatColor.translateAlternateColorCodes('$', "$6[$2Tree$aClick$6]$e ");

	public static void print(Object o) {
		Bukkit.getLogger().info(tag + o.toString());
	}

	public static boolean itemInHand(Player p, Material m) {
		return p.getInventory().getItemInMainHand().getType().equals(m);
	}

	public static boolean holdingAxe(Player p) {
		return AXES.contains(p.getInventory().getItemInMainHand().getType());
	}

	public static boolean isLog(Block b) {
		return log_leaf.containsKey(b.getType());
	}

	public static boolean isPlantableBlock(Block b) {
		return (b.getType().equals(Material.DIRT) || b.getType().equals(Material.PODZOL)
				|| b.getType().equals(Material.GRASS_BLOCK) || b.getType().equals(Material.FARMLAND));
	}

	public static boolean isLeaf(Block b) {
		return log_leaf.containsValue(b.getType());
	}

	public static Material getEquivalentLeaves(Material m) {
		return log_leaf.get(m);
	}

	public static void destroyBlock(Player playerWhoBroke, ItemStack itemInHand, Block blocktoBeBroken) {
		if (isLog(blocktoBeBroken)) {
			DamH.damage(itemInHand, playerWhoBroke);
			SoundUtil.breakLog(blocktoBeBroken);
		} else if (isLeaf(blocktoBeBroken)) {
			SoundUtil.breakLeaves(blocktoBeBroken);
		}
		if (playerWhoBroke.getGameMode().equals(GameMode.CREATIVE)) {
			blocktoBeBroken.setType(Material.AIR);
		}

		if (ConH.give_i_d) {
			for (ItemStack i : blocktoBeBroken.getDrops())
				giveItemDirectly(playerWhoBroke, i);
			blocktoBeBroken.setType(Material.AIR);
		} else {
			blocktoBeBroken.breakNaturally();
		}
	}

	public static Material getEquivalentSapling(Material m) {
		return log_sapling.get(m);
	}

	public static void giveItemDirectly(Player p, ItemStack i) {
		if (p.getInventory().firstEmpty() == -1)
			p.getWorld().dropItem(p.getLocation(), i);
		else
			p.getInventory().addItem(i);
	}
}
