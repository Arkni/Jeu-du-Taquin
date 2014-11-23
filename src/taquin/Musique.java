package taquin;


import java.io.*;
import javazoom.jl.player.*;

/**
 * 
 * @author brahim
 */
public class Musique extends Thread {

    private String fileLocation;
    private boolean loop;
    private Player player;
    private int pauseOnFrame = 0;
    
    /**
     * Constructor
     * @param fileLocation
     * @param loop 
     */
    public Musique(String fileLocation, boolean loop) {
        this.fileLocation = fileLocation;
        this.loop = loop;
    }

    @Override
    public void run() {

        try {
            do {
                FileInputStream buff = new FileInputStream(fileLocation);
                player = new Player(buff);
                player.play();
            } while (loop);
        } catch (Exception ioe) {
        	System.out.println(ioe.toString());
        }
    }
    
    /**
     * Close method
     */
    public void close(){
        loop = false;
        player.close();
        this.interrupt();
    }
}