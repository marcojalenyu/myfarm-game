/**
 The FarmerType class is an enumeration of the registration statuses of the farmer.
 */
public enum FarmerType {
    FARMER(5, 200, 0, 0, 0, 0),
    REGISTERED_FARMER(10, 300, 1, 1, 0, 0),
    DISTINGUISHED_FARMER(15, 400, 2, 2, 1, 0),
    LEGENDARY_FARMER(2147483647, 2147483647, 4, 3, 2, 1);

    private final int minLevelToLevelUp;
    private final int levelUpCost;
    private final int earnBonus;
    private final int seedCostReduction;
    private final int waterLimitIncrease;
    private final int fertilizerLimitIncrease;

    FarmerType(int minLevelToLevelUp, int levelUpCost, int earnBonus, int seedCostReduction, int waterLimitIncrease, int fertilizerLimitIncrease) {
        this.minLevelToLevelUp = minLevelToLevelUp;
        this.levelUpCost = levelUpCost;
        this.earnBonus = earnBonus;
        this.seedCostReduction = seedCostReduction;
        this.waterLimitIncrease = waterLimitIncrease;
        this.fertilizerLimitIncrease = fertilizerLimitIncrease;
    }

    public boolean canLevelUp(int level) {
        return this.minLevelToLevelUp <= level;
    }

    public int getEarnBonus() {
        return earnBonus;
    }

    public int getFertilizerLimitIncrease() {
        return fertilizerLimitIncrease;
    }

    public int getSeedCostReduction() {
        return seedCostReduction;
    }

    public int getWaterLimitIncrease() {
        return waterLimitIncrease;
    }

    public int getLevelUpCost() {
        return levelUpCost;
    }

    public FarmerType getNextLevel() {
        switch (this) {
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