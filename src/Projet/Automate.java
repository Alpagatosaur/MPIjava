package Projet;

public class Automate {
	private String entreeOUsortie;
	private String etatINIT;
	private String transition;
	private String etatFINAL;

	public Automate (String initORfin, String initial, String fleche, String terminal)
	{
		entreeOUsortie = initORfin;
		etatINIT = initial;
		transition = fleche;
		etatFINAL = terminal;
	}

	public String getEntreeOUsortie() {
		return entreeOUsortie;
	}

	public void setEntreeOUsortie(String entreeOUsortie) {
		this.entreeOUsortie = entreeOUsortie;
	}

	public String getEtatINIT() {
		return etatINIT;
	}

	public void setEtatINIT(String etatINIT) {
		this.etatINIT = etatINIT;
	}

	public String getTransition() {
		return transition;
	}

	public void setTransition(String transition) {
		this.transition = transition;
	}

	public String getEtatFINAL() {
		return etatFINAL;
	}

	public void setEtatFINAL(String etatFINAL) {
		this.etatFINAL = etatFINAL;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entreeOUsortie == null) ? 0 : entreeOUsortie.hashCode());
		result = prime * result + ((etatFINAL == null) ? 0 : etatFINAL.hashCode());
		result = prime * result + ((etatINIT == null) ? 0 : etatINIT.hashCode());
		result = prime * result + ((transition == null) ? 0 : transition.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Automate other = (Automate) obj;
		if (entreeOUsortie == null) {
			if (other.entreeOUsortie != null)
				return false;
		} else if (!entreeOUsortie.equals(other.entreeOUsortie))
			return false;
		if (etatFINAL == null) {
			if (other.etatFINAL != null)
				return false;
		} else if (!etatFINAL.equals(other.etatFINAL))
			return false;
		if (etatINIT == null) {
			if (other.etatINIT != null)
				return false;
		} else if (!etatINIT.equals(other.etatINIT))
			return false;
		if (transition == null) {
			if (other.transition != null)
				return false;
		} else if (!transition.equals(other.transition))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return entreeOUsortie + " " + etatINIT+ " "  + transition+ " " + etatFINAL + "|" ;
	}
	
	
}
