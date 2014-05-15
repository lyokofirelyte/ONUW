package com.github.lyokofirelyte.ONUW.Gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTextArea;

public class BGPanel extends JTextArea {

	private static final long serialVersionUID = System.currentTimeMillis();
	
	private String name = "";
	private Map<String, Image> imageAssign = new HashMap<String, Image>();
	private boolean fucked = false;
	
	public BGPanel(String name){
		this.name = name;
		imageAssign.put("chat.general.text", Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("panel1bg.png")));
	}
	
	/*@Override
	public void paintComponent(Graphics g){
		
		if (imageAssign.containsKey(name) && !fucked){
			g.drawImage(imageAssign.get(name), 0, 0, null);
			//fucked = !fucked;
		}

	}*/
	
	public String getName(){
		return name;
	}
}