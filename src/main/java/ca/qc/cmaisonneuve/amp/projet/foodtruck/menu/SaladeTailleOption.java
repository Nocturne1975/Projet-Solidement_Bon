package ca.qc.cmaisonneuve.amp.projet.foodtruck.menu;

public class SaladeTailleOption extends PlatOptionDecorator {

    private final SaladeTaille taille;

    public SaladeTailleOption(Plat base, SaladeTaille taille) {
        super(base);
        this.taille = taille == null ? SaladeTaille.PETITE : taille;
    }

    @Override
    public String getDescription() {
        return base.getDescription() + " " + (taille == SaladeTaille.GRANDE ? "grande" : "petite");
    }

    @Override
    public double getPrix() {
        // Supplément pour grande.
        return base.getPrix() + (taille == SaladeTaille.GRANDE ? 1.5 : 0.0);
    }
}
