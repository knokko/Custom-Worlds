package nl.knokko.worldgen.islands.nether;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

public class NetherIslandsQuartzPopulator extends BlockPopulator {

	@Override
	public void populate(World world, Random random, Chunk chunk) {
		int amount = 25 + random.nextInt(12);
		for(int i = 0; i < amount; i++){
			int x = random.nextInt(16);
			int y = random.nextInt(90) + NetherIslandsGenerator.BEDROCK_LEVEL;
			int z = random.nextInt(15);
			quartz(chunk, x, y, z);
			quartz(chunk, x + 1, y, z);
			quartz(chunk, x, y, z + 1);
			quartz(chunk, x + 1, y, z + 1);
			
			quartz(chunk, x, y + 1, z);
			quartz(chunk, x + 1, y + 1, z);
			quartz(chunk, x, y + 1, z + 1);
			quartz(chunk, x + 1, y + 1, z + 1);
			//TODO find those ores
		}
	}
	
	private void quartz(Chunk chunk, int x, int y, int z){
		Block block = chunk.getBlock(x, y, z);
		if(block.getType() == Material.NETHERRACK)
			block.setType(Material.QUARTZ_ORE);
	}
}
