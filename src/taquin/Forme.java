package taquin;

import java.awt.Cursor;
import java.awt.Event;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Forme extends JFrame {

    private static final long serialVersionUID = 1L;

    private JMenuBar principale;
    private JMenu mJeu, mHelp, mType, mTaille, mTheme;
    public JMenuItem mQuiter, mOuvrir, mLancer, mRejouer, mApropos;
    public JMenuItem mHowToUse, mStop, mStart, mScore;
    public JMenuItem mLancerPauser;
    public JRadioButtonMenuItem mWindows, mNimbus, mWindowsClassic, mMetal, mCDEMotif, mSystem;
    public JRadioButtonMenuItem m3, m4, m5, mAutreType;
    public JRadioButtonMenuItem m50, m75, m100;

    //le chemin de l'image
    String chemin = null;

    //le JPanel
    JPanel pan;

    //les variables taille du carreau et le type de jeu avec les valeurs par d�faut
    private int taille = 75, nbCarreaux = 3;

    //la variable boolean d�marrer qui stock l'�tat du jeu ( d�marrer ou non)
    private boolean demarrer = false;
    private boolean arreter = false;

    //la matrices des images
    private Carreaux[][] carreaux;

    //l'image
    private Image img = null;

    //une variable temporaire
    public static Forme temp;

    //le nom du joueur et le score et le type de jeu
    static String nom = null;
    static String score = null;
    static String type = null;
    static String dateJour = null;

    //la music
    Musique musique = null;

    //le constructeur
    public Forme() {
        this.setTitle("Jeu du Taquin");
        this.setSize((getNbCarreaux() + 2) * getTaille() + 4, (getNbCarreaux() + 2) * getTaille() + 50);
        this.setLocationRelativeTo(null);
        //this.setResizable(false);
        temp = this;

        //cr�er un objet Panneau sans image
        pan = new Panneau(null, getNbCarreaux(), getTaille(), carreaux);
        this.setContentPane(pan);

        //initialiser les composantes (la barre de menu)
        initComponents();

        //la construction de la barre de menu
        createMenu();

        //changer l'icone de l'application
        try {
            /*ImageIcon icon=new ImageIcon("Ressources/icons/puzzleicon.png");
             this.setIconImage(icon.getImage());*/
            this.setIconImage(Toolkit.getDefaultToolkit().getImage("Ressources/Icons/icon.png"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }//

        //changer le th�me de l'application
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        //actualiser toutes les composantes de la fen�tre
        SwingUtilities.updateComponentTreeUI(this);


        /* associer un MouseMotionListener � cette fen�tre
         * tout �a juste pour changer le cursor au cours du jeu
         */
        this.addMouseMotionListener(new MouseController(this));//

        /* associer un MouseListener � cette fen�tre
         * pour d�placer les images
         */
        this.addMouseListener(new MouseController(this));

        /*associer un WindowListener � cette fen�tre
         * pour demander au joueur est ce qu'il veut quitter ou non
         */
        this.addWindowListener(new ActionsController(this));

        //la SplashScreen
        new SplashScreen(this);

        //la music
        musique = new Musique("./Ressources/taquin.mp3", true);
        musique.start();

    }//fin du constructeur

    //initialiser les composantes
    public void initComponents() {

        principale = new JMenuBar();

        mJeu = new JMenu("Jeu du Taquin");
        mHelp = new JMenu("Aide");
        mTheme = new JMenu("Thème");

        mJeu.setMnemonic(KeyEvent.VK_J); //raccourci alt+F
        mHelp.setMnemonic(KeyEvent.VK_A); //raccourci alt+H
        mTheme.setMnemonic(KeyEvent.VK_T);//racocourci alt+L

        mType = new JMenu("Type de jeu");
        mTaille = new JMenu("Taille de carreau");
        mType.setMnemonic(KeyEvent.VK_T); //raccourci alt+T
        mTaille.setMnemonic(KeyEvent.VK_C); //raccourci alt+C

        mQuiter = new JMenuItem("Quitter", KeyEvent.VK_Q); //raccourci alt+Q
        mOuvrir = new JMenuItem("Importer une image", KeyEvent.VK_I);//raccourci alt+I
        mLancer = new JMenuItem("Nouvelle partie", KeyEvent.VK_N);//raccourci alt+N
        mRejouer = new JMenuItem("Rejouer", KeyEvent.VK_R);//raccourci alt+R
        mLancerPauser = new JMenuItem("Lancer/Pauser la musique");

        mApropos = new JMenuItem("A propos de nous", KeyEvent.VK_A);//raccourci alt+A
        mHowToUse = new JMenuItem("Comment jouer ?", KeyEvent.VK_C);//raccourci alt+C

        mStop = new JMenuItem("Suspendre le jeu", KeyEvent.VK_S);//raccourci alt+S
        mStart = new JMenuItem("Reprendre le jeu", KeyEvent.VK_P);//raccourci alt+P

        mScore = new JMenuItem("Score", KeyEvent.VK_O);//raccourci alt+O

        m3 = new JRadioButtonMenuItem("3x3");
        m4 = new JRadioButtonMenuItem("4x4");
        m5 = new JRadioButtonMenuItem("5x5");
        mAutreType = new JRadioButtonMenuItem("Autre ...");

        m50 = new JRadioButtonMenuItem("Taille 50px");
        m75 = new JRadioButtonMenuItem("Taille 75px");
        m100 = new JRadioButtonMenuItem("Taille 100px");

        mNimbus = new JRadioButtonMenuItem("Nimbus");
        mWindows = new JRadioButtonMenuItem("Windowns");
        mWindowsClassic = new JRadioButtonMenuItem("Windows Classic");
        mCDEMotif = new JRadioButtonMenuItem("CDE/Motif");
        mMetal = new JRadioButtonMenuItem("Metal");
        mSystem = new JRadioButtonMenuItem("System look & fell");

        mNimbus.setMnemonic(KeyEvent.VK_N);//raccourci alt+N
        mWindows.setMnemonic(KeyEvent.VK_W);//raccourci alt+W
        mWindowsClassic.setMnemonic(KeyEvent.VK_C);//raccourci alt+C
        mCDEMotif.setMnemonic(KeyEvent.VK_D);//raccourci alt+D
        mMetal.setMnemonic(KeyEvent.VK_M);//raccourci alt+M
        mSystem.setMnemonic(KeyEvent.VK_S);//raccourci alt+S

    }//

    //la m�thode qui construit la barre de menu
    public void createMenu() {

        // mettre le barre de menu 'principale' comme barre de menu pour cette fen�tre 
        this.setJMenuBar(principale);

        //ajouter les menus Jeu de Taquin, Look And Fell et Aide
        principale.add(mJeu);
        principale.add(mTheme);
        principale.add(mHelp);

        //--le menu Jeu de Taquin
        mJeu.add(mOuvrir);
        mJeu.addSeparator();
        mJeu.add(mLancer);
        mJeu.add(mRejouer);
        mJeu.add(mStart);
        mJeu.add(mStop);
        mJeu.add(mLancerPauser);
        mJeu.addSeparator();
        mJeu.add(mScore);
        mJeu.add(mType);
        mJeu.add(mTaille);
        mJeu.addSeparator();
        mJeu.add(mQuiter);

        //quitter l'application
        mQuiter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));//raccourci alt+F4
        mQuiter.setIcon(new ImageIcon("Ressources/Icons/fermer.png"));
        mQuiter.addActionListener(new ActionsController(this));

        //importer une image
        mOuvrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));//ctrl+O
        mOuvrir.setIcon(new ImageIcon("Ressources/Icons/import.png"));
        mOuvrir.addActionListener(new ActionsController(this));

        //le score
        mScore.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, Event.CTRL_MASK));
        mScore.setIcon(new ImageIcon("Ressources/Icons/score.png"));
        mScore.addActionListener(new ActionsController(this));

        //lancer le jeu
        mLancer.setEnabled(false); //desactiver le menuItem Lancer
        mLancer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));//ctrl+L
        mLancer.setIcon(new ImageIcon("Ressources/Icons/go.png"));
        mLancer.addActionListener(new ActionsController(this));

        //rejouer
        mRejouer.setEnabled(false); //desactiver le menuItem Rejouer
        mRejouer.setIcon(new ImageIcon("Ressources/Icons/repeat.png"));
        mRejouer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, Event.CTRL_MASK));//ctrl+R
        mRejouer.addActionListener(new ActionsController(this));

        //desactiver les deux JMenuItem
        mStart.setEnabled(false);
        mStop.setEnabled(false);

        //arr�ter le jeu et lecompteur
        mStop.setIcon(new ImageIcon("Ressources/Icons/pause.png"));
        mStop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, Event.SHIFT_MASK));
        mStop.addActionListener(new ActionsController(this));

        //relancer le jeu et le compteur
        mStart.setIcon(new ImageIcon("Ressources/Icons/start.png"));
        mStart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, Event.CTRL_MASK));
        mStart.addActionListener(new ActionsController(this));

        //la musique
        mLancerPauser.setIcon(new ImageIcon("Ressources/Icons/pause_start.png"));
        mLancerPauser.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, Event.CTRL_MASK));
        mLancerPauser.addActionListener(new ActionsController(this));

        //la taille des carreaux
        mTaille.add(m50); //50px
        mTaille.add(m75); //75px
        mTaille.add(m100);//100px

        m50.addActionListener(new ActionsController(this));
        m75.setSelected(true);
        m75.addActionListener(new ActionsController(this));
        m100.addActionListener(new ActionsController(this));

        ButtonGroup g1 = new ButtonGroup();
        g1.add(m50);
        g1.add(m75);
        g1.add(m100);

        //le type de jeu (3x3, 4x4, 5x5)
        mType.add(m3);
        mType.add(m4);
        mType.add(m5);
        mType.add(mAutreType);

        m3.setSelected(true);     //mettre m3 qui est selectionn�
        m3.addActionListener(new ActionsController(this));
        m4.addActionListener(new ActionsController(this));
        m5.addActionListener(new ActionsController(this));
        mAutreType.addActionListener(new ActionsController(this));

        ButtonGroup g = new ButtonGroup();
        g.add(m3);
        g.add(m4);
        g.add(m5);
        g.add(mAutreType);
		//--

        //--le menu LookAndFeel
        mTheme.add(mWindows);
        mTheme.add(mWindowsClassic);
        mTheme.add(mNimbus);
        mTheme.add(mMetal);
        mTheme.add(mCDEMotif);
        mTheme.add(mSystem);

        mWindows.addActionListener(new ActionsController(this));
        mWindowsClassic.addActionListener(new ActionsController(this));
        mNimbus.setSelected(true);
        mNimbus.addActionListener(new ActionsController(this));
        mMetal.addActionListener(new ActionsController(this));
        mCDEMotif.addActionListener(new ActionsController(this));
        mSystem.addActionListener(new ActionsController(this));

        ButtonGroup gLook = new ButtonGroup();
        gLook.add(mWindows);
        gLook.add(mWindowsClassic);
        gLook.add(mNimbus);
        gLook.add(mMetal);
        gLook.add(mCDEMotif);
        gLook.add(mSystem);
		//--

        //--le menu Help
        mHelp.add(mApropos);
        mHelp.addSeparator();
        mHelp.add(mHowToUse);

        //la boite de dialog A propos
        mApropos.setIcon(new ImageIcon("Ressources/Icons/help.png"));
        mApropos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.CTRL_MASK));
        mApropos.addActionListener(new ActionsController(this));

        //la boite de dialoge Comment jouer ?
        mHowToUse.setIcon(new ImageIcon("Ressources/Icons/how.png"));
        mHowToUse.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, Event.CTRL_MASK));
        mHowToUse.addActionListener(new ActionsController(this));
        //--

    }//fin de la m�thod createMenu

    //la m�thode qui initialise la fen�tre
    public void initialiser() {
        changeCursor('D');
        this.setContentPane(pan);
        this.setSize((getNbCarreaux() + 2) * getTaille() + 4, (getNbCarreaux() + 2) * getTaille() + 50);
        this.setLocationRelativeTo(null);
        SwingUtilities.updateComponentTreeUI(this);
    }//fin de la m�thode initialiser

    /**
     * la m�thode qui nous permet de diviser l'image ( cr�er un objet
     * DiviserImage, puis diviser l'image en plusieurs images) puis dissiner le
     * JPanel
     */
    public void diviserImage() {
        DiviserImage div = new DiviserImage(img, getNbCarreaux());
        carreaux = div.diviser();
        pan = new Panneau(img, getNbCarreaux(), getTaille(), carreaux);
    }//fin de la m�thode diviserImage

    //la m�thode qui permet de changer le cursor
    public void changeCursor(char lettre) {
        switch (lettre) {
            case 'D':
                this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                break;
            case 'H':
                this.setCursor(new Cursor(Cursor.HAND_CURSOR));
                break;
        }
    }//fin de la m�thode changeCursor

    //la m�thode go
    public void go() {
        if (img != null) {
            setDemarrer(true);
            setArreter(false);
            ArrayList<Carreaux> arr = new ArrayList<Carreaux>();
            for (Carreaux[] c : carreaux) {
                for(Carreaux b : c) {
                    arr.add(b);
                }
            }
            Random ran = new Random();
            int indice;
            for (int i = getNbCarreaux() * getNbCarreaux(); i > 0; i--) {
                indice = ran.nextInt(i);
                arr.get(indice).setNvEmplacemet(i - 1);
                arr.remove(indice);
            }
            arr.clear();
            pan.repaint();
            Compteur.compter(0, 0);

            if (!musique.isAlive()) {
                musique = new Musique("./Ressources/taquin.mp3", true);
                musique.start();
            }

        } else {
            JOptionPane.showMessageDialog(this, "Importer une image", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }//la fin de la m�thode go

    //pour identifier l'utilisateur
    public void identifier() {
        //le nom du joueur
        nom = JOptionPane.showInputDialog(null, "entrer votre nom svp :",
                "Entrer le nom", JOptionPane.PLAIN_MESSAGE);
        if (nom == null || nom.equals("")) {
            nom = "Anonyme";
        }
        //le type du jeu
        if (m3.isSelected()) {
            type = m3.getText();
            JOptionPane.showMessageDialog(this, "Vous avez 5 min", "Information", JOptionPane.INFORMATION_MESSAGE);
        } else if (m4.isSelected()) {
            type = m4.getText();
            JOptionPane.showMessageDialog(this, "Vous avez 7 min", "Information", JOptionPane.INFORMATION_MESSAGE);
        } else if (m5.isSelected()) {
            type = m5.getText();
            JOptionPane.showMessageDialog(this, "Vous avez 10 min", "Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            type = mAutreType.getText();
            JOptionPane.showMessageDialog(this, "Vous avez 15 min", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
        //la date
        String format = "dd/MM/yy  HH:mm:ss";
        SimpleDateFormat formater = new SimpleDateFormat(format);
        Date date = new Date();
        dateJour = formater.format(date);
    }//

    //getImage
    Image getImage() {
        return img;
    }//

    //setImage
    void setImage(Image img) {
        this.img = img;
    }//

    //getCarreaux
    Carreaux[][] getCarreaux() {
        return carreaux;
    }//

    //getTaille
    public int getTaille() {
        return taille;
    }//

    //setTaille
    public void setTaille(int taille) {
        this.taille = taille;
    }//

    //getNbCarreaux
    public int getNbCarreaux() {
        return nbCarreaux;
    }//

    //setNbCarreaux
    public void setNbCarreaux(int nbCarreaux) {
        this.nbCarreaux = nbCarreaux;
    }//

    //isDemarrer
    public boolean isDemarrer() {
        return demarrer;
    }//

    //setDemarrer
    public void setDemarrer(boolean demarrer) {
        this.demarrer = demarrer;
    }//

    //isArreter
    public boolean isArreter() {
        return arreter;
    }//

    //setArreter
    public void setArreter(boolean arreter) {
        this.arreter = arreter;
    }//

}
