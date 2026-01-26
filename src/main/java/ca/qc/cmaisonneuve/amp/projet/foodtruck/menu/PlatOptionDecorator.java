package ca.qc.cmaisonneuve.amp.projet.foodtruck.menu;

/**
 * Base pour le patron Decorator: une option wrappe un Plat.
 */
public abstract class PlatOptionDecorator implements Plat {

    protected final Plat base;

    protected PlatOptionDecorator(Plat base) {
        this.base = base;
    }

    @Override
    public String getType() {
        return base.getType();
    }

    @Override
    public double extraFromagePrix() {
        return base.extraFromagePrix();
    }

    @Override
    public double epicePrix() {
        return base.epicePrix();
    }

    @Override
    public boolean hasExtraFromage() {
        return base.hasExtraFromage();
    }

    @Override
    public boolean isEpice() {
        return base.isEpice();
    }
}
