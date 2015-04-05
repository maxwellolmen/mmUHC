package mcm.Maxxwell.mmUHC.listeners;

import mcm.Maxxwell.mmUHC.Arena;
import mcm.Maxxwell.mmUHC.Arena.ArenaState;
import mcm.Maxxwell.mmUHC.ArenaManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class EntityDamage implements Listener {
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		
		Player p = (Player) e.getEntity();
		
		Arena a = ArenaManager.getInstance().getArena(p);
		
		if (a == null) {
			e.setCancelled(true);
			return;
		}
		
		if (a.getState() == ArenaState.WAITING || a.getState() == ArenaState.COUNTDOWN) {
			e.setCancelled(true);
			return;
		}
		
		if (a.getState() == ArenaState.GRACE) {
			if (e.getCause() == DamageCause.FALL) {
				e.setCancelled(true);
				return;
			}
		}
	}
}
