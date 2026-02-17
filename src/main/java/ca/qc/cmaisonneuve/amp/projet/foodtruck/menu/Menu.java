package ca.qc.cmaisonneuve.amp.projet.foodtruck.menu;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Registre des plats de base (point d'extension).
 */
public class Menu {

    private final Map<String, Supplier<? extends Plat>> plats = new HashMap<>();

    public Menu() {
        register("BURGER", BurgerPlat::new);
        register("TACO", TacoPlat::new);
        register("WRAP", WrapPlat::new);
        register("SALADE", SaladePlat::new);
    }

    public final void register(String type, Supplier<? extends Plat> factory) {
        if (type == null || factory == null) {
            return;
        }
        plats.put(type.trim().toUpperCase(), factory);
    }

    public Plat createBase(String type) {
        if (type == null) {
            return null;
        }
        Supplier<? extends Plat> factory = plats.get(type.trim().toUpperCase());
        if (factory == null) {
            return null;
        }

        // Les plats sont stateless; on retourne une nouvelle instance pour éviter toute surprise.
        return factory.get();
    }
}
