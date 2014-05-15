package com.github.lyokofirelyte.ONUW.Etc;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Utils {

	public static void log(String a){
		System.out.println(a);
	}
	
	public static void popup(String message){
		JOptionPane.showMessageDialog(new JFrame(), message, "Information", JOptionPane.OK_OPTION);
	}
	
    public static String encrypt(String toEncrypt, String type){ 
    	
        try { 
            MessageDigest digest = MessageDigest.getInstance(type);               
            digest.update(toEncrypt.getBytes()); 
            byte[] bytes = digest.digest();       

            StringBuilder sb = new StringBuilder(); 

            for (int i = 0; i < bytes.length; i++) { 
                sb.append(String.format("%02X", bytes[i])); 
            } 

            return sb.toString().toLowerCase();
        } 
        catch (Exception exc) { return null; }
    }
    
	public static String getTime(Long l) {
		Calendar cal = Calendar.getInstance();
	  	cal.setTimeInMillis(l);
	  	SimpleDateFormat sdf = new SimpleDateFormat("EEEEE, MMMMM dd, H:mm");
	  	return ( sdf.format(cal.getTime()) );
	}
}
