package ca.qc.cmaisonneuve.amp.projet.foodtruck.persistence;

/**
 * Classe représentant un objet qui s'occupe de communiquer avec une base de
 * données SQL pour faire la sauvegarde des commandes
 */
public class SqlDatabasePersistence implements OrderPersistence {

    @Override
    public void sauvegarderCommande(String email,
            String type,
            double prix,
            boolean extraFromage,
            boolean epice) {
        System.out.println("Commande sauvegardee pour " + email + " : " + type + " -> " + prix);
    }
}
