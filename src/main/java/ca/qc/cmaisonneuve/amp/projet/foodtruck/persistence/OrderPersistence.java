package ca.qc.cmaisonneuve.amp.projet.foodtruck.persistence;

/**
 * Abstraction de persistance des commandes.
 *
 * Permet au gestionnaire (FoodTruckManager) de dépendre d'une interface (DIP)
 * plutôt que d'une classe SQL concrète.
 */
public interface OrderPersistence {

    void sauvegarderCommande(String destinataire,
            String type,
            double prix,
            boolean extraFromage,
            boolean epice);
}
