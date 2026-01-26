package ca.qc.cmaisonneuve.amp.projet.foodtruck.notification;

import java.util.ArrayList;
import java.util.List;

/**
 * Subject (Observer pattern): publie des événements à 0..n observers.
 */
public class OrderEventPublisher {

    private final List<OrderEventListener> listeners = new ArrayList<>();

    public void addListener(OrderEventListener listener) {
        if (listener == null) {
            return;
        }
        listeners.add(listener);
    }

    public void removeListener(OrderEventListener listener) {
        listeners.remove(listener);
    }

    public void publish(OrderEvent event) {
        for (OrderEventListener listener : listeners) {
            listener.onOrderEvent(event);
        }
    }
}
