package ca.qc.cmaisonneuve.amp.projet.foodtruck.notification;

/**
 * Observer concret: simule l'envoi d'un SMS.
 */
public class SmsOrderEventListener implements OrderEventListener {

    @Override
    public void onOrderEvent(OrderEvent event) {
        if (event == null) {
            return;
        }

        String message;
        if (event.getType() == OrderEventType.PLACED) {
            message = "SOLIDEMENT Bon: commande %s recue. En preparation.".formatted(event.getItemType());
        } else {
            message = "SOLIDEMENT Bon: commande %s prete!".formatted(event.getItemType());
        }

        System.out.println("[SMS] A " + (event.getRecipient() != null ? event.getRecipient() : "(inconnu)") + ": " + message);
    }
}
