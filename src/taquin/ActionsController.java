package taquin;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class ActionsController implements ActionListener,WindowListener{

	private Object o;

	//le constructeur
	public ActionsController(Object o) {
		this.o=o;
	}


	@Override
	public void actionPerformed(ActionEvent e) {

		if(o.getClass()==Forme.class){
			Forme forme=(Forme)o;

			//importer une image
			if(e.getSource()==forme.mOuvrir){
				new FileChooser(forme);
			}//

			//Lancer le jeu
			else if(e.getSource()==forme.mLancer){
				if(forme.mRejouer.isEnabled()){
					Compteur.pauseTime();
					Panneau.compteur.setText("00:00");
				}
				forme.identifier();
				forme.go();
				forme.mRejouer.setEnabled(true);
				forme.mStart.setEnabled(false);
				forme.mStop.setEnabled(true);

			}//

			//Rejouer
			else if(e.getSource()==forme.mRejouer){
				Compteur.pauseTime();
				forme.identifier();
				forme.go();
				forme.mStart.setEnabled(false);
				forme.mStop.setEnabled(true);
			}//

			//relancer le jeu et le compteur
			else if(e.getSource()==forme.mStart){
				Compteur.compter(Compteur.getTempEcouleSecondes(),Compteur.getTempEcouleMinutes());
				forme.setArreter(false);
				forme.mStart.setEnabled(false);
				forme.mStop.setEnabled(true);
			}//

			//arr�ter le jeu et le compteur
			else if(e.getSource()==forme.mStop){
				Compteur.pauseTime();
				forme.setArreter(true);
				forme.mStart.setEnabled(true);
				forme.mStop.setEnabled(false);
			}//

			//consulter le score
			else if(e.getSource()==forme.mScore){
				new ScoreDialog();
			}//

			//Quitter l'application
			else if(e.getSource()==forme.mQuiter){
				Toolkit.getDefaultToolkit().beep();
				int rep=JOptionPane.showConfirmDialog(forme, "Voulez-vous vraiment quitter ?","Quitter",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
				if(rep==JOptionPane.OK_OPTION)
					System.exit(0);
			}//

			//How To Use
			else if(e.getSource()==forme.mHowToUse){
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(forme,"Pour que vous pouvez jouer, il faut que vous suivez ces étapes :\n"
						+"             1- importer une image en cliquant sur 'Importer une image' ou 'CTRL+O'.\n"
						+"             2- Choisir le type du jeu (3x3, 4x4, 5x5 ou Autres).\n"
						+"             3- Choisir la taille du carré (la taille des sous images 50px, 75px et 100px).\n"
						+"             4- Cliquer sur 'lancer le jeu' ou 'CTRL+N'.\n"
						+"             5- S'amuser :)\n",
						"Comment jouer ?",JOptionPane.INFORMATION_MESSAGE);
			}//

			//A propos
			else if(e.getSource()==forme.mApropos){
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(forme,"Réalisé par:"
						+ "\n       Brahim Arkni","A propos ?",JOptionPane.INFORMATION_MESSAGE);
			}//

			//la taille 50px
			else if(e.getSource()==forme.m50){
				forme.setTaille(50);
				forme.pan=new Panneau(forme.getImage(), forme.getNbCarreaux(), forme.getTaille(), forme.getCarreaux());
				forme.initialiser();
			}//

			//la taille 75px
			else if(e.getSource()==forme.m75){
				forme.setTaille(75);
				forme.pan=new Panneau(forme.getImage(), forme.getNbCarreaux(), forme.getTaille(), forme.getCarreaux());
				forme.initialiser();
			}//

			//la taille 100px
			else if(e.getSource()==forme.m100){
				forme.setTaille(100);
				forme.pan=new Panneau(forme.getImage(), forme.getNbCarreaux(), forme.getTaille(), forme.getCarreaux());
				forme.initialiser();
			}//

			//le type 3x3
			else if(e.getSource()==forme.m3){
				forme.setNbCarreaux(3);
				forme.setDemarrer(false);
				forme.diviserImage();
				forme.initialiser();
			}//

			//le type 4x4
			else if(e.getSource()==forme.m4){
				forme.setNbCarreaux(4);
				forme.setDemarrer(false);
				forme.diviserImage();
				forme.initialiser();
			}//

			//le type 5x5
			else if(e.getSource()==forme.m5){
				forme.setNbCarreaux(5);
				forme.setDemarrer(false);
				forme.diviserImage();
				forme.initialiser();
			}//

			//les autres types
			else if(e.getSource()==forme.mAutreType){
				String nbcar=null;
				int valeur = 0,rep = -1;
				do{
					try{
						Toolkit.getDefaultToolkit().beep();
						nbcar=JOptionPane.showInputDialog(forme, "entrer le nombre des cases (sup�rieur � 5) :",
								"Entrer",JOptionPane.PLAIN_MESSAGE);
						if(nbcar!=null){
							valeur=Integer.parseInt(nbcar);
							if(valeur>5){
								forme.setNbCarreaux(valeur);
								break;
							}
							throw new Exception();
						}
						else
							break;

					}catch(Exception ex){
						rep=JOptionPane.showConfirmDialog(forme, "Vous n'avez pas entrer une valeur enti�re\n"
								+ "ou vous n'avez rien entrer.\n"
								+ "Cliquer sur OK pour entrer une autre valeur.",
								"Erreur",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE);						
					}

				}while(rep==JOptionPane.OK_OPTION || valeur<5);
				forme.setDemarrer(false);
				forme.diviserImage();
				forme.initialiser();
			}//

			//le look and fell Windows
			else if(e.getSource()==forme.mWindows){
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(forme,ex.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
				}
				//actualiser toutes les composantes de la fen�tre
				SwingUtilities.updateComponentTreeUI(forme);
			}
			//

			//le look and fell Windows Classic
			else if(e.getSource()==forme.mWindowsClassic){
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(forme,ex.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
				}
				//actualiser toutes les composantes de la fen�tre
				SwingUtilities.updateComponentTreeUI(forme);
			}
			//

			//le look and fell Nimbus
			else if(e.getSource()==forme.mNimbus){
				try {
					UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(forme,ex.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
				}
				//actualiser toutes les composantes de la fen�tre
				SwingUtilities.updateComponentTreeUI(forme);
			}
			//
			//le look and fell Metal
			if(e.getSource()==forme.mMetal){
				try {
					UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(forme,ex.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
				}
				//actualiser toutes les composantes de la fen�tre
				SwingUtilities.updateComponentTreeUI(forme);
			}
			//

			//le look and fell CDEMotif
			else if(e.getSource()==forme.mCDEMotif){
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(forme,ex.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
				}
				//actualiser toutes les composantes de la fen�tre
				SwingUtilities.updateComponentTreeUI(forme);
			}
			//

			//le look and fell System
			else if(e.getSource()==forme.mSystem){
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(forme,ex.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
				}
				//actualiser toutes les composantes de la fen�tre
				SwingUtilities.updateComponentTreeUI(forme);
			}
			//

			//la musique
			else if(e.getSource()==forme.mLancerPauser){
				if(forme.musique.isAlive())
					forme.musique.close();
				else{
					forme.musique=new Musique("./Ressources/taquin.mp3",true);
					forme.musique.start();
				}
			}
		}
		else if(o.getClass()==ScoreDialog.class){
			ScoreDialog scoreForme=(ScoreDialog)o;
			if(e.getSource()==scoreForme.fermer){
				scoreForme.dispose();
			}
			else if(e.getSource()==scoreForme.initialiser){
				Toolkit.getDefaultToolkit().beep();
				int rep=JOptionPane.showConfirmDialog(scoreForme,"Voullez-vous vraiment initialiser les scores ?","Initialiser les scores",JOptionPane.YES_NO_OPTION);
				if(rep==JOptionPane.OK_OPTION){
					scoreForme.getModel().viderListeJoueur();
					scoreForme.initialiser.setEnabled(false);
				}
			}
		}

	}


	@Override
	public void windowOpened(WindowEvent e) {}


	@Override
	public void windowClosing(WindowEvent e) {
		Forme forme=(Forme)o;
		Toolkit.getDefaultToolkit().beep();
		int rep=JOptionPane.showConfirmDialog(forme, "Voulez-vous vraiment quitter ?","Quitter",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
		if(rep==JOptionPane.OK_OPTION)
			System.exit(0);
		else{
			forme.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		}
	}


	@Override
	public void windowClosed(WindowEvent e) {}


	@Override
	public void windowIconified(WindowEvent e) {}


	@Override
	public void windowDeiconified(WindowEvent e) {}


	@Override
	public void windowActivated(WindowEvent e) {}


	@Override
	public void windowDeactivated(WindowEvent e) {}

}