package ca.qc.cmaisonneuve.amp.projet.foodtruck.menu;

/**
 * Nouveau plat SALADE.
 *
 * Prix de base: 6.0$ (petite). La taille GRANDE ajoute un supplément via une option.
 */
public class SaladePlat implements Plat {

    @Override
    public String getType() {
        return "SALADE";
    }

    @Override
    public String getDescription() {
        return "salade";
    }

    @Override
    public double getPrix() {
        return 6.0;
    }

    @Override
    public double extraFromagePrix() {
        // On autorise extraFromage pour garder la cohérence du système d'options.
        return 0.8;
    }

    @Override
    public double epicePrix() {
        // Optionnelle (ex: piments). Valeur similaire aux autres plats.
        return 0.5;
    }
}
