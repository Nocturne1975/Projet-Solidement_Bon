package ca.qc.cmaisonneuve.amp.projet.foodtruck.menu;

public enum SaladeTaille {
    PETITE,
    GRANDE;

    public static SaladeTaille parse(String value) {
        if (value == null) {
            return PETITE;
        }
        String v = value.trim().toUpperCase();
        return switch (v) {
            case "GRANDE" -> GRANDE;
            case "PETITE" -> PETITE;
            default -> PETITE;
        };
    }
}
