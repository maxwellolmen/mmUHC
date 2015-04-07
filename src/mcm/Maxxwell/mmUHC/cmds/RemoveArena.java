package mcm.Maxxwell.mmUHC.cmds;

import java.util.List;

import org.bukkit.entity.Player;

import mcm.Maxxwell.mmUHC.ArenaManager;
import mcm.Maxxwell.mmUHC.CommandInfo;
import mcm.Maxxwell.mmUHC.GameCommand;
import mcm.Maxxwell.mmUHC.Main;
import mcm.Maxxwell.mmUHC.SettingsManager;

@CommandInfo(description = "Remove an arena.", usage = "<name>", aliases = {"removearena", "ra"}, perms = true)
public class RemoveArena extends GameCommand {
	@Override
	public void onCommand(Player p, String[] args) {
		if (args.length == 0) {
			p.sendMessage(Main.warning + "You must specify the name of the arena.");
			return;
		}
		
		String name = args[0];
		
		if (ArenaManager.getInstance().getArena(name) == null) {
			p.sendMessage(Main.warning + "An arena with that name does not exist.");
			return;
		}
		
		List<String> arenas = SettingsManager.getArenas().<List<String>>get("arenas");
		arenas.remove(name);
		SettingsManager.getArenas().set("arenas", arenas);
		
		p.sendMessage(Main.good + "Deleted arena.");
	}
}
