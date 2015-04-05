package mcm.Maxxwell.mmUHC.listeners;

import mcm.Maxxwell.mmUHC.Arena;
import mcm.Maxxwell.mmUHC.Arena.ArenaState;
import mcm.Maxxwell.mmUHC.ArenaManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener  {
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Arena a = ArenaManager.getInstance().getArena(e.getPlayer());
		
		if (a == null) {
			return;
		}
		
		if (a.getState() == ArenaState.GRACE || a.getState() == ArenaState.BATTLE) {
			return;
		}
		
		if (e.getTo().getX() == e.getFrom().getX() && e.getTo().getZ() == e.getFrom().getZ()) {
			return;
		}
		
		e.setTo(e.getFrom());
	}
}
