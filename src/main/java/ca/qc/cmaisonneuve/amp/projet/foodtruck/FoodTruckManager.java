
package ca.qc.cmaisonneuve.amp.projet.foodtruck;

import java.util.HashMap;
import java.util.Map;

import ca.qc.cmaisonneuve.amp.projet.foodtruck.cuisine.CuisineCompleteService;
import ca.qc.cmaisonneuve.amp.projet.foodtruck.cuisine.CuisineFroideService;
import ca.qc.cmaisonneuve.amp.projet.foodtruck.cuisine.CuisineFroideStation;
import ca.qc.cmaisonneuve.amp.projet.foodtruck.cuisine.CuisineService;
import ca.qc.cmaisonneuve.amp.projet.foodtruck.cuisine.preparation.BurgerPreparation;
import ca.qc.cmaisonneuve.amp.projet.foodtruck.cuisine.preparation.PreparationStrategy;
import ca.qc.cmaisonneuve.amp.projet.foodtruck.cuisine.preparation.SaladePreparation;
import ca.qc.cmaisonneuve.amp.projet.foodtruck.cuisine.preparation.TacoPreparation;
import ca.qc.cmaisonneuve.amp.projet.foodtruck.cuisine.preparation.WrapPreparation;
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
import ca.qc.cmaisonneuve.amp.projet.foodtruck.paiement.PaymentProcessor;
import ca.qc.cmaisonneuve.amp.projet.foodtruck.persistence.OrderPersistence;
import ca.qc.cmaisonneuve.amp.projet.foodtruck.persistence.SqlDatabasePersistence;

/**
 * Gestionnaire du Food Truck SOLIDEMENT Bon
 */
public class FoodTruckManager {

    private final PaymentProcessor paymentProcessor;
    private final OrderPersistence database;

    private final CuisineService cuisine;
    private final CuisineFroideStation comptoirFroid;

    private final Menu menu;

    private final Map<String, PreparationStrategy> preparations = new HashMap<>();

    private final OrderEventPublisher eventPublisher;

    public FoodTruckManager() {
        this(new OrderEventPublisher());
    }

    public FoodTruckManager(OrderEventPublisher eventPublisher) {
        this(eventPublisher,
                new Menu(),
                new PaymentGateway(),
                new SqlDatabasePersistence(),
                new CuisineCompleteService(),
                new CuisineFroideService());
    }

    public FoodTruckManager(OrderEventPublisher eventPublisher,
            Menu menu,
            PaymentProcessor paymentProcessor,
            OrderPersistence database,
            CuisineService cuisine,
            CuisineFroideStation comptoirFroid) {

        this.eventPublisher = eventPublisher != null ? eventPublisher : new OrderEventPublisher();
        this.menu = menu != null ? menu : new Menu();
        this.paymentProcessor = paymentProcessor != null ? paymentProcessor : new PaymentGateway();
        this.database = database != null ? database : new SqlDatabasePersistence();
        this.cuisine = cuisine != null ? cuisine : new CuisineCompleteService();
        this.comptoirFroid = comptoirFroid != null ? comptoirFroid : new CuisineFroideService();

        registerDefaultPreparations();
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
        boolean paiementAccepte = paymentProcessor.payer(modePaiement, prix);
        if (!paiementAccepte) {
            System.out.println("[Journal] Commande annulee: paiement refuse.");
            return;
        }

        // Sauvegarde de la commande en base de données
        database.sauvegarderCommande(destinataire, plat.getType(), prix, extraFromage, epice);

        // Notification (Observer): commande placée
        eventPublisher.publish(new OrderEvent(OrderEventType.PLACED, destinataire, plat.getDescription(), prix));

        // Envoi en cuisine
        boolean success = prepare(plat);
        String status = "%s (%s)".formatted(success ? "prete" : "annulee", plat.getDescription());
        System.out.println("[Journal] Commande " + status);

        // Notification (Observer): commande prête
        if (success) {
            eventPublisher.publish(new OrderEvent(OrderEventType.READY, destinataire, plat.getDescription(), prix));
        }
    }

    public void registerPreparation(String type, PreparationStrategy strategy) {
        if (type == null || strategy == null) {
            return;
        }
        preparations.put(type.trim().toUpperCase(), strategy);
    }

    private void registerDefaultPreparations() {
        registerPreparation("BURGER", new BurgerPreparation(cuisine));
        registerPreparation("TACO", new TacoPreparation(cuisine));
        registerPreparation("WRAP", new WrapPreparation(comptoirFroid));
        registerPreparation("SALADE", new SaladePreparation(comptoirFroid));
    }

    private boolean prepare(Plat plat) {
        if (plat == null) {
            return false;
        }
        PreparationStrategy strategy = preparations.get(plat.getType());
        if (strategy == null) {
            return false;
        }
        return strategy.prepare(plat);
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

}
