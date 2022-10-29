package pixelArt;


import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStreamWriter;
import java.util.regex.*;

import javax.swing.*;


public class Main extends JFrame{
	
	Main() {		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new Board());
		setTitle("Pixel Art");
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Main();
			}
		});
	
	}

}
