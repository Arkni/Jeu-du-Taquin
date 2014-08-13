package taquin;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class FileChooser extends JFileChooser{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Filtre filtre1=new Filtre("Image JPG (*.jpg)",".jpg"),
			filtre2=new Filtre("Image PNG (*.png)",".png"),
			filtre3=new Filtre("Image GIF (*.gif)",".gif"),
			filtre4=new Filtre("Image Bitmap (*.bmp)",".bmp"),
			filtre5=new Filtre("Image Files (*.jpg, *.png, *.gif, *.bmp)",new String[]{".jpg",".png",".gif",".bmp"});
	
	public FileChooser(Forme forme) {
		super("./Ressources/Images");
		
		this.setDialogTitle("Choisir une image");
		this.setMultiSelectionEnabled(false);
		
		this.addChoosableFileFilter(filtre1);
		this.addChoosableFileFilter(filtre2);
		this.addChoosableFileFilter(filtre3);
		this.addChoosableFileFilter(filtre4);
		this.addChoosableFileFilter(filtre5);

		//mettre le filtre1 comme un filre par défaut
		this.setFileFilter(filtre1);
		
		//ouvrir le JFileChooser
		int rep=this.showOpenDialog(forme);
		if(rep!=JFileChooser.CANCEL_OPTION && this.getSelectedFile()!=null){
			forme.chemin=this.getSelectedFile().getAbsolutePath();
			if(forme.chemin!=null)
				try {
					forme.setImage(ImageIO.read(new File(forme.chemin)));
					forme.diviserImage();
					forme.initialiser();
					forme.setDemarrer(false);
					forme.mLancer.setEnabled(true);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(this,e1.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
				}
		}
	}

}