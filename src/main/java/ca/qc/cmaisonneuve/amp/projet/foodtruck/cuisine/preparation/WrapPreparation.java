package ca.qc.cmaisonneuve.amp.projet.foodtruck.cuisine.preparation;

import ca.qc.cmaisonneuve.amp.projet.foodtruck.cuisine.CuisineFroideStation;
import ca.qc.cmaisonneuve.amp.projet.foodtruck.menu.Plat;

public class WrapPreparation implements PreparationStrategy {

    private final CuisineFroideStation station;

    public WrapPreparation(CuisineFroideStation station) {
        this.station = station;
    }

    @Override
    public boolean prepare(Plat plat) {
        return station.assembler("WRAP")
                && station.ajouterExtras("WRAP", plat.hasExtraFromage(), plat.isEpice())
                && station.garderAuFrais("WRAP");
    }
}
