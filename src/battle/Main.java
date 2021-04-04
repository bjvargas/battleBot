package battle;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException; 


public class Main {

	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException, LineUnavailableException, UnsupportedAudioFileException {	 
		
		Play classPlay = new Play();

		while(true) {

			classPlay.logAndPlay();
		}
	}
	
}
