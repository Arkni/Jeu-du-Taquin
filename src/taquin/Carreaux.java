package taquin;

public class Carreaux {
	private int imgX; // la coordonnée x du premier coin    ___
	private int imgY; // la coordonnée y du premier coin   |
	private int imgX1; // la coordonnée x du dexième coin   ___|
	private int imgY1; // la coordonnée y du deuxième coin 
	private int origEmplacement; // l'emplacement d'origine
	private int nvEmplacement; // le nouveau emplacement
	
	public Carreaux(int x,int y,int x1,int y1,int orig){
		this.imgX=x;
		this.imgY=y;
		this.imgX1=x1;
		this.imgY1=y1;
		this.origEmplacement=orig;
		this.nvEmplacement=orig;
	}
	
	public int getImgX(){
		return this.imgX;
	}
	
	public int getImgY(){
		return this.imgY;
	}
	
	public int getImgX1(){
		return this.imgX1;
	}
	
	public int getImgY1(){
		return this.imgY1;
	}
	
	public int getOrigEmplacement(){
		return this.origEmplacement;
	}
	
	public int getNvEmplacement(){
		return this.nvEmplacement;
	}
	
	public void setNvEmplacemet(int nvEmplacement){
		this.nvEmplacement=nvEmplacement;
	}
	
	public boolean isSonEmplacement(){
		if(this.origEmplacement==this.nvEmplacement)
			return true;
		else
			return false;
	}
	
}
