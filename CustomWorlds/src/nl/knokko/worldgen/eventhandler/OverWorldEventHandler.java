package nl.knokko.worldgen.eventhandler;

import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;

public abstract class OverWorldEventHandler extends WorldEventHandler {
	
	private final String coal;
	private final String lapiz;
	private final String iron;
	private final String redstone;
	private final String gold;
	private final String diamond;
	private final String emerald;

	public OverWorldEventHandler() {
		coal = coal();
		lapiz = lapiz();
		iron = iron();
		redstone = redstone();
		gold = gold();
		diamond = diamond();
		emerald = emerald();
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onEntitySpawn(CreatureSpawnEvent event){
		if(active(event.getEntity().getWorld())){
			LivingEntity e = event.getEntity();
			if(e instanceof Zombie)
				event.setCancelled(zombie((Zombie) e));
			else if(e instanceof Skeleton)
				event.setCancelled(skeleton((Skeleton) e));
			else if(e instanceof Spider)
				event.setCancelled(spider((Spider) e));
			else if(e instanceof Creeper)
				event.setCancelled(creeper((Creeper) e));
			else if(e instanceof Witch)
				event.setCancelled(witch((Witch) e));
			else if(e instanceof Enderman)
				event.setCancelled(enderman((Enderman) e));
			else if(e instanceof Silverfish)
				event.setCancelled(silverfish((Silverfish) e));
			else if(e instanceof Slime)
				event.setCancelled(slime((Slime) e));
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event){
		if(active(event.getBlock().getWorld())){
			Material type = event.getBlock().getType();
			if(type == Material.COAL_ORE)
				process(event, coal);
			else if(type == Material.LAPIS_ORE)
				process(event, lapiz);
			else if(type == Material.IRON_ORE)
				process(event, iron);
			else if(type == Material.REDSTONE_ORE || type == Material.GLOWING_REDSTONE_ORE)
				process(event, redstone);
			else if(type == Material.GOLD_ORE)
				process(event, gold);
			else if(type == Material.DIAMOND_ORE)
				process(event, diamond);
			else if(type == Material.EMERALD_ORE)
				process(event, emerald);
		}
	}
	
	protected abstract boolean zombie(Zombie zombie);
	
	protected abstract boolean skeleton(Skeleton skeleton);
	
	protected abstract boolean spider(Spider spider);
	
	protected abstract boolean creeper(Creeper creeper);
	
	protected abstract boolean witch(Witch witch);
	
	protected abstract boolean enderman(Enderman enderman);
	
	protected abstract boolean slime(Slime slime);
	
	protected abstract boolean silverfish(Silverfish silverfish);
	
	protected abstract String coal();
	
	protected abstract String lapiz();
	
	protected abstract String iron();
	
	protected abstract String redstone();
	
	protected abstract String gold();
	
	protected abstract String diamond();
	
	protected abstract String emerald();
}
