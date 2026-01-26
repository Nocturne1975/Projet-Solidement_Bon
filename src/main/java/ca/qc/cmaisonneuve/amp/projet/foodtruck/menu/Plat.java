package ca.qc.cmaisonneuve.amp.projet.foodtruck.menu;

/**
 * Représente un plat commandé.
 *
 * Objectif: permettre d'ajouter des options (epice, extraFromage, etc.)
 * sans multiplier les if/else dans le gestionnaire (OCP).
 */
public interface Plat {

    /** Type de base (ex: BURGER, TACO, WRAP, SALADE). */
    String getType();

    /** Description complète incluant les options. */
    String getDescription();

    /** Prix final incluant les options. */
    double getPrix();

    /** Coût de l'option extraFromage pour ce plat (0 si non applicable). */
    double extraFromagePrix();

    /** Coût de l'option epice pour ce plat (0 si non applicable). */
    double epicePrix();

    /** Indique si l'option extraFromage est présente sur ce plat. */
    default boolean hasExtraFromage() {
        return false;
    }

    /** Indique si l'option epice est présente sur ce plat. */
    default boolean isEpice() {
        return false;
    }
}
