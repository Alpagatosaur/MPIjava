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
			System.out.println("Quel Automate voulez-vous etudier?");
			Scanner n = new Scanner(System.in);
			int nb = n.nextInt();
			
			while(nb!=-1)
			{
				System.out.println( "Traitement ..." );
				String fichier = new File("src/Files/L3NEW-MpI-12-" + nb + ".txt").getAbsolutePath();
				
				// Lire l'Transition
				
				ArrayList<Transition> AF = Methode.Lire(fichier);
				
				// Afficher
				Fenetre fen = new Fenetre(AF ,"Tableau AF automate "+nb);
				fen.setVisible(true);
				
				//Determinisation et Completion
				System.out.println();
				System.out.println("Determinisation et completion");
				ArrayList<Transition> AFDC = new ArrayList<Transition>();
				boolean verif = Methode.est_un_Automate_asynchrone(AF);
				if(verif)
				{
					Methode.afficher_raisons_asynchrone(AF);
					AFDC = Methode.determinisation_et_completion_Automate_asynchrone(AF);
					Fenetre fent = new Fenetre(AFDC , "Tableau AFDC automate"+nb);
					fent.setVisible(true);
				}
				else
				{
					Methode.afficher_raisons_asynchrone(AF);
					System.out.println("  ");
					System.out.println("Automate synchrone ");
					System.out.println("  ");
					if(Methode.est_un_Automate_deterministe(AF))
					{
						System.out.println(" automate deterministe");
						if(Methode.est_un_automate_complet(AF))
						{
							AFDC = Methode.determinisation_et_completion_automate_synchrone(AF);
						}
						else
						{
							System.out.println("  ");
							AFDC = Methode.completion(AF);
						}
					}
					else
					{
						System.out.println("  ");
						AFDC = Methode.determinisation_et_completion_automate_synchrone(AF);
					}
					Fenetre fent = new Fenetre(AFDC , "Tableau AFDC automate"+nb);
					fent.setVisible(true);
				}
				
				//Minimisation
				System.out.println();
				System.out.println("Minimisation");
				ArrayList<Transition> AFDCM = Methode.Minimisation(AFDC);
				Fenetre fenr = new Fenetre(AFDCM , "Tableau AFDCM");
				fenr.setVisible(true);
				
				
				
				//Lecture mot
				System.out.println("");
				System.out.println("Reconnaissance de mots");
				System.out.println("On utilise AFDC");
				
				
				ArrayList<Transition> A = AFDC;
				boolean STOP = false;
				while(!STOP)
				{
					System.out.println(" Quel mot voulez-vous analyser? (Tapez Stop pour arreter la lecture)");
					Scanner w = new Scanner(System.in);
					String Word= w.next();
					if(Word.equals("Stop"))
					{
						STOP = true;
					}
					else
					{
						System.out.println(Methode.Lire_mot(Word , A));
					}
					
				}
				
				//Langage complementaire
				System.out.println("");
				System.out.println("Langage complementaire");
				System.out.println("On utilise l automate AFDC ");
				
				
				ArrayList<Transition> AComp = Methode.automate_complementaire(A);
				STOP = false;
				while(!STOP)
				{
					System.out.println(" Quel mot voulez-vous analyser? (Tapez Stop pour arreter la lecture)");
					Scanner w = new Scanner(System.in);
					String Word= w.next();
					if(Word.equals("Stop"))
					{
						STOP = true;
					}
					else
					{
						System.out.println(Methode.Lire_mot(Word , AComp));
					}
				}
				
				
				//Standardisation
				System.out.println("");
				System.out.println("Standardisation");
				System.out.println(" Voulez-vous effectuer la standardisation sur le langage complementaire? 0 : Non  // 1 : Oui");
				Scanner stdscan = new Scanner(System.in);
				int std = stdscan.nextInt();
				
				while(std != -1)
				{
					if(std==0)
					{
						System.out.println("On utilise l automate AFDC ");
						ArrayList<Transition> AStd = new ArrayList<Transition>();
						boolean stdverif = Methode.checkSTD(A);
						
						if(!stdverif)
						{
							AStd = Methode.automate_standard(A);
							System.out.println("  Automate non standard");
							System.out.println("  Modification en cours ...");
						}
						else
						{
							AStd = A;
							System.out.println("  Automate standard");
						}
						STOP =false;
						while(!STOP)
						{
							System.out.println(" Quel mot voulez-vous analyser? (Tapez Stop pour arreter la lecture)");
							Scanner w = new Scanner(System.in);
							String Word= w.next();
							if(Word.equals("Stop"))
							{
								STOP = true;
							}
							else
							{
								System.out.println(Methode.Lire_mot(Word , AStd));
							}
						}
						System.out.println(" Voulez-vous effectuer la standardisation sur le langage complementaire? 0 : Non  // 1 : Oui // -1 Sortie");
						Scanner scan = new Scanner(System.in);
						std = scan.nextInt();
					}
					else if(std == 1)
					{
						System.out.println( "On utilise l automate AComp ");
						ArrayList<Transition> ACompStd = new ArrayList<Transition>();
						boolean stdverif = Methode.checkSTD(AComp);
						
						if(!stdverif)
						{
							ACompStd = Methode.automate_standard(AComp);
							System.out.println("  Automate non standard");
							System.out.println("  Modification en cours ...");
						}
						else
						{
							ACompStd = AComp;
							System.out.println("  Automate standard");
						}
						STOP =false;
						while(!STOP)
						{
							System.out.println(" Quel mot voulez-vous analyser? (Tapez Stop pour arreter la lecture)");
							Scanner w = new Scanner(System.in);
							String Word= w.next();
							if(Word.equals("Stop"))
							{
								STOP = true;
							}
							else
							{
								System.out.println(Methode.Lire_mot(Word , ACompStd));
							}
						}
						System.out.println(" Voulez-vous effectuer la standardisation sur le langage complementaire? 0 : Non  // 1 : Oui // -1 Sortie");
						Scanner scan = new Scanner(System.in);
						std = scan.nextInt();
					}
					else
					{
						System.out.println(" Nombre non reconnu : Fin d analyse ");
						std = -1;
						
					}
				}
				
				System.out.println("-------------------------------------------------------------------------------------------");
				System.out.println("");
				System.out.println("Quel Automate voulez-vous etudier?");
				Scanner nouveau = new Scanner(System.in);
				nb = nouveau.nextInt();
			}
			System.out.println("   FIN   ");
		}
		
	catch (Exception e) 
		{
		System.err.println("Un probleme est survenu :" + e);
		}
	}
}
