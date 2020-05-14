package Projet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class Methode {
	
	public static ArrayList<Transition> Lire(String Transition) throws IOException
	{
		ArrayList<Transition> Tableau= new ArrayList<Transition>();
		BufferedReader Bread = null;
		FileReader Fread = null;
		String Lecture;
		Fread = new FileReader(Transition);
		Bread = new BufferedReader(Fread);
		while ((Lecture = Bread.readLine()) != null) 
		{
			String[] Ligne = Lecture.split(" ");
			Transition auto=new Transition(Ligne[0], Ligne[1], Ligne[2], Ligne[3]);
			Tableau.add(auto);
			
		}
		Bread.close();
		return (Tableau);
	}
	
	//METHODES POUR CONSTRUIRE LE TABLEAU
	public static ArrayList<String> Lettre(ArrayList<Transition> Transition) //Liste des transitions possibles
	{
		ArrayList<String> Fleche = new ArrayList<String>();
		for(Transition e : Transition)
		{
			if(Fleche.size() == 0)
			{
				Fleche.add(e.getTransition());
			}
			else
			{
				boolean doublon = false;
				for(int j=0 ; j<Fleche.size(); j++)
				{
					if(e.getTransition().equals(Fleche.get(j)))
					{
						doublon = true;
					}
				}
				if(!doublon)
				{
					Fleche.add(e.getTransition());
				}
			}
		}
		return Fleche;
	}
	
	public static ArrayList<String> ListeEtatsTrInit(ArrayList<Transition> Transition)  //Liste tous les differents etats des transitions initiaux
	{
		ArrayList<String> Initiaux = new ArrayList<String>();
		Initiaux.add(Transition.get(0).getEtatINIT());
		for(Transition e : Transition)
		{
			boolean doublon = false;
			for(int j=0 ; j<Initiaux.size(); j++)
			{
				if(e.getEtatINIT().equals(Initiaux.get(j)))
				{
					doublon = true;
				}
			}
			if(!doublon)
			{
				Initiaux.add(e.getEtatINIT());
			}
		}
		return Initiaux;
	}
	
	public static ArrayList<String> ListeEtatsTrFin(ArrayList<Transition> Transition) //Liste tous les differents etats des transitions finaux
	{
		ArrayList<String> Fin = new ArrayList<String>();
		Fin.add(Transition.get(0).getEtatFINAL());
		for(Transition e : Transition)
		{
			boolean doublon = false;
			for(int j=0 ; j<Fin.size(); j++)
			{
				if(e.getEtatFINAL().equals(Fin.get(j)))
				{
					doublon = true;
				}
			}
			if(!doublon)
			{
				Fin.add(e.getEtatFINAL());
			}
		}
		return Fin;
	}
	
	public static ArrayList<String> ListeDiffEtats(ArrayList<String> init , ArrayList<String> fin) //Liste tous les differents etats
	{
		ArrayList<String> ListEtat = new ArrayList<String>();
		for(String i:init)
		{
			if(!ListEtat.contains(i))
			{
				ListEtat.add(i);
			}
		}
		for(String f : fin)
		{
			if(!ListEtat.contains(f))
			{
				ListEtat.add(f);
			}
		}
		return ListEtat;
	}
	
	public static ArrayList<String> getInit(ArrayList<Transition> Tableau) throws IOException  //Liste tous les etats E
	{
		ArrayList<String> EtatInitial = new ArrayList<String>();
		for(int i=0;i<Tableau.size();i++)
		{
			if(Tableau.get(i).getEntreeOUsortie().equals("E") && !EtatInitial.contains(Tableau.get(i).getEtatINIT()) )
			{
				EtatInitial.add(Tableau.get(i).getEtatINIT());
			}
		}
		return EtatInitial;
	}
	
	public static ArrayList<String> getFin(ArrayList<Transition> Tableau) throws IOException  //Liste tous les etats S
	{
		ArrayList<String> EtatFin = new ArrayList<String>();
		
		for(Transition tf : Tableau)
		{
			if(tf.getEntreeOUsortie().equals("S") && !EtatFin.contains(tf.getEtatINIT()))
			{
				EtatFin.add(tf.getEtatINIT());
			}
		}
		return EtatFin;
	}

	public static ArrayList<String> getES(ArrayList<Transition> Tableau) throws IOException // Liste tous les etats ES
	{
		ArrayList<String> EtatFin = new ArrayList<String>();
		for(int i=0;i<Tableau.size();i++)
		{
			if(Tableau.get(i).getEntreeOUsortie().equals("ES") && !EtatFin.contains(Tableau.get(i).getEtatINIT()))
			{
				EtatFin.add(Tableau.get(i).getEtatINIT());
			}
		}
		return EtatFin;
	}

	public static ArrayList<String> getLigneTableau(String etat, ArrayList<Transition> Tableau) throws IOException
	{
		ArrayList<String> Ligne = new ArrayList<String>();
		int nbLettre = Lettre(Tableau).size();
		
		while(Ligne.isEmpty())
		{
			for(String e:getInit(Tableau))
			{
				if(e.equals(etat))
				{
					Ligne.add("  E");
				}
			}
			for(String e:getFin(Tableau))
			{
				if(e.equals(etat))
				{
					Ligne.add("  S");
				}
			}
			for(String e:getES(Tableau))
			{
				if(e.equals(etat))
				{
					Ligne.add("  ES");
				}
			}
			if(Ligne.isEmpty())
			{
				Ligne.add("  *");
			}
		}
		Ligne.add("  "+etat); //2 espaces vides
		
		for(int i =0; i< nbLettre ;i++)
		{
			String LigneTemporaire = new String();
			for(int j=0;j<Tableau.size();j++)
			{
				if(etat.equals(Tableau.get(j).getEtatINIT()) && Lettre(Tableau).get(i).equals(Tableau.get(j).getTransition()) )
				{
					if(LigneTemporaire.isEmpty())
					{
						LigneTemporaire = Tableau.get(j).getEtatFINAL();
					}
					else
					{
						LigneTemporaire= LigneTemporaire + "  ,  " + Tableau.get(j).getEtatFINAL();
					}
				}
			}
			Ligne.add(LigneTemporaire);
		}
		return Ligne;
	}

	//DETERMINISATION ET COMPLETION
	public static ArrayList<Integer> PositionsMotVide (ArrayList<Transition> AF)
	{
		int etoile=0;
		ArrayList<Integer> Positions = new ArrayList<Integer>();
		for(Transition t : AF)
		{
			if(t.getTransition().equals("*"))
			{
				Positions.add(etoile);
			}
			etoile++;
		}
		return Positions;
	}
	
	public static ArrayList<Transition> getMotLu(String EtatFinMotVide,String EtatInitMotVide,ArrayList<Transition> AF) //Reconstruit la liste des transitions si on enleve *
	{
		ArrayList<Transition> tab = new ArrayList<Transition>();
		for(Transition e: AF)
		{
			if(e.getEtatINIT().equals(EtatFinMotVide))
			{
				Transition a = new Transition("*", EtatInitMotVide, e.getTransition(), e.getEtatFINAL());
				tab.add(a);
			}
		}
		return tab;
	}

	public static String RangerEtat(String etats) // Range un groupe d etat par ordre croissant
	{
		try
		{
			String[] List1 = etats.split(",");
			ArrayList<String> List2 = new ArrayList<String>();
			ArrayList<String> Memoire = new ArrayList<String>();
			for(String elt : List1)
			{
				List2.add(elt);
			}
			Object objet = Collections.min(List2);
			String Ranger = objet.toString();
			Memoire.add(Ranger);
			int Decompteur = List2.size();
			while(Decompteur > 1)
			{
				int k = 0;
				int n = Integer.parseInt(List2.get(k));
				while(Memoire.contains(List2.get(k)))
				{
					k++;
					n = Integer.parseInt(List2.get(k));
				}
				String etatpetit = "";
				int indice =0;
				for(int c=0 ; c <List2.size();c++)
				{
					int i = Integer.parseInt(List2.get(c));
					if(n>=i)
					{
						if(!Memoire.contains(List2.get(c)))
						{
							n=i;
							etatpetit = List2.get(c);
							indice =c;
						}
					}

				}
				Memoire.add(etatpetit);
				Ranger = Ranger + "," + etatpetit;
				List2.remove(indice);
				Decompteur--;
			}
			return Ranger;
		}
		catch (NumberFormatException e)
		{
			return "";
		}
	}
	
	public static ArrayList<Transition> toDeterministe (ArrayList<Transition> AFDC, ArrayList<Transition> AF ) throws IOException // Transforme un automate en automate deterministe et complet

	{
		ArrayList<Transition> AutoDet = new ArrayList<Transition>();
		ArrayList<String> EtatFutur = new ArrayList<String>();
		ArrayList<String> EtatS = getFin(AF);
		ArrayList<String> EtatE = getInit(AF);
		ArrayList<String> ListeTransitions = Lettre(AFDC);
		String indicationE = "E";
		boolean ESverif = false;
		for(String es : getES(AF))
		{
			if(!EtatE.contains(es))
			{
				EtatE.add(es);
				ESverif = true;
				indicationE = "ES";
			}
		}
		//Initialisation
		String etat1 ="";
		boolean f = true;
		for(String elt : EtatE)
		{
			if(f)
			{
				etat1 = elt;
				f=false;
			}
			else
			{
				etat1 = etat1 + "," + elt;
			}
		}
		
		for(int i = 0 ; i < ListeTransitions.size(); i++)
		{
			boolean first = true;
			String etat3 = "";
	
			for(String e : EtatE)
			{
				for(Transition a : AFDC)
				{
					if(a.getEtatINIT().equals(e))
					{
						if(a.getTransition().equals(ListeTransitions.get(i)))
						{
							if(!etat3.contains(a.getEtatFINAL()))
							{
								if(first)
								{
									etat3=a.getEtatFINAL();
									first =false;
								}
								else
								{
									etat3 = etat3 + "," + a.getEtatFINAL();
								}
							}
						}
					}
				}
			}
			Transition Entree = new Transition(indicationE,RangerEtat(etat1),ListeTransitions.get(i),RangerEtat(etat3));
			EtatFutur.add(RangerEtat(etat3));
			AutoDet.add(Entree);
		}
		
		//Algorithm
		
		ArrayList<String> EtatsInscrits = new ArrayList<String>();
		EtatsInscrits.add(RangerEtat(etat1)); //On a etudier l etat E range
		while(EtatFutur.size()!=0) //Tant qu il ya un etat a etudier dans notre liste
		{
			String etatNonE = RangerEtat(EtatFutur.get(0));
			if (!etatNonE.equals(""))
			{
				if(!EtatsInscrits.contains(etatNonE))//Si ce n est pas un etat qui est deja inscrit
				{
					for(String transition : ListeTransitions)
					{
						boolean firstverif = true;
						String etatfinal ="";
						String[] etatAvantDet = etatNonE.split(","); //etatNonE est range
						for(String etatSplit : etatAvantDet)
						{
							for( Transition simple : AFDC)
							{
								if(simple.getEtatINIT().equals(etatSplit))
								{
									if(simple.getTransition().equals(transition))
									{
										if(!etatfinal.contains(simple.getEtatFINAL()))
										{
											if(firstverif)
											{
												etatfinal =simple.getEtatFINAL();
												firstverif = false;
											}
											else
											{
												etatfinal =etatfinal +"," + simple.getEtatFINAL();
											}
										}
									}
								}
							}
						}
						Transition etatnew = new Transition ( "*" ,etatNonE,transition, RangerEtat(etatfinal));
						AutoDet.add(etatnew);
						EtatsInscrits.add(etatNonE);
						if(!EtatsInscrits.contains(RangerEtat(etatfinal)))
						{
							EtatFutur.add(RangerEtat(etatfinal));
						}
					}

				}
			}
		EtatFutur.remove(0);
		}
		//On ajoute vide par P
		for(int t = 0 ; t< AutoDet.size() ; t++)
		{
			if(AutoDet.get(t).getEtatINIT().equals("") || AutoDet.get(t).getEtatINIT().equals("*"))
			{
				Transition v0= new Transition (AutoDet.get(t).getEntreeOUsortie(),"P",AutoDet.get(t).getTransition(),AutoDet.get(t).getEtatFINAL());
				AutoDet.set(t, v0);
			}
			if(AutoDet.get(t).getEtatFINAL().equals("") || AutoDet.get(t).getEtatFINAL().equals("*"))
			{
				Transition v1= new Transition (AutoDet.get(t).getEntreeOUsortie(),AutoDet.get(t).getEtatINIT(),AutoDet.get(t).getTransition(),"P");
				AutoDet.set(t, v1);
			}
		}
		//On remplit etat P
		for(String transitionP : ListeTransitions)
		{
			boolean pb = true;
			for(Transition dc : AutoDet)
			{
				if(dc.getEtatINIT().equals("P") && dc.getTransition().equals(transitionP))
				{
					pb = false;
				}
			}
			if(pb)
			{
				AutoDet.add(new Transition("*","P",transitionP,"P"));
			}
		}
		//On ajoute S
		for(int n = 0 ; n< AutoDet.size() ; n++)
		{
			for(String etatSortie : EtatS)
			{
				if(AutoDet.get(n).getEntreeOUsortie().equals("*"))
				{
					String[] ListeEtatSplit = AutoDet.get(n).getEtatINIT().split(",");
					for(String eltSplit : ListeEtatSplit)
					{
						if(eltSplit.equals(etatSortie))
						{
							AutoDet.set(n, new Transition("S",AutoDet.get(n).getEtatINIT(), AutoDet.get(n).getTransition(), AutoDet.get(n).getEtatFINAL()));
						}
					}
				}
				
			}
		}
		
		
		return AutoDet;
	}
	
	public static boolean est_un_Automate_asynchrone(ArrayList<Transition> AF)
	{
		boolean verif = false;
		int etoile=0;
		ArrayList<Integer> Positions = new ArrayList<Integer>();
		for(Transition t : AF)
		{
			boolean now=false;
			if(t.getTransition().equals("*"))
			{
				verif=true;
				now=true;
				Positions.add(etoile);
			}
			System.out.println("Lecture de la ligne " + etoile + ":   Recherche de la transition * ---> " + now);
			etoile++;
		}
		System.out.println("------------------------------------------------------------------------------------");
		System.out.println(" Resume de la procedure :");
		if(verif)
		{
			System.out.println("C'est un Automate asynchrone car le/les mot vide * se trouve/ent à :");
			for(int p : Positions)
			{
				System.out.println( "   "+AF.get(p).getEtatINIT() + " --- " + AF.get(p).getTransition() + " ---> " + AF.get(p).getEtatFINAL());
			}
		}
		else
		{
			System.out.println("Ce n'est pas un Automate asynchrone car l Automate ne contient pas de transitions * ");
		}
		return verif;
	}
	
	public static ArrayList<Transition> determinisation_et_completion_Automate_asynchrone(ArrayList<Transition> AF) throws IOException
	{
		ArrayList<Integer> Positions = PositionsMotVide(AF);
		ArrayList<String> EtatFinauxMotVide = new ArrayList<String>();
		ArrayList<String> EtatInitMotVide = new ArrayList<String>();
		ArrayList<Transition> AFDC = new ArrayList<Transition>();
		for(Transition tr : AF)
		{
			AFDC.add(tr);
		}
		for(int i:Positions)
		{
			if(!EtatFinauxMotVide.contains(AF.get(i).getEtatFINAL()))
			{
				EtatFinauxMotVide.add(AF.get(i).getEtatFINAL());
				EtatInitMotVide.add(AF.get(i).getEtatINIT());
			}
		}
		for(int k=0;k<EtatFinauxMotVide.size();k++)
		{
			for(Transition e : getMotLu(EtatFinauxMotVide.get(k),EtatInitMotVide.get(k),AF)) // Donne un nouveau Transition si on enleve *
			{
				AFDC.add(e);
			}
		}
		for(int n=0 ; n<AFDC.size();n++)
		{
			if(AFDC.get(n).getTransition().contains("*"))
			{
				AFDC.remove(n);
			}
		}
		AFDC = toDeterministe(AFDC,AF);
		return AFDC;
	}

	public static boolean est_un_Automate_deterministe(ArrayList<Transition> AF)
	{
		boolean verif = false;
		
		return verif;
	}


	
	//Minimisation
	
	public static ArrayList<Transition> minimisation(ArrayList<Transition> AFDC)
	{
		ArrayList<Transition> AFDCM = new ArrayList<Transition>();
		ArrayList<String> Teta = new ArrayList<String>();
		
		return AFDCM;
	}


}
	
