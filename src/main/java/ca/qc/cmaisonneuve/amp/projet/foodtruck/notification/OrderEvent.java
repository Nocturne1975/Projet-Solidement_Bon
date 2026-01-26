package ca.qc.cmaisonneuve.amp.projet.foodtruck.notification;

/**
 * Événement de domaine minimal pour notifier un client.
 */
public class OrderEvent {

    private final OrderEventType type;
    private final String recipient;
    private final String itemType;
    private final double amount;

    public OrderEvent(OrderEventType type, String recipient, String itemType, double amount) {
        this.type = type;
        this.recipient = recipient;
        this.itemType = itemType;
        this.amount = amount;
    }

    public OrderEventType getType() {
        return type;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getItemType() {
        return itemType;
    }

    public double getAmount() {
        return amount;
    }
}
