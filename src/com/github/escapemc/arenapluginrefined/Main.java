package com.github.escapemc.arenapluginrefined;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.escapemc.arenapluginrefined.bases.ArenaManager;
import com.github.escapemc.arenapluginrefined.bases.ArenaManager.Arena;
import com.github.escapemc.arenapluginrefined.bases.ArenaManager.Team;

public class Main extends JavaPlugin {

	ArenaManager am = new ArenaManager();
	private List<String> arenaList = new ArrayList<String>();
	private List<String> blankList = new ArrayList<String>();

	PluginDescriptionFile pdfFile = getDescription();
	Logger logger = getLogger();
	
	public void onEnable() {
		
		blankList.add("hi");
		blankList.clear();
		arenaList = getConfig().getStringList("arenas");
		logger.info("-/-/-/-/" + getConfig().getStringList("arenas"));
		logger.info("------ ArenaList is: " + arenaList);
		
		if(arenaList.equals(blankList)) {
			
			logger.info("+++ Config for arenas not found.");
			
		}else{
			
			am.getArenasFromConfig(arenaList);
			logger.info("**** Config used to load arenas.");
		
		}
		
		logger.info("Enabled " + pdfFile.getName() + " Version - " + pdfFile.getVersion() + " Made By " + pdfFile.getAuthors());
		
	}
	
	public void onDisable() {
		
		am.setConfigArenas();
		System.out.println(am.arenaConfigNames);
		getConfig().set("arenas", am.arenaConfigNames.toString());
		saveConfig();
		logger.info("Disabled " + pdfFile.getName() + " Version - " + pdfFile.getVersion() + " Made By " + pdfFile.getAuthors());
		
	}
		
	private Arena arena;
	private Team team;
	private String playerName;
	private Location location;
	private String words;
	private String something = "";
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(cmd.getName().equalsIgnoreCase("arena")) {
			
			if(args.length < 1) {
				
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "ArenaPlugin Commands:");
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "/arena - lists all of the commands in ArenaPlugin");
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "/arena create <thing> <name> - will create the specified thing");
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "/arena delete <thing> <name> - will delete the specifed thing");
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "/arnea list <thing to list> - lists the items in the selected thing to list");
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "/arena join <team> <arena> <player name> - will add a player to a team");
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "/arena leave <team> <arena> - will remove a player from a team");
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "/arena check <player name> - checks for the arena and team of the specified player");
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "/arena start <arena name> - Teleports players to the team's spawns to start the arena");
				return true;
				
			}else if(args[0].equalsIgnoreCase("check")) {
				
				if(args.length < 2) {
					
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "/arena check <player name>");
					
				}else{
					
					if(Bukkit.getServer().getOnlinePlayers().toString().contains(args[1])) {
						
						playerName = args[1];
						@SuppressWarnings("deprecation")
						OfflinePlayer op = Bukkit.getPlayer(playerName);
						String uuid = op.getUniqueId().toString();
						something = am.getArenaAndTeam(uuid);
						if(something.equalsIgnoreCase("")) {
							
							sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + playerName + " is not on a team.");
						
						}else{
							
							sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + playerName + " is on " + something);							
						
						}
						
					}else{
						
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "Cannot find that player.");
						
					}
					
				}
			
			}else if(args[0].equalsIgnoreCase("setspawn")) {
				
				//arena setspawn <team> <arena>
				
				if(args.length < 2) {
					
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "/arena setspawn <team> <arena>");
					
				}else if(args.length < 3) {
					
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "You need to specify which arena to which the team is part of.");
					
				}else{
					
					arena = am.getArenaByName(args[2]);
					team = arena.getTeamByName(args[1]);					
					if(team == null) {
						
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "That is not a team.");
						
					}else if(arena == null) {
						
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "That is not an arena.");
					
					}else{
						
						Player p = (Player) sender;
						location = team.teleportLocation(p);
						team.setTeleportLocation(location);
						words = team.teleportLocationString(p);
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "Spawn of " + args[1] + " to " + words);
						
					}
				}
				
			
			}else if(args[0].equalsIgnoreCase("start")) {
				
				//arena start <arena>
				
				if(args.length < 2) {
					
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "/arena start [arena name]");
					return true;
					
				}else{
					
					arena = am.getArenaByName(args[1]);
					
					if(arena == null) {
						
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "That is not a valid arena.");
						
					}else{
					
						arena.teleportTeams();
					
					}
						
				}
				
			}else if(args[0].equalsIgnoreCase("leave")) {
				
				//arena leave team arena name
				
				if(args.length < 2) {
					
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "/arena leave <team> <arena> {player name}");
				
				}else if(args.length < 3) {
					
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "You need to specify in what arena to leave.");
					
				}else{
					
					arena = am.getArenaByName(args[2]);
					team = arena.getTeamByName(args[1]);
					
					if(team == null) {
						
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "That is not a team.");
						
					}else if(arena == null) {
						
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "That is not an arena.");
					
					}else if(args.length < 4) {

						playerName = sender.getName();
						@SuppressWarnings("deprecation")
						OfflinePlayer op = Bukkit.getPlayer(playerName);
						String uuid = op.getUniqueId().toString();
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "Removed " + playerName + " from any previous team.");
						arena.removedPlayerFromTeams(uuid);
					
					}else if (args.length < 5) {
					
						if(Bukkit.getServer().getOnlinePlayers().toString().contains(args[3])) {

							//arena join <team> <arena> <name>
							
							playerName = args[3];
							@SuppressWarnings("deprecation")
							OfflinePlayer op = Bukkit.getPlayer(playerName);
							String uuid = op.getUniqueId().toString();
							sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "Removed " + playerName + " from any previous team.");
							arena.removedPlayerFromTeams(uuid);
							
						}else{
							
							sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + args[3] + " cannot be found.");
							
						}
						
					}else{
						
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "Something broke.");
						
					}
					
				}
				
			}else if(args[0].equalsIgnoreCase("join")) {
				
				//arena join team arena name
				
				if(args.length < 2) {
					
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "/arena join <team> <arena> {player name}");
				
				}else if(args.length < 3) {
					
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "You need to specify in what arena to join.");
					
				}else{
					
					arena = am.getArenaByName(args[2]);
					team = arena.getTeamByName(args[1]);
					
					if(team == null) {
						
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "That is not a team.");
						
					}else if(arena == null) {
						
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "That is not an arena.");
					
					}else if(args.length < 4) {

						playerName = sender.getName();
						@SuppressWarnings("deprecation")
						OfflinePlayer op = Bukkit.getPlayer(playerName);
						String uuid = op.getUniqueId().toString();
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "Removed " + playerName + " from any previous team.");
						arena.removedPlayerFromTeams(uuid);
						team.addPlayer(uuid);
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "Added " + playerName + " to the " + args[1] + " team.");
					
					}else if (args.length < 5) {
					
						if(Bukkit.getServer().getOnlinePlayers().toString().contains(args[3])) {

							//arena join <team> <arena> <name>
							
							playerName = args[3];
							@SuppressWarnings("deprecation")
							OfflinePlayer op = Bukkit.getPlayer(playerName);
							String uuid = op.getUniqueId().toString();
							sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "Removed " + playerName + " from any previous team.");
							arena.removedPlayerFromTeams(uuid);
							team.addPlayer(uuid);
							sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "Added " + playerName + " to the " + args[1] + " team.");
							
						}else{
							
							sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + args[3] + " cannot be found.");
							
						}
						
					}else{
						
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "Something broke.");
						
					}
					
				}
				
			}else if(args[0].equalsIgnoreCase("create")) {
				
				if(args.length < 2) {
					
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "/arena create <thing>");					
			
				}else if(args[1].equalsIgnoreCase("arena")) {
					
					if(args.length < 3 && args.length > 1) {
						
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "You need to specify a name for the arena.");
						return true;
						
					}else{
					
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "Created Arena named " + args[2]);
						am.addArena(args[2]);
						
					}
					
				}else if(args[1].equalsIgnoreCase("team")) {
						
					if(args.length < 3) {
							
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "You need to specify a name for the team.");
						return true;
							
					}else if(args.length < 4) {
						
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "You need to specify an Arena to create the team for.");
						return true;
						
					}else{
						
						arena = am.getArenaByName(args[3]);

						if(arena == (null)) {
							
							sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + args[3] + " is not an arena.");
							
						}else{
							
							//arena create team <name> <arena>
							sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "Created Team named " + args[2]);
							arena.addTeam(args[2]);
							
							
						}
						
					}
					
				}else{
					
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "You cannot make a(n) " + args[1] + ".");
					
				}
					
			}else if(args[0].equalsIgnoreCase("delete")) {
				
				if(args.length < 2) {
					
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "/arena delete <arena/team>");					
			
				}else if(args[1].equalsIgnoreCase("arena")) {
					
					if(args.length < 3 && args.length > 1) {
						
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "You need to specify a name for the arena.");
						return true;
						
					}else{
					
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "Deleted Arena named " + args[2]);
						am.deleteArena(args[2]);
						
					}
					
				}else if(args[1].equalsIgnoreCase("team")) {
						
					if(args.length < 3) {
							
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "You need to specify a name for the team.");
						return true;
							
					}else if(args.length < 4) {
						
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "You need to specify an Arena to create the team for.");
						return true;
						
					}else{
						
						arena = am.getArenaByName(args[3]);

						if(arena == (null)) {
							
							sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + args[3] + " is not an arena.");
							
						}else{
							
							//arena create team <name> <arena>
							sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "Deleted Team named " + args[2]);
							arena.removeTeam(args[3], args[2]);
							
							
						}
						
					}
					
					
				}else{
					
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "You cannot make a(n) " + args[1] + ".");
					
				}
			
			}else if(args[0].equalsIgnoreCase("list")){
				
				if(args.length < 2) {
					
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "/arena list <arenas/teams>");
				
				}else if(args[1].equalsIgnoreCase("arenas")) {
					
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "Arenas:" + am.listArenas());
					
				}else if(args[1].equalsIgnoreCase("teams")) {
					
					//arena list teams <arena>
					if(args.length < 3) {
						
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "You need to specify an arena to list the teams for.");
						
					}else{	
						
						arena = am.getArenaByName(args[2]);

						if(arena == null) {
							
							sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "The specified arena is not an arena.");
						
						}else{

							sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "Teams:" + arena.listTeams());
						
						}
							
					}
						
				}else{
						
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "That is not an accepted argument (" + args[1] + ")");
						
				}
					
			}else{
				
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin: " + "Invalid command.");
				
			}
			
		}
				
		return false;
				
	}
		
}
