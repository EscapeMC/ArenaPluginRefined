package com.github.escapemc.arenapluginrefined;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	PluginDescriptionFile pdfFile = getDescription();
	Logger logger = getLogger();
	
	public void onEnable() {
		
		logger.info("Enabling " + pdfFile.getName() + " Version - " + pdfFile.getVersion() + " Made By " + pdfFile.getAuthors());
		
	}
	
	public void onDisable() {
		
		logger.info("Disabling " + pdfFile.getName() + " Version - " + pdfFile.getVersion() + " Made By " + pdfFile.getAuthors());
		
	}

	public ArrayList<String> arenaNames = new ArrayList<String>();
	public ArrayList<String> teamNames = new ArrayList<String>();
	
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
					
					}
					
				}else if(args[1].equalsIgnoreCase("team")) {
					
					if(args.length < 3 && args.length > 1) {
						
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "You need to specify a name for the arena.");
						
					}else{
						
						if(arenaNames.contains(args[3])) {
							
							sender.sendMessage(ChatColor.LIGHT_PURPLE + "Created Team named " + args[2] + " in Arena named " + args[3]);
						
						}else{
							
							sender.sendMessage(ChatColor.LIGHT_PURPLE + "That is not an Arena.");
						}
						
					}
					
				}
					
			}else if(args[0].equalsIgnoreCase("list")){
					
				if(args[1].equalsIgnoreCase("arenas")) {
					
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "Arenas: " + arenaNames.toString());
					
				}else if(args[1].equalsIgnoreCase("teams")) {
						
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "Teams: " + teamNames.toString());
						
				}else{
						
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "That is not an accepted argument (" + args[1] + ")");
						
				}
					
			}
			
		}

		return false;
		
	}
		
}
