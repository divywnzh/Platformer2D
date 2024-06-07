package main;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

public class GameWindow {
	private JFrame jframe;
	public GameWindow(GamePanel gamePanel) {
		jframe = new JFrame();
				
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jframe.add(gamePanel);//attaching panel to the frame
		jframe.setLocationRelativeTo(null);
		
		jframe.setResizable(false);
		jframe.pack();//look at the component -> make the window big enough to fit that
		
		jframe.setVisible(true);
		jframe.addWindowFocusListener(new WindowFocusListener() {
			
			@Override
			public void windowLostFocus(WindowEvent e) {
				//handling changing windows for keyboard inputs etc
				gamePanel.getGame().windowFocusLost();
				System.out.println(" Lost Focus ");
				
			}
			
			@Override
			public void windowGainedFocus(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
