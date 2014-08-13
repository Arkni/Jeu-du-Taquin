package taquin;


import java.io.*;
import javazoom.jl.player.*;

public class Musique extends Thread {

    private String fileLocation;
    private boolean loop;
    private Player prehravac;

    public Musique(String fileLocation, boolean loop) {
        this.fileLocation = fileLocation;
        this.loop = loop;
    }

    public void run() {

        try {
            do {
                FileInputStream buff = new FileInputStream(fileLocation);
                prehravac = new Player(buff);
                prehravac.play();
            } while (loop);
        } catch (Exception ioe) {
        	System.out.println(ioe.toString());
        }
    }

    public void close(){
        loop = false;
        prehravac.close();
        this.interrupt();
    }
}