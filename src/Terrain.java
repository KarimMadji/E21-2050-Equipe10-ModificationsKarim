/**
 * Cette classe represente un terrain non evaluee.
 *
 * @author Félix Bachand
 * Code permanent : BACF03089700
 * Courriel : ab491069@ens.uqam.ca
 * Cours : INF2050-20
 * @version 2021-05-21
 */
public class Terrain {
    public static final int TYPE_AGRICOLE = 0;
    public static final int TYPE_RESIDENTIEL = 1;
    public static final int TYPE_COMMERCIAL = 2;

    private int typeTerrain;
    private double prixMin;
    private double prixMax;
    private Lot[] lots;

    /**
     * Retourne le type du terrain:
     *  0 = Agricole
     *  1 = Residentiel
     *  2 = Commercial
     * @return Le type du terrain.
     */
    public int getTypeTerrain() {
        return typeTerrain;
    }

    /**
     * Retourne le prix minimum du terrain.
     * @return Le prix minimum du terrain.
     */
    public double getPrixMin() {
        return prixMin;
    }

    /**
     * Retourne le prix maximum du terrain.
     * @return Le prix maximum du terrain.
     */
    public double getPrixMax() {
        return prixMax;
    }

    /**
     * Retourne les lots du terrain.
     * @return Les lots du terrain.
     */
    public Lot[] getLots() {
        return lots;
    }

    /**
     * Modifie le type du terrain.
     *  0 = Agricole
     *  1 = Residentiel
     *  2 = Commercial
     * @param typeTerrain Nouveau type du terrain.
     */
    public void setTypeTerrain(int typeTerrain) {
        this.typeTerrain = typeTerrain;
    }

    /**
     * Modifie le prix minimum du terrain.
     * @param prixMin Nouveau prix minimum du terrain.
     */
    public void setPrixMin(double prixMin) {
        this.prixMin = prixMin;
    }

    /**
     * Modifie le prix maximum du terrain.
     * @param prixMax Nouveau prix maximum du terrain.
     */
    public void setPrixMax(double prixMax) {
        this.prixMax = prixMax;
    }

    /**
     * Modifie les lots du terrain.
     * @param lots Nouveau lots du terrain.
     */
    public void setLots(Lot[] lots) {
        this.lots = lots;
    }
}
