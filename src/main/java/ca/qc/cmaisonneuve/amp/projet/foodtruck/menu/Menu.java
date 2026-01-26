package ca.qc.cmaisonneuve.amp.projet.foodtruck.menu;

import java.util.HashMap;
import java.util.Map;

/**
 * Registre des plats de base (point d'extension).
 */
public class Menu {

    private final Map<String, Plat> plats = new HashMap<>();

    public Menu() {
        register(new BurgerPlat());
        register(new TacoPlat());
        register(new WrapPlat());
        register(new SaladePlat());
    }

    public void register(Plat plat) {
        if (plat == null) {
            return;
        }
        plats.put(plat.getType(), plat);
    }

    public Plat createBase(String type) {
        if (type == null) {
            return null;
        }
        Plat plat = plats.get(type.trim().toUpperCase());
        if (plat == null) {
            return null;
        }

        // Les plats sont stateless; on retourne une nouvelle instance pour éviter toute surprise.
        return switch (plat.getType()) {
            case "BURGER" -> new BurgerPlat();
            case "TACO" -> new TacoPlat();
            case "WRAP" -> new WrapPlat();
            case "SALADE" -> new SaladePlat();
            default -> null;
        };
    }
}
