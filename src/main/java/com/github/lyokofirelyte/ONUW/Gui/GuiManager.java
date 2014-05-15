package com.github.lyokofirelyte.ONUW.Gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.github.lyokofirelyte.ONUW.ONUW;
import com.github.lyokofirelyte.ONUW.Etc.ActionCommand;
import com.github.lyokofirelyte.ONUW.Etc.Utils;

public class GuiManager extends JFrame {

	private static final long serialVersionUID = 1L;
	private ONUW main;
	
	public GuiManager(ONUW i){
		main = i;
	}
	
	private Map<String, Object> local = new HashMap<String, Object>();
	private Map<currType, String> current = new HashMap<currType, String>();
	
	public void create(GUI gui){
		
		switch (gui){
		
			case WARNING:
			
				frame("warning").setUndecorated(true);
				frame();//.setPreferredSize(new Dimension(900, 40));
				frame().setLayout(new FlowLayout());
				frame().setResizable(false);
				frame().setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("icon.png")));
				
				textArea("warning.status");//.setPreferredSize(new Dimension(900, 40));
				textArea().setText("You may want to adjust your volume to be lower\nbefore logging in.");
				textArea().setFont(new Font("Verdana", Font.BOLD, 20));
				textArea().setEnabled(false);
				textArea().setOpaque(false);
				
				panel("warning.main").add(textArea());
				panel().setBorder(BorderFactory.createTitledBorder(null, "warning", TitledBorder.LEFT, TitledBorder.TOP, new Font("Verdana", Font.ITALIC, 10), Color.RED));
				
				frame().add(panel());
				frame().setLocationRelativeTo(null);
				frame().setVisible(true);
				frame().pack();
			
			break;
		
			case LOGIN:
				
				frame("ONUW v1").setLayout(new BorderLayout());
				frame().setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("icon.png")));
				
				BufferedImage img2 = null;
				
				try {
				    img2 = ImageIO.read(getClass().getClassLoader().getResource("bg.jpg"));
				    Image dimg2 = img2.getScaledInstance(500, 300, Image.SCALE_SMOOTH);
					label("login.bg", new ImageIcon(dimg2));
					label().setLayout(new FlowLayout(0, 100, 20));
				} catch (Exception e) {}
				
				try {
				    img2 = ImageIO.read(getClass().getClassLoader().getResource("banner.jpg"));
				    Image dimg2 = img2.getScaledInstance(310, 100, Image.SCALE_SMOOTH);
					label("login.bgtop", new ImageIcon(dimg2));
					label("login.bg").add(label("login.bgtop"));
					label("login.bg");
				} catch (Exception e) {}
				
				panel("selection.name");
				panel().setLayout(new GridLayout(2, 0));
				panel().setBorder(BorderFactory.createTitledBorder(null, "choose name", TitledBorder.LEFT, TitledBorder.TOP, new Font("Verdana", Font.BOLD, 12), Color.DARK_GRAY));
				
				hintTextField("name...");
				hintTextField().setHint("name...");
				panel().add(hintTextField());
				
				panel("selection.layout");
				panel().setLayout(new GridLayout(0, 2));
				panel().setPreferredSize(new Dimension(300, 30));
				
				button("Enter");
				button().setFont(new Font("Verdana", Font.CENTER_BASELINE, 12));
				button().addActionListener(main.listener);
				button().setActionCommand(ActionCommand.LOGIN_BUTTON.toString());
				panel().add(button());
				
				button("Exit").setEnabled(true);
				button().setFont(new Font("Verdana", Font.CENTER_BASELINE, 12));
				button().addActionListener(main.listener);
				button().setActionCommand(ActionCommand.EXIT_BUTTON.toString());
				panel().add(button());

				panel("selection.name").add(panel("selection.layout"));
				label().add(panel("selection.name"));
				frame().setLocationRelativeTo(null);
				frame().add(label());
				frame().setVisible(true);
				frame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				frame().pack();
				
			break;
				
			case MAIN:
				
				frame("ONUW Lobby");
				main.sd.getAudio("lobby").start();
				frame().setLayout(new BorderLayout());
				frame().setResizable(false);
				frame().setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("icon.png")));
				
				panel("nexus").setLayout(new FlowLayout(0, 0, 0));
				panel().setPreferredSize(new Dimension(900, 705));
				
				panel("system.info").setLayout(new FlowLayout(0, 200, 0));
				panel().setPreferredSize(new Dimension(900, 40));
				panel().setBorder(BorderFactory.createTitledBorder("system info"));
				
				label(main.sd.getUsername());
				panel().add(label());
				
				label("system.time");
				label().setText(Utils.getTime(System.currentTimeMillis()));
				panel().add(label());
				
				label("system.status");
				label().setText("NOT READY");
				label().setFont(new Font("Verdana", Font.BOLD, 10));
				panel().add(label());
				
				panel("nexus").add(panel("system.info"));
				
				panel("nexus.banner").setLayout(new FlowLayout(0, 50, 0));
				panel().setBorder(BorderFactory.createTitledBorder(""));
	
				BufferedImage img = null;
				
				try {
				    img = ImageIO.read(getClass().getClassLoader().getResource("back.jpg"));
				    Image dimg = img.getScaledInstance(100, 110, Image.SCALE_SMOOTH);
					label("leftbanner", new ImageIcon(dimg));
					panel().add(label());
					
					label("banner", new ImageIcon(getClass().getClassLoader().getResource("banner.jpg")));
					panel().add(label());
					
					label("rightbanner", new ImageIcon(dimg));
					panel().add(label());
				} catch (Exception e) {}

				panel("nexus").add(panel("nexus.banner"));
				
				for (int x = 1; x < 10; x++){
					panel("slot " + x);//.setLayout(new FlowLayout(0, 0, 0));
					panel().setPreferredSize(new Dimension(300, 140));
					panel().setBorder(BorderFactory.createTitledBorder("Slot " + x));
					
					img = null;
					
					try {
					    img = ImageIO.read(getClass().getClassLoader().getResource("open.gif"));
					    Image dimg = img.getScaledInstance(280, 110, Image.SCALE_SMOOTH);
						label("slot " + x + "label", new ImageIcon(dimg));
						panel().add(label());
					} catch (Exception e) {}

					panel("nexus").add(panel("slot " + x));
				}

				textPane("nexus.playertext").setPreferredSize(new Dimension(900, 70));
				textPane().setFont(new Font("Verdana", Font.BOLD, 10));
				textPane().setEnabled(false);
				int x = 1;
				
				for (int y = 1; y < 4; y++){
					textPane().setText(textPane().getText() + "\nPlayer " + x + " NOT READY          Player " + (x+1) + " NOT READY           Player " + (x+2) + " NOT READY");
					x += 3;
				}

				StyledDocument doc = textPane().getStyledDocument();
				SimpleAttributeSet center = new SimpleAttributeSet();
				StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
				doc.setParagraphAttributes(0, doc.getLength(), center, false);
				
				panel("nexus.buttons");
				panel().setLayout(new FlowLayout(0, 50, 0));
				
				button("ready").setPreferredSize(new Dimension(120, 25));
				button().setFont(new Font("Verdana", Font.BOLD, 12));
				button().addActionListener(main.listener);
				button().setActionCommand(ActionCommand.READY.toString());
				panel().add(button());
				
				button("not ready").setPreferredSize(new Dimension(120, 25));
				button().setFont(new Font("Verdana", Font.BOLD, 12));
				button().addActionListener(main.listener);
				button().setActionCommand(ActionCommand.NOT_READY.toString());
				panel().add(button());

				button("exit").setPreferredSize(new Dimension(120, 25));
				button().setFont(new Font("Verdana", Font.BOLD, 12));
				button().addActionListener(main.listener);
				button().setActionCommand(ActionCommand.EXIT_BUTTON.toString());
				panel().add(button());
				
				button("test sound").setPreferredSize(new Dimension(120, 25));
				button().setFont(new Font("Verdana", Font.ITALIC, 12));
				button().addActionListener(main.listener);
				button().setActionCommand(ActionCommand.TEST_SOUND.toString());
				panel().add(button());
				
				button("dev console").setPreferredSize(new Dimension(120, 25));
				button().setFont(new Font("Verdana", Font.TRUETYPE_FONT, 12));
				button().addActionListener(main.listener);
				button().setActionCommand(ActionCommand.DEV_CONSOLE.toString());
				panel().add(button());

				panel("nexus").add(textPane());
				panel().add(panel("nexus.buttons"));
				frame("ONUW Lobby").add(panel("nexus"));

				frame().setLocationRelativeTo(null);
				frame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame().setPreferredSize(new Dimension(910, 730));
				frame().pack();
				frame().setTitle("ONUW");
				frame().setVisible(true);
				
				main.sd.addTask("system.time", new TimerTask() {
					public void run() {
				    	updateTime();
				  	}
				});
				
				new Timer().scheduleAtFixedRate(main.sd.getTask("system.time"), 0L, 60000L);
				
			break;
			
			case GAME:
				
				try {
					handleAudio();
				} catch (Exception e){}
				
				frame("game.main");
				frame().setPreferredSize(new Dimension(1100, 700));
				frame().setTitle("ONUW Game");
				frame().setLayout(new FlowLayout(0, -3, 0));
				frame().setResizable(false);
				frame().setLocationRelativeTo(null);
				frame().setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("icon.png")));
				
				panel("game.nexus");//.setLayout(new GridLayout(3, 3));
				panel().setPreferredSize(new Dimension(590, 612));
				
				panel("game.nexus.info").setLayout(new FlowLayout(0, 250, 0));
				panel().setPreferredSize(new Dimension(1120, 50));
				panel().setBorder(BorderFactory.createTitledBorder(null, "game info", TitledBorder.CENTER, TitledBorder.TOP, new Font("Verdana", Font.BOLD, 12), Color.MAGENTA));
				
				label("game.nexus.stage");
				label().setText("Current Stage: Card Discovery");
				label().setFont(new Font("Verdana", Font.BOLD, 11));
				panel().add(label());
				
				label("game.nexus.info.time");
				label().setText("Time Remaining: 00:20");
				label().setFont(new Font("Verdana", Font.BOLD, 11));
				panel().add(label());
				
				for (int y = 1; y < 10; y++){
					
					panel("game.slot " + y);
					panel().setPreferredSize(new Dimension(180, 200));
					
					for (String s : main.sd.getUsers().keySet()){
						if (main.sd.getUsers().get(s) == y){
							panel().setBorder(BorderFactory.createTitledBorder(null, s, TitledBorder.CENTER, TitledBorder.TOP, new Font("Verdana", Font.BOLD, 13), Color.BLUE));
							panel().setToolTipText(s);
							break;
						}
					}
					
					if (panel().getToolTipText() == null){
						panel().setBorder(BorderFactory.createTitledBorder(null, "empty", TitledBorder.LEFT, TitledBorder.TOP, new Font("Verdana", Font.ITALIC, 10), Color.DARK_GRAY));
					}

					img = null;
					
					try {
					    img = ImageIO.read(getClass().getClassLoader().getResource("back.jpg"));
					    Image dimg = img.getScaledInstance(111, 110, Image.SCALE_SMOOTH);
						label("game.slot " + y + "label", new ImageIcon(dimg));
						panel().add(label());
						
						button("game.slot " + y + "button");
						button().setPreferredSize(new Dimension(115, 25));
						button().setText("Accuse (final)");
						button().setEnabled(false);
						button().addActionListener(main.listener);
						button().setActionCommand(ActionCommand.ACCUSE.toString() + "%%" + y);
						panel().add(button());
						
						button("game.slot " + y + "button2");
						button().setPreferredSize(new Dimension(115, 25));
						button().setText("no selection");
						button().setEnabled(false);
						button().addActionListener(main.listener);
						button().setActionCommand(ActionCommand.SPECIAL_MOVE.toString() + "%%" + y);
						panel().add(button());
						
						//panel().setBorder(BorderFactory.createTitledBorder(null, "void", TitledBorder.CENTER, TitledBorder.TOP, new Font("Verdana", Font.BOLD, 12), Color.BLUE));
					} catch (Exception e) {}

					panel("game.nexus").add(panel("game.slot " + y));
				}
				
				panel("game.nexus.env").setPreferredSize(new Dimension(500, 600));
				panel().setLayout(new FlowLayout());
				
				try {
					img = ImageIO.read(getClass().getClassLoader().getResource("startpic.jpg"));
				    Image dimg = img.getScaledInstance(480, 570, Image.SCALE_SMOOTH);
					label("game.nexus.env.banner", new ImageIcon(dimg));
				} catch (IOException e) {}

				panel().add(label());
				panel().setBorder(BorderFactory.createTitledBorder(null, "environment", TitledBorder.CENTER, TitledBorder.TOP, new Font("Verdana", Font.BOLD, 12), Color.DARK_GRAY));
				
				frame("game.main").add(panel("game.nexus.info"));
				frame().add(panel("game.nexus"));
				frame().add(panel("game.nexus.env"));
				frame().pack();
				frame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame().setVisible(true);
				
			break;
			
			case DEV:
				
				frame("dev");//.setPreferredSize(new Dimension(500, 500));
				frame().setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("icon.png")));
				
				panel("dev.nexus").setPreferredSize(new Dimension(490, 510));
				panel().setLayout(new FlowLayout(0, 0, 0));
				
				textArea("dev.display").setPreferredSize(new Dimension(490, 430));
				textArea().setEnabled(false);
				textArea().setBorder(BorderFactory.createSoftBevelBorder(1, Color.GRAY, Color.GRAY));
				
				textField("dev.input").setPreferredSize(new Dimension(490, 40));
				textField().setBorder(BorderFactory.createTitledBorder("input"));
				textField().setText("");
				textField().addActionListener(main.listener);
				textField().setActionCommand(ActionCommand.CHAT_INPUT.toString());
				
				hintPasswordField("dev.input2").setPreferredSize(new Dimension(490, 40));
				hintPasswordField().setBorder(BorderFactory.createTitledBorder("authorization input"));
				hintPasswordField().setText("");
				hintPasswordField().setEnabled(false);
				hintPasswordField().addActionListener(main.listener);
				hintPasswordField().setActionCommand(ActionCommand.CHAT_INPUT.toString());
				
				main.sd.addTask("dev.intro", new TimerTask() {
					public void run() {
						textArea("dev.display").setText("ONUW Development Console v1 Initalizing.");
				  	}
				});
				
				main.sd.addTask("dev.intro2", new TimerTask() {
					public void run() {
						textArea("dev.display").setText(textArea().getText() + "\nConnected. Type 'exit' at any time to leave.");
				  	}
				});
				
				main.sd.addTask("dev.intro3", new TimerTask() {
					public void run() {
						textArea("dev.display").setText(textArea().getText() + "\nPlease type an authorized username.");
				  	}
				});
				
				new Timer().schedule(main.sd.getTask("dev.intro"), 1000L);
				new Timer().schedule(main.sd.getTask("dev.intro2"), 2000L);
				new Timer().schedule(main.sd.getTask("dev.intro3"), 3000L);
				
				panel().add(textArea());
				panel().add(textField("dev.input"));
				panel().add(hintPasswordField("dev.input2"));
				
				frame().add(panel());
				frame().setLocationRelativeTo(null);
				frame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				frame().setResizable(false);
				frame().pack();
				frame().setVisible(true);
				
			break;
				
			default: break;
		}
	}
	
	public void handleAudio() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
		
		for (int x = 1; x < 12; x++){
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("s" + x + ".wav"));
			Clip clip = AudioSystem.getClip();
			main.sd.setAudio("s" + x, clip);
			main.sd.getAudio("s" + x).open(audioIn);
		}
	}
	
	public void updateTime(){
		
		try {
			label("system.time").setText(Utils.getTime(System.currentTimeMillis()));
		} catch (Exception e){
			main.sd.getTask("system.time").cancel();
		}
	}
	
	public JFrame frame(String title){
		
		current.put(currType.FRAME, title);
		if (local.containsKey(title + "FRAME")){
			return (JFrame) local.get(title + "FRAME");
		}
		JFrame a = new JFrame(title);
		local.put(title + "FRAME", a);
		return a;
	}
	
	public JPanel panel(String name){
		
		current.put(currType.PANEL, name);
		if (local.containsKey(name + "PANEL")){
			return (JPanel) local.get(name + "PANEL");
		}
		JPanel a = new JPanel();
		local.put(name + "PANEL", a);
		return a;
	}
	
	public JRadioButton radioButton(String title){
		
		current.put(currType.RADIO, title);
		if (local.containsKey(title + "RADIO_BUTTON")){
			return (JRadioButton) local.get(title + "RADIO_BUTTON");
		}
		JRadioButton a = new JRadioButton(title);
		local.put(title + "RADIO_BUTTON", a);
		return a;
	}
	
	public JButton button(String text){
		
		current.put(currType.BUTTON, text);
		if (local.containsKey(text + "BUTTON")){
			return (JButton) local.get(text + "BUTTON");
		}
		JButton a = new JButton(text);
		local.put(text + "BUTTON", a);
		return a;
	}
	
	public JTextField textField(String text){
		
		current.put(currType.TEXT_FIELD, text);
		if (local.containsKey(text + "TEXT_FIELD")){
			return (JTextField) local.get(text + "TEXT_FIELD");
		}
		JTextField a = new JTextField(text);
		local.put(text + "TEXT_FIELD", a);
		return a;
	}
	
	public BGPanel textArea(String text){
		
		current.put(currType.TEXT_AREA, text);
		if (local.containsKey(text + "TEXT_AREA")){
			return (BGPanel) local.get(text + "TEXT_AREA");
		}
		BGPanel a = new BGPanel(text);
		local.put(text + "TEXT_AREA", a);
		return a;
	}
	
	public HintTextField hintTextField(String text){
		
		current.put(currType.HINT_TEXT_FIELD, text);
		if (local.containsKey(text + "HINT_TEXT_FIELD")){
			return (HintTextField) local.get(text + "HINT_TEXT_FIELD");
		}
		HintTextField a = new HintTextField(text);
		local.put(text + "HINT_TEXT_FIELD", a);
		return a;
	}
	
	public HintPasswordField hintPasswordField(String text){
		
		current.put(currType.HINT_PASSWORD_FIELD, text);
		if (local.containsKey(text + "HINT_PASS_FIELD")){
			return (HintPasswordField) local.get(text + "HINT_PASS_FIELD");
		}
		HintPasswordField a = new HintPasswordField(text);
		local.put(text + "HINT_PASS_FIELD", a);
		return a;
	}
	
	public JTextPane textPane(String text){
		
		current.put(currType.TEXT_PANE, text);
		if (local.containsKey(text + "TEXT_PANE")){
			return (JTextPane) local.get(text + "TEXT_PANE");
		}
		JTextPane a = new JTextPane();
		local.put(text + "TEXT_PANE", a);
		return a;
	}
	
	public JLabel label(String text){
		
		current.put(currType.LABEL, text);
		if (local.containsKey(text + "LABEL")){
			return (JLabel) local.get(text + "LABEL");
		}
		JLabel a = new JLabel(text);
		local.put(text + "LABEL", a);
		return a;
	}
	
	public JLabel label(String text, ImageIcon icon){
		
		current.put(currType.LABEL, text);
		if (local.containsKey(text + "LABEL")){
			return (JLabel) local.get(text + "LABEL");
		}
		JLabel a = new JLabel(icon);
		local.put(text + "LABEL", a);
		return a;
	}
	
	public JLabel label(String text, int swing){
		
		current.put(currType.LABEL, text);
		if (local.containsKey(text + "LABEL")){
			return (JLabel) local.get(text + "LABEL");
		}
		JLabel a = new JLabel(text, swing);
		local.put(text + "LABEL", a);
		return a;
	}
	
	public JLabel label(){
		return label(current.get(currType.LABEL));
	}
	
	public JFrame frame(){
		return frame(current.get(currType.FRAME));
	}
	
	public JRadioButton radioButton(){
		return radioButton(current.get(currType.RADIO));
	}
	
	public JPanel panel(){
		return panel(current.get(currType.PANEL));
	}
	
	public JButton button(){
		return button(current.get(currType.BUTTON));
	}
	
	public JTextField textField(){
		return textField(current.get(currType.TEXT_FIELD));
	}
	
	public JTextPane textPane(){
		return textPane(current.get(currType.TEXT_PANE));
	}
	
	public BGPanel textArea(){
		return textArea(current.get(currType.TEXT_AREA));
	}
	
	public HintTextField hintTextField(){
		return hintTextField(current.get(currType.HINT_TEXT_FIELD));
	}
	
	public HintPasswordField hintPasswordField(){
		return hintPasswordField(current.get(currType.HINT_PASSWORD_FIELD));
	}
	
	enum currType {
		
		FRAME(0),
		PANEL(1),
		BUTTON(2),
		HINT_TEXT_FIELD(3),
		HINT_PASSWORD_FIELD(4),
		TEXT_FIELD(5),
		LABEL(6),
		TEXT_AREA(7),
		RADIO(8),
		TEXT_PANE(9);
		
		private currType(int t){
			type = t;
		}
		
		private int toInt(){
			return type;
		}
		
		private int type;
	}
}
