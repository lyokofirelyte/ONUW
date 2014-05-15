package com.github.lyokofirelyte.ONUW.Gui;

public enum GUI {
	
	MAIN(0),
	LOGIN(1),
	DEV(2),
	GAME(3),
	WARNING(4);
	
	GUI(int guiType){
		gui = guiType;
	}
	
	public int toInt(){
		return gui;
	}
	
	private int gui;
}
