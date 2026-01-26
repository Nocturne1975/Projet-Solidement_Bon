
package ca.qc.cmaisonneuve.amp.projet.foodtruck;

import ca.qc.cmaisonneuve.amp.projet.foodtruck.cuisine.CuisineCompleteService;
import ca.qc.cmaisonneuve.amp.projet.foodtruck.cuisine.CuisineFroideService;
import ca.qc.cmaisonneuve.amp.projet.foodtruck.cuisine.CuisineFroideStation;
import ca.qc.cmaisonneuve.amp.projet.foodtruck.cuisine.CuisineService;
import ca.qc.cmaisonneuve.amp.projet.foodtruck.menu.EpiceOption;
import ca.qc.cmaisonneuve.amp.projet.foodtruck.menu.ExtraFromageOption;
import ca.qc.cmaisonneuve.amp.projet.foodtruck.menu.Menu;
import ca.qc.cmaisonneuve.amp.projet.foodtruck.menu.Plat;
import ca.qc.cmaisonneuve.amp.projet.foodtruck.menu.SaladeTaille;
import ca.qc.cmaisonneuve.amp.projet.foodtruck.menu.SaladeTailleOption;
import ca.qc.cmaisonneuve.amp.projet.foodtruck.menu.SaladeVinaigrette;
import ca.qc.cmaisonneuve.amp.projet.foodtruck.menu.SaladeVinaigretteOption;
import ca.qc.cmaisonneuve.amp.projet.foodtruck.notification.OrderEvent;
import ca.qc.cmaisonneuve.amp.projet.foodtruck.notification.OrderEventPublisher;
import ca.qc.cmaisonneuve.amp.projet.foodtruck.notification.OrderEventType;
import ca.qc.cmaisonneuve.amp.projet.foodtruck.paiement.PaymentGateway;
import ca.qc.cmaisonneuve.amp.projet.foodtruck.persistence.SqlDatabasePersistence;

/**
 * Gestionnaire du Food Truck SOLIDEMENT Bon
 */
public class FoodTruckManager {

    private PaymentGateway paymentGateway = new PaymentGateway();
    private SqlDatabasePersistence database = new SqlDatabasePersistence();

    private CuisineService cuisine = new CuisineCompleteService();
    private CuisineFroideStation comptoirFroid = new CuisineFroideService();

    private final Menu menu = new Menu();

    private final OrderEventPublisher eventPublisher;

    public FoodTruckManager() {
        this(new OrderEventPublisher());
    }

    public FoodTruckManager(OrderEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher != null ? eventPublisher : new OrderEventPublisher();
    }

    public void traiterCommande(String email, String type, boolean extraFromage, boolean epice, String modePaiement) {
        traiterCommande(email, type, extraFromage, epice, modePaiement, null, null);
    }

    public void traiterCommande(String destinataire,
            String type,
            boolean extraFromage,
            boolean epice,
            String modePaiement,
            String tailleSalade,
            String vinaigretteSalade) {

        Plat plat = buildPlat(type, extraFromage, epice, tailleSalade, vinaigretteSalade);
        if (plat == null) {
            System.out.println("Type de plat inconnu: " + type);
            return;
        }

        double prix = plat.getPrix();

        // Paiement
        paymentGateway.payer(modePaiement, prix);

        // Sauvegarde de la commande en base de données
        database.sauvegarderCommande(destinataire, plat.getType(), prix, extraFromage, epice);

        // Notification (Observer): commande placée
        eventPublisher.publish(new OrderEvent(OrderEventType.PLACED, destinataire, plat.getDescription(), prix));

        // Envoi en cuisine
        PreparationResult result = prepare(plat);
        System.out.println("[Journal] Commande " + result.statusMessage());

        // Notification (Observer): commande prête
        if (result.success()) {
            eventPublisher.publish(new OrderEvent(OrderEventType.READY, destinataire, plat.getDescription(), prix));
        }
    }

    private PreparationResult prepare(Plat plat) {
        return switch (plat.getType()) {
            case "BURGER" -> prepareBurger(plat);
            case "TACO" -> prepareTaco(plat);
            case "WRAP" -> prepareWrap(plat);
            case "SALADE" -> prepareSalade(plat);
            default -> new PreparationResult(false, "annulee");
        };
    }

    private PreparationResult prepareBurger(Plat plat) {
        boolean success = cuisine.cuire("BURGER")
                && cuisine.assembler("BURGER")
            && cuisine.ajouterExtras("BURGER", plat.hasExtraFromage(), plat.isEpice())
                && cuisine.garderAuChaud("BURGER");

        String status = "%s (%s)".formatted(success ? "prete" : "annulee", plat.getDescription());
        return new PreparationResult(success, status);
    }

    private PreparationResult prepareTaco(Plat plat) {
        boolean success = cuisine.cuire("TACO")
                && cuisine.assembler("TACO")
            && cuisine.ajouterExtras("TACO", plat.hasExtraFromage(), plat.isEpice())
                && cuisine.garderAuChaud("TACO");
        String status = "%s (%s)".formatted(success ? "prete" : "annulee", plat.getDescription());
        return new PreparationResult(success, status);
    }

    private PreparationResult prepareWrap(Plat plat) {
        boolean success = comptoirFroid.assembler("WRAP")
            && comptoirFroid.ajouterExtras("WRAP", plat.hasExtraFromage(), plat.isEpice())
                && comptoirFroid.garderAuFrais("WRAP");
        String status = "%s (%s)".formatted(success ? "prete" : "annulee", plat.getDescription());
        return new PreparationResult(success, status);
    }

    private PreparationResult prepareSalade(Plat plat) {
        boolean success = comptoirFroid.assembler("SALADE")
            && comptoirFroid.ajouterExtras("SALADE", plat.hasExtraFromage(), plat.isEpice())
                && comptoirFroid.garderAuFrais("SALADE");
        String status = "%s (%s)".formatted(success ? "prete" : "annulee", plat.getDescription());
        return new PreparationResult(success, status);
    }

    private Plat buildPlat(String type,
            boolean extraFromage,
            boolean epice,
            String tailleSalade,
            String vinaigretteSalade) {
        Plat plat = menu.createBase(type);
        if (plat == null) {
            return null;
        }

        if ("SALADE".equals(plat.getType())) {
            SaladeTaille taille = SaladeTaille.parse(tailleSalade);
            SaladeVinaigrette vinaigrette = SaladeVinaigrette.parse(vinaigretteSalade);
            plat = new SaladeTailleOption(plat, taille);
            plat = new SaladeVinaigretteOption(plat, vinaigrette);
        }

        if (extraFromage) {
            plat = new ExtraFromageOption(plat);
        }
        if (epice) {
            plat = new EpiceOption(plat);
        }

        return plat;
    }

    private static final class PreparationResult {
        private final boolean success;
        private final String statusMessage;

        private PreparationResult(boolean success, String statusMessage) {
            this.success = success;
            this.statusMessage = statusMessage;
        }

        public boolean success() {
            return success;
        }

        public String statusMessage() {
            return statusMessage;
        }
    }

}
