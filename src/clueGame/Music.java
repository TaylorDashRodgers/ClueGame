package clueGame;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JOptionPane;

public class Music {

	
	public static void avengersTheme() {
		try
		{
			File MusicPath = new File("data/avgTheme.wav");
			
			AudioInputStream audioInput = AudioSystem.getAudioInputStream(MusicPath);
			Clip clip = AudioSystem.getClip();
			clip.open(audioInput);
			
			FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			volume.setValue(-15);
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
		}
	}
	

}
