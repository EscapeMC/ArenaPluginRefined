package com.github.escapemc.arenapluginrefined.bases;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class ArenaManager {
	
	private ArrayList<Arena> arenas = new ArrayList<Arena>();
	private String phs;
		
	public ArenaManager() {
		
		
		
	}
				
	public Arena getArenaByName(String name) {
		
        for (Arena a : arenas) {
        
        	if (a.getName().equalsIgnoreCase(name)) return a;
    
        }
     
        return null;
    
    }
	
    public void addArena(String name) {
    	
    	arenas.add(new Arena(name));

    }
     
    public String listArenas() {
    	    	
    	phs = " ";
    	for(Arena a : arenas) {
    		
    		phs = phs + a.getName() + " ";
    		
    	}
    	
    	return phs;
    	
    }
	
	public class Arena {
		   
        private String name;
        private String phrase;
        private String playerName;
        private ArrayList<Team> teams = new ArrayList<Team>();
	     
        public Arena(String name) {
	        
        	this.name = name;
	        
        }
        	     
        public String getName() {
	        
        	return name;
	        
        }

        public String removedPlayerFromTeamsString(String uuid, String name) {
			
        	phrase = "";
			for(@SuppressWarnings("unused") Arena a : arenas) {
				
				for(Team t : teams) {
					
					if(t.players.contains(uuid)) {
						
						phrase = t.getName().toString();
						
					}
					
				}
				
			}
			return phrase;
			
		}
        
        public void teleportTeams() {

    		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
    		
				playerName = p.getName();
				@SuppressWarnings("deprecation")
				OfflinePlayer op = Bukkit.getPlayer(playerName);
				String uuid = op.getUniqueId().toString();
    			
    			for(Team t : teams) {
        		        		
    				if(t.players.toString().contains(uuid)) {
        			
    					p.teleport(t.teleLoc);
    					p.sendMessage(ChatColor.LIGHT_PURPLE + "A fight has begun in the arena!");
    					
    				}
        			
        		}
        		
        	}
        	
        }
        
		public void removedPlayerFromTeams(String uuid) {
			
			for(@SuppressWarnings("unused") Arena a : arenas) {
				
				for(Team t : teams) {
					
					if(t.players.contains(uuid)) {
						
						t.players.remove(uuid);
						
					}
					
				}
				
			}
			
		}

		public void addTeam(String name) {
        	
        	teams.add(new Team(name));
        
        }

        public Team getTeamByName(String name) {
    		
    		for (Team t : teams) {
    			
    			if(t.getName().equalsIgnoreCase(name)) return t;
    			
    		}
    		
    		return null;
    		
    	}
      
        public String listTeams() {
        	
        	for(Team t : teams) {
        		
        		phs = " ";
        		phs = phs + t.getName() + " ";
        		
        	}
        	
        	return phs;
        
        }
	     
        public void sendBrodcast() {
	        
        	Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "Name : " + name);

        }

	}
	
	public class Team {
		
		private final String name;
		private ArrayList<String> players = new ArrayList<String>();
		private double posX;
		private double posY;
		private double posZ;
		private Location teleLoc;
		private String words;
		private World world;
		
		public Team(String name) {
			
			this.name = name;
			
		}
		
		public Location teleportLocation(Player playerIn) {
			
			posX = playerIn.getLocation().getX();
			posY = playerIn.getLocation().getY();
			posZ = playerIn.getLocation().getZ();
			world = playerIn.getLocation().getWorld();
			
			Location loc = new Location(world, posX, posY, posZ);
			
			return loc;
			
			
		}
				
		public String teleportLocationString(Player playerIn) {
			
			posX = playerIn.getLocation().getX();
			posY = playerIn.getLocation().getY();
			posZ = playerIn.getLocation().getZ();
			
			words = posX + " " + posY + " " + posZ;
			
			return words;
			
			
		}
		
		public void setTeleportLocation(Location loc) {
			
			teleLoc = loc;
			
		}
		
		public String getName() {
			
			return name;
			
		}
		
		public void addPlayer(String uuid) {
			
			players.add(uuid);
			
		}
		
		
	}
	
}
