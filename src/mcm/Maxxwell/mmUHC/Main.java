package mcm.Maxxwell.mmUHC;

import mcm.Maxxwell.mmUHC.listeners.*;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public static String warning = ChatColor.DARK_RED + "" + ChatColor.BOLD + ">> " + ChatColor.RED;
	public static String info = ChatColor.GRAY + "" + ChatColor.BOLD + ">> " + ChatColor.YELLOW;
	public static String good = ChatColor.DARK_GREEN + "" + ChatColor.BOLD + ">> " + ChatColor.GREEN;
	
	public void onEnable() {
		ArenaManager.getInstance().setup();
		
		getCommand("uhc").setExecutor(new CommandManager());
		
		PluginManager pm = getServer().getPluginManager();
		pm.addPermission(new Permission("mmUHC.admin"));
		
		pm.registerEvents(new EntityDamage(), this);
		pm.registerEvents(new EntityDamageByEntity(), this);
		pm.registerEvents(new PlayerJoin(), this);
		pm.registerEvents(new PlayerLeave(), this);
		pm.registerEvents(new PlayerMove(), this);
		pm.registerEvents(new InventoryClick(), this);
	}
	
	public static Plugin getPlugin() {
		return Bukkit.getServer().getPluginManager().getPlugin("mmUHC");
	}
	
	public static void saveLocation(Location location, ConfigurationSection section) {
		section.set("world", location.getWorld().getName());
		section.set("x", location.getX());
		section.set("y", location.getY());
		section.set("z", location.getZ());
		section.set("pitch", location.getPitch());
		section.set("yaw", location.getYaw());
	}
	
	public static Location loadLocation(ConfigurationSection section) {
		return new Location(
			Bukkit.getServer().getWorld(section.getString("world")),
			section.getDouble("x"),
			section.getDouble("y"),
			section.getDouble("z"),
			(float) section.getDouble("pitch"),
			(float) section.getDouble("yaw")
		);
	}
}
