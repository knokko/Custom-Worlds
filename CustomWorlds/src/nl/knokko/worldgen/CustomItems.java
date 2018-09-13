package nl.knokko.worldgen;

import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

public class CustomItems {
	
	public static final String SAPHIRE = "Saphire";
	public static final String RUBY = "Ruby";
	public static final String EMERALD = "EmeraldGem";
	
	private static Method getByID;
	
	static {
		try {
			getByID = Class.forName("nl.knokko.items.ItemRegistry").getMethod("getByID", String.class);
		} catch(Exception ex){
			Bukkit.getLogger().severe("Could not access Custom Items plug-in from the World Gen plugin: " + ex.getLocalizedMessage());
			getByID = null;
		}
	}
	
	public static ItemStack get(String id){
		if(getByID == null)
			return null;
		try {
			return (ItemStack) getByID.invoke(null, id);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
