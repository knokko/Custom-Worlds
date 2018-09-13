package nl.knokko.worldgen.eventhandler;

import nl.knokko.worldgen.CustomItems;

import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public abstract class WorldEventHandler implements Listener {
	
	private final String name;

	public WorldEventHandler() {
		name = worldName();
	}
	
	public boolean active(World world){
		return world.getName().equals(name);
	}
	
	protected void setDrops(BlockBreakEvent event, String item){
		event.setDropItems(false);
		event.getPlayer().getInventory().addItem(CustomItems.get(item));
	}
	
	protected void process(BlockBreakEvent event, String item){
		if(item != null)
			setDrops(event, item);
	}
	
	protected abstract String worldName();
}
