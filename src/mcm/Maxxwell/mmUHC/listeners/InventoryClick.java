package mcm.Maxxwell.mmUHC.listeners;

import java.util.List;

import mcm.Maxxwell.mmUHC.Arena;
import mcm.Maxxwell.mmUHC.ArenaManager;
import mcm.Maxxwell.mmUHC.SettingsManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener {
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Bukkit.getLogger().info("Someone has attempted to join an arena.");
		
		Player p = (Player) e.getWhoClicked();
		
		if (e.getCurrentItem() == null) {
			return;
		}
		
		String itemname = e.getCurrentItem().getItemMeta().getDisplayName();    
		
		if (ArenaManager.getInstance().getArena(p) != null) {
			Bukkit.getLogger().warning(p.getName() + " attempted to join an arena but they were already in an arena.");
			return;
		}
		
		if (!e.getInventory().getName().equals("Join an Arena")) {
			Bukkit.getLogger().warning(p.getName() + " attempted to join an arena but the item did not start with Arena.");
			return;
		}
		
		if (ChatColor.stripColor(itemname).split(" ").length != 2) {
			Bukkit.getLogger().warning(p.getName() + " attempted to join an arena but the item was not two words long.");
			return;
		}
		
		if (!ChatColor.stripColor(itemname).split(" ")[0].equals("Arena")) {
			Bukkit.getLogger().warning(p.getName() + " attempted to join an arena but the inventory had an incorrect name.");
			return;
		}
		
		if (!SettingsManager.getArenas().contains("arenas")) {
			Bukkit.getLogger().warning(p.getName() + " attempted to join an arena but there were no configured arenas available.");
			return;
		}
		
		List<String> arenas = SettingsManager.getArenas().<List<String>>get("arenas");
		
		if (!arenas.contains(ChatColor.stripColor(itemname).split(" ")[1])) {
			Bukkit.getLogger().warning(p.getName() + " attempted to join an arena but the arena that was trying to be joined was not an actual arena.");
			return;
		}
		
		Arena a = null;
		
		for (int i = 0; i < arenas.size(); i++) {
			if (arenas.get(i).equalsIgnoreCase(ChatColor.stripColor(itemname).split(" ")[1])) {
				a = ArenaManager.getInstance().getArena(arenas.get(i));
				break;
			}
		}
		
		e.setCancelled(true);
		
		p.closeInventory();
		
		a.addPlayer(p);
	}
}
