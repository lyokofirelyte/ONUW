package com.github.lyokofirelyte.ONUW.Etc;

public enum Card {
	
	WEREWOLF("WEREWOLF"),
	VILLAGER("VILLAGER"),
	SEER("SEER"),
	ROBBER("ROBBER"),
	TROUBLEMAKER("TROUBLEMAKER"),
	TANNER("TANNER"),
	DRUNK("DRUNK"),
	MASON("MASON"),
	INSOMNIAC("INSOMNIAC"),
	MINION("MINION"),
	HUNTER("HUNTER");

	Card(String t){
		type = t;
	}
	
	private String type;
	
	public String toString(){
		return type;
	}
}