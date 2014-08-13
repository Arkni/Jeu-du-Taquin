package taquin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class ScoreModel extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//l'ent�te du JTable
	private final String[] entete;

	//les donn�es du JTable
	private ArrayList<Joueur> list;

	//le constructeur
	public ScoreModel(){
		entete=new String[]{"Joueur","Score","Type de Jeu","Date"};
		//lecture � partir du fichier score.dat
		String ligne=null;
		String[] elements=null;

		list=new ArrayList<Joueur>();

		//lire � partir d'un fichier
		BufferedReader in=null;
		try{
			in=new BufferedReader(new FileReader("Ressources/Data/score.dat"));
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,e.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
			return;
		}
		try{
			while((ligne=in.readLine())!=null){
				elements=ligne.split(";");
				list.add(new Joueur(elements[0],elements[1],elements[2],elements[3]));
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,e.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
		}

		//fermer le buffer
		try {
			in.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
		}
		//

		//trier la liste des joueur
		Collections.sort(list);
	}

	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public int getColumnCount() {
		return entete.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return entete[columnIndex];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex){
		case 0:
			return list.get(rowIndex).getNom();
		case 1:
			return list.get(rowIndex).getScore();
		case 2:
			return list.get(rowIndex).getTypeJeu();
		case 3:
			return list.get(rowIndex).getDateJeu();
		default:
			return null;
		}
	}

	//supprimer un joueur
	public void viderListeJoueur(){
		int indice=list.size();
		list.clear();
		fireTableRowsDeleted(0,indice-1);

		//l'�criture dans le fichier
		PrintWriter out=null;
		try{
			out=new PrintWriter(new FileWriter("Ressources/Data/score.dat"));
			out.print("");
			out.close();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,e.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
		}//
	}

	//get List
	public ArrayList<Joueur> getList(){
		return list;
	}


	//la classe Joueur
	class Joueur implements Comparable<Joueur>{
		private String nom;
		private String score;
		private String typeJeu;
		private String dateJeu;

		public Joueur(String nom,String score,String typeJeu,String dateJeu) {
			this.nom=nom;
			this.score=score;
			this.typeJeu=typeJeu;
			this.dateJeu=dateJeu;
		}

		public String getNom(){
			return this.nom;
		}

		public String getScore(){
			return this.score;
		}

		public String getTypeJeu(){
			return this.typeJeu;
		}

		public String getDateJeu(){
			return this.dateJeu;
		}

		@Override
		public int compareTo(Joueur o) {
			SimpleDateFormat formater=new SimpleDateFormat("dd/MM/yy  HH:mm:ss");
			Date dateO1 = null,dateO2 = null;
			try {
				dateO1=formater.parse(this.getDateJeu());
				dateO2=formater.parse(o.getDateJeu());
			} catch (ParseException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
			}
			if(dateO1.compareTo(dateO2)==-1)
				return 1;
			else if(dateO1.compareTo(dateO2)==1)
				return -1;
			else
				return 0;
		}
	}
	//

}