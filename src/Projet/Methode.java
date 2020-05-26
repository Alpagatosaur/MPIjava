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
		ArrayList<String> A = new ArrayList<String>();
		A.add("*");
		for(Transition e : Transition)
		{
			//Initialisation
			if(Fleche.size() == 0)
			{
				Fleche.add(e.getTransition());
			}
			//On remplit la liste
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
		if(Fleche.equals(A))
		{
			A.remove(0);
			A.add("a");
			Fleche = A;
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
	
	public static ArrayList<String> getE(ArrayList<Transition> Tableau) throws IOException  //Liste tous les etats E
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
	
	public static ArrayList<String> getS(ArrayList<Transition> Tableau) throws IOException  //Liste tous les etats S
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
			for(String e:getE(Tableau))
			{
				if(e.equals(etat))
				{
					Ligne.add("  E");
				}
			}
			for(String e:getS(Tableau))
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

	public static ArrayList<Transition> infoEScompleter (ArrayList<Transition> A) throws IOException
	{
		ArrayList<Transition> write = new ArrayList<Transition>();
		for(Transition auto : A)
		{
			if(auto.getEntreeOUsortie().contains("*") && !auto.getTransition().contains("*"))
			{
				if(getE(A).contains(auto.getEtatINIT()))
				{
					write.add(new Transition("E" , auto.getEtatINIT(), auto.getTransition() , auto.getEtatFINAL()));
				}
				else if(getS(A).contains(auto.getEtatINIT()))
				{
					write.add(new Transition("S" , auto.getEtatINIT(), auto.getTransition() , auto.getEtatFINAL()));
				}
				else if(getES(A).contains(auto.getEtatINIT()))
				{
					write.add(new Transition("ES" , auto.getEtatINIT(), auto.getTransition() , auto.getEtatFINAL()));
				}
				else
				{
					write.add(auto);
				}
			}
			else if(auto.getTransition().contains("*"))
			{
				if(auto.getEntreeOUsortie().contains("*"))
				{
					if(getE(A).contains(auto.getEtatFINAL()))
					{
						write.add(new Transition("E" , auto.getEtatINIT(), auto.getTransition() , auto.getEtatFINAL()));
					}
					else if(getS(A).contains(auto.getEtatFINAL()))
					{
						write.add(new Transition("S" , auto.getEtatINIT(), auto.getTransition() , auto.getEtatFINAL()));
					}
					else if(getES(A).contains(auto.getEtatFINAL()))
					{
						write.add(new Transition("ES" , auto.getEtatINIT(), auto.getTransition() , auto.getEtatFINAL()));
					}
					else
					{
						write.add(auto);
					}
				}
				else
				{
					if(getES(A).contains(auto.getEtatFINAL()))
					{
						write.add(new Transition("ES" , auto.getEtatINIT(), auto.getTransition() , auto.getEtatFINAL()));
					}
					else
					{
						write.add(auto);
					}
				}
			}
			else
			{
				write.add(auto);
			}
		}
		return write;
	}
	
	//DETERMINISATION ET COMPLETION
	
	public static ArrayList<Transition> NoDoublonTXT ( ArrayList<Transition> A) // Pour eviter des doublons dans l automate
	{
		ArrayList<Transition> NoDoublon = new ArrayList<Transition>();
		
		for(Transition abc : A)
		{
			boolean k = false;
			for(Transition xyz : NoDoublon)
			{
				if( xyz.getEtatINIT().equals(abc.getEtatINIT())  &&  xyz.getTransition().equals(abc.getTransition())  && xyz.getEtatFINAL().equals(abc.getEtatFINAL()) )
				{
					k =true;
				}
			}
			if(!k)
			{
				NoDoublon.add(abc);
			}
		}
		return NoDoublon;
	}
	
	public static ArrayList<Integer> PositionsMotVide (ArrayList<Transition> AF) //Recherche la position des mots vides dans l automate
	{
		int etoile=0;
		ArrayList<Integer> Positions = new ArrayList<Integer>();
		for(Transition t : AF)
		{
			if(t.getTransition().equals("**"))
			{
				Positions.add(etoile);
			}
			etoile++;
		}
		return Positions;
	}
	
	public static ArrayList<Transition> getMotLu(String EtatFinMotVide,String EtatInitMotVide,ArrayList<Transition> AF) throws IOException //Pour un etatInitMotVide il va reprendre les transitions de l etatFinMotVide
	{
		ArrayList<Transition> tab = new ArrayList<Transition>();
		ArrayList<String> EtatsFin = new ArrayList<String>();
		String Info ="*";
		boolean Fin = true;
		//Savoir si cest un E , S , *
		for(Transition e: AF)
		{
			if(e.getEtatINIT().equals(EtatInitMotVide))
			{
				Info = e.getEntreeOUsortie();
			}
		}
		// On cherche les transitions a ajouter si on enleve motvide
		boolean fin = false;
		ArrayList<String> newEtatFin = new ArrayList<String>();
		ArrayList<String> Memoire = new ArrayList<String>();
		newEtatFin.add(EtatFinMotVide);
		Memoire.add(EtatFinMotVide);
		ArrayList<Transition> ListAdd = new ArrayList<Transition>();
		while(!fin)
		{
			for(Transition InfoEtatFin : AF)
			{
				if(InfoEtatFin.getEtatINIT().equals(newEtatFin.get(0)))
				{
					if(!InfoEtatFin.getTransition().contains("*"))
					{
						ListAdd.add(InfoEtatFin);
					}
					else
					{
						if(!Memoire.contains(InfoEtatFin.getEtatFINAL()))
						{
							newEtatFin.add(InfoEtatFin.getEtatFINAL());
						}
					}
				}
			}
			newEtatFin.remove(0);
			if(newEtatFin.size()==0)
			{
				fin = true;
			}
			
		}
		
		for(Transition f : ListAdd)
		{
			if(Info.contains("*") )
			{
				tab.add(new Transition(f.getEntreeOUsortie(),EtatInitMotVide, f.getTransition() , f.getEtatFINAL()));
			}
			else if(Info.equals("ES") || f.getEntreeOUsortie().contains("*") || f.getEntreeOUsortie().equals(Info) )
			{
				tab.add(new Transition(Info,EtatInitMotVide, f.getTransition() , f.getEtatFINAL()));
			}
			else
			{
				if(f.getEntreeOUsortie().equals("E") && Info.equals("S"))
				{
					tab.add(new Transition("ES",EtatInitMotVide, f.getTransition() , f.getEtatFINAL()));
				}
				else if(f.getEntreeOUsortie().equals("S") && Info.equals("E"))
				{
					tab.add(new Transition("ES",EtatInitMotVide, f.getTransition() , f.getEtatFINAL()));
				}
				else
				{
					tab.add(new Transition(f.getEntreeOUsortie(),EtatInitMotVide, f.getTransition() , f.getEtatFINAL()));
				}
			}
		}
		return tab;
	}

	public static String RangerEtat(String etats) // Range un groupe d etat par ordre croissant
	{
		try
		{
			//etats est une liste d etats apres determinisation
			String[] List1 = etats.split(",");// ex on peut avoir 0,2  ou 0 et 2 sont des etats qu on a regroupe apres determinisation
			ArrayList<String> List2 = new ArrayList<String>();
			ArrayList<String> Memoire = new ArrayList<String>();
			//On ajoute dans List2 tous etats de la list1
			for(String elt : List1)
			{
				List2.add(elt);
			}
			//Creation Object pour avoir la methode pour obtenir la valeur minimale de la list2
			Object objet = Collections.min(List2);
			String Ranger = objet.toString();
			Memoire.add(Ranger);
			int Decompteur = List2.size();
			while(Decompteur > 1) // On a deja pris en compte le plus petit etat
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
	
	
	public static ArrayList<Transition> toDeterministeAndC (ArrayList<Transition> AFDC, ArrayList<Transition> AF ) throws IOException // Transforme un automate en automate deterministe et complet

	{
		ArrayList<Transition> AutoDet = new ArrayList<Transition>();
		ArrayList<String> EtatFutur = new ArrayList<String>();
		ArrayList<String> EtatS = getS(AF);
		ArrayList<String> EtatE = getE(AF);
		ArrayList<String> ListeTransitions = Lettre(AFDC);
		String indicationE = "E";
		for(String es : getES(AF))
		{
			if(!EtatE.contains(es)) // On rajoute dans l initialisation tous les etats ES
			{
				EtatE.add(es);
				indicationE = "ES";
			}
			if(!EtatS.contains(es)) // On rajoute dans la liste les ES
			{
				EtatS.add(es);
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
					if(!etatNonE.contains(","))
					{
						for(Transition xyz : AFDC)
						{
							if(xyz.getEtatINIT().equals(etatNonE))
							{
								AutoDet.add(new Transition("*" , etatNonE , xyz.getTransition() , xyz.getEtatFINAL()));
								EtatsInscrits.add(etatNonE);
								if(!EtatsInscrits.contains(RangerEtat(xyz.getEtatFINAL())))
								{
									EtatFutur.add(RangerEtat(xyz.getEtatFINAL()));
								}
							}
						}
					}
					else
					{
						 //etatNonE est range
						String[] etatAvantDet = etatNonE.split(",");
						for(String transition : ListeTransitions)
						{
							boolean firstverif = true;
							String etatfinal ="";
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
							AutoDet.add(new Transition ( "*" ,etatNonE,transition, RangerEtat(etatfinal)));
							EtatsInscrits.add(etatNonE);
							if(!EtatsInscrits.contains(RangerEtat(etatfinal)))
							{
								EtatFutur.add(RangerEtat(etatfinal));
							}
						}
					}
				}
			}
		EtatFutur.remove(0);
		}
		
		//Completion
		//On ajoute vide par P
		boolean verifP = false;
		//On ajoute les etats non appele
		/*for(Transition p : AFDC)
		{
			if(!ListeDiffEtats(ListeEtatsTrInit(AutoDet) , ListeEtatsTrFin(AutoDet)).contains(p.getEtatINIT()))
			{
				System.out.println(new Transition(p.getEntreeOUsortie() , p.getEtatINIT() , "a" , "P"));
				AutoDet.add(new Transition(p.getEntreeOUsortie() , p.getEtatINIT() , "a" , "P"));
				verifP = true;
			}
		}*/
		for(String etatverif : ListeDiffEtats(ListeEtatsTrInit(AutoDet) , ListeEtatsTrFin(AutoDet)))
		{
			//POur chaque mot
			for(String mot : ListeTransitions)
			{
				boolean veriftr = false;
				for(Transition au : AutoDet)
				{
					//Sil existe une transition du mot a partir de letat choisi alors la verification true
					if(au.getEtatINIT().equals(etatverif) && au.getTransition().equals(mot))
					{
						veriftr = true;
					}
				}
				if(!veriftr)
				{
					AutoDet.add(new Transition("*" , etatverif , mot , "P"));
					verifP = true;
				}
			}
		}
		for(int t = 0 ; t< AutoDet.size() ; t++)
		{
			if(AutoDet.get(t).getEtatFINAL().equals("") || AutoDet.get(t).getEtatFINAL().contains("*"))
			{
				Transition v1= new Transition (AutoDet.get(t).getEntreeOUsortie(),AutoDet.get(t).getEtatINIT(),AutoDet.get(t).getTransition(),"P");
				AutoDet.set(t, v1);
				verifP = true;
			}
			if(AutoDet.get(t).getEtatINIT().equals("") || AutoDet.get(t).getEtatINIT().contains("*"))
			{
				AutoDet.remove(t);
				t--;
			}
		}
		//On ajoute P si on a deja utilise P
		if(verifP)
		{
			for(String transitionP : ListeTransitions)
			{
			AutoDet.add(new Transition("*","P",transitionP,"P"));
			}
		}
		//On cherche les S
		for(int n = 0 ; n< AutoDet.size() ; n++)
		{
			for(String etatSortie : EtatS)
			{
				if(AutoDet.get(n).getEntreeOUsortie().contains("*"))
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
		AutoDet = NoDoublonTXT(AutoDet);
		return AutoDet;
	}
	
	public static boolean est_un_Automate_asynchrone(ArrayList<Transition> AF)
	{
		boolean verif = false;
		for(Transition t : AF)
		{
			if(t.getTransition().equals("**"))
			{
				verif=true;
			}
		}
		return verif;
	}
	public static void afficher_raisons_asynchrone(ArrayList<Transition> AF)
	{
		boolean verif = false;
		int etoile=0;
		ArrayList<Integer> Positions = new ArrayList<Integer>();
		for(Transition t : AF)
		{
			boolean now=false;
			if(t.getTransition().equals("**"))
			{
				verif=true;
				now=true;
				Positions.add(etoile);
			}
			System.out.println("Lecture de la ligne " + etoile + ":   Recherche de la transition mot vide ** ---> " + now);
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
	}
	
	public static ArrayList<Transition> determinisation_et_completion_Automate_asynchrone(ArrayList<Transition> AF) throws IOException
	{
		AF = infoEScompleter(AF);
		ArrayList<Integer> Positions = PositionsMotVide(AF);
		ArrayList<Transition> EtatsMotVide = new ArrayList<Transition>();
		ArrayList<Transition> AFDC = new ArrayList<Transition>();
		for(Transition tr : AF)
		{
			AFDC.add(tr);
		}
		for(int i:Positions)
		{
			EtatsMotVide.add(AF.get(i));
		}
		for(int n=0 ; n<AFDC.size();n++)
		{
			if(AFDC.get(n).getTransition().contains("*"))
			{
				AFDC.remove(n);
				n--;
			}
		}
		for(int k=0;k< EtatsMotVide.size() ;k++)
		{
			for(Transition e : getMotLu(EtatsMotVide.get(k).getEtatFINAL(),EtatsMotVide.get(k).getEtatINIT(),AF))
			{
				AFDC.add(e);
			}
		}
		AFDC = NoDoublonTXT(AFDC);
		AFDC = toDeterministeAndC(AFDC,AF);
		return AFDC;
	}
	
	
			//est_un_automate_déterministe(AF) 
	public static boolean deux_etats_differents_meme_lettre(ArrayList<Transition> AF) {
	boolean verif = false;
	ArrayList<String> EtatsVerif = ListeDiffEtats(ListeEtatsTrInit(AF) , ListeEtatsTrFin(AF));
	ArrayList<Transition> Liste = new ArrayList<Transition>();
	
	for(String mtn : EtatsVerif)
	{
		for(Transition Etat : AF)
		{
			if(Etat.getEtatINIT().equals(mtn))
			{
				Liste.add(Etat);
			}
		}
		if(Liste.size()>1)
		{
			for(String lettre : Lettre(AF))
			{
				int count = 0;
				for(int i = 0 ; i < Liste.size() ; i++)
				{
					if(Liste.get(i).getTransition().equals(lettre))
					{
						count++;
					}
				}
				if(count > 1)
				{
					verif = true;
				}
				count =0;
			}
		}
		Liste.clear();
	}
	
	return verif;

	}

	public static  boolean est_un_Automate_deterministe(ArrayList<Transition> AF) {

	boolean verif = false;
	if(est_un_Automate_asynchrone(AF)==false ) {
	ArrayList<String> result1 = null;
	ArrayList<String> result2 = null;


	try {
	result1 = getE(AF);
	result2 = getES(AF);

	} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	}
	if(result1.size()>1 || result2.size()>1) {
	System.out.println("l'automate n'est pas déterministe car il n'a pas une seule entrée");

	}else if(deux_etats_differents_meme_lettre(AF)==true) {
	System.out.println("l'automate n'est pas déterministe car on ne peut pas aller vers deux états différents en lisant une même lettre. ");


	}else {
	verif = true;
	}

	}
	return verif;

	}

			//complétion
	public static boolean est_un_automate_complet(ArrayList<Transition> AF) {

	boolean verif = true;
	ArrayList<String> lettre = Lettre(AF);
	ArrayList<String> EtatsVerif = ListeDiffEtats(ListeEtatsTrInit(AF) , ListeEtatsTrFin(AF));
	
	for(String etat : EtatsVerif)
	{
		ArrayList<Transition> Check = new ArrayList<Transition>();
		for(Transition etatVerif : AF)
		{
			if(etatVerif.getEtatINIT().equals(etat))
			{
				Check.add(etatVerif);
			}
		}
		
		if(Check.size() != lettre.size())
		{
			verif= false;
		}
	}
	if(!verif)
	{
		System.out.println("l automate n est pas complet");
	}
	else
	{
		System.out.println("l automate est complet");
	}
	return verif;
	}

	public static ArrayList<Transition> completion(ArrayList<Transition> AF) throws IOException 
	{

	
	AF = NoDoublonTXT(AF);
	AF = toDeterministeAndC(AF,AF);
	
	return AF;

	}

	public static ArrayList<Transition> determinisation_et_completion_automate_synchrone(ArrayList<Transition> AF) throws IOException {

		ArrayList<Integer> Positions = PositionsMotVide(AF);
		ArrayList<String> EtatFinauxE = new ArrayList<String>();
		ArrayList<String> EtatInitE = new ArrayList<String>();
		ArrayList<Transition> AFDC = new ArrayList<Transition>();
		for(Transition tr : AF)
		{
			AFDC.add(tr);
		}
		for(int i:Positions)
		{
			if(!EtatFinauxE.contains(AF.get(i).getEtatFINAL()))
			{
				EtatFinauxE.add(AF.get(i).getEtatFINAL());
				EtatInitE.add(AF.get(i).getEtatINIT());
			}
		}
		for(int n=0 ; n<AFDC.size();n++)
		{
			if(AFDC.get(n).getTransition().contains("*"))
			{
				AFDC.remove(n);
				n--;
			}
		}
		for(int k=0;k< EtatFinauxE.size() ;k++)
		{
			for(Transition e : getMotLu(EtatFinauxE.get(k),EtatInitE.get(k),AF))
			{
				AFDC.add(e);
			}
		}
		AFDC = NoDoublonTXT(AFDC);
		AFDC = toDeterministeAndC(AFDC,AF);
		return AFDC;
	}



	//Minimisation
	
	public static ArrayList<Transition> Minimisation(ArrayList<Transition> AFDC)
	{
		ArrayList<String> ListeMotsLus = Lettre(AFDC);
		ArrayList<Transition> AFDCM = new ArrayList<Transition>();
		ArrayList<String> Teta = new ArrayList<String>();
		String T ="";
		String NT ="";
		//Initialisation
		for(Transition TTeta : AFDC)
		{
			if(TTeta.getEntreeOUsortie().contains("S"))
			{
				if(T.equals(""))
				{
					T = TTeta.getEtatINIT();
				}
				else
				{
					if(T.contains("/"))
					{
						boolean v = false;
						for(String a : T.split("/"))
						{
							if(a.equals(TTeta.getEtatINIT()))
							{
								v=true;
							}
						}
						if(!v)
						{
							T = T +"/"+ TTeta.getEtatINIT();
						}
					}
					else
					{
						if(!T.equals(TTeta.getEtatINIT()))
						{
							T = T +"/"+ TTeta.getEtatINIT();
						}
					}
				}
			}
			else
			{
				if(NT.equals(""))
				{
					NT = TTeta.getEtatINIT();
				}
				else
				{
					if(NT.contains("/"))
					{
						boolean v = false;
						for(String a : NT.split("/"))
						{
							if(a.equals(TTeta.getEtatINIT()))
							{
								v=true;
							}
						}
						if(!v)
						{
							NT = NT +"/"+ TTeta.getEtatINIT();
						}
					}
					else
					{
						if(!NT.equals(TTeta.getEtatINIT()))
						{
							NT = NT +"/"+ TTeta.getEtatINIT();
						}
					}
				}
			}
		}
		Teta.add(T);
		Teta.add(NT);
		boolean boucleFin = false;
		int numT = 0 ;
		System.out.println( "Minimisation de AFDC");
		//boucle algorithme teta n
		while(!boucleFin)
		{
			System.out.println(" Teta"+numT+" : " +Teta);
			ArrayList<String> TetaBis = new ArrayList<String>();
			
			for(int NumGrp=0 ; NumGrp<Teta.size() ;NumGrp++)
			{
				//Pour chaque etat d un groupe teta
				if(Teta.get(NumGrp).contains("/")) // Sil y a plus d un etat dans ce groupe
				{
					ArrayList<String> Diff = new ArrayList<String>();
					ArrayList<String> DifferentGrpReconnu = new ArrayList<String>();
					//Pour tous les etats dans un groupe ex: tous les etats dans T
					for(String etatA : Teta.get(NumGrp).split("/"))
					{
						String GrpReconnu ="";
						for(String motLu : ListeMotsLus)
						{
							for(Transition checkEtatA : AFDC)
							{
								//On recupere les numero du groupe de l etat
								//Ex : T = numero 0 et NT = numero 1
								//Classe par motlu on par ex 0:0 == T:T
								if(checkEtatA.getEtatINIT().equals(etatA) && checkEtatA.getTransition().equals(motLu))
								{
									for(int NumeroGrp=0 ; NumeroGrp < Teta.size() ; NumeroGrp++)
									{
										if(Teta.get(NumeroGrp).contains("/"))
										{
											for(String GrpSplit : Teta.get(NumeroGrp).split("/"))
											{
												if(checkEtatA.getEtatFINAL().equals(GrpSplit))
												{
													if (GrpReconnu.equals(""))
													{
														GrpReconnu = Integer.toString(NumeroGrp);
													}
													else
													{
														GrpReconnu = GrpReconnu + ":" + Integer.toString(NumeroGrp);
													}
												}
											}
										}
										else
										{
											if(checkEtatA.getEtatFINAL().equals(Teta.get(NumeroGrp)))
											{
												if (GrpReconnu.equals(""))
												{
													GrpReconnu = Integer.toString(NumeroGrp);
												}
												else
												{
													GrpReconnu = GrpReconnu + ":" + Integer.toString(NumeroGrp);
												}
											}
										}
									}
								}
							}
						}
						//ex Si cest la 1ere fois que T:NT a ete lu
						if(!DifferentGrpReconnu.contains(GrpReconnu))
						{
							DifferentGrpReconnu.add(GrpReconnu);
							Diff.add(etatA);
						}
						//On recherche dans le grp des elements lus
						else
						{
							for(int i=0 ; i < DifferentGrpReconnu.size() ; i++)
							{
								if(DifferentGrpReconnu.get(i).equals(GrpReconnu))
								{
									String nouveau = Diff.get(i)+"/"+ etatA;
									//Si l element est a la derniere position
									if ( i == DifferentGrpReconnu.size() -1)
									{
										Diff.remove(i);
										Diff.add(nouveau);
									}
									else
									{
										ArrayList<String> Precedent = new ArrayList<String>();
										for(int j = i + 1 ; j<Diff.size();j++)
										{
											Precedent.add(Diff.get(j));
											Diff.remove(j);
											j--;
										}
										Diff.remove(i);
										Diff.add(nouveau);
										for(String P : Precedent)
										{
											Diff.add(P);
										}
									}
								}
							}
						}
					}
					for(String d : Diff)
					{
						TetaBis.add(d);
					}
				}
				else
				{
					TetaBis.add(Teta.get(NumGrp));
				}
				
			}
			if(Teta.equals(TetaBis))
			{
				boucleFin = true;
			}
			else
			{
				Teta = TetaBis;
				numT++;
			}
		}
		for(String etatI : Teta)
		{
			//On doit verifier si tous les etats ont le meme etatFinal
			if(etatI.contains("/"))
			{
				String[] etat1 = etatI.split("/");
				ArrayList<Transition> MaybeDoublon = new ArrayList<Transition>();
				for(String a : etat1)
				{
					for(Transition elt:AFDC)
					{
						if(elt.getEtatINIT().equals(a))
						{
							MaybeDoublon.add(new Transition(elt.getEntreeOUsortie() ,etatI , elt.getTransition() , elt.getEtatFINAL()));
						}
					}
				}
				for(Transition noDoublon : NoDoublonTXT(MaybeDoublon))
				{
					AFDCM.add(noDoublon);
				}
				
			}
			else
			{
				for(Transition elt : AFDC)
				{
					if(elt.getEtatINIT().equals(etatI))
					{
						AFDCM.add(elt);
					}
				}
			}
		}
		return AFDCM;
	}

	//Reconnaissance de mots
	
	public static  String Lire_mot( String Mot ,ArrayList<Transition> A) throws IOException
	{
		if(Reconnaitre_mot(Mot , A))
		{
			return ("Oui");
		}
		else
		{
			return ("Non");
		}
	}
	
	public static boolean Reconnaitre_mot( String MotLu , ArrayList<Transition> A) throws IOException
	{
		boolean Rep = false;
		ArrayList<String> LesMots = Lettre(A);
		ArrayList<String> EtatE = getE(A);
		char[] caracteres = MotLu.toCharArray();
		for(int test = 0 ; test < caracteres.length ; test++ )
		{
			boolean lettre = false;
			for(int L = 0 ; L < LesMots.size() ; L++)
			{
				if(LesMots.get(L).charAt(0)==caracteres[test])
				{
					lettre = true;
				}
			}
			if(!lettre)
			{
				return Rep;
			}
		}
		//Si dans le mot tous les lettres sont presents dans l automate
		for(String es : getES(A))
		{
			if(!EtatE.contains(es)) // On rajoute dans l initialisation tous les etats ES avec les E
			{
				EtatE.add(es);
			}
		}
		String Mtn = "";
		for(Transition Step : A)
		{
			//Depart
			if(Step.getEntreeOUsortie().contains("E"))
			{
				Mtn = Step.getEtatINIT();
			}
		}
		for( int motlu = 0 ; motlu< caracteres.length ; motlu++)
		{
			String precedent = Mtn;
			boolean etatS = false;
			ArrayList<Transition> LecturePossible = new ArrayList<Transition>();
			//On va parcourir
			while(!etatS)
			{
				for(Transition MotPossible : A)
				{
					if(MotPossible.getEtatINIT().equals(Mtn))
					{
						LecturePossible.add(MotPossible);
					}
				}
				for(Transition EtatFutur : LecturePossible)
				{
					if(EtatFutur.getTransition().charAt(0) == caracteres[motlu] )//On verifie sil peut lire le caractere
					{
						Mtn = EtatFutur.getEtatFINAL();
					}
					else
					{
						Rep = false;
						etatS = true;
					}
				}
				if(motlu == caracteres.length-1)
				{
					for(Transition ABC : A)
					{
						if(ABC.getEtatINIT().equals(Mtn))
						{
							if(ABC.getEntreeOUsortie().contains("S"))
							{
								Rep = true;
								etatS = true;
							}
							else
							{
								Rep = false;
								etatS = true;
							}
						}
					}
				}
				etatS = true;
			}
		}
		return Rep;
	}

	
	//Langage complementaire
	
	public static ArrayList<Transition> automate_complementaire(ArrayList<Transition> A) throws IOException
	{
		ArrayList<Transition> Complementaire = new ArrayList<Transition>();
		ArrayList<String> ES = getES(A);
		ArrayList<String> E = getE(A);
		ArrayList<String> S = getS(A);
		for(Transition tr : A)
		{
			if(ES.contains(tr.getEtatINIT()))
			{
				Complementaire.add(new Transition("E" ,tr.getEtatINIT(),tr.getTransition(),tr.getEtatFINAL()));
			}
			else if (E.contains(tr.getEtatINIT()))
			{
				Complementaire.add(new Transition("ES",tr.getEtatINIT(),tr.getTransition(),tr.getEtatFINAL()));
			}
			else if ( S.contains(tr.getEtatINIT()))
			{
				Complementaire.add(new Transition("*",tr.getEtatINIT(),tr.getTransition(),tr.getEtatFINAL()));
			}
			else
			{
				Complementaire.add(new Transition("S",tr.getEtatINIT(),tr.getTransition(),tr.getEtatFINAL()));
			}
		}
		return Complementaire;
	}
	
	
	//Standardisation
	
	public static boolean checkSTD (ArrayList<Transition> A) throws IOException
	{
		ArrayList<String> etatsE = getE(A);
		
		for(String EtatES : getES(A))
		{
			if(!etatsE.contains(etatsE))
			{
				etatsE.add(EtatES);
			}
		}
		//Si il existe plus d'un etat inital donc non std
		if(etatsE.size()!=1)
		{
			return false;
		}
		else
		{
			for(String etatE : etatsE)
			{
				for(Transition etat : A)
				{
					//Sil y a une transition vers l etat initial donc non std ( ex boucle ou 1->etat inital)
					if(etat.getEtatFINAL().equals(etatE))
					{
						return false;
					}
				}
			}
			return true;
		}
	}
	
	public static ArrayList<Transition> automate_standard(ArrayList<Transition> A) throws IOException
	{
		ArrayList<Transition> ACompStd = new ArrayList<Transition>();
		ArrayList<Transition> etati = new ArrayList<Transition>();
		for(Transition abc : A)
		{
			if(getE(A).contains(abc.getEtatINIT()))
			{
				etati.add(new Transition("E" , "i" , abc.getTransition() , abc.getEtatFINAL()));
				ACompStd.add(new Transition("*",abc.getEtatINIT() , abc.getTransition() , abc.getEtatFINAL()));
			}
			else if(getES(A).contains(abc.getEtatINIT()))
			{
				etati.add(new Transition("ES" , "i" , abc.getTransition() , abc.getEtatFINAL()));
				ACompStd.add(new Transition("S",abc.getEtatINIT() , abc.getTransition() , abc.getEtatFINAL()));
			}
			else
			{
				ACompStd.add(abc);
			}
		}
		for(Transition infoi : etati)
		{
			ACompStd.add(infoi);
		}
		return ACompStd;
	}
}
	
