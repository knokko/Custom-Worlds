package nl.knokko.worldgen.islands;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
//import org.bukkit.TreeSpecies;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;
//import org.bukkit.material.Leaves;
//import org.bukkit.material.MaterialData;
//import org.bukkit.material.Tree;

public class PalmTreePopulator extends BlockPopulator {
	
	//private static final MaterialData JUNGLE_LOG = new Tree(TreeSpecies.JUNGLE);
	//private static final MaterialData JUNGLE_LEAVES = new Leaves(TreeSpecies.JUNGLE);
	
	@SuppressWarnings("deprecation")
	public static void spawnPalmTree(Location location, Random random){
		int startX = location.getBlockX();
		int y = location.getBlockY();
		int startZ = location.getBlockZ();
		World world = location.getWorld();
		int height = 13 + random.nextInt(7);
		int dx = random.nextInt(7) - 3;
		int dz = random.nextInt(7) - 3;
		int x = startX;
		int z = startZ;
		for(int l = 0; l < height; l++){
			float part = (float) l / height;
			x = Math.round(startX + dx * part * part);
			z = Math.round(startZ + dz * part * part);
			Block block = world.getBlockAt(x, y, z);
			block.setType(Material.LOG);
			block.setData((byte) 3);//the deprecated method works and the new method does not...
			//block.getState().setData(JUNGLE_LOG);
			//block.getState().update(true);
			y++;
		}
		startX += dx;
		startZ += dz;
		int startY = y;
		int dist = 3;
		for(x = -dist; x <= dist; x++){
			for(z = -dist; z <= dist; z++){
				for(y = 0; y <= dist; y++){
					if(Math.abs(x) + Math.abs(y) + Math.abs(z) <= dist){
						setToLeaves(world, startX + x, startY + y, startZ + z);
					}
				}
			}
		}
	}
	
	/*
	private static void leavesPX(World world, int topX, int topY, int z, int destX, int destY){
		int prevY = topY;
		int dx = destX - topX;
		int dy = destY - topY;
		for(int x = topX; x <= destX; x++){
			float part = (float)(x - topX) / dx;
			int y = Math.round(topY + part * dy);
			setToLeaves(world, x, y, z);
			if(y != prevY)
				setToLeaves(world, x, prevY, z);
			prevY = y;
		}
	}
	*/
	
	@SuppressWarnings("deprecation")
	private static void setToLeaves(World world, int x, int y, int z){
		Block block = world.getBlockAt(x, y, z);
		block.setType(Material.LEAVES);
		block.setData((byte) 3);
		//block.getState().setData(JUNGLE_LEAVES);
		//block.getState().update(true);
	}

	@Override
	public void populate(World world, Random random, Chunk chunk) {
		if(random.nextBoolean()){
			int x = random.nextInt(16);
			int z = random.nextInt(16);
			int y = -1;
			for(int Y = world.getMaxHeight() - 1; Y >= IslandsGenerator.SEA_LEVEL; Y--){
				if(chunk.getBlock(x, Y, z).getType() == Material.SAND){
					y = Y + 1;
					break;
				}
			}
			if(y != -1)
				spawnPalmTree(chunk.getBlock(x, y, z).getLocation(), random);
		}
	}
}