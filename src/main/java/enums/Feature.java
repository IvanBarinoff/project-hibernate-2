package enums;

public enum Feature {
    TRAILER("Trailers"),
    COMMENTARIES("Commentaries"),
    DELETED_SCENES("Deleted Scenes"),
    BEHIND_THE_SCENES("Behind the Scenes");

    private final String name;

    Feature(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Feature getFeatureByName(String name) {
        switch (name) {
            case "Trailers":
                return TRAILER;
            case "Commentaries":
                return COMMENTARIES;
            case "Deleted Scenes":
                return DELETED_SCENES;
            case "Behind the Scenes":
                return BEHIND_THE_SCENES;
            default: return null;
        }
    }
}
