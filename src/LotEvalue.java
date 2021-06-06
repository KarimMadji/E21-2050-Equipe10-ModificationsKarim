/**
 * Cette classe represente un lot qui a ete evalue
 *
 * @author Félix Bachand
 * Code permanent : BACF03089700
 * Courriel : ab491069@ens.uqam.ca
 * Cours : INF2050-20
 * @version 2021-06-11
 */
public class LotEvalue {
    private String description;
    private double valeur;

    /**
     * Retourne la description du lot.
     * @return La description du lot.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retourne la valeur du lot.
     * @return La valeur du lot.
     */
    public double getValeur() {
        return valeur;
    }

    /**
     * Modifie la description du lot.
     * @param description Nouvelle description du lot.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Modifie la valeur du lot.
     * @param valeur Nouvelle valeur du lot.
     */
    public void setValeur(double valeur) {
        this.valeur = valeur;
    }
}
