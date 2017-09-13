package com.github.escapemc.arenapluginrefined;

import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.escapemc.arenapluginrefined.bases.ArenaManager;
import com.github.escapemc.arenapluginrefined.bases.ArenaManager.Arena;
import com.github.escapemc.arenapluginrefined.bases.ArenaManager.Team;

public class Main extends JavaPlugin {

	PluginDescriptionFile pdfFile = getDescription();
	Logger logger = getLogger();
	
	public void onEnable() {
		
		logger.info("Enabling " + pdfFile.getName() + " Version - " + pdfFile.getVersion() + " Made By " + pdfFile.getAuthors());
		
	}
	
	public void onDisable() {
		
		logger.info("Disabling " + pdfFile.getName() + " Version - " + pdfFile.getVersion() + " Made By " + pdfFile.getAuthors());
		
	}
		
	ArenaManager am = new ArenaManager();
	private Arena arena;
	private Team team;
	private String playerName;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(cmd.getName().equalsIgnoreCase("arena")) {
			
			if(args.length < 1) {
				
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin Commands:");
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "/arena - lists all of the commands in ArenaPlugin");
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "/arena create [thing] [name] - will create the specified thing");
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "/arnea list [thing to list] - lists the items in the selected thing to list");
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "/arena join [team] [arena] {player name} - will add a player to a team");
				return true;
				
			}
			
			if(args[0].equalsIgnoreCase("join")) {
				
				//arena join team arena name
				
				if(args.length < 2) {
					
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "/arena join [team] [arena] {player name}");
				
				}else if(args.length < 3) {
					
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "You need to specify in what arena to join.");
					
				}else{
					
					arena = am.getArenaByName(args[2]);
					team = arena.getTeamByName(args[1]);
					
					if(team == null) {
						
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "That is not a team.");
						
					}else if(arena == null) {
						
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "That is not an arena.");
					
					}else if(args.length < 4) {
						playerName = sender.getName();
						@SuppressWarnings("deprecation")
						OfflinePlayer op = Bukkit.getPlayer(playerName);
						UUID uuid = op.getUniqueId();
						team.addPlayer(uuid);
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "Added " + playerName + " to the " + args[1] + " team.");
					
					}else if (args.length < 5) {
					
						if(Bukkit.getServer().getOnlinePlayers().contains(args[4])) {
						
							playerName = args[4];
							@SuppressWarnings("deprecation")
							OfflinePlayer op = Bukkit.getPlayer(playerName);
							UUID uuid = op.getUniqueId();
							team.addPlayer(uuid);
							sender.sendMessage(ChatColor.LIGHT_PURPLE + "Added " + playerName + " to the " + args[1] + " team.");
							
						}else{
							
							sender.sendMessage(ChatColor.LIGHT_PURPLE + args[4] + " cannot be found.");
							
						}
						
					}else{
						
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "Something broke.");
						
					}
					
				}
				
			}else if(args[0].equalsIgnoreCase("create")) {
				
				if(args[1].equalsIgnoreCase("arena")) {
					
					if(args.length < 3 && args.length > 1) {
						
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "You need to specify a name for the arena.");
						return true;
						
					}else{
					
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "Created Arena named " + args[2]);
						am.addArena(args[2]);
						
					}
					
				}else if(args[1].equalsIgnoreCase("team")) {
						
					if(args.length < 3) {
							
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "You need to specify a name for the team.");
						return true;
							
					}else if(args.length < 4) {
						
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "You need to specify an Arena to create the team for.");
						return true;
						
					}else{
						
						arena = am.getArenaByName(args[3]);

						if(arena == (null)) {
							
							sender.sendMessage(ChatColor.LIGHT_PURPLE + args[3] + " is not an arena.");
							
						}else{
							
							//arena create team [name] [arena]
							sender.sendMessage(ChatColor.LIGHT_PURPLE + "Created Team named " + args[2]);
							arena.addTeam(args[2]);
							
							
						}
						
					}
					
					
				}else{
					
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "You cannot make a(n) " + args[1] + ".");
					
				}
					
			}else if(args[0].equalsIgnoreCase("list")){
					
				if(args[1].equalsIgnoreCase("arenas")) {
					
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "Arenas:" + am.listArenas());
					
				}else if(args[1].equalsIgnoreCase("teams")) {
					
					//arena list teams [arena]
					if(args.length < 3) {
						
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "You need to specify an arena to list the teams for.");
						
					}else{
						
						arena = am.getArenaByName(args[2]);

						if(arena == null) {
							
							sender.sendMessage(ChatColor.LIGHT_PURPLE + "The specified arena is not an arena.");
						
						}else{

							sender.sendMessage(ChatColor.LIGHT_PURPLE + "Teams:" + arena.listTeams());
						
						}
							
					}
						
				}else{
						
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "That is not an accepted argument (" + args[1] + ")");
						
				}
					
			}else{
				
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "Invalid command.");
				
			}
			
		}
				
		return false;
				
	}
		
}
