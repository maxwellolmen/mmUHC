package mcm.Maxxwell.mmUHC.cmds;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import mcm.Maxxwell.mmUHC.ArenaManager;
import mcm.Maxxwell.mmUHC.CommandInfo;
import mcm.Maxxwell.mmUHC.GameCommand;
import mcm.Maxxwell.mmUHC.SettingsManager;

@CommandInfo(description = "Create an arena.", usage = "<name>", aliases = {"createarena", "ca"}, perms = true)
public class CreateArena extends GameCommand {
	@Override
	public void onCommand(Player p, String[] args) {
		if (args.length == 0) {
			p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + ">> " + ChatColor.RED + "You must specify a name for the arena.");
			return;
		}
		
		String name = args[0];
		
		if (ArenaManager.getInstance().getArena(name) != null) {
			p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + ">> " + ChatColor.RED + "An arena with that name does not exist.");
			return;
		}
		
		if (!SettingsManager.getArenas().contains("arenas")) {
			List<String> arenas = new ArrayList<String>();
			arenas.add(args[0]);
			SettingsManager.getArenas().set("arenas", arenas);
		} else {
			List<String> arenas = SettingsManager.getArenas().<List<String>>get("arenas");
			arenas.add(args[0]);
			SettingsManager.getArenas().set("arenas", arenas);
		}
		
		ArenaManager.getInstance().setup();
		
		p.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + ">> " + ChatColor.GREEN + "Created arena " + args[0] + "!");
	}
}
