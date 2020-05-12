package Projet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) throws IOException 
	{
		try 
		{
			System.out.println("Quel Transition voulez-vous etudier?");
			Scanner n = new Scanner(System.in);
			int nb = n.nextInt();
			
			String fichier = new File("src/Files/L3NEW-MpI-12-" + nb + ".txt").getAbsolutePath();
			
			// Lire l'Transition
			
			ArrayList<Transition> AF = Methode.Lire(fichier);
			System.out.println(AF);
			
			// Afficher
			Fenetre fen = new Fenetre(AF);
			fen.setVisible(true);
			
			System.out.println(Methode.Lettre(AF));
			
			//TEST
			/*ArrayList<String> Etats = Methode. DifferentEtatsFin(AF);
			System.out.println(Etats);
			ArrayList<String> Etat = Methode. DifferentEtatsInit(AF);
			System.out.println(Etat);
			ArrayList<String> E = Methode. DifferentEtats(Etat,Etats);
			System.out.println(E);*/
			ArrayList<String> Auto = new ArrayList<String>();
			Auto = Methode.getInit(AF);
			System.out.println(Auto);
			ArrayList<String> Auto4 = new ArrayList<String>();
			Auto4 = Methode.getLigneTableau("0",AF);
			System.out.println(Auto4);
			
			//Suite du projet
			boolean verif = Methode.est_un_Automate_asynchrone(AF);
			if(verif)
			{
				ArrayList<Transition> AFDC = Methode.determinisation_et_completion_Automate_asynchrone(AF);
				ArrayList<Transition> det = Methode.toDeterministe(AF);
				System.out.println(AFDC);
				System.out.println(det);
			}
			else
			{
				System.out.println("Automate synchrone ");
			}
		}
		
	catch (Exception e) 
		{
		System.err.println("Un probleme est survenu :" + e);
		}
	}
}
