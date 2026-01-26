package ca.qc.cmaisonneuve.amp.projet.foodtruck.menu;

public class SaladeVinaigretteOption extends PlatOptionDecorator {

    private final SaladeVinaigrette vinaigrette;

    public SaladeVinaigretteOption(Plat base, SaladeVinaigrette vinaigrette) {
        super(base);
        this.vinaigrette = vinaigrette == null ? SaladeVinaigrette.MAISON : vinaigrette;
    }

    @Override
    public String getDescription() {
        String v = vinaigrette == SaladeVinaigrette.CESAR ? "cesar" : "maison";
        return base.getDescription() + " vinaigrette " + v;
    }

    @Override
    public double getPrix() {
        // Pas de supplément (modifiable facilement si souhaité).
        return base.getPrix();
    }
}
