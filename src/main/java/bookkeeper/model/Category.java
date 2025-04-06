package bookkeeper.model;

public enum Category {
    ROMANCE, ADVENTURE, ACTION, HORROR, MYSTERY, FICTION, NONFICTION, SCIFI, EDUCATION;

    public static Category fromString(String category) {
        switch (category.toLowerCase()) {
        case "romance":
            return ROMANCE;
        case "adventure":
            return ADVENTURE;
        case "action":
            return ACTION;
        case "horror":
            return HORROR;
        case "mystery":
            return MYSTERY;
        case "fiction":
            return FICTION;
        case "non-fiction":
        case "nonfiction":
            return NONFICTION;
        case "scifi":
            return SCIFI;
        case "education":
            return EDUCATION;
        default:
            throw new IllegalArgumentException("Invalid category: " + category + "\nValid categories are: " +
                    "romance, adventure, action, horror, mystery, fiction, nonfiction, scifi, education");
        }
    }
}
