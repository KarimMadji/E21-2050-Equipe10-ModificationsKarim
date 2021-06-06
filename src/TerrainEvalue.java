import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe represente un terrain qui a ete evalue
 *
 * @author Félix Bachand
 * Code permanent : BACF03089700
 * Courriel : ab491069@ens.uqam.ca
 * Cours : INF2050-20
 * @version 2021-06-11
 */
public class TerrainEvalue {
    private double valeurFonciereTotale;
    private double taxeScolaire;
    private double taxeMunicipale;
    private ArrayList<LotEvalue> lotsEvalue;

    /**
     * Retourne la valeur fonciere du terrain.
     * @return La valeur fonciere du terrain.
     */
    public double getValeurFonciereTotale() {
        return valeurFonciereTotale;
    }

    /**
     * Retourne la taxe scolaire du terrain.
     * @return La taxe scolaire du terrain.
     */
    public double getTaxeScolaire() {
        return taxeScolaire;
    }

    /**
     * Retourne la taxe municipale du terrain.
     * @return La taxe municipale du terrain.
     */
    public double getTaxeMunicipale() {
        return taxeMunicipale;
    }

    /**
     * Retourne la liste des lots du terrain.
     * @return La liste des lots du terrain.
     */
    public List<LotEvalue> getLotsEvalue() {
        return lotsEvalue;
    }

    /**
     * Modifie la valeur fonciere du terrain.
     * @param valeurFonciereTotale Nouvelle valeur fonciere du terrain.
     */
    public void setValeurFonciereTotale(double valeurFonciereTotale) {
        this.valeurFonciereTotale = valeurFonciereTotale;
    }

    /**
     * Modifie la taxe scolaire du terrain.
     * @param taxeScolaire Nouvelle taxe scolaire du terrain.
     */
    public void setTaxeScolaire(double taxeScolaire) {
        this.taxeScolaire = taxeScolaire;
    }

    /**
     * Modifie la taxe municipale du terrain.
     * @param taxeMunicipale Nouvelle taxe municipale du terrain.
     */
    public void setTaxeMunicipale(double taxeMunicipale) {
        this.taxeMunicipale = taxeMunicipale;
    }

    /**
     * Modifie la liste des lots du terrain.
     * @param lotsEvalue Nouvelle liste des lots du terrain.
     */
    public void setLotsEvalue(ArrayList<LotEvalue> lotsEvalue) {
        this.lotsEvalue = lotsEvalue;
    }
}
