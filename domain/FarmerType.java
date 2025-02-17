/**
 The FarmerType class is an enumeration of the registration statuses of the farmer.
 */
public enum FarmerType {
    FARMER(5, 200),
    REGISTERED_FARMER(10, 300),
    DISTINGUISHED_FARMER(15, 400),
    LEGENDARY_FARMER(2147483647, 2147483647);

    private final int minLevelToLevelUp;
    private final int levelUpCost;

    FarmerType(int minLevelToLevelUp, int levelUpCost) {
        this.minLevelToLevelUp = minLevelToLevelUp;
        this.levelUpCost = levelUpCost;
    }

    public boolean canLevelUp(int level) {
        return this.minLevelToLevelUp <= level;
    }

    public int getLevelUpCost() {
        return levelUpCost;
    }

    public FarmerType getNextLevel(FarmerType farmerType) {
        switch (farmerType) {
            case FARMER -> {return REGISTERED_FARMER;}
            case REGISTERED_FARMER -> {return DISTINGUISHED_FARMER;}
            case DISTINGUISHED_FARMER -> {return LEGENDARY_FARMER;}
            default -> {return null;}
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case FARMER -> {return "Farmer";}
            case REGISTERED_FARMER -> {return "Registered Farmer";}
            case DISTINGUISHED_FARMER -> {return "Distinguished Farmer";}
            case LEGENDARY_FARMER -> {return "Legendary Farmer";}
            default -> {return "";}
        }
    }
}