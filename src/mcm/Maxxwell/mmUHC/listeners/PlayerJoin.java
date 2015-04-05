package mcm.Maxxwell.mmUHC.listeners;

import mcm.Maxxwell.mmUHC.Main;
import mcm.Maxxwell.mmUHC.SettingsManager;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permission;

public class PlayerJoin implements Listener {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (e.getPlayer().hasPermission(new Permission("mmUHC.admin"))) {
			return;
		}
		
		if (!SettingsManager.getConfig().contains("lobby")) {
			return;
		}
		
		e.getPlayer().teleport(Main.loadLocation(SettingsManager.getConfig().<ConfigurationSection>get("lobby")));
	}
}
