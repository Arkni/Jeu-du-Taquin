package taquin;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author brahim
 */
public class MouseController implements MouseMotionListener, MouseListener {

    private Forme forme;

    /**
     * Constructor
     *
     * @param forme
     */
    public MouseController(Forme forme) {
        this.forme = forme;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //si le joueur clique sur le bouton droit de la sourie
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (forme.isDemarrer() && !forme.isArreter()) {
                int xMouse = e.getX(), yMouse = e.getY(), xBlanc, yBlanc, xCarre, yCarre, t;
                Carreaux[][] carreaux = forme.getCarreaux();
                if (xMouse >= (forme.getTaille() + 4) && xMouse <= (forme.getTaille() * forme.getNbCarreaux() + forme.getTaille() + 4)) {
                    if (yMouse >= (forme.getTaille() + 50) && yMouse <= (forme.getTaille() * forme.getNbCarreaux() + forme.getTaille()) + 50) {
                        //calculer le num�ro de la colonne dans laquelle le carr� selectionn� appartient
                        xCarre = (xMouse - forme.getTaille() - 4) / forme.getTaille();
                        //calculer le num�ro de la ligne dans laquelle le carr� selectionn� appartient
                        yCarre = (yMouse - forme.getTaille() - 50) / forme.getTaille();

                        t = carreaux[forme.getNbCarreaux() - 1][forme.getNbCarreaux() - 1].getNvEmplacement();
                        //calculer le num�ro de la colonne dans laquelle le carr� blanc appartient
                        xBlanc = t % (forme.getNbCarreaux());
                        //calculer le num�ro de la ligne dans laquelle le carr� blanc appartient
                        yBlanc = t / (forme.getNbCarreaux());

                        if (xBlanc == xCarre || yBlanc == yCarre) {
                            int x = 0, y = 0;

                            //xBlanc < xCarre
                            if (xBlanc < xCarre) {
                                while (!(xBlanc == xCarre)) {
                                    for (int i = 0; i < forme.getNbCarreaux(); i++) {
                                        for (int j = 0; j < forme.getNbCarreaux(); j++) {
                                            if (carreaux[i][j].getNvEmplacement() == yBlanc * forme.getNbCarreaux() + xBlanc + 1) {
                                                x = i;
                                                y = j;
                                            }
                                        }
                                    }
                                    carreaux[forme.getNbCarreaux() - 1][forme.getNbCarreaux() - 1].setNvEmplacement(carreaux[x][y].getNvEmplacement());
                                    carreaux[x][y].setNvEmplacement(t);
                                    t++;
                                    xBlanc++;
                                }
                            }//

                            //xBlanc > xCarre
                            if (xBlanc > xCarre) {
                                while (!(xBlanc == xCarre)) {
                                    for (int i = 0; i < forme.getNbCarreaux(); i++) {
                                        for (int j = 0; j < forme.getNbCarreaux(); j++) {
                                            if (carreaux[i][j].getNvEmplacement() == yBlanc * forme.getNbCarreaux() + xBlanc - 1) {
                                                x = i;
                                                y = j;
                                            }
                                        }
                                    }
                                    carreaux[forme.getNbCarreaux() - 1][forme.getNbCarreaux() - 1].setNvEmplacement(carreaux[x][y].getNvEmplacement());
                                    carreaux[x][y].setNvEmplacement(t);
                                    t--;
                                    xBlanc--;
                                }
                            }//

                            //yBlanc < yCarre
                            if (yBlanc < yCarre) {
                                while (!(yBlanc == yCarre)) {
                                    for (int i = 0; i < forme.getNbCarreaux(); i++) {
                                        for (int j = 0; j < forme.getNbCarreaux(); j++) {
                                            if (carreaux[i][j].getNvEmplacement() == yBlanc * forme.getNbCarreaux() + xBlanc + forme.getNbCarreaux()) {
                                                x = i;
                                                y = j;
                                            }
                                        }
                                    }
                                    carreaux[forme.getNbCarreaux() - 1][forme.getNbCarreaux() - 1].setNvEmplacement(carreaux[x][y].getNvEmplacement());
                                    carreaux[x][y].setNvEmplacement(t);
                                    t += forme.getNbCarreaux();
                                    yBlanc++;
                                }
                            }//

                            //yBlanc > yCarre
                            if (yBlanc > yCarre) {
                                while (!(yBlanc == yCarre)) {
                                    for (int i = 0; i < forme.getNbCarreaux(); i++) {
                                        for (int j = 0; j < forme.getNbCarreaux(); j++) {
                                            if (carreaux[i][j].getNvEmplacement() == yBlanc * forme.getNbCarreaux() + xBlanc - forme.getNbCarreaux()) {
                                                x = i;
                                                y = j;
                                            }
                                        }
                                    }
                                    carreaux[forme.getNbCarreaux() - 1][forme.getNbCarreaux() - 1].setNvEmplacement(carreaux[x][y].getNvEmplacement());
                                    carreaux[x][y].setNvEmplacement(t);
                                    t -= forme.getNbCarreaux();
                                    yBlanc--;
                                }
                            }//

                        }//fin de if(xBlanc==xCarre || ...)

                        //redissiner le panel
                        forme.pan.repaint();

                        //gagner
                        boolean gagner = true;
                        for (int i = 0; i < forme.getNbCarreaux(); i++) {
                            for (int j = 0; j < forme.getNbCarreaux(); j++) {
                                if (!carreaux[i][j].isSonEmplacement()) {
                                    gagner = false;
                                }
                            }
                        }
                        if (gagner == true) {
                            Compteur.pauseTime();
                            forme.mLancer.setEnabled(false);

                            Forme.score = Panneau.compteur.getText();
                            //�criture dans le fichier score
                            PrintWriter out = null;
                            try {
                                out = new PrintWriter(new FileWriter("Ressources/Data/score.dat", true));
                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(null, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                            }
                            out.println(Forme.nom + ";" + Forme.score + ";" + Forme.type + ";" + Forme.dateJour);
                            try {
                                out.close();
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                            }
							//

                            Clip clip = null;
                            try {
                                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("./Ressources/Bravo.wav").getAbsoluteFile());
                                clip = AudioSystem.getClip();
                                clip.open(audioInputStream);
                                forme.musique.close();
                                clip.start();
                            } catch (Exception ex) {
                                System.out.println("Error with playing sound.");
                                ex.printStackTrace();
                            }

                            int rep = JOptionPane.showConfirmDialog(null, "Félicitation vous avez gagné"
                                    + "\nVotre score est : " + Panneau.compteur.getText()
                                    + "\nVoullez vous rejouer ?", "Félicitation", JOptionPane.YES_NO_OPTION,
                                    JOptionPane.INFORMATION_MESSAGE, new ImageIcon("Ressources/Icons/smile.png"));

                            if (rep == JOptionPane.OK_OPTION) {
                                forme.go();
                            }
                            if (rep == JOptionPane.NO_OPTION) {
                                forme.diviserImage();
                                forme.initialiser();
                                forme.setDemarrer(false);
                                Panneau.compteur.setText("00:00");
                                forme.mStart.setEnabled(false);
                                forme.mStop.setEnabled(false);
                                forme.mLancer.setEnabled(false);
                                forme.mRejouer.setEnabled(true);
                            }
                        }//

                    }//fin de if(yMouse>..)
                }//fin de if(xMouse>..)
            }//fin de if(demarrer)
        }
    }//mousePressed

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    //pour changer
    @Override
    public void mouseMoved(MouseEvent e) {
        if (forme.isDemarrer() && !forme.isArreter()) {
            int xMouse = e.getX(), yMouse = e.getY(), xBlanc, yBlanc, xCarre, yCarre;
            if (xMouse >= (forme.getTaille() + 4) && xMouse <= (forme.getTaille() * forme.getNbCarreaux() + forme.getTaille() + 4)) {
                if (yMouse >= (forme.getTaille() + 50) && yMouse <= (forme.getTaille() * forme.getNbCarreaux() + forme.getTaille() + 50)) {

                    //calculer le num�ro de la colonne dans laquelle le carreau selectionn� appartient
                    xCarre = (xMouse - forme.getTaille() - 4) / forme.getTaille();
                    //calculer le num�ro de la ligne dans laquelle le carreau selectionn� appartient
                    yCarre = (yMouse - forme.getTaille() - 50) / forme.getTaille();

                    Carreaux[][] carreaux = forme.getCarreaux();
                    int t = carreaux[forme.getNbCarreaux() - 1][forme.getNbCarreaux() - 1].getNvEmplacement();

                    //calculer le num�ro de la colonne dans laquelle le carreau blanc appartient
                    xBlanc = t % forme.getNbCarreaux();
                    //calculer le num�ro de la ligne dans laquelle le carreau blanc appartient
                    yBlanc = t / forme.getNbCarreaux();

                    if (xBlanc == xCarre || yBlanc == yCarre) {
                        if (!(xBlanc == xCarre && yBlanc == yCarre)) {
                            forme.changeCursor('H');
                        } else {
                            forme.changeCursor('D');
                        }
                    } else {
                        forme.changeCursor('D');
                    }
                } else {
                    forme.changeCursor('D');
                }
            } else {
                forme.changeCursor('D');
            }
        }
    }//mouseMoved

}
