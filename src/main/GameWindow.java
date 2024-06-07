package main;
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
	}
}
