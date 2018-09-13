package nl.knokko.worldgen.command;

import java.util.Collection;

import nl.knokko.worldgen.WorldGenHelper;
import nl.knokko.worldgen.heightmod.HeightModifier;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static nl.knokko.worldgen.islands.IslandsGenerator.*;

public class CommandWorldGen implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 2){
			if(args[0].equals("baseheight")){
				if(sender instanceof Player){
					Player player = (Player) sender;
					long seed = player.getWorld().getSeed();
					Location loc = player.getLocation();
					int chunkX = Math.floorDiv(loc.getBlockX(), 16);
					int chunkZ = Math.floorDiv(loc.getBlockZ(), 16);
					Collection<HeightModifier> hm = null;
					int baseHeight = -1;
					int minHeight = -1;
					if(args[1].equals("islands")){
						hm = WorldGenHelper.getHeightModifiers(new HillFactory(), MAX_HILL_RADIUS, HILL_REGION_SIZE, chunkX, chunkZ, seed);
						hm.addAll(WorldGenHelper.getHeightModifiers(new IslandFactory(), MAX_ISLAND_RADIUS, ISLAND_REGION_SIZE, chunkX, chunkZ, seed));
						baseHeight = 50;
						minHeight = BOTTOM_LEVEL;
					}
					int[] heights = WorldGenHelper.getHeights(chunkX, chunkZ, baseHeight, minHeight, 250, hm);
					int index = 16 * (loc.getBlockX() - 16 * chunkX) + loc.getBlockZ() - 16 * chunkZ;
					player.sendMessage(ChatColor.YELLOW + "The ground height here is " + heights[index]);//TODO show the sum of the height modifiers
				}
			}
			if(args[0].equals("heightmodifiers")){
				if(sender instanceof Player){
					Player player = (Player) sender;
					long seed = player.getWorld().getSeed();
					Location loc = player.getLocation();
					int chunkX = Math.floorDiv(loc.getBlockX(), 16);
					int chunkZ = Math.floorDiv(loc.getBlockZ(), 16);
					Collection<HeightModifier> hm = null;
					if(args[1].equals("islands")){
						hm = WorldGenHelper.getHeightModifiers(new HillFactory(), MAX_HILL_RADIUS, HILL_REGION_SIZE, chunkX, chunkZ, seed);
						hm.addAll(WorldGenHelper.getHeightModifiers(new IslandFactory(), MAX_ISLAND_RADIUS, ISLAND_REGION_SIZE, chunkX, chunkZ, seed));
					}
					player.sendMessage(ChatColor.AQUA + "This region contains " + hm.size() + " height modifiers:");
					for(HeightModifier h : hm)
						player.sendMessage(ChatColor.YELLOW + "" + h);
				}
			}
		}
		return false;
	}
}