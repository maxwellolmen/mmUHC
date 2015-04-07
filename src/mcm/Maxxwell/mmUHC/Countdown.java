package mcm.Maxxwell.mmUHC;

import java.util.ArrayList;

import mcm.Maxxwell.mmUHC.Arena.ArenaState;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Countdown extends Thread {
	private Arena arena;
	private int i;
	private ArrayList<Integer> cNums;
	
	public Countdown(Arena arena, int start, int... cNums) {
		this.arena = arena;
		this.i = start;
		this.cNums = new ArrayList<Integer>();
		
		for (int c : cNums) {
			this.cNums.add(c);
		}
	}
	
	public void run() {
		while (true) {
			if (i == 0) {
				arena.setState(ArenaState.GRACE);
				
				arena.getWorld().getWorldBorder().setCenter(new Location(arena.getWorld(), 0, 0, 0));
				
				arena.getWorld().getWorldBorder().setSize(1000);
				
				arena.startCounters();
				
				for (Player p : arena.getPlayers()) {
					p.sendMessage(Main.good + "The game has begun!");
					
					p.setHealth(20.0D);
					p.setGameMode(GameMode.SURVIVAL);
					
					this.interrupt();
					break;
				}
			}
			
			if (cNums.contains(i)) {
				for (Player p : arena.getPlayers()) {
					p.sendMessage(Main.info + "The game will begin in " + i + " seconds!");
					p.setLevel(i);
				}
			}
			
			i--;
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getCount() {
		return i;
	}
}
