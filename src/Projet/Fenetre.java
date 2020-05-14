package Projet;

import java.awt.Container;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Fenetre extends JFrame
{
	private ArrayList<Transition> Transition;
	
	public Fenetre(ArrayList<Transition> Transition) throws IOException
	{
		this.Transition = Transition;
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Table");
		this.setSize(800,500);
			
		ArrayList<String> EtatInitial = Methode.ListeEtatsTrInit(Transition);
		ArrayList<String> EtatFinal = Methode.ListeEtatsTrFin(Transition);
		ArrayList<String> ListEtat =  Methode.ListeDiffEtats(EtatInitial,EtatFinal);

		//Creation Tableau
		String colonne[] = new String[Methode.Lettre(Transition).size()+2]; //+2 pour laisser du vide avant d ecrire les transitions possibles
		colonne[0]=" ";
		colonne[1]=" ";
		for(int i=2 ; i<Methode.Lettre(Transition).size()+2 ; i++)//On liste les transitions possibles
		{
			colonne[i] = Methode.Lettre(Transition).get(i-2);
		}
		
		//Donnees du tableau
		Object data[][] = new Object[ListEtat.size()][colonne.length]; //La matrice
		
		for(int e=0 ; e<ListEtat.size() ;e++)
		{
			ArrayList<String> Ligne = Methode.getLigneTableau(ListEtat.get(e) , Transition);
			for(int c=0 ; c<colonne.length ;c++)
			{
				data[e][c] = Ligne.get(c);
			}
		}
		// Tableau
		JTable table = new JTable(data,colonne);
		
		this.getContentPane().add(new JScrollPane(table));
	}

}
