package ca.qc.cmaisonneuve.amp.projet.foodtruck.paiement;

/**
 * Strategy concrète: paiement comptant.
 */
public class CashPayment implements PaymentMethod {

    @Override
    public void pay(double amount) {
        System.out.println("Argent comptant recu: " + amount);
    }
}
