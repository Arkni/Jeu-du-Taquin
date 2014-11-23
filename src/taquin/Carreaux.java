package taquin;

/**
 * 
 * @author brahim
 */
public class Carreaux {

    private int imgX; // la coordonnée x du premier coin    ___
    private int imgY; // la coordonnée y du premier coin   |
    private int imgX1; // la coordonnée x du dexième coin   ___|
    private int imgY1; // la coordonnée y du deuxième coin 
    private int origEmplacement; // l'emplacement d'origine
    private int nvEmplacement; // le nouveau emplacement

    /**
     * 
     * @param x
     * @param y
     * @param x1
     * @param y1
     * @param orig 
     */
    public Carreaux(int x, int y, int x1, int y1, int orig) {
        this.imgX = x;
        this.imgY = y;
        this.imgX1 = x1;
        this.imgY1 = y1;
        this.origEmplacement = orig;
        this.nvEmplacement = orig;
    }

    /**
     * Get ImgX
     * @return int
     */
    public int getImgX() {
        return imgX;
    }

    /**
     * Set imgX
     * @param imgX 
     */
    public void setImgX(int imgX) {
        this.imgX = imgX;
    }

    /**
     * Get ImgY
     * @return int
     */
    public int getImgY() {
        return imgY;
    }

    /**
     * Set imgY
     * @param imgY 
     */
    public void setImgY(int imgY) {
        this.imgY = imgY;
    }

    /**
     * Get ImgX1
     * @return int
     */
    public int getImgX1() {
        return imgX1;
    }

    /**
     * Set imgX1
     * @param imgX1 
     */
    public void setImgX1(int imgX1) {
        this.imgX1 = imgX1;
    }

    /**
     * Get imgY1
     * @return int
     */
    public int getImgY1() {
        return imgY1;
    }

    /**
     * Set imgY1
     * @param imgY1 
     */
    public void setImgY1(int imgY1) {
        this.imgY1 = imgY1;
    }

    /**
     * Get origEmplacement
     * @return int
     */
    public int getOrigEmplacement() {
        return origEmplacement;
    }

    /**
     * Set origEmplacement
     * @param origEmplacement 
     */
    public void setOrigEmplacement(int origEmplacement) {
        this.origEmplacement = origEmplacement;
    }

    /**
     * Get nvEmplacement
     * @return int
     */
    public int getNvEmplacement() {
        return nvEmplacement;
    }

    /**
     * Set nvEmplacement
     * @param nvEmplacement 
     */
    public void setNvEmplacement(int nvEmplacement) {
        this.nvEmplacement = nvEmplacement;
    }
 
    /**
     * 
     * @return boolean
     */
    public boolean isSonEmplacement() {
        if (this.origEmplacement == this.nvEmplacement) {
            return true;
        } else {
            return false;
        }
    }

}
