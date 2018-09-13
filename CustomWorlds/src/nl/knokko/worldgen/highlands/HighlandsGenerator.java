package nl.knokko.worldgen.highlands;

import java.util.Collection;
import java.util.Random;

import nl.knokko.worldgen.WorldGenHelper;
import nl.knokko.worldgen.blockmod.BlockModFactory;
import nl.knokko.worldgen.blockmod.BlockModification;
import nl.knokko.worldgen.blockmod.BlockModificationOres;
import nl.knokko.worldgen.heightmod.HeightModFactory;
import nl.knokko.worldgen.heightmod.HeightModifier;
import nl.knokko.worldgen.heightmod.SmoothHill;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;

public class HighlandsGenerator extends ChunkGenerator {
	
	private static final int BASE_HEIGHT = 50;
	private static final int MIN_HEIGHT = 20;
	private static final int MAX_HEIGHT = 230;
	
	private static final int HILL_REGION_SIZE = 100;
	private static final int BLOCK_REGION_SIZE = 16;
	
	private static final int MAX_HILL_RADIUS = 200;
	private static final int MAX_BLOCK_RADIUS = 10;
	
	@Override
	public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome){
		ChunkData chunk = createChunkData(world);
		Collection<HeightModifier> hm = WorldGenHelper.getHeightModifiers(new HeightFactory(), MAX_HILL_RADIUS, HILL_REGION_SIZE, chunkX, chunkZ, world.getSeed());//I'm considering reflection...
		WorldGenHelper.setHeights(chunk, Material.STONE, chunkX, chunkZ, BASE_HEIGHT, MIN_HEIGHT, MAX_HEIGHT, hm);
		Collection<BlockModification> bms = WorldGenHelper.getBlockModifications(new BlockFactory(), MAX_BLOCK_RADIUS, BLOCK_REGION_SIZE, chunkX, chunkZ, world.getSeed());
		for(int x = 0; x < 16; x++)
			for(int z = 0; z < 16; z++)
				biome.setBiome(x, z, Biome.EXTREME_HILLS);
		WorldGenHelper.setBlocks(chunk, Material.STONE, chunkX, chunkZ, bms);
		return chunk;
	}
		
	private static class HeightFactory extends HeightModFactory {

		public void addHeightModifiers(Collection<HeightModifier> mods, int regionX, int regionZ, long seed) {
			Random random = WorldGenHelper.createRandom(regionX, regionZ, seed);
			for(int i = 0; i < 30; i++)
				mods.add(new SmoothHill(regionX * HILL_REGION_SIZE + random.nextInt(HILL_REGION_SIZE), regionZ * HILL_REGION_SIZE + random.nextInt(HILL_REGION_SIZE), 10 + random.nextInt(10), 1d + 0.4 * random.nextDouble(), 1d + 0.2 * random.nextDouble()));
			for(int i = 0; i < 5; i++)
				mods.add(new SmoothHill(regionX * HILL_REGION_SIZE + random.nextInt(HILL_REGION_SIZE), regionZ * HILL_REGION_SIZE + random.nextInt(HILL_REGION_SIZE), 10 + random.nextInt(10), 0.7 + 0.4 * random.nextDouble(), 0.6 + 0.2 * random.nextDouble()));
		}
	}
	
	private static class BlockFactory extends BlockModFactory {
		
		@Override
		public void addBlockModifications(Collection<BlockModification> mods, int regionX, int regionZ, long seed){
			Random random = WorldGenHelper.createRandom(regionX, regionZ, seed);
			mods.add(new BlockModificationOres(random, Material.MONSTER_EGGS, 30, 15, 10, 150, regionX, regionZ));
			mods.add(new BlockModificationOres(random, Material.IRON_ORE, 20, 15, 10, 100, regionX, regionZ));
			mods.add(new BlockModificationOres(random, Material.GOLD_ORE, 12, 5, 10, 100, regionX, regionZ));
			mods.add(new BlockModificationOres(random, Material.DIAMOND_ORE, 8, 5, 10, 100, regionX, regionZ));
			mods.add(new BlockModificationOres(random, Material.EMERALD_ORE, 8, 2, 10, 50, regionX, regionZ));
			mods.add(new BlockModificationOres(random, Material.LAPIS_ORE, 8, 2, 10, 50, regionX, regionZ));
			mods.add(new BlockModificationOres(random, Material.REDSTONE_ORE, 8, 2, 10, 50, regionX, regionZ));
		}
	}
}
