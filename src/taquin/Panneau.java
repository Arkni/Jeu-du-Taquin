package taquin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Panneau extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Image img;
	private int nbCarreau,taille;
	private Carreaux[][] carreau;

	//
	private JPanel temps;
	private JLabel titreCompteur=new JLabel("Temps écoulé :");
	public static JLabel compteur=new JLabel("00:00");

	public Panneau(Image img,int nbCarreaux,int taille,Carreaux[][] carreau){
		this.img=img;
		this.nbCarreau=nbCarreaux;
		this.taille=taille;
		this.carreau=carreau;

		temps=new JPanel();
		temps.setLayout(new FlowLayout(FlowLayout.CENTER,15,3));

		Font police = new Font("Tahoma",Font.BOLD,11);
		titreCompteur.setFont(police);
		titreCompteur.setForeground(Color.BLUE);

		Font police1 = new Font("Tahoma",Font.BOLD,11);
		compteur.setFont(police1);
		compteur.setForeground(Color.RED);

		temps.add(titreCompteur);
		temps.add(Box.createHorizontalGlue());
		temps.add(compteur);

		this.setLayout(new BorderLayout());
		this.add(temps,BorderLayout.SOUTH);
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.GRAY);
		g.fillRect(0, 0,this.getWidth(),this.getHeight());

		if(img!=null){
			Carreaux carr=null;
			int x=0,y=0,xBlanc,yBlanc;
			for(int i=0;i<nbCarreau;i++)
				for(int j=0;j<nbCarreau;j++){
					carr=carreau[i][j];

					//les coordonn�es de la sous image dans la matrice des sous images
					x=carr.getNvEmplacement()%nbCarreau;
					y=carr.getNvEmplacement()/nbCarreau;

					//dessiner l'image
					g.drawImage(img,taille*(x+1),taille*(y+1),taille*(x+2),taille*(y+2),carr.getImgX(),carr.getImgY(),carr.getImgX1(),carr.getImgY1(),null);

					//dessiner les contour de l'image
					g.setColor(Color.white);
					g.drawRect(taille*(x+1),taille*(y+1),taille,taille);
				}

			//dessiner le carré blanc
			xBlanc=carreau[nbCarreau-1][nbCarreau-1].getNvEmplacement()%nbCarreau;
			yBlanc=carreau[nbCarreau-1][nbCarreau-1].getNvEmplacement()/nbCarreau;
			g.setColor(Color.WHITE);
			g.fillRect(taille*(xBlanc+1),taille*(yBlanc+1),taille,taille);

		}

	}//fin de paint

}