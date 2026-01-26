package ca.qc.cmaisonneuve.amp.projet.foodtruck.paiement;

/**
 * Strategy (patron de conception): encapsule un algorithme de paiement.
 *
 * Ajouter un nouveau mode de paiement devient un ajout de classe (OCP),
 * sans modifier le code qui orchestre la commande.
 */
public interface PaymentMethod {

    void pay(double amount);
}
