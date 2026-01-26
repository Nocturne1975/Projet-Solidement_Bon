
package ca.qc.cmaisonneuve.amp.projet.foodtruck;

import java.util.Scanner;

import ca.qc.cmaisonneuve.amp.projet.foodtruck.notification.EmailOrderEventListener;
import ca.qc.cmaisonneuve.amp.projet.foodtruck.notification.OrderEventPublisher;
import ca.qc.cmaisonneuve.amp.projet.foodtruck.notification.SmsOrderEventListener;

public class App {

    public static void main(String[] args) {
        OrderEventPublisher publisher = new OrderEventPublisher();
        FoodTruckManager manager = new FoodTruckManager(publisher);
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== SOLIDEMENT Bon - Commandes CLI ===");
        System.out.println("Saisissez vos commandes (BURGER, TACO, WRAP, SALADE). Tapez QUIT pour terminer.");
        System.out.println("Options: extraFromage (o/n), epice (o/n), typePaiement (CARTE/COMPTANT/VIREMENT), notification (EMAIL/SMS)");

        boolean running = true;
        while (running) {
            System.out.print("Type de plat (BURGER/TACO/WRAP/SALADE) ou QUIT: ");
            String itemType = scanner.nextLine();
            if (itemType == null) {
                itemType = "";
            }
            itemType = itemType.trim().toUpperCase();

            if ("QUIT".equals(itemType)) {
                running = false;
                break;
            }
            if (!("BURGER".equals(itemType) || "TACO".equals(itemType) || "WRAP".equals(itemType)
                    || "SALADE".equals(itemType))) {
                System.out.println("Type invalide. Veuillez saisir BURGER, TACO, WRAP ou SALADE.");
                continue;
            }

            System.out.print("Type de notification (EMAIL/SMS): ");
            String typeNotification = scanner.nextLine();
            if (typeNotification == null) {
                typeNotification = "";
            }
            typeNotification = typeNotification.trim().toUpperCase();
            if (!("EMAIL".equals(typeNotification) || "SMS".equals(typeNotification))) {
                System.out.println("Type de notification non supporte: utilisation par defaut de EMAIL.");
                typeNotification = "EMAIL";
            }

            if ("SMS".equals(typeNotification)) {
                publisher.addListener(new SmsOrderEventListener());
            } else {
                publisher.addListener(new EmailOrderEventListener());
            }

            String destinatairePrompt = "SMS".equals(typeNotification)
                    ? "Numero de telephone du client (pour SMS): "
                    : "Adresse courriel du client: ";
            System.out.print(destinatairePrompt);
            String destinataire = scanner.nextLine();
            if (destinataire == null) {
                destinataire = "";
            }
            destinataire = destinataire.trim();

            boolean extraCheese = askYesNo(scanner, "Extra fromage (o/n): ");
            boolean spicy = askYesNo(scanner, "Epice (o/n): ");

            String tailleSalade = null;
            String vinaigretteSalade = null;
            if ("SALADE".equals(itemType)) {
                System.out.print("Taille de la salade (PETITE/GRANDE): ");
                tailleSalade = scanner.nextLine();
                if (tailleSalade == null) {
                    tailleSalade = "";
                }
                tailleSalade = tailleSalade.trim().toUpperCase();
                if (!("PETITE".equals(tailleSalade) || "GRANDE".equals(tailleSalade))) {
                    System.out.println("Taille non supportee: utilisation par defaut PETITE.");
                    tailleSalade = "PETITE";
                }

                System.out.print("Vinaigrette (CESAR/MAISON): ");
                vinaigretteSalade = scanner.nextLine();
                if (vinaigretteSalade == null) {
                    vinaigretteSalade = "";
                }
                vinaigretteSalade = vinaigretteSalade.trim().toUpperCase();
                if (!("CESAR".equals(vinaigretteSalade) || "MAISON".equals(vinaigretteSalade))) {
                    System.out.println("Vinaigrette non supportee: utilisation par defaut MAISON.");
                    vinaigretteSalade = "MAISON";
                }
            }

            System.out.print("Type de paiement (CARTE/COMPTANT/VIREMENT): ");
            String typePaiement = scanner.nextLine();
            if (typePaiement == null) {
                typePaiement = "";
            }
            typePaiement = typePaiement.trim().toUpperCase();
            if (!("CARTE".equals(typePaiement) || "COMPTANT".equals(typePaiement) || "VIREMENT".equals(typePaiement))) {
                System.out.println("Type de paiement non supporte: utilisation par defaut d'argent COMPTANT.");
                typePaiement = "COMPTANT";
            }

                manager.traiterCommande(destinataire, itemType, extraCheese, spicy, typePaiement, tailleSalade,
                    vinaigretteSalade);

            // Pour l'application CLI, on s'assure de ne pas accumuler de listeners entre commandes.
            // (Chaque commande choisit son propre canal.)
            publisher = new OrderEventPublisher();
            manager = new FoodTruckManager(publisher);

            System.out.println("Commande traitee. Voulez-vous passer une autre commande ? (o/n)");
            boolean again = askYesNo(scanner, "> ");
            if (!again) {
                running = false;
            }
        }

        System.out.println("Au revoir !");
        scanner.close();
    }

    private static boolean askYesNo(Scanner scanner, String prompt) {
        System.out.print(prompt);
        String answer = scanner.nextLine();
        if (answer == null) {
            answer = "";
        }
        answer = answer.trim().toLowerCase();
        if ("y".equals(answer) || "yes".equals(answer) || "o".equals(answer) || "oui".equals(answer)) {
            return true;
        }
        return false;
    }
}
