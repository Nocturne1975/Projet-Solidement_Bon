package ca.qc.cmaisonneuve.amp.projet.foodtruck.menu;

public class WrapPlat implements Plat {

    @Override
    public String getType() {
        return "WRAP";
    }

    @Override
    public String getDescription() {
        return "wrap";
    }

    @Override
    public double getPrix() {
        return 6.5;
    }

    @Override
    public double extraFromagePrix() {
        return 0.8;
    }

    @Override
    public double epicePrix() {
        // historiquement, WRAP n'avait pas l'option epice
        return 0.0;
    }
}
