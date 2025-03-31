package bookkeeper.model;

public enum Condition {
    GOOD, FAIR, POOR;

    public static Condition fromString(String condition) {
        switch (condition.toLowerCase()) {
        case "good":
            return GOOD;
        case "fair":
            return FAIR;
        case "poor":
            return POOR;
        default:
            throw new IllegalArgumentException("Invalid condition: " + condition +
                    "\nValid conditions are: good, fair, poor");
        }
    }
}
