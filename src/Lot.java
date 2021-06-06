/**
 * Cette classe représente un lot.
 *
 * @author Félix Bachand
 * Code permanent : BACF03089700
 * Courriel : ab491069@ens.uqam.ca
 * Cours : INF2050-20ß
 * @version 2021-05-21
 */
public class Lot {
    public static final int NOMBRE_DE_SERVICE_DE_BASE = 2;

    private String description;
    private int nbrDroitsPassage;
    private int nbrServices;
    private int superficie;
    private String dateMesure;

    /**
     * Retourne la description du lot.
     * @return La description du lot.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retourne le nombre de droits de passage du lot.
     * @return Le nombre de droits de passage du lot.
     */
    public int getNombreDroitsPassage() {
        return nbrDroitsPassage;
    }

    /**
     * Retourne le nombre de services du lot.
     * @return Le nombre de services du lot.
     */
    public int getNombreServices() {
        return nbrServices;
    }

    /**
     * Retourne la superficie du lot.
     * @return La superficie du lot.
     */
    public int getSuperficie() {
        return superficie;
    }

    /**
     * Retourne la date de mesure du lot.
     * @return La date de mesure du lot.
     */
    public String getDateMesure() {
        return dateMesure;
    }

    /**
     * Modifie la description du terrain.
     * @param description Nouvelle description du terrain.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Modifie le nombre de droits de passage du terrain.
     * @param nombreDroitsPassage Nouveau nombre de droits de passage
     *                            du terrain.
     */
    public void setNombreDroitsPassage(int nombreDroitsPassage) {
        this.nbrDroitsPassage = nombreDroitsPassage;
    }

    /**
     * Modifie le nombre de services du terrain.
     * @param nombreServices Nouveau nombre de services du terrain.
     */
    public void setNombreServices(int nombreServices) {
        this.nbrServices = nombreServices;
    }

    /**
     * Modifie la superficie du terrain.
     * @param superficie Nouvelle superficie du terrain.
     */
    public void setSuperficie(int superficie) {
        this.superficie = superficie;
    }

    /**
     * Modifie la date de mesure du terrain.
     * @param dateMesure Nouvelle date de mesure du terrain.
     */
    public void setDateMesure(String dateMesure) {
        this.dateMesure = dateMesure;
    }
}