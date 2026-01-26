
package ca.qc.cmaisonneuve.amp.projet.foodtruck.cuisine;

/**
 * Représente une cuisine complète (fours, friteuses, éléments chauffants).
 *
 * ISP: cette classe expose uniquement des capacités via des interfaces fines
 * (cuisson/assemblage/extras/conservation).
 */
public class CuisineCompleteService implements CuisineService {

    @Override
    public boolean cuire(String itemType) {
        System.out.println("Cuisine: plat %s cuit".formatted(itemType));
        return true;
    }

    @Override
    public boolean assembler(String itemType) {
        System.out.println("Cuisine: plat %s assemble".formatted(itemType));
        return true;
    }

    @Override
    public boolean garderAuFrais(String itemType) {
        System.out.println("Cuisine: plat %s mis au refrigerateur".formatted(itemType));
        return true;
    }

    @Override
    public boolean garderAuChaud(String itemType) {
        System.out.println("Cuisine: plat %s mis sur le rechaud".formatted(itemType));
        return true;
    }

    @Override
    public boolean ajouterExtras(String itemType, boolean extraFromage, boolean epice) {
        System.out.println("Cuisine: ajout d'extras pour le plat %s: epice (%s), fromage (%s)".formatted(itemType,
                epice ? "o" : "n", extraFromage ? "o" : "n"));
        return true;
    }
}
