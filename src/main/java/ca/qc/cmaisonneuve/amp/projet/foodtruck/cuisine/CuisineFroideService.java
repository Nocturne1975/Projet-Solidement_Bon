
package ca.qc.cmaisonneuve.amp.projet.foodtruck.cuisine;

/**
 * Représente une station froide (pas de cuisson ni de maintien au chaud).
 *
 * ISP/LSP: la station froide n'expose pas les capacités qu'elle ne supporte pas.
 */
public class CuisineFroideService implements CuisineFroideStation {

    @Override
    public boolean assembler(String itemType) {
        System.out
                .println("Cuisine froide: plat %s assemble".formatted(itemType));
        return true;
    }

    @Override
    public boolean garderAuFrais(String itemType) {
        System.out.println("Cuisine froide: plat %s mis dans une glaciere".formatted(itemType));
        return true;
    }

    @Override
    public boolean ajouterExtras(String itemType, boolean extraFromage, boolean epice) {
        System.out.println("Cuisine froide: ajout d'extras pour le plat %s: epice (%s), fromage (%s)"
                .formatted(itemType, epice ? "o" : "n", extraFromage ? "o" : "n"));
        return true;
    }
}
