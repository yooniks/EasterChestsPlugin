package xyz.yooniks.easterchests;

public enum OpenType {

    ONE("jedna skrzynka -1", 9),
    TWO("dwie skrzynki -2", 9 * 2),
    THREE("trzy skrzynki -3", 9 * 3),
    FOUR("cztery skrzynki -4", 9 * 4),
    FIVE("piec skrzynek -5", 9 * 5),
    SIX("szesc skrzynek -6", 9 * 6);

    private final String name;
    private final int slots;

    private OpenType(String name, int slots) {
        this.name = name.toUpperCase();
        this.slots = slots;
    }

    public static OpenType getByID(int id) {
        switch (id) {
            case 1: {
                return OpenType.ONE;
            }
            case 2: {
                return OpenType.TWO;
            }
            case 3: {
                return OpenType.THREE;
            }
            case 4: {
                return OpenType.FOUR;
            }
            case 5: {
                return OpenType.FIVE;
            }
            case 6: {
                return OpenType.SIX;
            }
            default:
                return OpenType.ONE;
        }
    }

    public int getSlots() {
        return this.slots;
    }

    public int getRows() {
        return this.slots / 9;
    }

    public String getName() {
        return name;
    }
}
