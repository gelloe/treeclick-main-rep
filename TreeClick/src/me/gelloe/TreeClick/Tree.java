package me.gelloe.TreeClick;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.gelloe.TreeClick.Utils.ConH;
import me.gelloe.TreeClick.Utils.SoundUtil;
import me.gelloe.TreeClick.Utils.Util;

public class Tree {

	private Player p;
	private ItemStack playerAxe;

	private Material logtype;

	private HashSet<Block> treeBody = new HashSet<Block>();
	private HashSet<Block> leaves = new HashSet<Block>();
	private HashSet<Block> stem = new HashSet<Block>();
	private HashSet<Block> remaining = new HashSet<Block>();

	private HashSet<Location> base = new HashSet<Location>();

	public Tree(Block origin, Player p) {
		this.logtype = origin.getType();
		this.p = p;
		this.playerAxe = p.getInventory().getItemInMainHand();
		stem.add(origin);
		locateStem(origin);
		locateBase();
		locateLeaves();
		treeBody.addAll(leaves);
		treeBody.addAll(stem);
		locateAdjacent();
		treeBody.addAll(remaining);
	}

	private void locateStem(Block startingPoint) {
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				for (int dz = -1; dz <= 1; dz++) {
					Block nextBlock = startingPoint.getLocation().add(dx, dy, dz).getBlock();
					if (nextBlock.getType().equals(logtype) && !stem.contains(nextBlock)) {
						stem.add(nextBlock);
						locateStem(nextBlock);
					}
				}
			}
		}
	}

	private void locateBase() {
		int lowestY = 255;
		for (Block b : stem) {
			if (b.getY() < lowestY)
				lowestY = b.getY();
		}
		for (Block b : stem)
			if (b.getY() == lowestY)
				base.add(b.getLocation());
	}

	private void locateLeaves() {
		HashMap<Block, Integer> blockL = new HashMap<Block, Integer>();
		stem.forEach(b -> blockL.put(b, 0));
		for (int i = 1; i < ConH.leaf_r; i++) {
			HashMap<Block, Integer> nextBlockL = new HashMap<Block, Integer>();
			for (Block b : blockL.keySet()) {
				for (BlockFace bf : Util.allFaces) {
					Block nextBlock = b.getRelative(bf);
					if (!blockL.containsKey(nextBlock)
							&& nextBlock.getType().equals(Util.getEquivalentLeaves(logtype))) {
						nextBlockL.put(nextBlock, i);
					}
				}
			}
			blockL.putAll(nextBlockL);
		}
		blockL.keySet().forEach(b -> {
			if (b.getType().equals(Util.getEquivalentLeaves(logtype)))
				leaves.add(b);
		});
	}

	private void locateAdjacent() {
		for (Block b : treeBody) {
			for (BlockFace bf : Util.someFaces) {
				Block nextBlock = b.getRelative(bf);
				if (!leaves.contains(nextBlock) && !stem.contains(nextBlock)
						&& Util.FORBIDDEN_BLOCKS.contains(nextBlock.getType()))
					remaining.add(nextBlock);
			}
		}
	}

	public boolean isCuttable() {
		if (leaves.isEmpty())
			return false;
		if (!remaining.isEmpty())
			return false;
		if (p.getGameMode() == GameMode.CREATIVE && !ConH.creative)
			return false;
		return true;
	}

	public void timber() {
		if (!isCuttable())
			return;
		if (ConH.log_speed > 0) {
			int lowestY = 256;
			int highestY = 0;
			for (Block b : stem) {
				if (b.getY() < lowestY)
					lowestY = b.getY();
				if (b.getY() > highestY)
					highestY = b.getY();
			}
			cutLayer(0, lowestY, highestY);
		} else {
			stem.forEach(b -> Util.destroyBlock(p, playerAxe, b));
			cutLeaves();
		}
	}

	private void cutLayer(int layer, int lowestY, int highestY) {
		new BukkitRunnable() {
			@Override
			public void run() {
				for (Block b : stem) {
					if (b.getY() == lowestY + layer && playerAxe != null) {
						Util.destroyBlock(p, playerAxe, b);
					}
				}
				if (layer + lowestY < highestY) {
					cutLayer(layer + 1, lowestY, highestY);
				} else
					cutLeaves();
			}
		}.runTaskLater(Main.getPlugin(Main.class), ConH.log_speed);
	}

	private void cutLeaves() {
		if (ConH.leaf_speed > 0) {
			Random r = new Random();
			for (Block b : leaves) {
				new BukkitRunnable() {
					@Override
					public void run() {
						Util.destroyBlock(p, playerAxe, b);
					}
				}.runTaskLater(Main.getPlugin(Main.class), r.nextInt(leaves.size() / ConH.leaf_speed));
			}
			new BukkitRunnable() {
				@Override
				public void run() {
					plantSaplings();
				}
			}.runTaskLater(Main.getPlugin(Main.class), leaves.size() / ConH.leaf_speed);

		} else {
			leaves.forEach(b -> Util.destroyBlock(p, playerAxe, b));
			plantSaplings();
		}
	}

	private void plantSaplings() {
		for (Location l : base) {
			SoundUtil.breakTree(l.getBlock());
			break;
		}
		if (!ConH.auto)
			return;
		Material sapling = Util.getEquivalentSapling(logtype);
		for (Location l : base) {
			if (p.getInventory().contains(sapling)) {
				if (Util.isPlantableBlock(l.getBlock().getRelative(BlockFace.DOWN))) {
					l.getBlock().setType(sapling);
					p.getInventory().removeItem(new ItemStack(sapling, 1));
				}
			}
		}
	}

}
