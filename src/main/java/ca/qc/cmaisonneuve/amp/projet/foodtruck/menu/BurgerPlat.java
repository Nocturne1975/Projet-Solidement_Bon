package ca.qc.cmaisonneuve.amp.projet.foodtruck.menu;

public class BurgerPlat implements Plat {

    @Override
    public String getType() {
        return "BURGER";
    }

    @Override
    public String getDescription() {
        return "burger";
    }

    @Override
    public double getPrix() {
        return 8.0;
    }

    @Override
    public double extraFromagePrix() {
        return 1.0;
    }

    @Override
    public double epicePrix() {
        return 0.5;
    }
}
