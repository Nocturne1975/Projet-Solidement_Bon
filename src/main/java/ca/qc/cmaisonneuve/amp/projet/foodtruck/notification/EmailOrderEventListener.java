package ca.qc.cmaisonneuve.amp.projet.foodtruck.notification;

/**
 * Observer concret: simule l'envoi d'un courriel.
 */
public class EmailOrderEventListener implements OrderEventListener {

    private static final String LINE_SEPARATOR = System.lineSeparator();

    @Override
    public void onOrderEvent(OrderEvent event) {
        if (event == null) {
            return;
        }

        String subject;
        String body;
        if (event.getType() == OrderEventType.PLACED) {
            subject = "SOLIDEMENT Bon - Confirmation de commande";
            body = "Votre commande de %s est en preparation.".formatted(event.getItemType());
        } else {
            subject = "SOLIDEMENT Bon - Commande prete";
            body = "Votre commande de %s est prete!".formatted(event.getItemType());
        }

        envoyerCourriel(event.getRecipient(), subject, body, event.getItemType(), event.getAmount());
    }

    // Formattage et envoi d'un courriel de confirmation au client
    private void envoyerCourriel(String destinataire,
            String sujet,
            String corpsTexte,
            String typeCommande,
            double montant) {
        String messageId = "MSG-" + System.currentTimeMillis();

        String separateur = "--------------------------------------------------";
        StringBuilder sb = new StringBuilder();
        sb.append(LINE_SEPARATOR).append(separateur).append(LINE_SEPARATOR);
        sb.append("SOLIDement Bon - Notification de commande").append(LINE_SEPARATOR);
        sb.append("Message-ID : ").append(messageId).append(LINE_SEPARATOR);
        sb.append("A          : ").append(destinataire != null ? destinataire : "(inconnu)").append(LINE_SEPARATOR);
        sb.append("Sujet      : ").append(sujet != null ? sujet : "(sans sujet)").append(LINE_SEPARATOR);
        sb.append(separateur).append(LINE_SEPARATOR);
        sb.append("Resume de la commande").append(LINE_SEPARATOR);
        sb.append(" - Type : ").append(typeCommande != null ? typeCommande : "(inconnu)").append(LINE_SEPARATOR);
        sb.append(" - Montant : ").append(String.format(java.util.Locale.CANADA_FRENCH, "%.2f $", montant))
                .append(LINE_SEPARATOR);
        sb.append(separateur).append(LINE_SEPARATOR);
        sb.append("Message :").append(LINE_SEPARATOR);
        sb.append(corpsTexte != null ? corpsTexte : "(aucun contenu)").append(LINE_SEPARATOR);
        sb.append(separateur).append(LINE_SEPARATOR);
        sb.append("Courriel envoye. ").append(LINE_SEPARATOR);

        System.out.println(sb.toString());
        System.out.println("[Journal] Courriel envoye a " + destinataire + " avec Message-ID " + messageId + ".");
    }
}
