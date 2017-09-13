package com.github.escapemc.arenapluginrefined.bases;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

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
    	    	
    	for(Arena a : arenas) {
    		
    		phs = phs + a.getName();
    		
    	}
    	
    	return phs;
    	
    }
	
	public class Arena {
		   
        private final String name;
    	private ArrayList<Team> teams = new ArrayList<Team>();
	     
        public Arena(String name) {
	        
        	this.name = name;
	        
        }
        	     
        public String getName() {
	        
        	return name;
	        
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
        		
        		phs = phs + t.getName();
        		
        	}
        	
        	return phs;
        
        }
	     
        public void sendBrodcast() {
	        
        	Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "Name : " + name);

        }

	}
	
	public class Team {
		
		private final String name;
		
		public Team(String name) {
			
			this.name = name;
			
		}
		
		public String getName() {
			
			return name;
			
		}
		
	}
	
}
