package nl.knokko.worldgen.eventhandler;

import org.bukkit.Material;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;

public abstract class NetherEventHandler extends WorldEventHandler {
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onEntitySpawn(CreatureSpawnEvent event){
		if(active(event.getEntity().getWorld())){
			LivingEntity e = event.getEntity();
			if(e instanceof PigZombie)
				event.setCancelled(pigzombie((PigZombie) e));
			else if(e instanceof Ghast)
				event.setCancelled(ghast((Ghast) e));
			else if(e instanceof MagmaCube)
				event.setCancelled(magmacube((MagmaCube) e));
			else if(e instanceof WitherSkeleton)
				event.setCancelled(witherskeleton((WitherSkeleton) e));
			else if(e instanceof Skeleton)
				event.setCancelled(skeleton((Skeleton) e));
			else if(e instanceof Blaze)
				event.setCancelled(blaze((Blaze) e));
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event){
		if(active(event.getBlock().getWorld())){
			Material type = event.getBlock().getType();
			if(type == Material.QUARTZ_ORE)
				quartzBreak(event);
		}
	}
	
	protected abstract void quartzBreak(BlockBreakEvent event);
	
	protected abstract boolean pigzombie(PigZombie pig);
	
	protected abstract boolean ghast(Ghast ghast);
	
	protected abstract boolean magmacube(MagmaCube cube);
	
	protected abstract boolean witherskeleton(WitherSkeleton skeleton);
	
	protected abstract boolean skeleton(Skeleton skeleton);
	
	protected abstract boolean blaze(Blaze blaze);
}
