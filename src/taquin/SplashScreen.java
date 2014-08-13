package taquin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.Timer;

public class  SplashScreen extends JWindow implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel label; 
	private JPanel panel;
	private Timer compteur;
	private Forme forme;
	private JProgressBar bar;

	public SplashScreen(Forme forme){
		this.forme=forme;
		this.setSize(400,387);
		this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		panel=new JPanel(new BorderLayout());
		label=new JLabel(new ImageIcon("Ressources/Images/jeux_taquin.png"));
		label.setVerticalAlignment(JLabel.CENTER);
		label.setHorizontalAlignment(JLabel.CENTER);
		panel.add(label,BorderLayout.NORTH);

		bar=new JProgressBar(0, 100);
		bar.setValue(0);
		bar.setStringPainted(true);
		panel.add(bar,BorderLayout.CENTER);

		this.setBackground(Color.black);
		this.getContentPane().add(panel);
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);
		this.pack();
		
		this.setVisible(true);
		Thread thread=new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(int i=0;i<=100;i++){
					bar.setValue(i);
					try{
						Thread.sleep(30);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();
		
		lancer();
	}

	public void lancer(){
		compteur=new Timer(0, this);
		compteur.setInitialDelay(3075);
		compteur.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		compteur.stop();
		this.setVisible(false);
		this.dispose();
		forme.setVisible(true);
	}

	/*@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d=(Graphics2D)g;
		System.out.println(this.getWidth()+":"+this.getHeight());

		try{
			Image img=ImageIO.read(new File("Ressources/Images/jeux_taquin.png"));
			System.out.println(img);
			g2d.drawImage(img,0,0,400,387,this);
		}catch(Exception e){}
	}*/



}
