package com.github.lyokofirelyte.ONUW;

import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;
import javax.swing.UIManager;

import com.github.lyokofirelyte.ONUW.Gui.GUI;
import com.github.lyokofirelyte.ONUW.Gui.GuiManager;

import static com.github.lyokofirelyte.ONUW.Etc.Utils.*;

public class ONUW extends JFrame {

	private static final long serialVersionUID = System.currentTimeMillis() + 1337L;
	
	public SystemData sd;
	public GuiManager gui;
	public Listener listener;

	public static void main(String[] args){
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e){
			log("Error loading system themes - resorting to default.");
		}
		
		new ONUW();
	}
	
	public ONUW(){
		
		sd = new SystemData();
		gui = new GuiManager(this);
		listener = new Listener(this);
		sd.getUserCombos().put("root", "abc123");
		start();
	}
	
	public void start(){
		

		Clip clip;
		
		try {
			clip = AudioSystem.getClip();
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("lobby.wav"));
			sd.setAudio("lobby", clip);
			sd.getAudio("lobby").open(audioIn);
			
			clip = AudioSystem.getClip();
			audioIn = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("button_hit.wav"));
			sd.setAudio("button_hit", clip);
			sd.getAudio("button_hit").open(audioIn);
			
			clip = AudioSystem.getClip();
			audioIn = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("lobby_music.wav"));
			sd.setAudio("lobby_music", clip);
			sd.getAudio("lobby_music").open(audioIn);
		} catch (Exception e) {}
		
		sd.addTask("start", new TimerTask() {
			public void run() {
				gui.frame("warning").dispose();
				gui.create(GUI.LOGIN);
		  	}
		});
		
		new Timer().schedule(sd.getTask("start"), 8000L);

		gui.create(GUI.WARNING);
	}
}