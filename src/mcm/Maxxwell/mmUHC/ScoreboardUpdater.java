package mcm.Maxxwell.mmUHC;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class ScoreboardUpdater extends Thread {
	private Arena arena;
	private Scoreboard board;
	
	public ScoreboardUpdater(Arena arena) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		
		this.board = manager.getNewScoreboard();
	}
	
	public void run() {
		board.registerNewObjective(arena.getID(), "dumy");
		
		while (true) {
			
			Objective obj = board.getObjective(arena.getID());
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			
			Score state;
			
			switch(arena.getState()) {
			case WAITING:
				state = obj.getScore(ChatColor.GRAY + "Awaiting Players");
			case COUNTDOWN:
				state = obj.getScore(ChatColor.YELLOW + "Countdown: " + arena.getCount());
			case GRACE:
				state = obj.getScore(ChatColor.GREEN + "Grace Period");
			case BATTLE:
				state = obj.getScore(ChatColor.RED + "Battle Period");
			}
			
			state.setScore(arg0)
		}
	}
}
