package admin_user.model;

public enum UserRole {
    ADMIN("ADMIN"),
    TUTEUR("TUTEUR"),
    ETUDIANT("ETUDIANT");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
