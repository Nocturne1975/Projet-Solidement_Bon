package ca.qc.cmaisonneuve.amp.projet.foodtruck.menu;

public enum SaladeVinaigrette {
    CESAR,
    MAISON;

    public static SaladeVinaigrette parse(String value) {
        if (value == null) {
            return MAISON;
        }
        String v = value.trim().toUpperCase();
        return switch (v) {
            case "CESAR" -> CESAR;
            case "MAISON" -> MAISON;
            default -> MAISON;
        };
    }
}
