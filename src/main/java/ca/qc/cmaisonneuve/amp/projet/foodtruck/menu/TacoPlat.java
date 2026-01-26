package ca.qc.cmaisonneuve.amp.projet.foodtruck.menu;

public class TacoPlat implements Plat {

    @Override
    public String getType() {
        return "TACO";
    }

    @Override
    public String getDescription() {
        return "taco";
    }

    @Override
    public double getPrix() {
        return 7.0;
    }

    @Override
    public double extraFromagePrix() {
        return 0.5;
    }

    @Override
    public double epicePrix() {
        return 0.5;
    }
}
