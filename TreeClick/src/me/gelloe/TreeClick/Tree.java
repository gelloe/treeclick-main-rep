package me.gelloe.TreeClick;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.gelloe.TreeClick.Utils.SoundUtil;
import me.gelloe.TreeClick.Utils.Util;

public class Tree {

	private int xOrigin, zOrigin;
	public Material logType;
	private final Material leafType;
	private ItemStack theAxe;
	List<Block> stem = new ArrayList<Block>();
	List<Block> floatingLogs = new ArrayList<Block>();
	List<Block> leaves = new ArrayList<Block>();
	List<Block> deadLeaves = new ArrayList<Block>();
	List<Block> treeBody = new ArrayList<Block>();
	private Plugin plugin = Main.getPlugin(Main.class);
	private FileConfiguration config = this.plugin.getConfig();
	private boolean dropItems = this.config.getBoolean("drop-items");
	private int log_break_speed = this.config.getInt("log-break-speed");
	private int leaves_break_speed = this.config.getInt("leaves-break-speed");
	private boolean auto_plant = this.config.getBoolean("auto-replant-saplings");
	private boolean isBeingBroken = false;

	public Tree(Block b, Material logType, ItemStack theAxe) {
		this.xOrigin = b.getX();
		this.zOrigin = b.getZ();
		this.logType = logType;
		this.leafType = Util.getEquivalentLeaves(logType);
		this.theAxe = theAxe;
		iterateStem(b);
		iterateLeaves();
		treeBody.addAll(leaves);
		treeBody.addAll(stem);
		treeBody.addAll(floatingLogs);
	}

	public void iterateLeaves(Block b) {
		if (leaves.contains(b))
			return;
		if (deadLeaves.contains(b))
			return;
		if (b.getType() == leafType) {
			boolean doesPass = false;
			for (int i = 0; i < stem.size(); i++) {
				if (b.getLocation().distance(stem.get(i).getLocation()) <= 5)
					doesPass = true;
			}
			if (doesPass)
				leaves.add(b);
			else
				deadLeaves.add(b);
		}
		
		
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				for (int k = -1; k <= 1; k++) {
					Block nextBlock = b.getWorld().getBlockAt(b.getX() + i, b.getY() + j, b.getZ() + k);

					if (nextBlock.getType() != Material.AIR) {
						if (withinBounds(nextBlock.getLocation())) {
							if (nextBlock.getType() == leafType)
								iterateLeaves(nextBlock);
							else if (nextBlock.getType() == logType) {
								if (!(floatingLogs.contains(nextBlock) || stem.contains(nextBlock)))
									iterateFloatingLog(nextBlock);
							} else if (equalsForbiddenBlock(nextBlock) && !treeBody.contains(nextBlock))
								treeBody.add(nextBlock);
						}
					}
				}
			}
		}
	}

	public void iterateFloatingLog(Block b) {
		for (int i = -1; i <= 1; i++) {
			for (int j = 0; j <= 1; j++) {
				for (int k = -1; k <= 1; k++) {
					if (i == 0 && j == 0 && k == 0)
						continue;
					Block nextBlock = b.getWorld().getBlockAt(b.getX() + i, b.getY() + j, b.getZ() + k);
					if (stem.contains(nextBlock) || floatingLogs.contains(nextBlock))
						continue;
					if (nextBlock.getType() == logType) {
						floatingLogs.add(nextBlock);
						iterateFloatingLog(nextBlock);
					}
				}
			}
		}
	}

	public Block getStemBase() {
		if (stem.isEmpty())
			return null;
		else {
			Block b0 = stem.get(0);
			for (Block b1 : stem) {
				if (b1.getY() < b0.getY())
					b0 = b1;
			}
			return b0;
		}
	}

	public int getLayerCount() {
		Block highestBlock = stem.get(0);
		Block lowestBlock = stem.get(stem.size() - 1);
		for (Block b : stem) {
			if (b.getY() < lowestBlock.getY())
				lowestBlock = b;
			else if (b.getY() > highestBlock.getY())
				highestBlock = b;
		}
		return highestBlock.getY() - lowestBlock.getY() + 1;
	}

	public void iterateLeaves() {
		for (int i = 0; i < stem.size(); i++)
			iterateLeaves(stem.get(i));
	}

	public void iterateStem(Block b) {
		if (stem.contains(b))
			return;
		stem.add(b);

		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				for (int k = -1; k <= 1; k++) {
					Location loc = b.getLocation();
					loc.add(i, j, k);
					Block nextBlock = loc.getBlock();
					if (nextBlock.getType() == logType)
						iterateStem(nextBlock);
					else if (equalsForbiddenBlock(nextBlock) && !treeBody.contains(nextBlock))
						treeBody.add(nextBlock);
				}
			}
		}
	}

	public boolean hasLeaves() {
		for (int i = 0; i < treeBody.size(); i++) {
			if (Util.isLeaf(treeBody.get(i))) {
				return true;
			}
		}
		return false;
	}

	public boolean containsForbiddenBlocks() {
		for (int i = 0; i < treeBody.size(); i++) {
			for (int j = 0; j < Util.FORBIDDEN_BLOCKS.length; j++) {
				if (treeBody.get(i).getType() == Util.FORBIDDEN_BLOCKS[j])
					return true;
			}
		}
		return false;
	}

	public boolean equalsForbiddenBlock(Block b) {
		for (int j = 0; j < Util.FORBIDDEN_BLOCKS.length; j++) {
			if (b.getType() == Util.FORBIDDEN_BLOCKS[j])
				return true;
		}
		return false;
	}

	private boolean withinBounds(Location l) {
		if (Math.abs(l.getX() - xOrigin) <= 16 && Math.abs(l.getZ() - zOrigin) < 16)
			return true;
		return false;
	}

	public void breakSlowly(Player p) {
		if (isBeingBroken)
			return;
		isBeingBroken = true;
		breakStem(getStemBase().getY(), p);
	}

	public void breakStem(int i, Player p) {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (log_break_speed == 0) {
					for (Block b : stem)
						Util.destroyBlock(p, theAxe, dropItems, b, false);
					breakLeaves(p, 0);
					plantSapling();
					return;
				} else {
					ArrayList<Block> blocks = new ArrayList<Block>();
					for (Block b : stem) {
						if (b.getY() == i)
							blocks.add(b);
					}
					if (blocks.isEmpty()) {
						breakLeaves(p, 0);
						plantSapling();
						return;
					}
					for (Block b : blocks)
						Util.destroyBlock(p, theAxe, dropItems, b, false);
					SoundUtil.breakLog(blocks.get(0));
					breakStem(i + 1, p);
				}
			}
		}.runTaskLater(Main.getPlugin(Main.class), log_break_speed);
	}

	public void breakLeaves(Player p, long delay) {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (leaves_break_speed == 0) {
					for (Block b : leaves)
						Util.destroyBlock(p, theAxe, dropItems, b, false);
					SoundUtil.breakTree(getStemBase());
				} else {
					for (int i = 0; i < leaves_break_speed; i++) {
						if (leaves.isEmpty()) {
							SoundUtil.breakTree(getStemBase());
							return;
						}
						final int blockBroken = new Random().nextInt(leaves.size());
						SoundUtil.breakLeaves(leaves.get(blockBroken));
						Util.destroyBlock(p, theAxe, dropItems, leaves.get(blockBroken), false);
						leaves.remove(blockBroken);
					}
					breakLeaves(p, 1);
				}
			}
		}.runTaskLater(Main.getPlugin(Main.class), delay);
	}

	public void plantSapling() {
		if (!auto_plant)
			return;
		for (Block b : stem) {
			if (b.getY() == getStemBase().getY()) {
				Material blockUnder = b.getWorld().getBlockAt(b.getX(), b.getY() - 1, b.getZ()).getType();
				if (blockUnder == Material.DIRT || blockUnder == Material.GRASS_BLOCK || blockUnder == Material.PODZOL)
					b.setType(Util.getEquivalentSapling(logType));
			}
		}
	}
}
