
package ca.qc.cmaisonneuve.amp.projet.foodtruck.cuisine;

/**
 * Interface de compatibilité.
 *
 * Historiquement, la cuisine était modélisée via une seule interface “grosse”.
 * Après refactor (ISP), on utilise plutôt des interfaces fines
 * (cuisson/assemblage/extras/conservation).
 *
 * Cette interface compose ces capacités pour les cuisines “complètes”.
 */
public interface CuisineService
        extends CuissonService, AssemblageService, ExtrasService, ConservationFroidService, ConservationChaudService {
}
