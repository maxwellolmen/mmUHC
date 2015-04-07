package mcm.Maxxwell.mmUHC.cmds;

import org.bukkit.entity.Player;

import mcm.Maxxwell.mmUHC.Arena;
import mcm.Maxxwell.mmUHC.ArenaManager;
import mcm.Maxxwell.mmUHC.CommandInfo;
import mcm.Maxxwell.mmUHC.GameCommand;
import mcm.Maxxwell.mmUHC.Main;
import mcm.Maxxwell.mmUHC.Arena.ArenaState;

@CommandInfo(description = "Starts a game.", usage = "<arenaName>", aliases = {"forcestart", "fs"}, perms = true)
public class ForceStart extends GameCommand {
	@Override
	public void onCommand(Player p, String[] args) {
		if (args.length == 0) {
			p.sendMessage(Main.warning + "You must specify the arena to start.");
			return;
		}
		
		Arena a = ArenaManager.getInstance().getArena(args[0]);
		
		if (a == null) {
			p.sendMessage(Main.warning + "An arena with that name does not exist.");
			return;
		}
		
		a.setState(ArenaState.COUNTDOWN);
		
		a.startCountdown();
	}
}
