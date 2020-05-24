package Projet;

import java.awt.Container;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Fenetre extends JFrame
{
	private ArrayList<Transition> Automate;
	
	public Fenetre(ArrayList<Transition> Automate , String NomTab) throws IOException
	{
		this.Automate = Automate;
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(NomTab);
		this.setSize(800,500);
			
		ArrayList<String> EtatInitial = Methode.ListeEtatsTrInit(Automate);
		ArrayList<String> EtatFinal = Methode.ListeEtatsTrFin(Automate);
		ArrayList<String> ListEtat =  Methode.ListeDiffEtats(EtatInitial,EtatFinal);
		
		for(int indice=0 ; indice < ListEtat.size() ; indice++)
		{
			boolean trverif = false;
			for(Transition tr : Automate)
			{
				if(tr.getEtatINIT().equals(ListEtat.get(indice)))
				{
					trverif = true;
				}
			}
			if(!trverif)
			{
				ListEtat.remove(indice);
				indice--;
			}
		}
		
		//Creation Tableau
		String colonne[] = new String[Methode.Lettre(Automate).size()+2]; //+2 pour laisser du vide avant d ecrire les transitions possibles
		colonne[0]=" ";
		colonne[1]=" ";
		for(int i=2 ; i<Methode.Lettre(Automate).size()+2 ; i++)//On liste les transitions possibles
		{
			colonne[i] = Methode.Lettre(Automate).get(i-2);
		}
		
		//Donnees du tableau
		Object data[][] = new Object[ListEtat.size()][colonne.length]; //La matrice
		
		for(int e=0 ; e<ListEtat.size() ;e++)
		{
			ArrayList<String> Ligne = Methode.getLigneTableau(ListEtat.get(e) , Automate);
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
