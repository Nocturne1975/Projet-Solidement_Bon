
package ca.qc.cmaisonneuve.amp.projet.foodtruck.paiement;

import java.util.HashMap;
import java.util.Map;

/**
 * Passerelle de paiement, qui permet de payer en utilisant divers modes de paiement.
 */
public class PaymentGateway implements PaymentProcessor {

    private final Map<String, PaymentMethod> methods = new HashMap<>();

    public PaymentGateway() {
        // Registre par défaut. Ajouter un mode de paiement = ajouter une nouvelle classe Strategy
        // et l'enregistrer ici (ou via register()).
        register("CARTE", new CardPayment());
        register("COMPTANT", new CashPayment());
        register("VIREMENT", new BankTransferPayment());
    }

    public final void register(String code, PaymentMethod method) {
        if (code == null || method == null) {
            return;
        }
        methods.put(code.trim().toUpperCase(), method);
    }

    @Override
    public boolean payer(String modePaiement, double montant) {
        String key = modePaiement == null ? "" : modePaiement.trim().toUpperCase();
        PaymentMethod method = methods.get(key);
        if (method == null) {
            echec("Mode de paiement non pris en charge: " + modePaiement);
            return false;
        }

        method.pay(montant);
        return true;
    }

    public void payerCarte(double montant) {
        payer("CARTE", montant);
    }

    public void payerComptant(double montant) {
        payer("COMPTANT", montant);
    }

    public void echec(String raison) {
        System.out.println("Le paiement a echoue: " + raison);
    }
}
