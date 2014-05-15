package com.github.lyokofirelyte.ONUW;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;

import com.github.lyokofirelyte.ONUW.Etc.ActionCommand;
import com.github.lyokofirelyte.ONUW.Etc.Card;
import com.github.lyokofirelyte.ONUW.Etc.EventHandler;
import com.github.lyokofirelyte.ONUW.Etc.Request;
import com.github.lyokofirelyte.ONUW.Etc.Utils;
import com.github.lyokofirelyte.ONUW.Gui.GUI;

import static com.github.lyokofirelyte.ONUW.Etc.Utils.*;

public class Listener implements ActionListener {
	
	public ONUW main;
	
	public Listener(ONUW i){
		main = i;
	}

	@EventHandler
	public void actionPerformed(ActionEvent e) {
		
		ActionCommand command = null;
		
		if (e.getActionCommand().startsWith("SPECIAL_MOVE")){
			
			playButton();
			
			switch (main.sd.getGameStatus()){
			
				case "initial": default: break;
				
				case "robber":
					
					String[] ab = e.getActionCommand().split("%%");
					up(Request.SWAP_CARD, main.gui.panel("game.slot " + ab[1]).getToolTipText() + "%%" + main.sd.getCards().get(main.sd.getUsername()).toString());
					up(Request.CHANGE_CARD, main.sd.getUsername() + "%%" + main.sd.getCards().get(main.gui.panel("game.slot " + ab[1]).getToolTipText()).toString());
					resetCardsToBlank();
					
				break;
				
				case "troublemaker":
					
					ab = e.getActionCommand().split("%%");
					
					if (main.sd.getTroubleMaker()){
						main.sd.setT(2, main.gui.panel("game.slot " + ab[1]).getToolTipText());
						up(Request.SWAP_CARD, main.sd.getT(1) + "%%" + main.sd.getCards().get(main.sd.getT(2)).toString());
						up(Request.SWAP_CARD, main.sd.getT(2) + "%%" + main.sd.getCards().get(main.sd.getT(1)).toString());
					} else {
						main.sd.setTroubleMaker(true);
						main.sd.setT(1, main.gui.panel("game.slot " + ab[1]).getToolTipText());
						main.gui.button("game.slot " + ab[1] + "button2").setEnabled(false);
					}
					
				break;
				
				case "seer":
					
					ab = e.getActionCommand().split("%%");
					main.gui.button("game.slot " + ab[1] + "button2").setEnabled(false);
					
					BufferedImage img;
						 				
					try {
						img = ImageIO.read(getClass().getClassLoader().getResource("card_" + main.sd.getCard(ab[0]).toString().toLowerCase() + ".jpg"));
						Image dimg = img.getScaledInstance(111, 110, Image.SCALE_SMOOTH);
						main.gui.label("game.slot " + ab[1] + "label").setIcon(new ImageIcon(dimg));
					} catch (IOException ee) {}
					
				break;
			}
			
			return;
		}
		
		if (e.getActionCommand().startsWith("ACCUSE")){
			String[] aa = e.getActionCommand().split("%%");
			playButton();
			up(Request.ACCUSE, main.gui.panel("game.slot " + aa[1]).getToolTipText() + "%%" + "bleh");
			for (int x = 1; x < 10; x++){
				main.gui.button("game.slot " + x + "button").setEnabled(false);
			}
			return;
		}
		
		try {
			command = ActionCommand.valueOf(e.getActionCommand());
		} catch (Exception ee){
			System.out.println("If this triggers, David dun fucked up!");
			return;
		}
		
		switch (command){
		
			default: break;
			
			case EXIT_BUTTON:
				
				System.exit(0);
					
			break;
			
			case LOGIN_BUTTON:
				
				playButton();
				connect();
				up(Request.AUTH, main.gui.hintTextField("name...").getText());
				main.sd.getAudio("lobby_music").start();
				
			break;
			
			case READY:

				playButton();
				
				if (!main.sd.isReady()){
					main.gui.label("system.status").setText("READY");
					main.sd.setReady(true);
					main.gui.button("ready").setEnabled(false);
					main.gui.button("not ready").setEnabled(false);
					main.gui.textPane("nexus.playertext").setText(main.gui.textPane().getText().replace("Player " + main.sd.getSlot() + " NOT READY", "Player " + main.sd.getSlot() + " READY        "));
					up(Request.READY, "bleh");
					
					main.sd.addTask("system.ready", new TimerTask() {
						public void run() {
							main.gui.button("not ready").setEnabled(true);
					  	}
					});
					
					new Timer().schedule(main.sd.getTask("system.ready"), 6000L);
				}
				
			break;
			
			case NOT_READY:
				
				playButton();
				
				if (main.sd.isReady()){
					main.gui.label("system.status").setText("NOT READY");
					main.sd.setReady(false);
					main.gui.button("not ready").setEnabled(false);
					main.gui.button("ready").setEnabled(false);
					main.gui.textPane("nexus.playertext").setText(main.gui.textPane().getText().replace("Player " + main.sd.getSlot() + " READY        ", "Player " + main.sd.getSlot() + " NOT READY"));
					up(Request.NOT_READY, "bleh");
					
					main.sd.addTask("system.notready", new TimerTask() {
						public void run() {
							main.gui.button("ready").setEnabled(true);
					  	}
					});
					
					new Timer().schedule(main.sd.getTask("system.notready"), 6000L);
				}
				
			break;
			
			case DEV_CONSOLE:
				
				if (main.sd.getDev()){
					main.gui.frame("dev").dispose();
					main.sd.setDevStatus("username");
				} else {
					main.gui.create(GUI.DEV);
				}
				
				playButton();
				
			break;
			
			case TEST_SOUND:
				
				main.sd.getAudio("lobby").stop();
				main.sd.getAudio("lobby").flush();
				
				Clip clip;
				
				try {
					clip = AudioSystem.getClip();
					AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("lobby.wav"));
					main.sd.setAudio("lobby", clip);
					main.sd.getAudio("lobby").open(audioIn);
				} catch (Exception eee) {}
				
				main.sd.getAudio("lobby").start();
				
			break;
			
			case CHAT_INPUT:
				
				if (main.gui.textField("dev.input").getText().equals("exit") || main.gui.hintPasswordField("dev.input2").getText().equals("exit")){
					main.gui.frame("dev").dispose();
					main.sd.setDevStatus("username");
					break;
				}
				
				switch (main.sd.devStatus()){
				
					case "username":
						
						if (main.sd.getUserCombos().containsKey(main.gui.textField("dev.input").getText())){
							main.sd.setDevUser(main.gui.textField("dev.input").getText());
							main.sd.setDevStatus("password");
							updateLine("Password:");
							main.gui.hintPasswordField("dev.input2").setEnabled(true);
							main.gui.hintPasswordField().setText("");
							main.gui.textField("dev.input").setEnabled(false);
							main.gui.textField().setText("");
						} else {
							updateLine("Invalid username. Try again.");
							main.gui.textField("dev.input").setText("");
						}
						
					break;
					
					case "password":
						
						if (main.sd.getUserCombos().get(main.sd.getDevUser()).equals(main.gui.hintPasswordField("dev.input2").getText())){
							main.sd.setDevStatus("idle");
							updateLine("Welcome. You may now enter commands.\nType 'commands' for a full list.");
							main.gui.textField("dev.input").setEnabled(true);
							main.gui.textField().setText("");
							main.gui.hintPasswordField("dev.input2").setEnabled(false);
							main.gui.hintPasswordField().hideHint();
							main.gui.frame("dev").setTitle(main.sd.getDevUser() + "@dev");
						} else {
							updateLine("Invalid password. Try again.");
							main.gui.hintPasswordField("dev.input2").setText("");
							main.gui.hintPasswordField().hideHint();
						}
						
					break;
					
					case "idle":
						
						switch (main.gui.textField("dev.input").getText()){
						
							case "start":
								up(Request.START, "bleh");
								updateLine("Starting...");
							break;
							
							case "list":
								
								updateLine("The following users are connected...");
								
								for (String s : main.sd.getUsers().keySet()){
									updateLine(s + ": Slot " + main.sd.getUsers().get(s));
								}
								
							break;
							
							case "commands":
								
								updateLine("Usable commands:");
								updateLine("start............starts a game");
								updateLine("list.............displays clients");
								updateLine("commands.........command list");
								updateLine("stop.............stop game (debug)");
								updateLine("exit.............close terminal");
								//updateLine("kick <username>..kicks a user");
								
							break;
							
							case "stop":
								
								up(Request.STOP, "bleh");
								updateLine("Stopping...");
								
							break;	
						}
						
						main.gui.textField("dev.input").setText("");
						
					break;
				}
				
			break;
		}
	}
	
	public void updateLine(String text){
		
		String[] amt = main.gui.textArea("dev.display").getText().split("\n");
		
		if (amt.length >= 23){
			String newAmt = "";
			int x = 0;
			for (String s : amt){
				if (x != 0){
					newAmt = newAmt + s + "\n";
				}
				x++;
			}
			main.gui.textArea().setText(newAmt + text);
		} else {
			main.gui.textArea("dev.display").setText(main.gui.textArea().getText() + "\n" + text);
		}
	}
	
	public void up(Request req, String msg){
		main.sd.getOut().println(req.toString() + "%%" + msg);
		main.sd.getOut().flush();
	}
	
	public void connect(){

		if (!main.sd.isConnected()){
		
			Socket socket = null;
			
			try {
				
				socket = new Socket(main.sd.getIp(), main.sd.getPort());
				main.sd.setSocket(socket);
				main.sd.setIn(new BufferedReader(new InputStreamReader(socket.getInputStream())));
				main.sd.setOut(new PrintWriter(socket.getOutputStream(), true));
				Utils.log("Connected");
				
			} catch (Exception e){
				popup("The server is currently offline.");
				System.exit(0);
			}
			
			if (!main.sd.isConnected()){
				try {
					listen();
				} catch (Exception e){}
			}
		}
	}
	
	public void listen() throws IOException {
		
		main.sd.setConnected(true);

		new Thread(new Runnable(){
			
			public void run(){
				
				while (true){
		
					String line = "";
					
					try {
						line = main.sd.getIn().readLine();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					String[] inFromServer = line.split("%%");
					Request req = Request.valueOf(inFromServer[0]);
						
					switch (req){
					
						case RESULTS:
							
							BufferedImage img;
							
					 		for (int z = 1; z < 10; z++){
								try {
									if (main.gui.panel("game.slot " + z).getToolTipText() != null){
										String un = main.gui.panel("game.slot " + z).getToolTipText();
										img = ImageIO.read(getClass().getClassLoader().getResource("card_" + main.sd.getCard(un).toString().toLowerCase() + ".jpg"));
										Image dimg = img.getScaledInstance(111, 110, Image.SCALE_SMOOTH);
										main.gui.label("game.slot " + z + "label").setIcon(new ImageIcon(dimg));
									}
								} catch (IOException ee) {}
					 		}
						
							String[] deadUsers = inFromServer[1].split("#");
							
							if (deadUsers[0].startsWith("none")){
								
								boolean nope = false;
								
								for (Card s : main.sd.getCards().values()){
									if (s.equals(Card.WEREWOLF)){
										nope = !nope;
										break;
									}
								}
								
								if (!nope){
									popup("Villagers win, no one died and there are no werewolves!");
								}
								
							} else {
								
							}
						
						break;
						
						case CHANGE_CARD:
							
							main.sd.getCards().put(inFromServer[1], Card.valueOf(inFromServer[2]));
							
						break;
						
					 	case UPDATE_CARDS:
					 		
					 		String[] userAssignments = inFromServer[1].split("#");

					 		for (String user2 : userAssignments){
					 			String[] ua = user2.split("@");
					 			if (ua[0].equals("center")){
					 				main.sd.getCenterCards().add(Card.valueOf(ua[1]));
					 			} else {
					 				main.sd.getCards().put(ua[0], Card.valueOf(ua[1]));
					 			}
					 		}
					 		
					 		if (main.sd.getGameStatus().equals("initial")){
					 			
						 		for (int z = 1; z < 10; z++){
						 			
						 			if (main.gui.panel("game.slot " + z).getToolTipText().equals(main.sd.getUsername())){
						 				
										try {
											img = ImageIO.read(getClass().getClassLoader().getResource("card_" + main.sd.getCard(main.sd.getUsername()).toString().toLowerCase() + ".jpg"));
										    Image dimg = img.getScaledInstance(111, 110, Image.SCALE_SMOOTH);
							 				main.gui.label("game.slot " + z + "label").setIcon(new ImageIcon(dimg));
										} catch (IOException e) {}
		
							 			break;
						 			}
						 		}
						 		
						 		begin();
					 		}
					 		
						break;
						
						case AUTH:
								
							if (inFromServer[1].equals("duplicate")){
								popup("That name already exists. Pick another one :)");
							} else if (inFromServer[1].equals("started")){
								popup("The game is already in progress, you'll have to wait!");
							} else if (inFromServer[1].equals("full")){
								popup("The game is full, sorry!");
							} else {
								main.sd.setUsername(main.gui.hintTextField("name...").getText());
								main.gui.frame().dispose();
								main.gui.create(GUI.MAIN);
								main.sd.setSlot(Integer.parseInt(inFromServer[2]));
								main.sd.getOut().println(Request.UPDATE_USER_LOBBY.toString());
								main.sd.getOut().flush();
							}
							
						break;
						
						case UPDATE_USER_LOBBY:
							
							Map<String, Integer> users = new HashMap<String, Integer>();
							
							for (int x = 0; x < inFromServer.length-1; x++){
								String[] slotGrab = inFromServer[x+1].split("#");
								users.put(slotGrab[0], Integer.parseInt(slotGrab[1]));
							}
							
							for (String user : users.keySet()){
								
								main.gui.panel("slot " + users.get(user));
								main.gui.panel().setBorder(BorderFactory.createTitledBorder(null, user, TitledBorder.CENTER, TitledBorder.TOP, new Font("Verdana", Font.BOLD, 14), Color.BLUE));

								try {
								    img = ImageIO.read(getClass().getClassLoader().getResource("slot" + users.get(user) + ".jpg"));
								    Image dimg = img.getScaledInstance(280, 110, Image.SCALE_SMOOTH);
								    main.gui.label("slot " + users.get(user) + "label").setIcon(new ImageIcon(dimg));
								} catch (Exception e) {}
							}
							
							main.sd.setUsers(users);
							
						break;
						
						case READY:
							
							String[] slotGrab = inFromServer[1].split("#");
							main.gui.textPane("nexus.playertext").setText(main.gui.textPane().getText().replace("Player " + slotGrab[1] + " NOT READY", "Player " + slotGrab[1] + " READY        "));
							main.gui.panel("slot " + slotGrab[1]);
							main.gui.panel().setBorder(BorderFactory.createTitledBorder(null, slotGrab[0], TitledBorder.CENTER, TitledBorder.TOP, new Font("Verdana", Font.BOLD, 14), Color.GREEN));
						
						break;
						
						case NOT_READY:
							
							slotGrab = inFromServer[1].split("#");
							main.gui.textPane("nexus.playertext").setText(main.gui.textPane().getText().replace("Player " + slotGrab[1] + " READY        ", "Player " + slotGrab[1] + " NOT READY"));
							main.gui.panel("slot " + slotGrab[1]);
							main.gui.panel().setBorder(BorderFactory.createTitledBorder(null, slotGrab[0], TitledBorder.CENTER, TitledBorder.TOP, new Font("Verdana", Font.BOLD, 14), Color.BLUE));
							
						break;
						
						case START:
							
							main.gui.frame("ONUW Lobby").dispose();
							freeAudio("lobby_music");
							main.gui.create(GUI.GAME);
							main.sd.setGameStatus("initial");
							
						break;
						
						default: break;
					}
				}
			}}).start();
	}
	
	public void begin(){
		
		startTimer("0", "10", "timer.1", "Werewolf Discovery", 0L, 1000L, 1);
		
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(getClass().getClassLoader().getResource("sleeping.jpg"));
		    Image dimg = img.getScaledInstance(480, 570, Image.SCALE_SMOOTH);
			main.gui.label("game.nexus.env.banner", new ImageIcon(dimg));
		} catch (IOException e) {}
		
		main.sd.getAudio("s1").start();
	}
	
	public void r1(){
		
		resetCardsToBlank();
 		main.sd.getAudio("s2").start();
 		
 		if (main.sd.getCard(main.sd.getUsername()).equals(Card.WEREWOLF)){
 	 		for (String user : main.sd.getCards().keySet()){
 	 			if (main.sd.getCard(user).equals(Card.WEREWOLF)){
 	 				BufferedImage img;
 					try {
 						img = ImageIO.read(getClass().getClassLoader().getResource("card_werewolf.jpg"));
 					    Image dimg = img.getScaledInstance(111, 110, Image.SCALE_SMOOTH);
 		 				main.gui.label("game.slot " + main.sd.getUsers().get(user) + "label").setIcon(new ImageIcon(dimg));
 					} catch (IOException e) {}
 	 			}
 	 		}
 		}

		startTimer("0", "10", "timer.2", "Minion Discovery", 1000L, 1000L, 2);
	}
	
	public void r2(){
		
		resetCardsToBlank();
		main.sd.getAudio("s3").start();
		
 		if (main.sd.getCard(main.sd.getUsername()).equals(Card.MINION)){
 	 		for (String user : main.sd.getCards().keySet()){
 	 			if (main.sd.getCard(user).equals(Card.MINION)){
 	 				BufferedImage img;
 					try {
 						img = ImageIO.read(getClass().getClassLoader().getResource("card_minion.jpg"));
 					    Image dimg = img.getScaledInstance(111, 110, Image.SCALE_SMOOTH);
 		 				main.gui.label("game.slot " + main.sd.getUsers().get(user) + "label").setIcon(new ImageIcon(dimg));
 					} catch (IOException e) {}
 	 			}
 	 		}
 		}
 		
 		startTimer("0", "10", "timer.3", "Robber Special Move", 1000L, 1000L, 3);
	}
	
	public void r3(){
		
		resetCardsToBlank();
		main.sd.getAudio("s4").start();

		if (main.sd.getCard(main.sd.getUsername()).equals(Card.ROBBER)){
			
	 		for (int z = 1; z < 10; z++){
	 			
	 			if (main.gui.panel("game.slot " + z).getToolTipText() != null && !main.gui.panel("game.slot " + z).getToolTipText().equals(main.sd.getUsername())){
	 				main.gui.button("game.slot " + z + "button2").setEnabled(true);
	 				main.gui.button().setText("SWITCH");
	 				main.sd.setGameStatus("robber");
	 			}
	 		}
		}
		
		startTimer("0", "25", "timer.4", "Mason Discovery", 1000L, 1000L, 4);
	}
	
	public void r4(){
		
		resetCardsToBlank();
		main.sd.getAudio("s5").start();
		
 		if (main.sd.getCard(main.sd.getUsername()).equals(Card.MASON)){
 	 		for (String user : main.sd.getCards().keySet()){
 	 			if (main.sd.getCard(user).equals(Card.MASON)){
 	 				BufferedImage img;
 					try {
 						img = ImageIO.read(getClass().getClassLoader().getResource("card_mason.jpg"));
 					    Image dimg = img.getScaledInstance(111, 110, Image.SCALE_SMOOTH);
 		 				main.gui.label("game.slot " + main.sd.getUsers().get(user) + "label").setIcon(new ImageIcon(dimg));
 					} catch (IOException e) {}
 	 			}
 	 		}
 		}
 		
 		startTimer("0", "10", "timer.5", "Troublemaker Special Move", 1000L, 1000L, 5);
	}
	
	public void r5(){
		
		resetCardsToBlank();
		main.sd.getAudio("s6").start();

		if (main.sd.getCard(main.sd.getUsername()).equals(Card.TROUBLEMAKER)){
			
	 		for (int z = 1; z < 10; z++){
	 			
	 			if (main.gui.panel("game.slot " + z).getToolTipText() != null && !main.gui.panel("game.slot " + z).getToolTipText().equals(main.sd.getUsername())){
	 				main.gui.button("game.slot " + z + "button2").setEnabled(true);
	 				main.gui.button().setText("SWITCH");
	 				main.sd.setGameStatus("troublemaker");
	 			}
	 		}
		}
		
		startTimer("0", "25", "timer.6", "Seer Special Move", 1000L, 1000L, 6);
	}
	
	public void r6(){
		
		resetCardsToBlank();
		main.sd.getAudio("s7").start();

		if (main.sd.getCard(main.sd.getUsername()).equals(Card.SEER)){
			
	 		for (int z = 1; z < 10; z++){
	 			
	 			if (main.gui.panel("game.slot " + z).getToolTipText() != null && !main.gui.panel("game.slot " + z).getToolTipText().equals(main.sd.getUsername())){
	 				main.gui.button("game.slot " + z + "button2").setEnabled(true);
	 				main.gui.button().setText("VIEW");
	 				main.sd.setGameStatus("seer");
	 			}
	 		}
		}
		
		startTimer("0", "10", "timer.7", "Insomniac Special Move", 1000L, 1000L, 7);
	}
	
	public void r7(){
		
		resetCardsToBlank();
		main.sd.getAudio("s8").start();
		
		if (main.sd.getCard(main.sd.getUsername()).equals(Card.INSOMNIAC)){
			
		}
		
		startTimer("0", "10", "timer.8", "SLEEP", 1000L, 1000L, 8);
	}
	
	public void r8(){
		
		resetCardsToBlank();
		main.sd.getAudio("s9").start();
		
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(getClass().getClassLoader().getResource("sleeping.jpg"));
		    Image dimg = img.getScaledInstance(480, 570, Image.SCALE_SMOOTH);
			main.gui.label("game.nexus.env.banner").setIcon(new ImageIcon(dimg));
		} catch (IOException e) {}
		
		startTimer("0", "20", "timer.9", "WAKE UP!", 1000L, 1000L, 9);
	}
	
	public void r9(){
		
		resetCardsToBlank();
		main.sd.getAudio("s10").start();
		
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(getClass().getClassLoader().getResource("library.jpg"));
		    Image dimg = img.getScaledInstance(480, 570, Image.SCALE_SMOOTH);
			main.gui.label("game.nexus.env.banner").setIcon(new ImageIcon(dimg));
		} catch (IOException e) {}
		
		startTimer("7", "10", "timer.10", "RESULTS", 1000L, 1000L, 10);
	}
	
	public void r10(){
		
		resetCardsToBlank();
		main.sd.getAudio("s11").start();
		
		for (int x = 1; x < 10; x++){
			main.gui.button("game.slot " + x + "button").setEnabled(true);
		}
	}
	
	public void resetCardsToBlank(){
 		for (int z = 1; z < 10; z++){
 			
 			if (main.gui.panel("game.slot " + z).getToolTipText().equals(main.sd.getUsername())){
 				
 				BufferedImage img;
 				
				try {
					img = ImageIO.read(getClass().getClassLoader().getResource("back.jpg"));
				    Image dimg = img.getScaledInstance(111, 110, Image.SCALE_SMOOTH);
	 				main.gui.label("game.slot " + z + "label").setIcon(new ImageIcon(dimg));
	 				main.gui.button("game.slot " + z + "button2").setEnabled(false);
	 				main.gui.button().setText("no selection");
				} catch (IOException e) {}

	 			break;
 			}
 		}
	}
	
	public void startTimer(String initMinutes, String initSeconds, final String taskName, final String nextStage, long taskDelay, long taskRepeat, final int nextRound){
		
		main.sd.setMinutes(initMinutes);
		main.sd.setSeconds(initSeconds);
		
		main.sd.addTask(taskName, new TimerTask() {
			public void run() {
				
				int seconds = Integer.parseInt(main.sd.getSeconds());
				int minutes = Integer.parseInt(main.sd.getMinutes());
				seconds--;
				main.sd.setSeconds(seconds + "");
				
				if (seconds <= 0){
					if (minutes > 0){
						main.sd.setMinutes((minutes-1) + "");
						main.sd.setSeconds((seconds + 60) + "");
						if (minutes >= 10){
							main.gui.label("game.nexus.info.time").setText("Time Remaining: " + (main.sd.getMinutes()+1) + ":00");
						} else {
							main.gui.label("game.nexus.info.time").setText("Time Remaining: 0" + (main.sd.getMinutes()+1) + ":00");
						}
					} else {
						main.gui.label("game.nexus.info.time").setText("ROUND COMPLETE");
						main.sd.getTask(taskName).cancel();
						main.gui.label("game.nexus.stage").setText("Current Stage: " + nextStage);
						freeAudio("s" + nextRound);
						switch (nextRound){ case 1: r1(); break; case 2: r2(); break; case 3: r3(); break; case 4: r4(); break; case 5: r5();  break; case 6: r6(); break; case 7: r7(); break; case 8: r8(); break; case 9: r9(); break; case 10: r10(); break; default: break;}
					}
				} else if (seconds <= 9){
					if (minutes >= 10){
						main.gui.label("game.nexus.info.time").setText("Time Remaining: " + main.sd.getMinutes() + ":0" + main.sd.getSeconds());
					} else {
						main.gui.label("game.nexus.info.time").setText("Time Remaining: 0" + main.sd.getMinutes() + ":0" + main.sd.getSeconds());
					}
				} else {
					if (minutes >= 10){
						main.gui.label("game.nexus.info.time").setText("Time Remaining: " + main.sd.getMinutes() + ":" + main.sd.getSeconds());
					} else {
						main.gui.label("game.nexus.info.time").setText("Time Remaining: 0" + main.sd.getMinutes() + ":" + main.sd.getSeconds());
					}
				}
		  	}
		});
		
		new Timer().scheduleAtFixedRate(main.sd.getTask(taskName), taskDelay, taskRepeat);
	}
	
	public void playButton(){
		
		main.sd.getAudio("button_hit").start();
		
		main.sd.addTask("system.button_reverb", new TimerTask() {
			public void run() {
				freeAudio("button_hit");
		  	}
		});
		
		new Timer().schedule(main.sd.getTask("system.button_reverb"), 600L);
		
		Clip clip;
		
		try {
			clip = AudioSystem.getClip();
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("button_hit.wav"));
			main.sd.setAudio("button_hit", clip);
			main.sd.getAudio("button_hit").open(audioIn);
		} catch (Exception eee) {}
	}
	
	public void freeAudio(String name){
		
		try {
			main.sd.getAudio(name).stop();
			main.sd.getAudio(name).flush();
		} catch (Exception e){}
	}
}