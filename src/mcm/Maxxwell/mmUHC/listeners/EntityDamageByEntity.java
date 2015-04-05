package mcm.Maxxwell.mmUHC.listeners;

import mcm.Maxxwell.mmUHC.Arena;
import mcm.Maxxwell.mmUHC.Arena.ArenaState;
import mcm.Maxxwell.mmUHC.ArenaManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntity implements Listener {
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player) || !(e.getDamager() instanceof Player)) {
			return;
		}
		
		Player p = (Player) e.getEntity();
		Player d = (Player) e.getDamager();
		
		if (ArenaManager.getInstance().getArena(p) == null || ArenaManager.getInstance().getArena(d) == null) {
			e.setCancelled(true);
			return;
		}
		
		Arena a = ArenaManager.getInstance().getArena(p);
		
		if (!a.hasPlayer(d)) {
			e.setCancelled(true);
			return;
		}
		
		if (a.getState() != ArenaState.GRACE) {
			return;
		}
		
		e.setCancelled(true);
	}
}
