package mcm.Maxxwell.mmUHC;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public void onEnable() {
		ArenaManager.getInstance().setup();
		
		getCommand("uhc").setExecutor(new CommandManager());
		
		PluginManager pm = getServer().getPluginManager();
		pm.addPermission(new Permission("mmUHC.admin"));
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
