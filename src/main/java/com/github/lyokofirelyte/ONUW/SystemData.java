package com.github.lyokofirelyte.ONUW;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import javax.sound.sampled.Clip;
import com.github.lyokofirelyte.ONUW.Etc.Card;

public class SystemData {

	private float version = 1;
	private int port = 22001;
	private int slot = 0;
	private String minutes = "00";
	private String seconds = "00";
	private String ip = "144.76.184.51";
	private String t1 = "";
	private String t2 = "";
	private Map<String, String> userCombos = new HashMap<String, String>();
	private String devStatus = "username";
	private String devUser = "none";
	public String gameStatus = "none";
	private static String storedUsername = "none";
	private String storedPassword = "none";
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private Map<String, Clip> audio = new HashMap<String, Clip>();
	private boolean connected = false;
	private boolean saved = false;
	private boolean ready = false;
	private boolean dev = false;
	private boolean troubleMaker = false;
	private Map<String, TimerTask> tasks = new HashMap<String, TimerTask>();
	private Map<String, Integer> users = new HashMap<String, Integer>();
	private Map<String, Card> cards = new HashMap<String, Card>();
	private List<Card> centerCards = new ArrayList<Card>();
	
	public void setT(int t, String value){
		if (t == 1){
			t1 = value;
		} else {
			t2 = value;
		}
	}
	
	public String getT(int t){
		if (t == 1){
			return t1;
		}
		return t2;
	}
	
	public void setTroubleMaker(boolean a){
		troubleMaker = a;
	}
	
	public boolean getTroubleMaker(){
		return troubleMaker;
	}
	
	public String getMinutes(){
		return minutes;
	}
	
	public void setMinutes(String a){
		minutes = a;
	}
	
	public String getSeconds(){
		return seconds;	
	}
	
	public void setSeconds(String a){
		seconds = a;
	}
	
	public List<Card> getCenterCards(){
		return centerCards;
	}
	
	public void setUsers(Map<String, Integer> a){
		users = a;
	}
	
	public void setGameStatus(String name){
		gameStatus = name;
	}
	
	public String getGameStatus(){
		return gameStatus;
	}
	
	public Card getCard(String name){
		return cards.get(name);
	}
	
	public void setCard(String name, Card card){
		cards.put(name, card);
	}
	
	public Map<String, Card> getCards(){
		return cards;
	}
	
	public Map<String, Integer> getUsers(){
		return users;
	}
	
	public void setDevUser(String a){
		devUser = a;
	}
	
	public String getDevUser(){
		return devUser;
	}
	
	public Map<String, String> getUserCombos(){
		return userCombos;
	}
	
	public void setDevStatus(String a){
		devStatus = a;
	}
	
	public String devStatus(){
		return devStatus;
	}
	
	public void setSlot(int a){
		slot = a;
	}
	
	public void setDev(boolean a){
		dev = a;
	}
	
	public boolean getDev(){
		return dev;
	}
	
	public Integer getSlot(){
		return slot;
	}
	
	public TimerTask getTask(String task){
		return tasks.get(task);
	}
	
	public Map<String, TimerTask> getTasks(){
		return tasks;
	}
	
	public void addTask(String taskName, TimerTask task){
		tasks.put(taskName, task);
	}
	
	public void remTask(String task){
		tasks.remove(task);
	}
	
	public float getVersion(){
		return version;
	}
	
	public int getPort(){
		return port;
	}
	
	public String getUsername(){
		return storedUsername;
	}
	
	public String getPassword(){
		return storedPassword;
	}
	
	public String getIp(){
		return ip;
	}
	
	public Socket getSocket(){
		return socket;
	}
	
	public BufferedReader getIn(){
		return in;
	}
	
	public PrintWriter getOut(){
		return out;
	}
	
	public Clip getAudio(String a){
		return audio.get(a);
	}
	
	public boolean isConnected(){
		return connected;
	}
	
	public boolean isSaved(){
		return saved;
	}
	
	public boolean isReady(){
		return ready;
	}
	
	public void setReady(boolean a){
		ready = a;
	}
	
	public void setVersion(float a){
		version = a;
	}

	public void setPort(int a){
		port = a;
	}
	
	public void setUsername(String a){
		storedUsername = a;
	}
	
	public void setPassword(String a){
		storedPassword = a;
	}
	
	public void setIp(String a){
		ip = a;
	}
	
	public void setSocket(Socket a){
		socket = a;
	}
	
	public void setIn(BufferedReader a){
		in = a;
	}
	
	public void setOut(PrintWriter a){
		out = a;
	}
	
	public void setAudio(String a, Clip b){
		audio.put(a, b);
	}
	
	public void setConnected(boolean a){
		connected = a;
	}
	
	public void setSaved(boolean a){
		saved = a;
	}
}