package nl.knokko.worldgen.islands.nether;

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

import com.google.common.collect.Lists;

public class NetherIslandsGenerator extends ChunkGenerator {
	
	static final int BEDROCK_LEVEL = 4;
	static final int BOTTOM_LEVEL = 40;
	static final int SEA_LEVEL = 150;
	
	private static final int MAX_HILL_RADIUS = 40;
	private static final int HILL_REGION_SIZE = 40;
	
	
	public static final int MIN_ISLAND_HEIGHT = 50;
	public static final int MAX_ISLAND_HEIGHT = 140;
	
	public static final double MIN_ISLAND_FACTOR = 0.9;
	public static final double MAX_ISLAND_FACTOR = 1.1;
	
	public static final double MIN_ISLAND_DELAY = 0.008;
	public static final double MAX_ISLAND_DELAY = 0.012;
	
	public static final double MIN_ISLAND_POWER = 1.6;
	public static final double MAX_ISLAND_POWER = 1.8;
	
	private static final int MAX_ISLAND_RADIUS = (int) (Math.pow(MAX_ISLAND_HEIGHT / MIN_ISLAND_FACTOR, 1 / MIN_ISLAND_POWER) / MIN_ISLAND_DELAY);
	private static final int ISLAND_REGION_SIZE = 1600;
	
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
				int diff = height - SEA_LEVEL;
				if(diff > 10){
					chunk.setRegion(x, BEDROCK_LEVEL, z, x + 1, height, z + 1, Material.NETHERRACK);
				}
				else {
					chunk.setRegion(x, BEDROCK_LEVEL, z, x + 1, height - 7, z + 1, Material.NETHERRACK);
					chunk.setRegion(x, height - 7, z, x + 1, height, z + 1, Material.SOUL_SAND);
					if(diff < 0)
						chunk.setRegion(x, height, z, x + 1, SEA_LEVEL, z + 1, Material.LAVA);
				}
				biome.setBiome(x, z, Biome.HELL);
			}
		}
		return chunk;
	}
	
	@Override
	public List<BlockPopulator> getDefaultPopulators(World world){
		return Lists.<BlockPopulator>newArrayList(new NetherIslandsQuartzPopulator());
	}
	
	private static class HillFactory extends HeightModFactory {

		@Override
		public void addHeightModifiers(Collection<HeightModifier> mods, int regionX, int regionZ, long seed) {
			Random random = WorldGenHelper.createRandom(regionX, regionZ, seed);
			int amount = random.nextInt(4) + 3;
			for(int i = 0; i < amount; i++)
				mods.add(new SmoothHill(regionX * HILL_REGION_SIZE + random.nextInt(HILL_REGION_SIZE), regionZ * HILL_REGION_SIZE + random.nextInt(HILL_REGION_SIZE), 5 + random.nextInt(4), 0.3 + 0.05 * random.nextDouble(), 0.9 + 0.2 * random.nextDouble()));
		}
	}
	
	private static class IslandFactory extends HeightModFactory {

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
