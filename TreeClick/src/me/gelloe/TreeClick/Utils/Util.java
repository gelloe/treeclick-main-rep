package me.gelloe.TreeClick.Utils;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;



public class Util {
	
	public static BlockFace[] blockFaceList = {BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.WEST, BlockFace.SOUTH, BlockFace.EAST};
	
	public static Material[] getForbiddenBlocks() {
		Material[] blocks = new Material[ConH.block_l.size()];
		for (int i = 0; i < blocks.length; i++)
			blocks[i] = Material.getMaterial(ConH.block_l.get(i).toUpperCase());
		return blocks;
	}

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
	
	public static void destroyBlock(Player p, ItemStack itemStack, boolean dropItems, Block b, boolean override) {
		boolean Creative = p.getGameMode() == GameMode.CREATIVE;
		if (isLog(b))
			DamH.damage(itemStack, p, override);
		if (Creative) {
			if (dropItems) {
				if (ConH.give_i_d) {
					for (ItemStack i : b.getDrops())
						giveItemDirectly(p, i);
					b.setType(Material.AIR);
					return;
				} else {
					b.breakNaturally();
				}
			} else {
				b.setType(Material.AIR);
			}
		} else {
			if (ConH.give_i_d) {
				for (ItemStack i : b.getDrops())
					giveItemDirectly(p, i);
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
	
	public static void giveItemDirectly(Player p, ItemStack i) {
		if (p.getInventory().firstEmpty() == -1)
			p.getWorld().dropItem(p.getLocation(), i);
		else
			p.getInventory().addItem(i);
	}
}
