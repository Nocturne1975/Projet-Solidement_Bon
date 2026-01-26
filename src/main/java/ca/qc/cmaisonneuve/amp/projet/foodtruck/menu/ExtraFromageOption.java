package ca.qc.cmaisonneuve.amp.projet.foodtruck.menu;

public class ExtraFromageOption extends PlatOptionDecorator {

    public ExtraFromageOption(Plat base) {
        super(base);
    }

    @Override
    public String getDescription() {
        return base.getDescription() + " extra fromage";
    }

    @Override
    public double getPrix() {
        return base.getPrix() + base.extraFromagePrix();
    }

    @Override
    public boolean hasExtraFromage() {
        return true;
    }
}
