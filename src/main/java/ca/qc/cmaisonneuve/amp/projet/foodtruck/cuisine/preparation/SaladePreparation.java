package ca.qc.cmaisonneuve.amp.projet.foodtruck.cuisine.preparation;

import ca.qc.cmaisonneuve.amp.projet.foodtruck.cuisine.CuisineFroideStation;
import ca.qc.cmaisonneuve.amp.projet.foodtruck.menu.Plat;

public class SaladePreparation implements PreparationStrategy {

    private final CuisineFroideStation station;

    public SaladePreparation(CuisineFroideStation station) {
        this.station = station;
    }

    @Override
    public boolean prepare(Plat plat) {
        return station.assembler("SALADE")
                && station.ajouterExtras("SALADE", plat.hasExtraFromage(), plat.isEpice())
                && station.garderAuFrais("SALADE");
    }
}
