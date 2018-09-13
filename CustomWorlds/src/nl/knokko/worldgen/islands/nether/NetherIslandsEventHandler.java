package nl.knokko.worldgen.islands.nether;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import nl.knokko.worldgen.AttributeHelper;
import nl.knokko.worldgen.AttributeHelper.Entry;
import nl.knokko.worldgen.AttributeHelper.Slot;
import nl.knokko.worldgen.WorldGenPlugin;
import nl.knokko.worldgen.eventhandler.NetherEventHandler;

public class NetherIslandsEventHandler extends NetherEventHandler {
	
	private static final ItemStack PIG_SWORD;
	private static final ItemStack PIG_HELMET;
	private static final ItemStack PIG_PLATE;
	private static final ItemStack PIG_LEGS;
	private static final ItemStack PIG_BOOTS;
	
	static {
		PIG_SWORD = AttributeHelper.addAttributes(new ItemStack(Material.GOLD_SWORD), Slot.MAINHAND, new Entry(Attribute.GENERIC_ATTACK_DAMAGE, 16), new Entry(Attribute.GENERIC_MOVEMENT_SPEED, 0.05));
		PIG_HELMET = AttributeHelper.addAttributes(new ItemStack(Material.GOLD_HELMET), Slot.HEAD, new Entry(Attribute.GENERIC_ARMOR, 3), new Entry(Attribute.GENERIC_ARMOR_TOUGHNESS, 1.5), new Entry(Attribute.GENERIC_MAX_HEALTH, 3));
		PIG_PLATE = AttributeHelper.addAttributes(new ItemStack(Material.GOLD_CHESTPLATE), Slot.CHEST, new Entry(Attribute.GENERIC_ARMOR, 8), new Entry(Attribute.GENERIC_ARMOR_TOUGHNESS, 4), new Entry(Attribute.GENERIC_MAX_HEALTH, 8));
		PIG_LEGS = AttributeHelper.addAttributes(new ItemStack(Material.GOLD_LEGGINGS), Slot.LEGS, new Entry(Attribute.GENERIC_ARMOR, 6), new Entry(Attribute.GENERIC_ARMOR_TOUGHNESS, 3), new Entry(Attribute.GENERIC_MAX_HEALTH, 6));
		PIG_BOOTS = AttributeHelper.addAttributes(new ItemStack(Material.GOLD_BOOTS), Slot.FEET, new Entry(Attribute.GENERIC_ARMOR, 3), new Entry(Attribute.GENERIC_ARMOR_TOUGHNESS, 1.5), new Entry(Attribute.GENERIC_MAX_HEALTH, 3));
		setNameAndUnbreakable(PIG_SWORD, "Pig Sword");
		setNameAndUnbreakable(PIG_HELMET, "Pig Helmet");
		setNameAndUnbreakable(PIG_PLATE, "Pig Chestplate");
		setNameAndUnbreakable(PIG_LEGS, "Pig Leggings");
		setNameAndUnbreakable(PIG_BOOTS, "Pig Boots");
	}
	
	static void setName(ItemStack stack, String displayName){
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(displayName);
		stack.setItemMeta(meta);
	}
	
	static void setNameAndUnbreakable(ItemStack stack, String displayName){
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(displayName);
		meta.setUnbreakable(true);
		stack.setItemMeta(meta);
	}

	@Override
	protected void quartzBreak(BlockBreakEvent event) {
		// TODO special quartz coin

	}

	@Override
	protected boolean pigzombie(PigZombie pig) {
		pig.getEquipment().setHelmet(PIG_HELMET);
		pig.getEquipment().setChestplate(PIG_PLATE);
		pig.getEquipment().setLeggings(PIG_LEGS);
		pig.getEquipment().setBoots(PIG_BOOTS);
		pig.getEquipment().setItemInMainHand(PIG_SWORD);
		pig.getEquipment().setHelmetDropChance(1);
		pig.getEquipment().setChestplateDropChance(1);
		pig.getEquipment().setLeggingsDropChance(1);
		pig.getEquipment().setBootsDropChance(1);
		pig.getEquipment().setItemInMainHandDropChance(1);
		pig.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(10);
		pig.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(64);
		pig.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.2);
		pig.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(60);
		pig.setHealth(pig.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
		
		return false;
	}

	@Override
	protected boolean ghast(Ghast ghast) {
		ghast.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(100);
		return false;
	}

	@Override
	protected boolean magmacube(MagmaCube cube) {
		cube.getWorld().spawnEntity(cube.getLocation(), EntityType.WITHER_SKELETON);
		return true;
	}

	@Override
	protected boolean witherskeleton(WitherSkeleton skeleton) {
		// TODO demons?
		return false;
	}

	@Override
	protected boolean skeleton(Skeleton skeleton) {
		return true;
	}

	@Override
	protected boolean blaze(Blaze blaze) {
		return true;
	}

	@Override
	protected String worldName() {
		return WorldGenPlugin.NETHER_ISLANDS;
	}
}
