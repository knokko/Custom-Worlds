package nl.knokko.worldgen.highlands;

import nl.knokko.worldgen.CustomItems;
import nl.knokko.worldgen.WorldGenPlugin;
import nl.knokko.worldgen.eventhandler.OverWorldEventHandler;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.Material.*;
import static nl.knokko.worldgen.entity.EntityHelper.setAttributes;
import static nl.knokko.worldgen.entity.EntityHelper.setEquipment;

public class HighlandsEventHandler extends OverWorldEventHandler {
	
	private static final ItemStack SKELETON_BOW = new ItemStack(BOW);
	
	static {
		SKELETON_BOW.addEnchantment(Enchantment.ARROW_DAMAGE, 3);
		SKELETON_BOW.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onInteract(PlayerInteractEvent event){
		World world = event.getPlayer().getWorld();
		if(event.getAction() == Action.LEFT_CLICK_BLOCK && world.getName().equals(WorldGenPlugin.HIGHLANDS)){
			if(event.getClickedBlock().getType() == Material.MONSTER_EGGS){
				int midX = event.getClickedBlock().getX();
				int midY = event.getClickedBlock().getY();
				int midZ = event.getClickedBlock().getZ();
				breakEgg(world, midX, midY, midZ);
			}
		}
	}
	
	private void breakEgg(World world, int blockX, int blockY, int blockZ){
		int minX = blockX - 3;
		int minY = blockY - 3;
		int minZ = blockZ - 3;
		int maxX = blockX + 3;
		int maxY = blockY + 3;
		int maxZ = blockZ + 3;
		for(int x = minX; x <= maxX; x++){
			for(int y = minY; y <= maxY; y++){
				for(int z = minZ; z <= maxZ; z++){
					Block block = world.getBlockAt(x, y, z);
					if(block.getType() == Material.MONSTER_EGGS){
						block.setType(Material.AIR);
						world.spawnEntity(new Location(world, x + 0.5, y + 0.5, z + 0.5), EntityType.SILVERFISH);
					}
				}
			}
		}
	}

	@Override
	protected String worldName() {
		return WorldGenPlugin.HIGHLANDS;
	}

	@Override
	protected boolean zombie(Zombie zombie) {
		setAttributes(zombie, 50, 8, 0.4, 32);
		setEquipment(zombie, IRON_SWORD, IRON_HELMET, IRON_CHESTPLATE, IRON_LEGGINGS, IRON_BOOTS);
		return false;
	}

	@Override
	protected boolean skeleton(Skeleton skeleton) {
		setAttributes(skeleton, 30, 0, 0.3, 32);
		setEquipment(skeleton, SKELETON_BOW.clone(), new ItemStack(CHAINMAIL_HELMET), new ItemStack(CHAINMAIL_CHESTPLATE), new ItemStack(CHAINMAIL_LEGGINGS), new ItemStack(CHAINMAIL_BOOTS));
		return false;
	}

	@Override
	protected boolean spider(Spider spider) {
		setAttributes(spider, 40, 10, 0.5, 32);
		return false;
	}

	@Override
	protected boolean creeper(Creeper creeper) {
		setAttributes(creeper, 60, 0, 0.3, 32);
		return false;
	}

	@Override
	protected boolean witch(Witch witch) {
		return true;
	}

	@Override
	protected boolean enderman(Enderman enderman) {
		return true;
	}

	@Override
	protected boolean slime(Slime slime) {
		return true;
	}

	@Override
	protected boolean silverfish(Silverfish silverfish) {
		setAttributes(silverfish, 20, 8, 0.3, 16);
		return false;
	}

	@Override
	protected String coal() {
		return null;
	}

	@Override
	protected String lapiz() {
		return CustomItems.SAPHIRE;
	}

	@Override
	protected String iron() {
		return null;
	}

	@Override
	protected String redstone() {
		return CustomItems.RUBY;
	}

	@Override
	protected String gold() {
		return null;
	}

	@Override
	protected String diamond() {
		return null;
	}

	@Override
	protected String emerald() {
		return CustomItems.EMERALD;
	}
}
