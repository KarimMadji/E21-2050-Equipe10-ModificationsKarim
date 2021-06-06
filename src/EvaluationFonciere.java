import java.util.ArrayList;

/**
 * Cette classe représente le gestionnaire de
 * l'évaluation foncière d'un terrain.
 *
 * @author Félix Bachand
 *         Karim Madji
 *         TODO:Rajouter votre Nom
 * Code permanent : BACF03089700
 *                  MADK27059904
 *                  TODO:Rajouter votre Code permanent
 * Courriel : ab491069@ens.uqam.ca
 *            ad291907@ens.uqam.ca
 *            TODO:Rajouter votre courriel
 * Cours : INF2050-20
 * @version 2021-05-21
 */
public class EvaluationFonciere {
    private static final String MSG_ARGS_INVALIDE = "Les arguments passes" +
            "en parametres sont invalides";
    private static final String MSG_FICHIER_PROBLEME = "Il y a eu un" +
            "probleme à la creation du fichier JSON.";

    // Terrain
    public static final double TERRAIN_VALEUR_BASE = 733.77;
    public static final double TERRAIN_PCT_TAXE_SCOLAIRE = 0.012;
    public static final double TERRAIN_PCT_TAXE_MUNICIPALE = 0.025;

    // Droit de passage
    public static final int DROIT_DE_PASSAGE_MONTANT_DE_BASE = 500;
    public static final double DROIT_DE_PASSAGE_PCT_AGRICOLE = 0.05;
    public static final double DROIT_DE_PASSAGE_PCT_RESIDENTIEL = 0.10;
    public static final double DROIT_DE_PASSAGE_PCT_COMMERCIAL = 0.15;

    // Service Agricole
    public static final int SERVICE_MONTANT_AGRICOLE = 0;

    // Service Residentiel
    public static final int SERVICE_LIMIT_1_RESIDENTIEL = 500;
    public static final int SERVICE_MONTANT_LIMIT_1_RESIDENTIEL = 0;
    public static final int SERVICE_LIMIT_2_RESIDENTIEL = 10000;
    public static final int SERVICE_MONTANT_LIMIT_2_RESIDENTIEL = 500;
    public static final int SERVICE_MONTANT_LIMIT_3_RESIDENTIEL = 1000;

    // Service Commercial
    public static final int SERVICE_LIMIT_1_COMMERCIAL = 500;
    public static final int SERVICE_MONTANT_LIMIT_1_COMMERCIAL = 500;
    public static final int SERVICE_MONTANT_LIMIT_2_COMMERCIAL = 1500;
    public static final int SERVICE_MONTANT_MAX_COMMERCIAL = 5000;

    /**
     * Évalue la valeur du terrain et de ses lots.
     * @param terrain Terrain obtenu a partir du fichier JSON.
     * @return Le terrain evalué, prêt pour le fichier de sortie.
     */
    private static TerrainEvalue evaluerValeurFonciere(Terrain terrain) {
        TerrainEvalue terrainEvalue = new TerrainEvalue();
        ArrayList<LotEvalue> lotEvalue = new ArrayList<>();
        Lot lotCourant;
        double valeurFonciere = 0;

        // Lotissements
        if (terrain.getLots() != null) {
            for (int i = 0; i < terrain.getLots().length; i++) {
                lotCourant = terrain.getLots()[i];
                if (lotCourant != null) {
                    lotEvalue.add(new LotEvalue());
                    lotEvalue.get(i)
                            .setDescription(lotCourant.getDescription());
                    lotEvalue.get(i).setValeur(calculerValeurLot(terrain, i));
                    valeurFonciere += lotEvalue.get(i).getValeur();
                }
            }
        }
        // Valeur de base
        valeurFonciere += TERRAIN_VALEUR_BASE;

        // Taxe scolaire
        terrainEvalue.setTaxeScolaire(calculTaxeScolaire(valeurFonciere));

        // Taxe municipale
        terrainEvalue.setTaxeMunicipale(calculTaxeMunicipale(valeurFonciere));

        terrainEvalue.setLotsEvalue(lotEvalue);

        terrainEvalue.setValeurFonciereTotale(valeurFonciere +
                                            terrainEvalue.getTaxeScolaire() +
                                            terrainEvalue.getTaxeMunicipale());
        return terrainEvalue;
    }

    /**
     * Évalue la valeur du lot d'un terrain.
     * @param terrain Terrain obtenu à partir du fichier JSON.
     * @param indexLot Index du lot à evaluer.
     * @return La valeur du lot.
     */
    private static double calculerValeurLot(Terrain terrain, int indexLot) {
        double valeurLot;
        double valeurTotal = 0;
        int nbPassage;
        int nbServices;
        Lot lotCourant;

        if (terrain != null && terrain.getLots() != null) {
            try {
                lotCourant = terrain.getLots()[indexLot];
                valeurLot = calculerValeurSuperficie(terrain.getTypeTerrain(),
                                                    lotCourant.getSuperficie(),
                                                    terrain.getPrixMin(),
                                                    terrain.getPrixMax());
                nbPassage = lotCourant.getNombreDroitsPassage();
                nbServices = lotCourant.getNombreServices();
                valeurTotal = valeurLot +
                        calculerValeurDroitPassages(terrain.getTypeTerrain(),
                                                    valeurLot,
                                                    nbPassage) +
                        calculerValeurServices(terrain.getTypeTerrain(),
                                                lotCourant.getSuperficie(),
                                                nbServices);
            }
            catch (IndexOutOfBoundsException exception) {
                valeurTotal = 0;
            }
        }
        return valeurTotal;
    }

    /**
     * Calcul la valeur foncière du lot selon le type de terrain,
     * la superficie, le prix minimum et le prix maximum.
     * @param typeTerrain Type du terrain.
     * @param superficie La superficie en m^2.
     * @param prixMin Prix minimum du m^2.
     * @param prixMax Prix maximum du m^2.
     * @return La valeur foncière du lot.
     */
    private static double calculerValeurSuperficie(int typeTerrain,
                                                   int superficie,
                                                   double prixMin,
                                                   double prixMax) {
        double valeur = 0;

        switch (typeTerrain) {
            case Terrain.AGRICOLE:
                valeur = superficie * prixMin;
                break;
            case Terrain.RESIDENTIEL:
                valeur = superficie * ((prixMin + prixMax) / 2);
                break;
            case Terrain.COMMERCIAL:
                valeur = superficie * prixMax;
                break;
        }
        return valeur;
    }

    /**
     * Calcul la valeur des droits de passage du lot
     * selon le type de terrain, la valeur du lot et le nombre de passages.
     * @param typeTerrain Type du terrain.
     * @param valeurLot La valeur foncière du lot.
     * @param nombrePassage Nombre de passages pour le lot.
     * @return La valeur des droits de passages du lot.
     */
    private static double calculerValeurDroitPassages(int typeTerrain,
                                                      double valeurLot,
                                                      int nombrePassage) {
        double valeur = 0;

        switch (typeTerrain) {
            case Terrain.AGRICOLE:
                valeur = DROIT_DE_PASSAGE_MONTANT_DE_BASE -
                        nombrePassage *
                                (DROIT_DE_PASSAGE_PCT_AGRICOLE * valeurLot);
                break;
            case Terrain.RESIDENTIEL:
                valeur = DROIT_DE_PASSAGE_MONTANT_DE_BASE -
                        nombrePassage *
                                (DROIT_DE_PASSAGE_PCT_RESIDENTIEL * valeurLot);
                break;
            case Terrain.COMMERCIAL:
                valeur = DROIT_DE_PASSAGE_MONTANT_DE_BASE -
                        nombrePassage *
                                (DROIT_DE_PASSAGE_PCT_COMMERCIAL * valeurLot);
                break;
        }
        return valeur;
    }

    /**
     * Calcul la valeur des services du lot selon le
     * type de terrain, la superficie du lot et le nombre de services.
     * @param typeTerrain Type du terrain.
     * @param superficie La superficie du lot en m^2.
     * @param nombreService Nombre de services pour le lot.
     * @return La valeur des services du lot.
     */
    private static double calculerValeurServices(int typeTerrain,
                                                 int superficie,
                                                 int nombreService) {
        double valeur = 0;

        switch (typeTerrain) {
            case Terrain.AGRICOLE:
                valeur = SERVICE_MONTANT_AGRICOLE;
                break;
            case Terrain.RESIDENTIEL:
                if (superficie <= SERVICE_LIMIT_1_RESIDENTIEL) {
                    valeur = SERVICE_MONTANT_LIMIT_1_RESIDENTIEL;
                }
                else if (superficie <= SERVICE_LIMIT_2_RESIDENTIEL) {
                    valeur = SERVICE_MONTANT_LIMIT_2_RESIDENTIEL
                                * nombreService;
                }
                else {
                    valeur = SERVICE_MONTANT_LIMIT_3_RESIDENTIEL
                                * nombreService;
                }
                break;
            case Terrain.COMMERCIAL:
                if (superficie <= SERVICE_LIMIT_1_COMMERCIAL) {
                    valeur = SERVICE_MONTANT_LIMIT_1_COMMERCIAL
                                * nombreService;
                }
                else {
                    valeur = SERVICE_MONTANT_LIMIT_2_COMMERCIAL
                                * nombreService;
                }
                if (valeur > SERVICE_MONTANT_MAX_COMMERCIAL) {
                    valeur = SERVICE_MONTANT_MAX_COMMERCIAL;
                }
                break;
        }
        return valeur;
    }

    /**
     * Calcul la taxe scolaire selon la valeur foncière.
     * @param valeurFonciere Valeur foncière total du terrain (avant taxes)
     * @return Le montant de la taxe scolaire.
     */
    private static double calculTaxeScolaire(double valeurFonciere) {
        return valeurFonciere * TERRAIN_PCT_TAXE_SCOLAIRE;
    }

    /**
     * Calcul la taxe municipale selon la valeur foncière.
     * @param valeurFonciere Valeur foncière total du terrain (avant taxes)
     * @return Le montant de la taxe municipale.
     */
    private static double calculTaxeMunicipale(double valeurFonciere) {
        return valeurFonciere * TERRAIN_PCT_TAXE_MUNICIPALE;
    }

    public static void main(String[] args) {
        if (GestionJSON.verifierArgumentsProgramme(args)) {
            Terrain terrain = GestionJSON.extraireTerrainJSON(args[0]);
            TerrainEvalue terrainEvalue = evaluerValeurFonciere(terrain);
            if (!GestionJSON.ecrireFichier(args[1], terrainEvalue)) {
                System.out.println(MSG_FICHIER_PROBLEME);
            }
        }
        else {
            System.out.println(MSG_ARGS_INVALIDE);
        }
    }
}