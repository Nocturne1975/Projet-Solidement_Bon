package ca.qc.cmaisonneuve.amp.projet.foodtruck.paiement;

/**
 * Abstraction de traitement de paiement.
 *
 * Permet au gestionnaire (FoodTruckManager) de dépendre d'une interface (DIP)
 * plutôt que d'une implémentation concrète.
 */
public interface PaymentProcessor {

    /**
     * Effectue le paiement.
     *
     * @return true si le paiement est accepté, false sinon.
     */
    boolean payer(String modePaiement, double montant);
}
