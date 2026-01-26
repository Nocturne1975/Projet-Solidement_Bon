package ca.qc.cmaisonneuve.amp.projet.foodtruck.notification;

/**
 * Observer: un listener réagit aux événements d'une commande.
 */
public interface OrderEventListener {

    void onOrderEvent(OrderEvent event);
}
