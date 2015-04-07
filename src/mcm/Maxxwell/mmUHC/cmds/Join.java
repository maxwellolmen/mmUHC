package mcm.Maxxwell.mmUHC.cmds;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import mcm.Maxxwell.mmUHC.Arena;
import mcm.Maxxwell.mmUHC.ArenaManager;
import mcm.Maxxwell.mmUHC.CommandInfo;
import mcm.Maxxwell.mmUHC.GameCommand;
import mcm.Maxxwell.mmUHC.Main;
import mcm.Maxxwell.mmUHC.SettingsManager;

@CommandInfo(description = "Join a game.", usage = "", aliases = {"join", "j"}, perms = false)
public class Join extends GameCommand {
	@Override
	public void onCommand(Player p, String[] args) {
		if (ArenaManager.getInstance().getArena(p) != null) {
			p.sendMessage(Main.warning + "You are already in a game.");
			return;
		}
		
		if (!SettingsManager.getArenas().contains("arenas")) {
			p.sendMessage(Main.warning + "There are no existing arenas.");
			return;
		}
		
		List<String> arenas = SettingsManager.getArenas().<List<String>>get("arenas");
		
		Inventory inv = Bukkit.createInventory(null, 27, "Join an Arena");
		
		for (int i = 0; i < arenas.size(); i++) {
			String name = arenas.get(i);
			
			if (ArenaManager.getInstance().getArena(name) == null) {
				return;
			}
			
			Arena a = ArenaManager.getInstance().getArena(name);
			
			ItemStack item = null;
			
			switch (a.getState()) {
			case WAITING:
				item = new ItemStack(Material.WOOL, 1, (byte) 5);
				break;
			case COUNTDOWN:
				item = new ItemStack(Material.WOOL, 1, (byte) 1);
				break;
			case GRACE:
				item = new ItemStack(Material.WOOL, 1, (byte) 14);
				break;
			case BATTLE:
				item = new ItemStack(Material.WOOL, 1, (byte) 14);
				break;
			default:
				return;
			}
			
			int alive = a.getPlayers().size();
			
			int bordersize = a.getBoundaryRadius();
			
			ItemMeta im = item.getItemMeta();
			
			im.setDisplayName(ChatColor.RED + "Arena " + a.getID());
			
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GREEN + "" + alive + " Remaining Players");
			lore.add(ChatColor.GOLD + "" + bordersize + "-Block Border Radius");
			
			
			im.setLore(lore);
			
			item.setItemMeta(im);
			
			inv.setItem(i, item);
		}
		
		p.openInventory(inv);
	}
}
