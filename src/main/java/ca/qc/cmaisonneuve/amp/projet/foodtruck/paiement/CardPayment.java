package ca.qc.cmaisonneuve.amp.projet.foodtruck.paiement;

/**
 * Strategy concrète: paiement par carte.
 */
public class CardPayment implements PaymentMethod {

    @Override
    public void pay(double amount) {
        System.out.println("Carte facturee: " + amount);
    }
}
