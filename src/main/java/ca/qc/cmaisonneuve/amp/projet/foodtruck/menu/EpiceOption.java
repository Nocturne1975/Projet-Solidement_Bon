package ca.qc.cmaisonneuve.amp.projet.foodtruck.menu;

public class EpiceOption extends PlatOptionDecorator {

    public EpiceOption(Plat base) {
        super(base);
    }

    @Override
    public String getDescription() {
        return base.getDescription() + " epice";
    }

    @Override
    public double getPrix() {
        return base.getPrix() + base.epicePrix();
    }

    @Override
    public boolean isEpice() {
        return true;
    }
}
