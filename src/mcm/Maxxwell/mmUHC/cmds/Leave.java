package mcm.Maxxwell.mmUHC.cmds;

import org.bukkit.entity.Player;

import mcm.Maxxwell.mmUHC.Arena;
import mcm.Maxxwell.mmUHC.ArenaManager;
import mcm.Maxxwell.mmUHC.CommandInfo;
import mcm.Maxxwell.mmUHC.GameCommand;
import mcm.Maxxwell.mmUHC.Main;

@CommandInfo(description = "Leave a game.", usage = "", aliases = {"leave", "l"}, perms = false)
public class Leave extends GameCommand {
	@Override
	public void onCommand(Player p, String[] args) {
		Arena a = ArenaManager.getInstance().getArena(p);
		
		if (a == null) {
			p.sendMessage(Main.warning + "You are not in a game.");
			return;
		}
		
		a.removePlayer(p);
		
		p.sendMessage(Main.info + "You have been removed from arena " + a.getID() + ".");
	}
}
