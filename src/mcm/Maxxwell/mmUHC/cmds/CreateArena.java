package mcm.Maxxwell.mmUHC.cmds;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import mcm.Maxxwell.mmUHC.ArenaManager;
import mcm.Maxxwell.mmUHC.CommandInfo;
import mcm.Maxxwell.mmUHC.GameCommand;
import mcm.Maxxwell.mmUHC.Main;
import mcm.Maxxwell.mmUHC.SettingsManager;

@CommandInfo(description = "Create an arena.", usage = "<name>", aliases = {"createarena", "ca"}, perms = true)
public class CreateArena extends GameCommand {
	@Override
	public void onCommand(Player p, String[] args) {
		if (args.length == 0) {
			p.sendMessage(Main.warning + "You must specify a name for the arena.");
			return;
		}
		
		String name = args[0];
		
		if (ArenaManager.getInstance().getArena(name) != null) {
			p.sendMessage(Main.warning + "An arena with that name already exists.");
			return;
		}
		
		if (!SettingsManager.getArenas().contains("arenas")) {
			List<String> arenas = new ArrayList<String>();
			arenas.add(name);
			SettingsManager.getArenas().set("arenas", arenas);
		} else {
			List<String> arenas = SettingsManager.getArenas().<List<String>>get("arenas");
			arenas.add(name);
			SettingsManager.getArenas().set("arenas", arenas);
		}
		
		ArenaManager.getInstance().setup();
		
		p.sendMessage(Main.good + "Created arena " + name + "!");
	}
}
