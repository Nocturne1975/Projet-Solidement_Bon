package ca.qc.cmaisonneuve.amp.projet.foodtruck.cuisine.preparation;

import ca.qc.cmaisonneuve.amp.projet.foodtruck.cuisine.CuisineService;
import ca.qc.cmaisonneuve.amp.projet.foodtruck.menu.Plat;

public class TacoPreparation implements PreparationStrategy {

    private final CuisineService cuisine;

    public TacoPreparation(CuisineService cuisine) {
        this.cuisine = cuisine;
    }

    @Override
    public boolean prepare(Plat plat) {
        return cuisine.cuire("TACO")
                && cuisine.assembler("TACO")
                && cuisine.ajouterExtras("TACO", plat.hasExtraFromage(), plat.isEpice())
                && cuisine.garderAuChaud("TACO");
    }
}
