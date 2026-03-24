package net.ictcampus.baemtli.security;

/**
 * Zentrales Verzeichnis aller Berechtigungen (Authorities) im System.
 * Verhindert Tippfehler (Magic Strings) und ermöglicht Autocomplete in der IDE.
 */
public class Permission {

    // --- Trainee ---
    public static final String TRAINEE_WRITE_ALL = "trainee:write:all";
    public static final String TRAINEE_READ_ALL = "trainee:read:all";
    public static final String TRAINEE_READ_TEAM = "trainee:read:team";

    // --- Team ---
    public static final String TEAM_WRITE_ALL = "team:write:all";
    public static final String TEAM_READ_ALL = "team:read:all";

    // --- Chore Category ---
    public static final String CHORE_CATEGORY_WRITE_ALL = "chore-category:write:all";
    public static final String CHORE_CATEGORY_READ_ALL = "chore-category:read:all";

    // --- Month Assignment ---
    public static final String MONTH_ASSIGNMENT_WRITE_ALL = "month-assignment:write:all";
    public static final String MONTH_ASSIGNMENT_READ_ALL = "month-assignment:read:all";

    // --- Day Assignment (Tagesplanung) ---
    public static final String DAY_ASSIGNMENT_WRITE_TEAM = "day-assignment:write:team";
    public static final String DAY_ASSIGNMENT_READ_TEAM = "day-assignment:read:team";
    public static final String DAY_ASSIGNMENT_READ_ALL = "day-assignment:read:all";

    // Konstruktor, damit die Klasse nicht instanziiert wird
    private Permission() {}
}

