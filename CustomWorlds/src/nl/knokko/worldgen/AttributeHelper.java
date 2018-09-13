package nl.knokko.worldgen;

import net.minecraft.server.v1_12_R1.ItemStack;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagDouble;
import net.minecraft.server.v1_12_R1.NBTTagInt;
import net.minecraft.server.v1_12_R1.NBTTagList;
import net.minecraft.server.v1_12_R1.NBTTagLong;
import net.minecraft.server.v1_12_R1.NBTTagString;

import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;

public class AttributeHelper {
	
	private static final String ATTRIBUTE_MODIFIERS = "AttributeModifiers";
	
	private static final String ATTRIBUTE_NAME = "AttributeName";
	private static final String NAME = "Name";
	private static final String AMOUNT = "Amount";
	private static final String OPERATION = "Operation";
	private static final String ID_LEAST = "UUIDLeast";
	private static final String ID_MOST = "UUIDMost";
	private static final String SLOT = "Slot";
	
	public static org.bukkit.inventory.ItemStack addAttribute(org.bukkit.inventory.ItemStack target, Slot slot, Attribute attribute, double value){
		ItemStack nms = CraftItemStack.asNMSCopy(target);
		NBTTagCompound compound = (nms.hasTag()) ? nms.getTag() : new NBTTagCompound();
		NBTTagList modifiers = new NBTTagList();
		addSingleAttribute(modifiers, slot, attribute, value);
		compound.set(ATTRIBUTE_MODIFIERS, modifiers);
		nms.setTag(compound);
		return CraftItemStack.asBukkitCopy(nms);
	}
	
	public static org.bukkit.inventory.ItemStack addAttributes(org.bukkit.inventory.ItemStack target, Slot slot, Entry... values){
		ItemStack nms = CraftItemStack.asNMSCopy(target);
		NBTTagCompound compound = (nms.hasTag()) ? nms.getTag() : new NBTTagCompound();
		NBTTagList modifiers = new NBTTagList();
		for(Entry entry : values)
			addSingleAttribute(modifiers, slot, entry.getAttribute(), entry.getValue());
		compound.set(ATTRIBUTE_MODIFIERS, modifiers);
		nms.setTag(compound);
		return CraftItemStack.asBukkitCopy(nms);
	}
	
	private static void addSingleAttribute(NBTTagList modifiers, Slot slot, Attribute attribute, double value){
		NBTTagCompound sub = new NBTTagCompound();
		NBTTagString name = new NBTTagString(attributeToString(attribute));
		sub.set(ATTRIBUTE_NAME, name);
		sub.set(NAME, name);
		sub.set(AMOUNT, new NBTTagDouble(value));
		sub.set(OPERATION, new NBTTagInt(0));
		sub.set(ID_LEAST, new NBTTagLong(attribute.ordinal() + 3498934));
		sub.set(ID_MOST, new NBTTagLong(slot.ordinal() + 9681));
		sub.set(SLOT, new NBTTagString(slot.value));
		modifiers.add(sub);
	}
	
	private static String attributeToString(Attribute attribute){
		switch(attribute){
		case GENERIC_ARMOR:
			return "generic.armor";
		case GENERIC_ARMOR_TOUGHNESS:
			return "generic.armorToughness";
		case GENERIC_ATTACK_DAMAGE:
			return "generic.attackDamage";
		case GENERIC_ATTACK_SPEED:
			return "generic.attackSpeed";
		case GENERIC_FLYING_SPEED:
			return "generic.flyingSpeed";
		case GENERIC_FOLLOW_RANGE:
			return "generic.followRange";
		case GENERIC_KNOCKBACK_RESISTANCE:
			return "generic.knockbackResistance";
		case GENERIC_LUCK:
			return "generic.luck";
		case GENERIC_MAX_HEALTH:
			return "generic.maxHealth";
		case GENERIC_MOVEMENT_SPEED:
			return "generic.movementSpeed";
		case HORSE_JUMP_STRENGTH:
			return "horse.jumpStrength";
		case ZOMBIE_SPAWN_REINFORCEMENTS:
			return "zombie.spawnReinforcements";
		default:
			throw new IllegalArgumentException("Unknown attribute: " + attribute);
		}
	}
	
	public static enum Slot {
		
		MAINHAND,
		OFFHAND,
		FEET,
		LEGS,
		CHEST,
		HEAD;
		
		private final String value;
		
		Slot(){
			value = name().toLowerCase();
		}
	}
	
	public static class Entry {
		
		private final Attribute attribute;
		private final double value;
		
		public Entry(Attribute attribute, double value){
			this.attribute = attribute;
			this.value = value;
		}
		
		public Attribute getAttribute(){
			return attribute;
		}
		
		public double getValue(){
			return value;
		}
	}
}