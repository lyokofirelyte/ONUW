package com.github.lyokofirelyte.ONUW.Etc;

public enum ActionCommand {

	LOGIN_BUTTON("LOGIN_BUTTON"),
	EXIT_BUTTON("EXIT_BUTTON"),
	DEV_CONSOLE("DEV_CONSOLE"),
	TYPING_WA_USERNAME("TYPING_WA_USERNAME"),
	TYPING_WA_PASSWORD("TYPING_WA_PASSWORD"),
	CHAT_INPUT("CHAT_INPUT"),
	READY("READY"),
	NOT_READY("NOT_READY"),
	ACCUSE("ACCUSE"),
	TEST_SOUND("TEST_SOUND"),
	SPECIAL_MOVE("SPECIAL_MOVE");
	
	ActionCommand(String t){
		type = t;
	}
	
	private String type;
	
	public String toString(){
		return type;
	}
}
