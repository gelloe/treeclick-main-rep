package me.gelloe.TreeClick.Utils;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import me.gelloe.TreeClick.Main;



public class Util {
	
//	public static BlockFace[] blockFaceList = {BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.WEST, BlockFace.SOUTH, BlockFace.EAST};
	private static Plugin plugin = Main.getPlugin(Main.class);
	private static FileConfiguration config = plugin.getConfig();
	private static boolean give_item_drops_directly = config.getBoolean("give-item-drops-directly");
	
	public static Material[] FORBIDDEN_BLOCKS = { Material.CHEST, Material.WALL_TORCH, Material.REDSTONE_WALL_TORCH,
			Material.REDSTONE_TORCH, Material.OAK_SIGN, Material.BIRCH_SIGN, Material.SPRUCE_SIGN, Material.JUNGLE_SIGN,
			Material.ACACIA_SIGN, Material.DARK_OAK_SIGN, Material.COBBLESTONE, Material.STONE_BRICKS,
			Material.OAK_PLANKS, Material.BIRCH_PLANKS, Material.SPRUCE_PLANKS, Material.JUNGLE_PLANKS,
			Material.ACACIA_PLANKS, Material.DARK_OAK_PLANKS, Material.DARK_OAK_WALL_SIGN, Material.OAK_WALL_SIGN,
			Material.BIRCH_WALL_SIGN, Material.SPRUCE_WALL_SIGN, Material.JUNGLE_WALL_SIGN, Material.ACACIA_WALL_SIGN,
			Material.GLOWSTONE, Material.REDSTONE_LAMP, Material.DAYLIGHT_DETECTOR, Material.LADDER, Material.FURNACE,
			Material.BLAST_FURNACE};

	public static boolean itemInHand(Player p, Material m) {
		if (p.getInventory().getItemInMainHand().getType() == m)
			return true;
		return false;
	}

	public static boolean holdingAxe(Player p) {
		if (itemInHand(p, Material.WOODEN_AXE) || itemInHand(p, Material.STONE_AXE)
				|| itemInHand(p, Material.GOLDEN_AXE) || itemInHand(p, Material.IRON_AXE)
				|| itemInHand(p, Material.DIAMOND_AXE))
			return true;
		return false;
	}

	public static boolean isLog(Block b) {
		if (b.getType() == Material.BIRCH_LOG || b.getType() == Material.DARK_OAK_LOG || b.getType() == Material.OAK_LOG
				|| b.getType() == Material.ACACIA_LOG || b.getType() == Material.SPRUCE_LOG
				|| b.getType() == Material.JUNGLE_LOG)
			return true;
		return false;
	}

	public static boolean isLeaf(Block b) {
		if (b.getType() == Material.BIRCH_LEAVES || b.getType() == Material.DARK_OAK_LEAVES
				|| b.getType() == Material.OAK_LEAVES || b.getType() == Material.ACACIA_LEAVES
				|| b.getType() == Material.SPRUCE_LEAVES || b.getType() == Material.JUNGLE_LEAVES)
			return true;
		return false;
	}

	public static Material getEquivalentLeaves(Material m) {

		if (m == Material.AIR || m == null)
			return null;

		if (m == Material.OAK_LOG)
			return Material.OAK_LEAVES;
		if (m == Material.BIRCH_LOG)
			return Material.BIRCH_LEAVES;
		if (m == Material.SPRUCE_LOG)
			return Material.SPRUCE_LEAVES;
		if (m == Material.JUNGLE_LOG)
			return Material.JUNGLE_LEAVES;
		if (m == Material.DARK_OAK_LOG)
			return Material.DARK_OAK_LEAVES;
		if (m == Material.ACACIA_LOG)
			return Material.ACACIA_LEAVES;

		return null;
	}
	
	public static void destroyBlock(Player p, boolean dropItems, Block b, boolean override) {
		boolean Creative = p.getGameMode() == GameMode.CREATIVE;
		if (isLog(b)) {
			DamageHandler.damage(p.getInventory().getItemInMainHand(), p, override);
		}
		if (Creative) {
			if (dropItems) {
				if (give_item_drops_directly) {
					for (ItemStack i : b.getDrops())
						p.getInventory().addItem(i);
					
					b.setType(Material.AIR);
					return;
				} else {
					b.breakNaturally();
				}
			} else {
				b.setType(Material.AIR);
			}
		} else {
			if (give_item_drops_directly) {
				for (ItemStack i : b.getDrops())
					p.getInventory().addItem(i);
				
				b.setType(Material.AIR);
				return;
			} else {
				b.breakNaturally();
			}
		}
	}

	public static Material getEquivalentSapling(Material m) {

		if (m == Material.AIR)
			return null;

		if (m == Material.OAK_LOG)
			return Material.OAK_SAPLING;
		if (m == Material.BIRCH_LOG)
			return Material.BIRCH_SAPLING;
		if (m == Material.SPRUCE_LOG)
			return Material.SPRUCE_SAPLING;
		if (m == Material.JUNGLE_LOG)
			return Material.JUNGLE_SAPLING;
		if (m == Material.DARK_OAK_LOG)
			return Material.DARK_OAK_SAPLING;
		if (m == Material.ACACIA_LOG)
			return Material.ACACIA_SAPLING;

		return null;
	}
}
