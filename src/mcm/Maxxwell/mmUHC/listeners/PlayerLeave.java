package mcm.Maxxwell.mmUHC.listeners;

import mcm.Maxxwell.mmUHC.Arena;
import mcm.Maxxwell.mmUHC.ArenaManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener {
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		handle(e.getEntity());
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		handle(e.getPlayer());
	}
	
	private void handle(Player p) {
		Arena a = ArenaManager.getInstance().getArena(p);
		
		if (a == null) {
			return;
		}
		
		a.removePlayer(p);
	}
}
