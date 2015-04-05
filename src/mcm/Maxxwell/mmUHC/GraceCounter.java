package mcm.Maxxwell.mmUHC;

import org.bukkit.entity.Player;

import mcm.Maxxwell.mmUHC.Arena.ArenaState;

public class GraceCounter extends Thread {
	private Arena arena;
	private int counter;
	
	public GraceCounter(Arena arena) {
		this.arena = arena;
		
		this.counter = 0;
	}
	
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			counter++;
			
			if (counter == 600) {
				arena.setState(ArenaState.BATTLE);
				for (Player p : arena.getPlayers()) {
					p.sendMessage(Main.info + "Grace period has ended! You may now attack other players.");
				}
			}
		}
	}
}
