package taquin;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class Filtre extends FileFilter{

	private String extension; //l'extension du fichier
	private String description; //la description
	private String[] extensions;//un tableau des extensions
	
	//le premier constructeur qui prend comme arguments l'extension et la description correspondant
	public Filtre(String description,String extension){
		if(description==null || extension==null)
			throw new NullPointerException("La desciption ou l'extension ne peut pas être null");
		this.description=description;
		this.extension=extension;
	}
	
	//le premier constructeur qui prend comme arguments plusieurs extensions et la description correspondant
	public Filtre(String description,String[] extensions){
		if(description==null || extensions==null)
			throw new NullPointerException("La desciption ou l'extension ne peut pas être null");
		this.description=description;
		this.extensions=extensions;
	}
	
	@Override
	public boolean accept(File f) {
		if(f.isDirectory())
			return true;
		String fileName=f.getName();
		if (extensions != null){
            boolean accept = false;
            for (int i=0; i<extensions.length; i++){
                if (fileName.endsWith(extensions[i])){
                    accept = true;
                }
            }
            return accept;
        }
        else {
            return fileName.endsWith(extension);
        }
	}

	@Override
	public String getDescription() {
		return this.description;
	}

}