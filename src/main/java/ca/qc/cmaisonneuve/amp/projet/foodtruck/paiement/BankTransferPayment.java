package ca.qc.cmaisonneuve.amp.projet.foodtruck.paiement;

/**
 * Strategy concrète: paiement par virement bancaire.
 */
public class BankTransferPayment implements PaymentMethod {

    @Override
    public void pay(double amount) {
        System.out.println("Virement bancaire recu: " + amount);
    }
}
