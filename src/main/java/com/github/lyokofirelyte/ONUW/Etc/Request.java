package com.github.lyokofirelyte.ONUW.Etc;

public enum Request {
	
	AUTH("AUTH"),
	CHAT_IN("CHAT_IN"),
	UPDATE_USER_LOBBY("UPDATE_USER_LOBBY"),
	READY("READY"),
	NOT_READY("NOT_READY"),
	START("START"),
	STOP("STOP"),
	ACCUSE("ACCUSE"),
	RESULTS("RESULTS"),
	UPDATE_CARDS("UPDATE_CARDS"),
	SWAP_CARD("SWAP_CARD"),
	CHANGE_CARD("CHANGE_CARD");

	Request(String t){
		type = t;
	}
	
	private String type;
	
	public String toString(){
		return type;
	}
}