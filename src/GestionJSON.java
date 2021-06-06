import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

import net.sf.json.JSONObject;
import net.sf.json.JSONArray;
import org.apache.commons.io.FileUtils;

/**
 * Classe utilitaire pour la gestion du JSON.
 *
 * @author Félix Bachand
 * Code permanent : BACF03089700
 * Courriel : ab491069@ens.uqam.ca
 * Cours : INF2050-20
 * @version 2021-05-21
 */
public class GestionJSON {
    private static final String CHARSET_FICHIER = "UTF-8";
    private static final String ID_TYPE_TERRAIN = "type_terrain";
    private static final String ID_PRIX_MIN = "prix_m2_min";
    private static final String ID_PRIX_MAX = "prix_m2_max";
    private static final String ID_LOTS = "lotissements";
    private static final String ID_DESCRIPTION = "description";
    private static final String ID_NB_DROITS_PASSAGE = "nombre_droits_passage";
    private static final String ID_NB_SERVICES = "nombre_services";
    private static final String ID_SUPERFICIE = "superficie";
    private static final String ID_DATE_MESURE = "date_mesure";
    private static final String ID_VALEUR_LOT = "valeur_par_lot";
    private static final String ID_VALEUR_TOTAL = "valeur_fonciere_totale";
    private static final String ID_TAXE_SCOLAIRE = "taxe_scolaire";
    private static final String ID_TAXE_MUNICIPALE = "taxe_municipale";

    private static final String FORMAT_ARGENT = "%.02f";
    private static final String SIGNE_DOLLAR = " $";

    private static double obtenirPrix(String prix) throws Throwable {
        double prixFinal;

        if (prix != null && !prix.isEmpty()) {
            prixFinal = Double.parseDouble(
                            prix.substring(0, prix.indexOf(SIGNE_DOLLAR)));
        }
        else {
            throw new Throwable();
        }
        return prixFinal;
    }

    /**
     * Retourne les lots du terrain.
     * @param lotsJSON Lots en format JSON.
     * @return Les lots du terrain. S'il y a un problème
     *         (ex: contenu), on retourne un tableau null.
     */
    private static Lot[] extraireLotsJSON(JSONArray lotsJSON) {
        Lot[] lots = null;
        JSONObject objetJSON;

        if (lotsJSON != null && lotsJSON.size() > 0) {
            lots = new Lot[lotsJSON.size()];
            for (int index = 0; index < lotsJSON.size(); index++) {
                objetJSON = lotsJSON.getJSONObject(index);
                if (objetJSON != null) {
                    lots[index] = new Lot();
                    try {
                        lots[index].setDescription(
                                objetJSON.getString(ID_DESCRIPTION));
                        lots[index].setNombreDroitsPassage(
                                objetJSON.getInt(ID_NB_DROITS_PASSAGE));
                        lots[index].setNombreServices(
                                objetJSON.getInt(ID_NB_SERVICES));
                        lots[index].setSuperficie(
                                objetJSON.getInt(ID_SUPERFICIE));
                        lots[index].setDateMesure(
                                objetJSON.getString(ID_DATE_MESURE));
                    }
                    catch (Throwable exception) {
                        lots[index] = null;
                    }
                }
            }
        }
        return lots;
    }

    /**
     * Retourne si le fichier passé en argument au programme est valide.
     * @param args Liste des arguments passés en paramètres
     * @return Vrai si le fichier d'entré et de sortie est valide,
     *         sinon false.
     */
    public static boolean verifierArgumentsProgramme(String[] args) {
        return args != null &&
                args.length == 2 &&
                args[0] != null &&
                args[1] != null &&
                !args[0].isEmpty() &&
                !args[1].isEmpty() &&
                Files.exists(Path.of(args[0]));
    }

    /**
     * Retourne le terrain non evalué qui est dans le fichier JSON.
     * @param nomFichier Nom du fichier JSON.
     * @return Un terrain non evalué. S'il y a une erreur avec
     *         le fichier (emplacement, contenu ou autre), on
     *         retourne un terrain null.
     */
    public static Terrain extraireTerrainJSON(String nomFichier) {
        Terrain terrain = new Terrain();
        Lot[] lots = null;
        String contenuJSON = null;
        JSONObject terrainJSON;
        JSONArray lotsJSON = null;

        try {
            contenuJSON = Files.readString(Path.of(nomFichier),
                    Charset.forName(CHARSET_FICHIER));
        }
        catch (IOException exception) {
            terrain = null;
        }

        if (contenuJSON != null && !contenuJSON.isEmpty()) {
            try {
                terrainJSON = JSONObject.fromObject(contenuJSON);
                if (terrainJSON != null) {
                    terrain.setTypeTerrain(
                            terrainJSON.getInt(ID_TYPE_TERRAIN));
                    terrain.setPrixMin(
                            obtenirPrix(terrainJSON.getString(ID_PRIX_MIN)));
                    terrain.setPrixMax(
                            obtenirPrix(terrainJSON.getString(ID_PRIX_MAX)));

                    terrain.setLots(
                            extraireLotsJSON(
                                    terrainJSON.getJSONArray(ID_LOTS)));
                }
            }
            catch (Throwable exception) {
                terrain = null;
            }
        }
        return terrain;
    }

    /**
     * Permet d'écrire le JSON dans un fichier.
     * @param filePath Le nom du fichier.
     * @param contentToSave Le JSON.
     * @return True si le fichier a été créé sinon false.
     */
    private static boolean sauvegarderJSON(String filePath,
                                           String contentToSave) {
        boolean operationReussi;
        File fichier;

        try {
            fichier = new File(filePath);
            FileUtils.writeStringToFile(fichier, contentToSave, "UTF-8");
            operationReussi = true;
        }
        catch (Exception exception) {
            operationReussi = false;
        }
        return operationReussi;
    }
    
    /**
     * Permet d'arrondir la valeur au 0.05 plafond.
     * Inspiré par un post sur StackOverflow:
     * https://stackoverflow.com/questions/9256005/java-rounding-to-nearest-0-05
     * @param prix La valeur à arrondir.
     * @return Variable du type double contenant la valeur arrondie.
     */
    private static double arrondi(double prix) {
        return (Math.ceil(prix * 20.0)) / 20.0 ;
    }
     
    /**
     * Pour l'écriture des données calculées dans un fichier de sortie.
     * La valeur foncière,la taxe municipale et scolaire sont formatées
     * pour une représentation de deux chiffres après la virgule. Ces
     * valeurs sont ensuite ajoutées dans la methode @accumulate sous
     * forme de (clef, valeur), qui les convertiront en format JSON.
     * Les valeurs des lots sont recueillis et accumulés selon les étapes
     * précécentes. La méthode pour l'écriture fichier est applée sur l'object
     * contenant les données accumulées et écrit le fichier de sortie.
     * @param nomFichier Nom du fichier de sortie.
     * @param terrainEvalue Le terrain evalue.
     * @return True si le fichier JSON a été créer sinon false.
     */
    public static boolean ecrireFichier(String nomFichier,
                                         TerrainEvalue terrainEvalue) {
        JSONObject detailsPrix = new JSONObject();
        JSONArray lotissement = new JSONArray();
        JSONObject lots = new JSONObject();
        String strValeursLots;
        double valeurFonciere = arrondi(terrainEvalue
                                            .getValeurFonciereTotale());
        double taxeMunicipale = arrondi(terrainEvalue.getTaxeMunicipale());
        double taxeScolaire = arrondi(terrainEvalue.getTaxeScolaire());

        String strValeurFonciere =String.format(Locale.ENGLISH,
                                                FORMAT_ARGENT,
                                                valeurFonciere)
                                    .concat(SIGNE_DOLLAR);
        String strTaxeMunicipale =String.format(Locale.ENGLISH,
                                                FORMAT_ARGENT,
                                                taxeMunicipale)
                                    .concat(SIGNE_DOLLAR);
        String strTaxeScolaire =String.format(Locale.ENGLISH,
                                                FORMAT_ARGENT,
                                                taxeScolaire)
                                    .concat(SIGNE_DOLLAR);
        
        detailsPrix.accumulate(ID_VALEUR_TOTAL,strValeurFonciere);
        detailsPrix.accumulate(ID_TAXE_SCOLAIRE,strTaxeScolaire);
        detailsPrix.accumulate(ID_TAXE_MUNICIPALE,strTaxeMunicipale);

        for(int i = 0; i < terrainEvalue.getLotsEvalue().size(); i++) {
             
            strValeursLots =String.format(Locale.ENGLISH,
                                            FORMAT_ARGENT,
                                            terrainEvalue
                                                    .getLotsEvalue()
                                                    .get(i)
                                                    .getValeur());
            lots.accumulate(ID_DESCRIPTION,
                                terrainEvalue
                                .getLotsEvalue()
                                .get(i)
                                .getDescription());
            lots.accumulate(ID_VALEUR_LOT,
                    strValeursLots.concat(SIGNE_DOLLAR));
            lotissement.add(lots);
            lots.clear();
        }
        detailsPrix.accumulate(ID_LOTS, lotissement);
        return sauvegarderJSON(nomFichier, detailsPrix.toString());
    }
}