package mcm.Maxxwell.mmUHC;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Arena {
	public enum ArenaState {
		WAITING, COUNTDOWN, GRACE, BATTLE
	}
	
	private String id;
	private ArenaState state;
	
	private World world;
	
	private ArrayList<Player> players;
	
	private Countdown cd;
	
	private GraceCounter gc;
	private BorderCounter bc;
	private ScoreboardUpdater su;
	
	protected Arena(String id) {
		this.id = id;
		
		WorldCreator wc = new WorldCreator(id);
		
		wc.environment(Environment.NORMAL);
		wc.generateStructures(false);
		wc.type(WorldType.NORMAL);
		
		this.world = wc.createWorld();
		
		this.state = ArenaState.WAITING;
		
		this.players = new ArrayList<Player>();
		
		cd = new Countdown(this, 30, 30, 20, 10, 5, 4, 3, 2, 1);
		
		this.gc = new GraceCounter(this);
		this.bc = new BorderCounter(this);
		this.su = new ScoreboardUpdater(this);
		
		su.start();
	}
	
	public String getID() {
		return id;
	}
	
	public int getBoundaryRadius() {
		return (int) Math.round(world.getWorldBorder().getSize() / 2);
	}
	
	public ArenaState getState() {
		return state;
	}
	
	public void setState(ArenaState state) {
		this.state = state;
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public boolean hasPlayer(Player p) {
		return players.contains(p);
	}
	
	public void addPlayer(Player p) {
		if (state == ArenaState.GRACE || state == ArenaState.BATTLE) {
			p.sendMessage(Main.warning + "That arena has already started.");
			return;
		}
		
		if (players.size() > 9) {
			p.sendMessage(Main.warning + "This arena is already full.");
		}
		
		Random r1 = new Random(), r2 = new Random();
		int i1 = r1.nextInt(1000) - 500, i2 = r2.nextInt(1000) - 500;
		
		p.teleport(new Location(world, i1, 256, i2));
		
		players.add(p);
		
		p.getInventory().clear();
		
		p.sendMessage(Main.good + "You have joined arena " + id + ".");
		
		if (players.size() >= 10 && state == ArenaState.WAITING) {
			this.state = ArenaState.COUNTDOWN;
			
			cd.start();
		}
	}
	
	public void removePlayer(Player p) {
		players.remove(p);
		
		if (!SettingsManager.getConfig().contains("lobby")) {
			p.getInventory().clear();
			p.setGameMode(GameMode.CREATIVE);
			Bukkit.getLogger().warning("There is no lobby set. A player has been kicked from the server by default.");
			p.kickPlayer(ChatColor.RED + "You have been removed from the game!");
		} else {
			p.getInventory().clear();
			p.setGameMode(GameMode.CREATIVE);
			p.teleport(Main.loadLocation(SettingsManager.getConfig().<ConfigurationSection>get("lobby")));
		}
		
		if (players.size() <= 1) {
			if (players.size() == 1) {
				Bukkit.getServer().broadcastMessage(Main.good + players.get(0).getName() + " has won arena " + id + "!");
				
				if (!SettingsManager.getConfig().contains("lobby")) {
					players.get(0).getInventory().clear();
					players.get(0).setGameMode(GameMode.SPECTATOR);
					Bukkit.getLogger().warning("There is no lobby set. A player has been kicked from the server by default.");
					players.get(0).kickPlayer(ChatColor.RED + "You have been removed from the game!");
				} else {
					players.get(0).getInventory().clear();
					players.get(0).setGameMode(GameMode.SPECTATOR);
					players.get(0).teleport(Main.loadLocation(SettingsManager.getConfig().<ConfigurationSection>get("lobby")));
				}
				
				players.remove(0);
			}
			
			Bukkit.unloadWorld(world, false);
			File world = new File(Main.getPlugin().getDataFolder().getParentFile().getPath() + "\\" + id);
			world.delete();
			
			resetCounters();
			
			players.clear();
			
			state = ArenaState.WAITING;
		}
	}
	
	public World getWorld() {
		return world;
	}
	
	public void startCounters() {
		gc.start();
		bc.start();
	}
	
	public void resetCounters() {
		gc.interrupt();
		bc.interrupt();
		
		gc = new GraceCounter(this);
		bc = new BorderCounter(this);
	}
	
	public int getCount() {
		return cd.getCount();
	}
	
	public void startCountdown() {
		cd.start();
	}
}
