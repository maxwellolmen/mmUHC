package mcm.Maxxwell.mmUHC;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import mcm.Maxxwell.mmUHC.cmds.*;

public class CommandManager implements CommandExecutor {
	private ArrayList<GameCommand> cmds;
	
	protected CommandManager() {
		cmds = new ArrayList<GameCommand>();
		
		cmds.add(new CreateArena());
		cmds.add(new ForceStart());
		cmds.add(new Join());
		cmds.add(new RemoveArena());
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}
		
		Player p = (Player) sender;
		
		if (cmd.getName().equalsIgnoreCase("uhc")) {
			if (args.length == 0) {
				for (GameCommand gcmd : cmds) {
					CommandInfo info = gcmd.getClass().getAnnotation(CommandInfo.class);
					p.sendMessage(Main.good + "/uhc (" + StringUtils.join(info.aliases(), " ").trim() + ") " + info.usage() + " - " + info.description());
				}
				
				return true;
			}
			
			GameCommand wanted = null;
			
			for (GameCommand gcmd : cmds) {
				CommandInfo info = gcmd.getClass().getAnnotation(CommandInfo.class);
				for (String alias : info.aliases()) {
					if (alias.equals(args[0])) {
						wanted = gcmd;
						break;
					}
				}
			}
			
			if (wanted == null) {
				p.sendMessage(Main.warning + "Could not find command.");
				return true;
			}
			
			if (wanted.getClass().getAnnotation(CommandInfo.class).perms() && !p.hasPermission(new Permission("mmUHC.admin"))) {
				p.sendMessage(Main.warning + "You do not have permission to use this command.");
				return true;
			}
			
			ArrayList<String> newArgs = new ArrayList<String>();
			Collections.addAll(newArgs, args);
			newArgs.remove(0);
			args = newArgs.toArray(new String[newArgs.size()]);
			
			wanted.onCommand(p, args);
		}
		
		return true;
	}
}
