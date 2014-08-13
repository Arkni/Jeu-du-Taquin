package taquin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;


abstract public class Compteur implements ActionListener{


	private static Timer timer;  //le timer

	private static int secondes; //les secondes
	private static int minutes;  //les minutes

	private static int tempsEcouleSecondes; //pour enregistrer les secondes
	private static int tempsEcouleMinutes;  //pour enregistrer les minutes
	
	private static int a=0;
	private static Clip clip=null;

	//le constructeur
	private Compteur(){}

	//la m�thode qui compte le temps
	public static void compter(final int lastSecond,final int lastMinute){
		secondes=lastSecond;
		minutes=lastMinute;
		ActionListener compterTemps=new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				boolean rep=verifier();
				if(rep){
					pauseTime();
					return;
				}

				if(secondes >= 59){
					secondes = 0;
					minutes++;
					if(minutes >= 59){
						minutes = 0;
						secondes = 0;
					}
				}
				else{
					secondes++;
				}
				//l'affichage du temps
				if(secondes<10 && minutes<10)
					Panneau.compteur.setText("0"+minutes+":0"+secondes);
				else if(secondes<10 && minutes>=10)
					Panneau.compteur.setText(minutes+":0"+secondes);
				else if(secondes>=10 && minutes<10)
					Panneau.compteur.setText("0"+minutes+":"+secondes);
				else
					Panneau.compteur.setText(minutes+":"+secondes);

			}
		};
		timer=new Timer(1000,compterTemps);
		timer.start();		

	}//la fin de la m�thode compteur	

	//pour arr�ter le compteur
	public static void pauseTime(){
		if(timer.isRunning()){
			Compteur.timer.stop();
			tempsEcouleSecondes=secondes;
			tempsEcouleMinutes=minutes;
		}
	}

	//get les secondes
	public static int getSecondes(){
		return secondes;
	}

	//get les minutes
	public static int getMinutes(){
		return minutes;
	}

	//get le temps �coul� en secondes
	public static int getTempEcouleSecondes(){
		return tempsEcouleSecondes;
	}

	//get le temps �coul� en minutes
	public static int getTempEcouleMinutes(){
		return tempsEcouleMinutes;
	}

	//la fonction v�rifier qui v�rifier le temps
	public static boolean verifier(){
		//perdu
		if(a<1){
			a+=1;
			try {
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("./Ressources/perdu.wav"));
				clip = AudioSystem.getClip();
				clip.open(audioInputStream);
			} catch(Exception ex) {
				System.out.println("Error with playing sound.");
				ex.printStackTrace();
			}
		}

		int i=0;
		if(Forme.temp.m3.isSelected()){
			if(Compteur.getMinutes()==5 && Compteur.getSecondes()==0){
				Forme.temp.setDemarrer(false);
				Forme.temp.setArreter(true);
				Forme.temp.diviserImage();
				Forme.temp.initialiser();
				Compteur.pauseTime();
				Forme.temp.musique.close();
				clip.start();
				JOptionPane.showMessageDialog(Forme.temp, "Vous avez perdu :(","Perdu :(",
						JOptionPane.PLAIN_MESSAGE,new ImageIcon("Ressources/Icons/sad.png"));
				i=3;
			}
		}
		else if(Forme.temp.m4.isSelected()){
			if(Compteur.getMinutes()==7 && Compteur.getSecondes()==0){
				Forme.temp.setDemarrer(false);
				Forme.temp.setArreter(true);
				Forme.temp.diviserImage();
				Forme.temp.initialiser();
				Compteur.pauseTime();
				Forme.temp.musique.close();
				clip.start();
				JOptionPane.showMessageDialog(Forme.temp, "Vous avez perdu :(","Perdu :(",
						JOptionPane.PLAIN_MESSAGE,new ImageIcon("Ressources/Icons/sad.png"));
				i=4;
			}
		}
		else if(Forme.temp.m5.isSelected()){
			if(Compteur.getMinutes()==10 && Compteur.getSecondes()==0){
				Forme.temp.setDemarrer(false);
				Forme.temp.setArreter(true);
				Forme.temp.diviserImage();
				Forme.temp.initialiser();
				Compteur.pauseTime();
				Forme.temp.musique.close();
				clip.start();
				JOptionPane.showMessageDialog(Forme.temp, "Vous avez perdu :(","Perdu :(",
						JOptionPane.PLAIN_MESSAGE,new ImageIcon("Ressources/Icons/sad.png"));
				i=5;
			}
		}
		else if(Forme.temp.mAutreType.isSelected()){
			if(Compteur.getMinutes()==15 && Compteur.getSecondes()==0){
				Forme.temp.setDemarrer(false);
				Forme.temp.setArreter(true);
				Forme.temp.diviserImage();
				Forme.temp.initialiser();
				Compteur.pauseTime();
				Forme.temp.musique.close();
				clip.start();
				JOptionPane.showMessageDialog(Forme.temp, "Vous avez perdu :(","Perdu :(",
						JOptionPane.PLAIN_MESSAGE,new ImageIcon("Ressources/Icons/sad.png"));
				i=6;
			}
		}
		//
		//�criture dans le fichier score
		if(i!=0){
			PrintWriter out=null;
			try{
				out=new PrintWriter(new FileWriter("Ressources/Data/score.dat",true));
			}catch(IOException ex){
				JOptionPane.showMessageDialog(null,ex.getMessage(),"Erreur",JOptionPane.WARNING_MESSAGE);
				return false;
			}
			out.print(Forme.nom+";"+"Perdu"+";"+Forme.type+";"+Forme.dateJour+"\n");
			try{
				out.close();
				return true;
			}catch(Exception ex){
				JOptionPane.showMessageDialog(null,ex.getMessage(),"Erreur",JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		else
			return false;
		//
	}

}