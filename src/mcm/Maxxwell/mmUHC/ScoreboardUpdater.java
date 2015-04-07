package mcm.Maxxwell.mmUHC;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
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
		Objective obj = board.registerNewObjective(arena.getID(), "dummy");
		
		while (true) {
			board.resetScores(ChatColor.GRAY + "Awaiting Players");
			for (int i = 0; i < 31; i ++) {
				board.resetScores(ChatColor.YELLOW + "Countdown: " + i);
			}
			board.resetScores(ChatColor.GREEN + "Grace Period");
			board.resetScores(ChatColor.RED + "Battle Period");
			board.resetScores(ChatColor.RED + "Error");
			for (int i = 0; i < 11; i++) {
				board.resetScores(ChatColor.GREEN + "Players: " + i);
			}
			
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			
			Score state;
			
			switch(arena.getState()) {
			case WAITING:
				state = obj.getScore(ChatColor.GRAY + "Awaiting Players");
				break;
			case COUNTDOWN:
				state = obj.getScore(ChatColor.YELLOW + "Countdown: " + arena.getCount());
				break;
			case GRACE:
				state = obj.getScore(ChatColor.GREEN + "Grace Period");
				break;
			case BATTLE:
				state = obj.getScore(ChatColor.RED + "Battle Period");
				break;
			default:
				state = obj.getScore(ChatColor.RED + "Error");
			}
			
			state.setScore(4);
			
			Score aliveplayers;
			
			aliveplayers = obj.getScore(ChatColor.GREEN + "Players: " + arena.getPlayers().size());
			
			aliveplayers.setScore(3);
			
			Score time;
			
			int minutes = 0;
			int seconds = arena.getCount();
			
			if (seconds >= 60) {
				while (seconds >= 60) {
					seconds-=60;
					minutes++;
				}
			}
			
			time = obj.getScore(ChatColor.GOLD + "Time: " + minutes + "m" + seconds + "s");
			
			time.setScore(2);
			
			for (Player p : arena.getPlayers()) {
				p.setScoreboard(board);
			}
		}
	}
}
