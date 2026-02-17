package ca.qc.cmaisonneuve.amp.projet.foodtruck.cuisine.preparation;

import ca.qc.cmaisonneuve.amp.projet.foodtruck.menu.Plat;

@FunctionalInterface
public interface PreparationStrategy {

    boolean prepare(Plat plat);
}
