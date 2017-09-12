package com.github.escapemc.arenapluginrefined;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.escapemc.arenapluginrefined.bases.ArenaManager;

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
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(cmd.getName().equalsIgnoreCase("arena")) {
			
			if(args.length < 1) {
				
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "ArenaPlugin Commands:");
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "/arena - lists all of the commands in ArenaPlugin");
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "/arena create [thing] [name] - will create the specified thing");
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "/arnea list [thing to list]");
				return true;
				
			}
						
			if(args[0].equalsIgnoreCase("create")) {
				
				if(args[1].equalsIgnoreCase("arena")) {
					
					if(args.length > 3) {
						
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "You need to specify a name for the arena.");
						
					}else{
					
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "Created Arena named " + args[2]);
						am.addArena(args[2]);
						
					}
					
				}

				if(args[1].equalsIgnoreCase("team")) {
						
					if(args.length > 3) {
							
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "You need to specify a name for the team.");
							
					}else{
						
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "Created Team named " + args[2]);
						am.addTeam(args[2]);
							
					}
					
					
				}else{
					
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "You cannot make a(n) " + args[1] + ".");
					
				}
					
			}else if(args[0].equalsIgnoreCase("list")){
					
				if(args[1].equalsIgnoreCase("arenas")) {
					
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "Arenas: " + am.listArenas());
					
				}else if(args[1].equalsIgnoreCase("teams")) {
						
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "Teams: " + am.listTeams());
						
				}else{
						
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "That is not an accepted argument (" + args[1] + ")");
						
				}
					
			}
			
		}
			
		return false;
				
	}
		
}
