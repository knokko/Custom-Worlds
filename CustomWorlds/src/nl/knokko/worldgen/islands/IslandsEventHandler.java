package nl.knokko.worldgen.islands;

import nl.knokko.worldgen.WorldGenPlugin;
import nl.knokko.worldgen.entity.task.TaskOceanZombie;
import nl.knokko.worldgen.eventhandler.OverWorldEventHandler;

import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Zombie;

public class IslandsEventHandler extends OverWorldEventHandler {

	public IslandsEventHandler() {}

	@Override
	protected String worldName() {
		return WorldGenPlugin.ISLANDS;
	}

	@Override
	protected boolean zombie(Zombie zombie) {
		/*
		Class<?> clas = ((CraftZombie) zombie).getHandle().getClass();
		if(clas == EntityZombie.class){
			Location l = zombie.getLocation();
			EntityHelper.spawn(new OceanZombie(l.getWorld()), new Location(l.getWorld(), l.getX(), l.getY(), l.getZ()));
			return true;
		}
		else
			System.out.println(clas);//the entity doesn't exist/is invisible on client side
		*/
		WorldGenPlugin.getInstance().getTaskManager().registerTask(new TaskOceanZombie(zombie));
		return false;
	}

	@Override
	protected boolean skeleton(Skeleton skeleton) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean spider(Spider spider) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean creeper(Creeper creeper) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean witch(Witch witch) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean enderman(Enderman enderman) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean slime(Slime slime) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean silverfish(Silverfish silverfish) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected String coal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String lapiz() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String iron() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String redstone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String gold() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String diamond() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String emerald() {
		// TODO Auto-generated method stub
		return null;
	}
}
