package ca.qc.cmaisonneuve.amp.projet.foodtruck.cuisine.preparation;

import ca.qc.cmaisonneuve.amp.projet.foodtruck.cuisine.CuisineService;
import ca.qc.cmaisonneuve.amp.projet.foodtruck.menu.Plat;

public class BurgerPreparation implements PreparationStrategy {

    private final CuisineService cuisine;

    public BurgerPreparation(CuisineService cuisine) {
        this.cuisine = cuisine;
    }

    @Override
    public boolean prepare(Plat plat) {
        return cuisine.cuire("BURGER")
                && cuisine.assembler("BURGER")
                && cuisine.ajouterExtras("BURGER", plat.hasExtraFromage(), plat.isEpice())
                && cuisine.garderAuChaud("BURGER");
    }
}
