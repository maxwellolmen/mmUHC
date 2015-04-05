package mcm.Maxxwell.mmUHC.cmds;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import mcm.Maxxwell.mmUHC.CommandInfo;
import mcm.Maxxwell.mmUHC.GameCommand;
import mcm.Maxxwell.mmUHC.Main;
import mcm.Maxxwell.mmUHC.SettingsManager;

@CommandInfo(description = "Set the lobby.", usage = "", aliases = {"setlobby", "sl"}, perms = true)
public class SetLobby extends GameCommand {
	@Override
	public void onCommand(Player p, String[] args) {
		Location loc = p.getLocation();
		
		Main.saveLocation(loc, SettingsManager.getConfig().createSection("lobby"));
		
		p.sendMessage(Main.good + "Set lobby.");
	}
}