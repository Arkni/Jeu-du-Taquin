package taquin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ScoreDialog extends JDialog{

	/*
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//la table et le JScrollPane
	private JTable table;
	private ScoreModel model;
	private JScrollPane pane=new JScrollPane();

	//un paneau
	private JPanel panel=new JPanel();

	//les boutons
	private JPanel boutonPane=new JPanel();
	JButton initialiser=new JButton("Initialiser");
	JButton fermer=new JButton("Fermer");

	public ScoreDialog() {
		this.setTitle("Score - Jeu du Taquin");
		this.setSize(500,350);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setIconImage(new ImageIcon("essources/Icons/icon.png").getImage());

		//la table
		model=new ScoreModel();
		table=new JTable(model);
		pane.setViewportView(table);		

		//le bouton initialiser
		if(model.getList().size()==0)
			initialiser.setEnabled(false);
		initialiser.addActionListener(new ActionsController(this));

		//le bouton fermer
		fermer.addActionListener(new ActionsController(this));
		getRootPane().setDefaultButton(fermer);

		//le boutonPane
		boutonPane.setLayout(new FlowLayout(FlowLayout.RIGHT,10,5));
		boutonPane.add(initialiser);
		boutonPane.add(fermer);

		//la paneau par d�faut
		panel.setLayout(new BorderLayout());
		panel.add(pane,BorderLayout.CENTER);
		panel.add(boutonPane,BorderLayout.SOUTH);

		this.getContentPane().add(panel);

		centrerTable(table);

		this.setVisible(true);

	}//fin constructeur

	//centrer les cellules
	private void centrerTable(JTable table) {
		
		((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment( JLabel.CENTER );
		table.getTableHeader().setForeground(Color.RED);

		if(table.getRowCount()!=0)
			table.setRowSelectionInterval(0, 0);
		
		DefaultTableCellRenderer custom = new DefaultTableCellRenderer(); 
		custom.setHorizontalAlignment(JLabel.CENTER); 
		for (int i=0 ; i<table.getColumnCount() ; i++) 
			table.getColumnModel().getColumn(i).setCellRenderer(custom); 
	}//

	//getModel
	ScoreModel getModel(){
		return model;
	}

}
