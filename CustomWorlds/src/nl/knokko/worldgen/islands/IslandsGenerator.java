package nl.knokko.worldgen.islands;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import nl.knokko.worldgen.WorldGenHelper;
import nl.knokko.worldgen.heightmod.HeightModFactory;
import nl.knokko.worldgen.heightmod.HeightModifier;
import nl.knokko.worldgen.heightmod.AdvancedSmoothHill;
import nl.knokko.worldgen.heightmod.SmoothHill;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

public class IslandsGenerator extends ChunkGenerator {
	
	public static final int BEDROCK_LEVEL = 4;
	public static final int BOTTOM_LEVEL = 40;
	public static final int SEA_LEVEL = 150;
	
	public static final int MIN_HILL_HEIGHT = 5;
	public static final int MAX_HILL_HEIGHT = 8;
	
	public static final double MIN_HILL_FACTOR = 0.30;
	public static final double MAX_HILL_FACTOR = 0.35;
	
	public static final double MIN_HILL_POWER = 0.9;
	public static final double MAX_HILL_POWER = 1.1;
	
	public static final int MAX_HILL_RADIUS = (int) Math.round(Math.ceil(Math.pow(MAX_HILL_HEIGHT / MIN_HILL_FACTOR, 1 / MIN_HILL_POWER)));
	public static final int HILL_REGION_SIZE = 40;
	
	
	public static final int MIN_ISLAND_HEIGHT = 50;
	public static final int MAX_ISLAND_HEIGHT = 140;
	
	public static final double MIN_ISLAND_FACTOR = 0.9;
	public static final double MAX_ISLAND_FACTOR = 1.1;
	
	public static final double MIN_ISLAND_DELAY = 0.008;
	public static final double MAX_ISLAND_DELAY = 0.012;
	
	public static final double MIN_ISLAND_POWER = 1.6;
	public static final double MAX_ISLAND_POWER = 1.8;
	
	/*
	 * distance = ((height / multiplier) ^ (1 / power)) / delay
	 * 
	 * see SmoothHill.getExtraHeight
	 * 
	 * exponent = 1 / power;
	 * 
	 * power is between 1 and 2, so 
	 * exponent is max for power = MIN_ISLAND_POWER
	 * exponent is min for power = MAX_ISLAND_POWER
	 * 
	 * base = height / factor, so
	 * base is max for height = MAX_ISLAND_HEIGHT and factor = MIN_ISLAND_FACTOR
	 * base is min for height = MIN_ISLAND_HEIGHT and factor = MAX_ISLAND_FACTOR
	 * 
	 * base should be larger than 1, so result is max for exponent is max and base is max
	 */
	public static final int MAX_ISLAND_RADIUS = (int) Math.round(Math.ceil(Math.pow(MAX_ISLAND_HEIGHT / MIN_ISLAND_FACTOR, 1 / MIN_ISLAND_POWER) / MIN_ISLAND_DELAY));
	public static final int ISLAND_REGION_SIZE = 1600;
	
	@Override
	public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome){
		ChunkData chunk = createChunkData(world);
		chunk.setRegion(0, 0, 0, 16, BEDROCK_LEVEL, 16, Material.BEDROCK);
		Collection<HeightModifier> hm = WorldGenHelper.getHeightModifiers(new HillFactory(), MAX_HILL_RADIUS, HILL_REGION_SIZE, chunkX, chunkZ, world.getSeed());
		hm.addAll(WorldGenHelper.getHeightModifiers(new IslandFactory(), MAX_ISLAND_RADIUS, ISLAND_REGION_SIZE, chunkX, chunkZ, world.getSeed()));
		int[] heights = WorldGenHelper.getHeights(chunkX, chunkZ, 50, BOTTOM_LEVEL, 250, hm);
		int i = 0;
		for(int x = 0; x < 16; x++){
			for(int z = 0; z < 16; z++){
				int height = heights[i++];
				chunk.setRegion(x, BEDROCK_LEVEL, z, x + 1, height - 10, z + 1, Material.SANDSTONE);
				chunk.setRegion(x, height - 10, z, x + 1, height, z + 1, Material.SAND);
				int diff = height - SEA_LEVEL;
				if(diff < 0)
					chunk.setRegion(x, height, z, x + 1, SEA_LEVEL, z + 1, Material.WATER);
				if(diff > 10)
					biome.setBiome(x, z, Biome.DESERT);
				else if(diff >= 0)
					biome.setBiome(x, z, Biome.BEACHES);
				else if(diff >= -40)
					biome.setBiome(x, z, Biome.OCEAN);
				else
					biome.setBiome(x, z, Biome.DEEP_OCEAN);
			}
		}
		return chunk;
	}
	
	@Override
	public List<BlockPopulator> getDefaultPopulators(World world) {
	    return Arrays.asList((BlockPopulator)new PalmTreePopulator());
	}
	
	public static class HillFactory extends HeightModFactory {

		@Override
		public void addHeightModifiers(Collection<HeightModifier> mods, int regionX, int regionZ, long seed) {
			Random random = WorldGenHelper.createRandom(regionX, regionZ, seed);
			int amount = random.nextInt(4) + 3;
			for(int i = 0; i < amount; i++)
				mods.add(new SmoothHill(regionX * HILL_REGION_SIZE + random.nextInt(HILL_REGION_SIZE), regionZ * HILL_REGION_SIZE + random.nextInt(HILL_REGION_SIZE), MIN_HILL_HEIGHT + random.nextInt(1 + MAX_HILL_HEIGHT - MIN_HILL_HEIGHT), MIN_HILL_FACTOR + (MAX_HILL_FACTOR - MIN_HILL_FACTOR) * random.nextDouble(), MIN_HILL_POWER + (MAX_HILL_POWER - MIN_HILL_POWER) * random.nextDouble()));
		}
	}
	
	public static class IslandFactory extends HeightModFactory {

		@Override
		public void addHeightModifiers(Collection<HeightModifier> mods, int regionX, int regionZ, long seed) {
			Random random = WorldGenHelper.createRandom(regionX, regionZ, seed);
			int x = regionX * ISLAND_REGION_SIZE + random.nextInt(ISLAND_REGION_SIZE);
			int z = regionZ * ISLAND_REGION_SIZE + random.nextInt(ISLAND_REGION_SIZE);
			if(random.nextInt(3) == 0)
				mods.add(new AdvancedSmoothHill(x, z, MIN_ISLAND_HEIGHT + random.nextInt(1 + MAX_ISLAND_HEIGHT - MIN_ISLAND_HEIGHT), MIN_ISLAND_FACTOR + (MAX_ISLAND_FACTOR - MIN_ISLAND_FACTOR) * random.nextDouble(), MIN_ISLAND_DELAY + (MAX_ISLAND_DELAY - MIN_ISLAND_DELAY) * random.nextDouble(), MIN_ISLAND_POWER + (MAX_ISLAND_POWER - MIN_ISLAND_POWER) * random.nextDouble()));
		}
	}
}
