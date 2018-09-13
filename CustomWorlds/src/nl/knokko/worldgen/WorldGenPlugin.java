package nl.knokko.worldgen;

import java.io.File;
import java.util.Random;

import nl.knokko.worldgen.command.CommandWorldGen;
import nl.knokko.worldgen.entity.task.EntityTaskManager;
import nl.knokko.worldgen.highlands.HighlandsEventHandler;
import nl.knokko.worldgen.highlands.HighlandsGenerator;
import nl.knokko.worldgen.islands.IslandsEventHandler;
import nl.knokko.worldgen.islands.IslandsGenerator;
import nl.knokko.worldgen.islands.nether.NetherIslandsEventHandler;
import nl.knokko.worldgen.islands.nether.NetherIslandsGenerator;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

public class WorldGenPlugin extends JavaPlugin {
	
	public static final String HIGHLANDS = "Highlands";
	public static final String ISLANDS = "Islands";
	public static final String NETHER_ISLANDS = "NetherIslands";
	
	private static final String TEST = "test";
	
	public static WorldGenPlugin getInstance(){
		return JavaPlugin.getPlugin(WorldGenPlugin.class);
	}
	
	private final EntityTaskManager entityTaskManager;

	public WorldGenPlugin() {
		entityTaskManager = new EntityTaskManager();
	}

	public WorldGenPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
		super(loader, description, dataFolder, file);
		entityTaskManager = new EntityTaskManager();
	}
	
	@Override
	public void onEnable(){
		super.onEnable();
		getCommand("kwg").setExecutor(new CommandWorldGen());
		Bukkit.getPluginManager().registerEvents(new IslandsEventHandler(), this);
		Bukkit.getPluginManager().registerEvents(new HighlandsEventHandler(), this);
		Bukkit.getPluginManager().registerEvents(new NetherIslandsEventHandler(), this);
		entityTaskManager.load();
		Bukkit.getPluginManager().registerEvents(entityTaskManager, this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, entityTaskManager, 0, 1);
	}
	
	@Override
	public void onDisable(){
		entityTaskManager.save();
		super.onDisable();
	}
	
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id){
		System.out.println("A ChunkGenerator with worldName '" + worldName + "' and id '" + id + "' is requested.");
		if(worldName.equals(HIGHLANDS))
			return new HighlandsGenerator();
		if(worldName.equals(ISLANDS))
			return new IslandsGenerator();
		if(worldName.equals(NETHER_ISLANDS))
			return new NetherIslandsGenerator();
		if(worldName.equals(TEST))
			return new MultiverseFoolGenerator();//apparently, this is required to work with multiverse
		return super.getDefaultWorldGenerator(worldName, id);
	}
	
	public EntityTaskManager getTaskManager(){
		return entityTaskManager;
	}
	
	private static class MultiverseFoolGenerator extends ChunkGenerator {
		
		@Override
		public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome){
			return createChunkData(world);
		}
	}
}
