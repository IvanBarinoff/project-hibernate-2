package enums;

public enum Rating {
    G("G"),
    PG("PG"),
    PG13("PG-13"),
    R("R"),
    NC17("NC-17");

    private final String name;

    Rating(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }

    public static Rating ratingByName(String name) {
        switch (name) {
            case "G":
                return G;
            case "PG":
                return PG;
            case "PG-13":
                return PG13;
            case "R":
                return R;
            case "NC-17":
                return NC17;
            default: return null;
        }
    }
}
